package com.zhipu.oapi;

import com.zhipu.oapi.mock.MockClientV4;
import com.zhipu.oapi.service.v4.api.VideosClientApiService;
import com.zhipu.oapi.service.v4.embedding.Embedding;
import com.zhipu.oapi.service.v4.embedding.EmbeddingApiResponse;
import com.zhipu.oapi.service.v4.embedding.EmbeddingRequest;
import com.zhipu.oapi.service.v4.embedding.EmbeddingResult;
import com.zhipu.oapi.service.v4.videos.VideoCreateParams;
import com.zhipu.oapi.service.v4.videos.VideosResponse;
import com.zhipu.oapi.utils.StringUtils;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Testcontainers
public class TestEmbeddingClientApiService {
    private final static Logger logger = LoggerFactory.getLogger(V4Test.class);
    private static final String ZHIPUAI_API_KEY = Constants.getApiKey() != null ? Constants.getApiKey() : "test-api-key.test-api-secret";
    private static final String ZHIPUAI_BASE_URL = Constants.getBaseUrl();
    private static ClientV4 client = null;

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
    public void testEmbedding() {
        // Check if using test API key, skip real API call if so
        if (ZHIPUAI_API_KEY != null && ZHIPUAI_API_KEY.contains("test-api-key")) {
            logger.info("Using test API key, skipping real API call, using mock data");
            
            EmbeddingRequest world = EmbeddingRequest.builder().input("hello world").dimensions(512).model("embedding-3").build();
            // Use mock data
            EmbeddingApiResponse embeddingApiResponse = MockClientV4.mockEmbeddingApi(world);
            EmbeddingResult data = embeddingApiResponse.getData();
            List<Embedding> data1 = data.getData();
            data1.forEach(embedding -> {
                logger.info("Mock embedding: {}", embedding.getEmbedding());
                assert embedding.getEmbedding().size() == 512;
            });
            logger.info("Mock embedding response: {}", embeddingApiResponse);
            return;
        }
        
        EmbeddingRequest world = EmbeddingRequest.builder().input("hello world").dimensions(512).model("embedding-3").build();
        EmbeddingApiResponse embeddingApiResponse = client.invokeEmbeddingsApi(world);
        EmbeddingResult data = embeddingApiResponse.getData();
        List<Embedding> data1 = data.getData();
        data1.forEach(embedding -> {
            logger.info("embedding:{}", embedding.getEmbedding());
            assert embedding.getEmbedding().size() == 512;
        });
        logger.info("apply:{}", embeddingApiResponse);
    }

    @Test
    public void testEmbeddingList() {
        // Check if using test API key, skip real API call if so
        if (ZHIPUAI_API_KEY != null && ZHIPUAI_API_KEY.contains("test-api-key")) {
            logger.info("Using test API key, skipping real API call, using mock data");
            
            EmbeddingRequest world = EmbeddingRequest.builder()
                    .input(Arrays.asList("hello world", "hello world"))
                    .dimensions(512).model("embedding-3").build();
            
            // Use mock data
            EmbeddingApiResponse embeddingApiResponse = MockClientV4.mockEmbeddingApi(world);
            EmbeddingResult data = embeddingApiResponse.getData();
            List<Embedding> data1 = data.getData();
            data1.forEach(embedding -> {
                logger.info("Mock embedding list: {}", embedding.getEmbedding());
                assert embedding.getEmbedding().size() == 512;
            });
            logger.info("Mock embedding list response: {}", embeddingApiResponse);
            return;
        }
        
        EmbeddingRequest world = EmbeddingRequest.builder()
                .input(Arrays.asList("hello world", "hello world"))
                .dimensions(512).model("embedding-3").build();
        EmbeddingApiResponse embeddingApiResponse = client.invokeEmbeddingsApi(world);
        EmbeddingResult data = embeddingApiResponse.getData();
        List<Embedding> data1 = data.getData();
        data1.forEach(embedding -> {
            logger.info("embedding:{}", embedding.getEmbedding());
            assert embedding.getEmbedding().size() == 512;
        });
        logger.info("apply:{}", embeddingApiResponse);
    }

}
