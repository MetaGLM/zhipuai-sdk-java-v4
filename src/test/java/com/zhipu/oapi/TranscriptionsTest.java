package com.zhipu.oapi;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.zhipu.oapi.service.v4.audio.AudioTranscriptionsRequest;
import com.zhipu.oapi.service.v4.model.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.TimeUnit;
@Testcontainers
public class TranscriptionsTest {

    private final static Logger logger = LoggerFactory.getLogger(TranscriptionsTest.class);
    private static final String API_SECRET_KEY = Constants.getApiKey();

    private static final ClientV4 client = new ClientV4.Builder(API_SECRET_KEY)
            .enableTokenCache()
            .networkConfig(300, 100, 100, 100, TimeUnit.SECONDS)
            .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
            .build();

    private static final ObjectMapper mapper = new ObjectMapper();


    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        return mapper;
    }

    @Test
    public void test() {

    }

    /**
     * sse-V4：function调用
     */
    @Test
    public void testInvokeTranscriptions() throws JsonProcessingException {
        AudioTranscriptionsRequest audioTranscriptionsRequest = new AudioTranscriptionsRequest();
        audioTranscriptionsRequest.setFile(new java.io.File("src/test/resources/asr.wav"));
        audioTranscriptionsRequest.setModel("glm-asr");
        audioTranscriptionsRequest.setStream(false);
        ModelApiResponse modelApiResp = client.invokeTranscriptionsApi(audioTranscriptionsRequest);
        logger.info("testInvokeTranscriptions output: {}", mapper.writeValueAsString(modelApiResp));
    }

    @Test
    public void testSSEInvokeTranscriptions() {
        AudioTranscriptionsRequest audioTranscriptionsRequest = new AudioTranscriptionsRequest();
        audioTranscriptionsRequest.setFile(new java.io.File("src/test/resources/asr.wav"));
        audioTranscriptionsRequest.setModel("glm-asr");
        audioTranscriptionsRequest.setStream(true);
        ModelApiResponse sseModelApiResp = client.invokeTranscriptionsApi(audioTranscriptionsRequest);
        if (sseModelApiResp.isSuccess()) {
            sseModelApiResp.getFlowable()
                    .doOnNext(modelData -> {logger.info("modelData:{}",modelData);})
                    .doOnComplete(() -> System.out.println("Stream completed."))
                    .doOnError(throwable -> System.err.println("Error: " + throwable))
                    .blockingSubscribe();
        }
    }

}
