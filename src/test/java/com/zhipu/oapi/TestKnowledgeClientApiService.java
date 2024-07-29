package com.zhipu.oapi;

import com.zhipu.oapi.service.v4.api.KnowledgeClientApiService;
import com.zhipu.oapi.service.v4.knowledge.*;
import com.zhipu.oapi.utils.StringUtils;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.testcontainers.containers.GenericContainer;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestKnowledgeClientApiService {
    private final static Logger logger = LoggerFactory.getLogger(TestKnowledgeClientApiService.class);
    private static final String ZHIPUAI_API_KEY = System.getProperty("ZHIPUAI_API_KEY");
    private static final String ZHIPUAI_BASE_URL = System.getProperty("ZHIPUAI_BASE_URL");
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
    private KnowledgeResponse knowledgeResponse;

    @Test
    @Order(1)
    public void testKnowledgeCreate() {
        KnowledgeBaseParams build = KnowledgeBaseParams.builder()
                .embeddingId(1)
                .name("test")
                .description("测试")
                .icon("question")
                .background("blue")
                .build();
        // 设置params的相关属性
        KnowledgeResponse apply = new KnowledgeClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .knowledgeCreate(build)
                .apply(client);

        logger.info("knowledgeCreate result: {}", apply);
        knowledgeResponse = apply;
        // 这里可以加上断言来验证返回结果
        Assertions.assertNotNull(apply);
    }

    @Test
    @Order(2)
    public void testKnowledgeModify() {
        KnowledgeBaseParams build = KnowledgeBaseParams.builder()
                .knowledgeId(knowledgeResponse.getData().getId())
                .embeddingId(1)
                .name("测试1")
                .description("测试")
                .icon("question")
                .background("blue")
                .build();
        KnowledgeEditResponse apply = new KnowledgeClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .knowledgeModify(build)
                .apply(client);


        logger.info("knowledgeModify result: {}", apply);
        // 这里可以加上断言来验证返回结果
        Assertions.assertNotNull(apply);
    }

    @Test
    @Order(3)
    public void testKnowledgeQuery() {
        QueryKnowledgeRequest build = QueryKnowledgeRequest.builder()
                .page(1)
                .size(10)
                .build();
        // 设置params的相关属性
        QueryKnowledgeApiResponse apply = new KnowledgeClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .knowledgeQuery(build)
                .apply(client);

        logger.info("knowledgeQuery result: {}", apply);
        // 这里可以加上断言来验证返回结果
        Assertions.assertNotNull(apply);
    }

    @Test
    @Order(999)
    public void testKnowledgeDelete() {
        KnowledgeBaseParams build = KnowledgeBaseParams.builder()
                .knowledgeId(knowledgeResponse.getData().getId())
                .build();
        // 设置params的相关属性
        KnowledgeEditResponse apply = new KnowledgeClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .knowledgeDelete(build)
                .apply(client);

        logger.info("knowledgeDelete result: {}", apply);
        // 这里可以加上断言来验证返回结果
        Assertions.assertNotNull(apply);
    }

    @Test
    @Order(6)
    public void testKnowledgeUsed() {
        KnowledgeUsedResponse apply = new KnowledgeClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .knowledgeUsed()
                .apply(client);

        logger.info("knowledgeUsed result: {}", apply);
        // 这里可以加上断言来验证返回结果
        Assertions.assertNotNull(apply);
    }
}
