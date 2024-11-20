package com.zhipu.oapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhipu.oapi.service.v4.api.AssistantClientService;
import com.zhipu.oapi.service.v4.assistant.*;
import com.zhipu.oapi.service.v4.assistant.conversation.ConversationParameters;
import com.zhipu.oapi.service.v4.assistant.conversation.ConversationUsageListResponse;
import com.zhipu.oapi.service.v4.assistant.conversation.ConversationUsageListStatus;
import com.zhipu.oapi.service.v4.assistant.message.MessageContent;
import com.zhipu.oapi.service.v4.assistant.query_support.AssistantSupportResponse;
import com.zhipu.oapi.service.v4.assistant.query_support.QuerySupportParams;
import com.zhipu.oapi.service.v4.tools.ChoiceDelta;
import com.zhipu.oapi.service.v4.tools.WebSearchPro;
import com.zhipu.oapi.utils.StringUtils;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class TestAssistantClientApiService {
    private final static Logger logger = LoggerFactory.getLogger(TestAssistantClientApiService.class);
    private static final String ZHIPUAI_API_KEY = Constants.getApiKey();
    private static final String ZHIPUAI_BASE_URL = Constants.getBaseUrl();
    private static ClientV4 client = null;

    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        if (StringUtils.isNotEmpty(ZHIPUAI_BASE_URL)) {
            client = new ClientV4.Builder(ZHIPUAI_BASE_URL, ZHIPUAI_API_KEY)
                    .enableTokenCache()
                    .networkConfig(300, 100, 100, 100, TimeUnit.SECONDS)
                    .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
                    .build();
        } else {
            client = new ClientV4.Builder(ZHIPUAI_API_KEY)
                    .enableTokenCache()
                    .networkConfig(300, 100, 100, 100, TimeUnit.SECONDS)
                    .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
                    .build();
        }
    }


    @Test
    @Order(1)
    public void testAssistantCompletionStream() throws JsonProcessingException {


        MessageTextContent textContent = MessageTextContent.builder()
                .text("帮我搜索下智谱的cogvideox发布时间")
                .type("text")
                .build();

        ConversationMessage messages = ConversationMessage.builder()
                .role("user")
                .content(Collections.singletonList(textContent))
                .build();

        AssistantParameters build = AssistantParameters.builder()
                .assistantId("659e54b1b8006379b4b2abd6")
                .model("glm-4-assistant")
                .stream(true)
                .messages(Collections.singletonList(messages))
                .build();
        // 设置params的相关属性
        AssistantApiResponse apply = new AssistantClientService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .assistantCompletionStream(build)
                .apply(client);
        if (apply.isSuccess()) {
            AtomicBoolean isFirst = new AtomicBoolean(true);
            List<MessageContent> choices = new ArrayList<>();
            AtomicReference<AssistantCompletion> lastAccumulator = new AtomicReference<>();

            apply.getFlowable().map(result -> result)
                    .doOnNext(accumulator -> {
                        {
                            if (isFirst.getAndSet(false)) {
                                logger.info("Response: ");
                            }
                            MessageContent delta = accumulator.getChoices().get(0).getDelta();
                            logger.info("MessageContent: {}", mapper.writeValueAsString(delta));
                            choices.add(delta);
                            lastAccumulator.set(accumulator);

                        }
                    })
                    .doOnComplete(() -> System.out.println("Stream completed."))
                    .doOnError(throwable -> System.err.println("Error: " + throwable)) // Handle errors
                    .blockingSubscribe();// Use blockingSubscribe instead of blockingGet()

            AssistantCompletion assistantCompletion = lastAccumulator.get();

            apply.setFlowable(null);// 打印前置空
            apply.setData(assistantCompletion);
        }
        logger.info("apply output: {}", mapper.writeValueAsString(apply));


    }
    @Test
    @Order(2)
    public void testQuerySupport(){
        QuerySupportParams build = QuerySupportParams.builder()
                .assistantIdList(Collections.singletonList("659e54b1b8006379b4b2abd6"))
                .build();
        AssistantSupportResponse apply = new AssistantClientService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .querySupport(build)
                .apply(client);
        logger.info("apply output: {}", apply);
    }

    @Test
    @Order(3)
    public void testQueryConversationUsage(){
        ConversationParameters build = ConversationParameters.builder()
                .assistantId("659e54b1b8006379b4b2abd6")
                .build();
        ConversationUsageListResponse apply = new AssistantClientService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .queryConversationUsage(build)
                .apply(client);
        logger.info("apply output: {}", apply);
    }
}
