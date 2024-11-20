package com.zhipu.oapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhipu.oapi.service.v4.api.DocumentClientApiService;
import com.zhipu.oapi.service.v4.api.KnowledgeClientApiService;
import com.zhipu.oapi.service.v4.knowledge.*;
import com.zhipu.oapi.service.v4.knowledge.document.*;
import com.zhipu.oapi.utils.StringUtils;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.testcontainers.containers.GenericContainer;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;

@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestKnowledgeClientApiService {
    private final static Logger logger = LoggerFactory.getLogger(TestKnowledgeClientApiService.class);
    private static final String ZHIPUAI_API_KEY = Constants.getApiKey();
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
    private KnowledgeResponse knowledgeResponse;
    private DocumentObjectResponse documentObjectResponse;

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
    public void testCreateDocumentFile() throws JsonProcessingException {
        String filePath = "file.xlsx";

        String path = ClassLoader.getSystemResource(filePath).getPath();
        String purpose = "retrieval";

        DocumentCreateParams build = DocumentCreateParams.builder()
                .filePath(path)
                .knowledgeId(knowledgeResponse.getData().getId())
                .sentenceSize(202)
                .purpose(purpose)
                .customSeparator(null)
                .build();
        // 设置params的相关属性
        DocumentObjectResponse apply = new DocumentClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .createDocument(build)
                .apply(client);

        logger.info("createDocument result: {}", apply);
        documentObjectResponse = apply;
        // 这里可以加上断言来验证返回结果
        Assertions.assertNotNull(apply);

    }

    @Test
    @Order(4)
    public void testCreateDocumentUploadDetail() throws JsonProcessingException {
        String purpose = "retrieval";
        UploadDetail uploadDetail = new UploadDetail();
        uploadDetail.setUrl("http://www.baidu.com");
        DocumentCreateParams build = DocumentCreateParams.builder()
                .knowledgeId(knowledgeResponse.getData().getId())
                .sentenceSize(202)
                .purpose(purpose)
                .uploadDetail(Collections.singletonList(uploadDetail))
                .customSeparator(null)
                .build();
        // 设置params的相关属性
        DocumentObjectResponse apply = new DocumentClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .createDocument(build)
                .apply(client);

        logger.info("testCreateDocumentUploadDetail result: {}", apply);
        // 这里可以加上断言来验证返回结果
        Assertions.assertNotNull(apply);

    }
    @Test
    @Order(5)
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
    @Order(6)
    public void testModifyDocument() {
        DocumentEditParams build = DocumentEditParams.builder()
                .id(documentObjectResponse.getData().getSuccessInfos().get(0).getDocumentId())
                .knowledgeType(1)
                .sentenceSize(203)
                .build();
        // 设置params的相关属性
        DocumentEditResponse apply = new DocumentClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .modifyDocument(build)
                .apply(client);

        logger.info("modifyDocument result: {}", apply);
        // 这里可以加上断言来验证返回结果
        Assertions.assertNotNull(apply);

    }
    @Test
    @Order(7)
    public void testRetrieveDocument() {
        DocumentEditParams build = DocumentEditParams.builder()
                .id(documentObjectResponse.getData().getSuccessInfos().get(0).getDocumentId())
                .sentenceSize(203)
                .build();
        // 设置params的相关属性
        DocumentDataResponse apply = new DocumentClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .retrieveDocument(build)
                .apply(client);

        logger.info("retrieveDocument result: {}", apply);
        // 这里可以加上断言来验证返回结果
        Assertions.assertNotNull(apply);

    }

    @Test
    @Order(7)
    public void testQueryDocumentList() {
        QueryDocumentRequest build = QueryDocumentRequest.builder()
                .knowledgeId(knowledgeResponse.getData().getId())
                .purpose("retrieval")
                .page(1)
                .limit(10)
                .build();
        // 设置params的相关属性
        QueryDocumentApiResponse apply = new DocumentClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .queryDocumentList(build)
                .apply(client);

        logger.info("queryDocumentList result: {}", apply);
        // 这里可以加上断言来验证返回结果
        Assertions.assertNotNull(apply);

    }

    @Test
    @Order(997)
    public void testDeleteDocument() {
        DocumentEditParams build = DocumentEditParams.builder()
                .id(documentObjectResponse.getData().getSuccessInfos().get(0).getDocumentId())
                .build();
        // 设置params的相关属性
        DocumentEditResponse apply = new DocumentClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .deleteDocument(build)
                .apply(client);

        logger.info("deleteDocument result: {}", apply);
        // 这里可以加上断言来验证返回结果
        Assertions.assertNotNull(apply);
    }

    @Test
    @Order(998)
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
