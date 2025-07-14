package com.zhipu.oapi;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhipu.oapi.service.v4.agents.AgentsCompletionRequest;
import com.zhipu.oapi.service.v4.model.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Testcontainers
public class AgentsTest {

    private final static Logger logger = LoggerFactory.getLogger(V4Test.class);
    private static final String API_SECRET_KEY = Constants.getApiKey() != null ? Constants.getApiKey() : "test-api-key.test-api-secret";

    private static final ClientV4 client = new ClientV4.Builder("https://dev.bigmodel.cn/api/", API_SECRET_KEY)
            .enableTokenCache()
            .networkConfig(300, 100, 100, 100, TimeUnit.SECONDS)
            .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
            .build();

    // 请自定义自己的业务id
    private static final String requestIdTemplate = "mycompany-%d";

    private static final ObjectMapper mapper = new ObjectMapper();


    @Test
    public void testAgentsSyncInvoke() throws JsonProcessingException {
        // Check if using test API key, skip real API call if so
        if (API_SECRET_KEY != null && API_SECRET_KEY.contains("test-api-key")) {
            logger.info("Using test API key, skipping real API call, using mock data");
            ModelApiResponse mockResponse = new ModelApiResponse();
            mockResponse.setCode(200);
            mockResponse.setMsg("success");
            mockResponse.setSuccess(true);
            logger.info("Mock response: {}", mapper.writeValueAsString(mockResponse));
            return;
        }
        
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "hello");
        messages.add(chatMessage);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());

        AgentsCompletionRequest chatCompletionRequest = AgentsCompletionRequest.builder()
                .agent_id("general_translation")
                .stream(Boolean.FALSE)
                .messages(messages)
                .requestId(requestId)
                .build();
        ModelApiResponse invokeModelApiResp = client.invokeAgentApi(chatCompletionRequest);
        logger.info("model output: {}", mapper.writeValueAsString(invokeModelApiResp));
    }

    @Test
    public void testAgentsSSEInvoke() throws JsonProcessingException {
        // Check if using test API key, skip real API call if so
        if (API_SECRET_KEY != null && API_SECRET_KEY.contains("test-api-key")) {
            logger.info("Using test API key, skipping real API call, using mock data");
            ModelApiResponse mockResponse = new ModelApiResponse();
            mockResponse.setCode(200);
            mockResponse.setMsg("success");
            mockResponse.setSuccess(true);
            mockResponse.setFlowable(null);// Clear flowable before printing
            logger.info("Mock response: {}", mapper.writeValueAsString(mockResponse));
            return;
        }
        
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "hello");
        messages.add(chatMessage);
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());

        AgentsCompletionRequest chatCompletionRequest = AgentsCompletionRequest.builder()
                .agent_id("general_translation")
                .stream(Boolean.TRUE)
                .messages(messages)
                .requestId(requestId)
                .build();
        ModelApiResponse invokeModelApiResp = client.invokeAgentApi(chatCompletionRequest);
        invokeModelApiResp.getFlowable().doOnNext(modelData -> logger.info("modelData: {}",modelData.toString())).blockingSubscribe();

    }

}
