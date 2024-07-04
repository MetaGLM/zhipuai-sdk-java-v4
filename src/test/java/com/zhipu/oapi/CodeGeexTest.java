package com.zhipu.oapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.model.*;
import com.zhipu.oapi.service.v4.model.params.CodeGeexExtra;
import com.zhipu.oapi.service.v4.model.params.CodeGeexTarget;
import io.reactivex.Flowable;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


@Testcontainers
public class CodeGeexTest {

    private final static Logger logger = LoggerFactory.getLogger(CodeGeexTest.class);
    private static final String API_SECRET_KEY = System.getProperty("ZHIPUAI_API_KEY");

    private static final ClientV4 client = new ClientV4.Builder("https://test.bigmodel.cn/stage-api/paas/v4/",
            "e6a98ef1c54484c2afeac1ae8cef93ef.rlpKehWCGDttN9Pl")
            .networkConfig(300, 100, 100, 100, TimeUnit.SECONDS)
            .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
            .build();
    private static final ObjectMapper mapper = MessageDeserializeFactory.defaultObjectMapper();
    // 请自定义自己的业务id
    private static final String requestIdTemplate = "mycompany-%d";


    @Test
    public void testCodegeex() throws JsonProcessingException {


        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "帮我查询北京天气");
        messages.add(chatMessage);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());

        CodeGeexTarget codeGeexTarget = new CodeGeexTarget();
        codeGeexTarget.setPath("111");
        codeGeexTarget.setLanguage("python");
        codeGeexTarget.setCodePrefix("EventSource.Factory factory = EventSources.createFactory(OkHttpUtils.getInstance());");
        codeGeexTarget.setCodeSuffix("TaskMonitorLocal taskMonitorLocal = getTaskMonitorLocal(algoMqReq);");
        CodeGeexExtra codeGeexExtra = new CodeGeexExtra();
        codeGeexExtra.setContexts(new ArrayList<>());
        codeGeexExtra.setTarget(codeGeexTarget);
        List<String> stop = new ArrayList<>();
        stop.add("<|endoftext|>");
        stop.add("<|user|>");
        stop.add("<|assistant|>");
        stop.add("<|observation|>");

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("codegeex-4")
                .stream(Boolean.FALSE)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .stop(stop)
                .extra(codeGeexExtra)
                .requestId(requestId)
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