package com.zhipu.oapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhipu.oapi.mock.MockClientV4;
import com.zhipu.oapi.service.v4.model.*;
import com.zhipu.oapi.service.v4.web_search.WebSearchRequest;
import com.zhipu.oapi.service.v4.web_search.WebSearchResponse;
import com.zhipu.oapi.utils.StringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Testcontainers
public class WebSearchTest {

    private final static Logger logger = LoggerFactory.getLogger(TestAssistantClientApiService.class);
    private static final String ZHIPUAI_API_KEY = getTestApiKey();
    private static final String ZHIPUAI_BASE_URL = Constants.getBaseUrl();

    private static ClientV4 client = null;

    private static final String requestIdTemplate = "mycompany-%d";

    private static final ObjectMapper mapper = new ObjectMapper();
    
    private static String getTestApiKey() {
        String apiKey = Constants.getApiKey();
        return apiKey != null ? apiKey : "test-api-key.test-api-secret";
    }
    
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

    /**
     * Web search tool capability test
     */
    @Test
    public void testWebSearch() throws JsonProcessingException {
        // Check if using test API key, skip real API call if so
        if (ZHIPUAI_API_KEY.contains("test-api-key")) {
            logger.info("Using test API key, skipping real API call, using mock data");
            
            WebSearchRequest webSearchRequest = WebSearchRequest.builder()
                    .searchEngine("search_std")
                    .searchQuery("How is the weather in Beijing today")
                    .searchEngine("search_std")
                    .count(50)
                    .searchDomainFilter("finance.sina.com.cn")
                    .searchRecencyFilter("oneYear")
                    .contentSize("high")
                    .requestId("11111111")
                    .build();

            // Use mock data
            WebSearchResponse webSearchResponse = MockClientV4.mockWebSearch(webSearchRequest);
            logger.info("Mock webSearch response: {}", mapper.writeValueAsString(webSearchResponse));
            return;
        }

        WebSearchRequest webSearchRequest = WebSearchRequest.builder()
                .searchEngine("search_std")
                .searchQuery("How is the weather in Beijing today")
                .searchEngine("search_std")
                .count(50)
                .searchDomainFilter("finance.sina.com.cn")
                .searchRecencyFilter("oneYear")
                .contentSize("high")
                .requestId("11111111")
                .build();

        WebSearchResponse webSearchResponse = client.invokeWebSearch(webSearchRequest);
        logger.info("webSearch output: {}", mapper.writeValueAsString(webSearchResponse));
    }

    /**
     * Large model + web_search tool capability test
     */
    @Test
    public void testV4ChatWithWebSearch() throws JsonProcessingException {
        // Check if using test API key, skip real API call if so
        if (ZHIPUAI_API_KEY.contains("test-api-key")) {
            logger.info("Using test API key, skipping real API call, using mock data");
            
            List<ChatMessage> messages = new ArrayList<>();
            ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "How much tariff did Trump impose on China in 2025");
            messages.add(chatMessage);

            ChatTool webSearchTool = new ChatTool();
            webSearchTool.setType("web_search");
            WebSearch webSearch = new WebSearch();
            webSearch.setEnable(Boolean.TRUE);
            webSearch.setSearch_engine("search_std");
            webSearch.setSearch_result(Boolean.TRUE);
            webSearchTool.setWeb_search(webSearch);

            String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                    .model(Constants.ModelChatGLM4)
                    .stream(Boolean.FALSE)
                    .messages(messages)
                    .requestId(requestId)
                    .invokeMethod(Constants.invokeMethod)
                    .tools(Collections.singletonList(webSearchTool))
                    .build();
            
            // Use mock data
            ModelApiResponse modelApiResp = MockClientV4.mockModelApi(chatCompletionRequest);
            logger.info("Mock model response: {}", mapper.writeValueAsString(modelApiResp));
            return;
        }

        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "How much tariff did Trump impose on China in 2025");
        messages.add(chatMessage);

        ChatTool webSearchTool = new ChatTool();
        webSearchTool.setType("web_search");
        WebSearch webSearch = new WebSearch();
        webSearch.setEnable(Boolean.TRUE);
        webSearch.setSearch_engine("search_std");
        webSearch.setSearch_result(Boolean.TRUE);
        webSearchTool.setWeb_search(webSearch);


        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.FALSE)
                .messages(messages)
                .requestId(requestId)
                .invokeMethod(Constants.invokeMethod)
                .tools(Collections.singletonList(webSearchTool))
                .build();
        ModelApiResponse modelApiResp = client.invokeModelApi(chatCompletionRequest);
        logger.info("model output: {}", mapper.writeValueAsString(modelApiResp));
    }

    @Test
    public void testV4ChatWithWebSearchSSE() {
        // Check if using test API key, skip real API call if so
        if (ZHIPUAI_API_KEY.contains("test-api-key")) {
            logger.info("Using test API key, skipping real API call, using mock data");
            
            List<ChatMessage> messages = new ArrayList<>();
            ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "How much tariff did Trump impose on China in 2025");
            messages.add(chatMessage);

            ChatTool webSearchTool = new ChatTool();
            webSearchTool.setType("web_search");
            WebSearch webSearch = new WebSearch();
            webSearch.setEnable(Boolean.TRUE);
            webSearch.setSearch_engine("search_std");
            webSearch.setResult_sequence("before");
            webSearch.setSearch_result(Boolean.TRUE);
            webSearchTool.setWeb_search(webSearch);

            String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
            ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                    .model(Constants.ModelChatGLM4)
                    .stream(Boolean.TRUE)
                    .messages(messages)
                    .requestId(requestId)
                    .tools(Collections.singletonList(webSearchTool))
                    .build();
            
            // Use mock data
            ModelApiResponse sseModelApiResp = MockClientV4.mockModelApi(chatCompletionRequest);
            if (sseModelApiResp.isSuccess()) {
                sseModelApiResp.getFlowable().doOnNext(
                        modelData -> {
                            logger.info("Mock model streaming response: {}", mapper.writeValueAsString(modelData));
                        }
                ).blockingSubscribe();
            }
            return;
        }

        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "How much tariff did Trump impose on China in 2025");
        messages.add(chatMessage);

        ChatTool webSearchTool = new ChatTool();
        webSearchTool.setType("web_search");
        WebSearch webSearch = new WebSearch();
        webSearch.setEnable(Boolean.TRUE);
        webSearch.setSearch_engine("search_std");
        webSearch.setResult_sequence("before");
        webSearch.setSearch_result(Boolean.TRUE);
        webSearchTool.setWeb_search(webSearch);

        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.TRUE)
                .messages(messages)
                .requestId(requestId)
                .tools(Collections.singletonList(webSearchTool))
                .build();
        ModelApiResponse sseModelApiResp = client.invokeModelApi(chatCompletionRequest);
        if (sseModelApiResp.isSuccess()) {
            sseModelApiResp.getFlowable().doOnNext(
                    modelData -> {
                        logger.info("model output: {}", mapper.writeValueAsString(modelData));
                    }
            ).blockingSubscribe();
        }
    }

}
