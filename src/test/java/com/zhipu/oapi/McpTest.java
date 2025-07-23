package com.zhipu.oapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhipu.oapi.service.v4.model.*;
import com.zhipu.oapi.utils.StringUtils;
import io.reactivex.Flowable;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Testcontainers
public class McpTest {

    private final static Logger logger = LoggerFactory.getLogger(TestAssistantClientApiService.class);
    private static final String ZHIPUAI_API_KEY = getTestApiKey();

    private static ClientV4 client = null;

    private static final String requestIdTemplate = "mycompany-%d";

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        client = new ClientV4.Builder(ZHIPUAI_API_KEY)
                .enableTokenCache()
                .networkConfig(300, 100, 100, 100, TimeUnit.SECONDS)
                .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
                .build();
    }

    private static String getTestApiKey() {
        String apiKey = Constants.getApiKey();
        return apiKey != null ? apiKey : "test-api-key.test-api-secret";
    }

    @Test
    void testMcpTool_ServerUrl_SSE() throws JsonProcessingException {
        // MCP 参数构建部分
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer" + ZHIPUAI_API_KEY);
        MCPTool mcpTool = new MCPTool();
        mcpTool.setServer_label("sougou_search");
        mcpTool.setServer_url("https://open.bigmodel.cn/api/mcp/sogou/sse");
        mcpTool.setTransport_type(McpToolTransportType.SSE.getCode());
        mcpTool.setHeaders(headers);

        ChatTool chatTool = new ChatTool();
        chatTool.setType(ChatToolType.MCP.value());
        chatTool.setMcp(mcpTool);

        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "今天是几月几号？");

        messages.add(chatMessage);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.FALSE)
                .messages(messages)
                .requestId(requestId)
                .invokeMethod(Constants.invokeMethod)
                .tools(Collections.singletonList(chatTool))
                .build();
        ModelApiResponse modelApiResp = client.invokeModelApi(chatCompletionRequest);
        logger.info("model output: {}", mapper.writeValueAsString(modelApiResp));
    }

    @Test
    void testMcpTool_ServerLabel() throws JsonProcessingException {
        // MCP 参数构建部分
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + ZHIPUAI_API_KEY);
        MCPTool mcpTool = new MCPTool();
        mcpTool.setServer_label("aviation");
        mcpTool.setHeaders(headers);

        ChatTool chatTool = new ChatTool();
        chatTool.setType(ChatToolType.MCP.value());
        chatTool.setMcp(mcpTool);

        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "北京现在天气怎么样？");

        messages.add(chatMessage);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.TRUE)
                .messages(messages)
                .requestId(requestId)
                .invokeMethod(Constants.invokeMethod)
                .tools(Collections.singletonList(chatTool))
                .build();
        ModelApiResponse sseModelApiResp = client.invokeModelApi(chatCompletionRequest);
        if (sseModelApiResp.isSuccess()) {
            AtomicBoolean isFirst = new AtomicBoolean(true);
            List<Choice> choices = new ArrayList<>();
            AtomicReference<ChatMessageAccumulator> lastAccumulator = new AtomicReference<>();

            mapStreamToAccumulator(sseModelApiResp.getFlowable())
                    .doOnNext(accumulator -> {
                        {
                            if (isFirst.getAndSet(false)) {
                                logger.info("Response: ");
                            }
                            if (accumulator.getDelta() != null && accumulator.getDelta().getTool_calls() != null) {
                                String jsonString = mapper.writeValueAsString(accumulator.getDelta().getTool_calls());
                                logger.info("tool_calls: {}", jsonString);
                            }
                            if (accumulator.getDelta() != null && accumulator.getDelta().getContent() != null) {
                                logger.info(accumulator.getDelta().getContent());
                            }
                            choices.add(accumulator.getChoice());
                            lastAccumulator.set(accumulator);

                        }
                    })
                    .doOnComplete(() -> System.out.println("Stream completed."))
                    .doOnError(throwable -> System.err.println("Error: " + throwable)) // Handle errors
                    .blockingSubscribe();// Use blockingSubscribe instead of blockingGet()

            ChatMessageAccumulator chatMessageAccumulator = lastAccumulator.get();
            ModelData data = new ModelData();
            data.setChoices(choices);
            if (chatMessageAccumulator != null) {
                data.setUsage(chatMessageAccumulator.getUsage());
                data.setId(chatMessageAccumulator.getId());
                data.setCreated(chatMessageAccumulator.getCreated());
            }
            data.setRequestId(chatCompletionRequest.getRequestId());
            sseModelApiResp.setFlowable(null);// 打印前置空
            sseModelApiResp.setData(data);
        }
        logger.info("model output: {}", mapper.writeValueAsString(sseModelApiResp));
        client.getConfig().getHttpClient().dispatcher().executorService().shutdown();

        client.getConfig().getHttpClient().connectionPool().evictAll();
        // List all active threads
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            logger.info("Thread: " + t.getName() + " State: " + t.getState());
        }
    }

    public static Flowable<ChatMessageAccumulator> mapStreamToAccumulator(Flowable<ModelData> flowable) {
        return flowable.map(chunk -> {
            return new ChatMessageAccumulator(chunk.getChoices().get(0).getDelta(), null, chunk.getChoices().get(0), chunk.getUsage(), chunk.getCreated(), chunk.getId());
        });
    }
}
