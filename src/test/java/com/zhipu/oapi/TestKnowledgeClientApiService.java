package com.zhipu.oapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhipu.oapi.service.v4.api.DocumentClientApiService;
import com.zhipu.oapi.service.v4.api.KnowledgeClientApiService;
import com.zhipu.oapi.service.v4.knowledge.*;
import com.zhipu.oapi.service.v4.knowledge.document.*;
import com.zhipu.oapi.utils.StringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assumptions;
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
    private static final String ZHIPUAI_API_KEY = getTestApiKey();
    private static final String ZHIPUAI_BASE_URL = Constants.getBaseUrl();
    private static ClientV4 client = null;
    
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
    private KnowledgeResponse knowledgeResponse;
    private DocumentObjectResponse documentObjectResponse;

    @Test
    @Order(1)
    public void testKnowledgeCreate() {
        // Check if using test API key, use mock data if so
        if (ZHIPUAI_API_KEY != null && ZHIPUAI_API_KEY.contains("test-api-key")) {
            logger.info("Using test API key, using mock data");
            // Create mock KnowledgeResponse
            knowledgeResponse = new KnowledgeResponse();
            KnowledgeInfo knowledgeInfo = new KnowledgeInfo();
            knowledgeInfo.setId("mock-knowledge-id-123");
            knowledgeInfo.setName("test");
            knowledgeInfo.setDescription("Test");
            knowledgeResponse.setData(knowledgeInfo);
            knowledgeResponse.setSuccess(true);
            logger.info("Mock knowledgeCreate result: {}", knowledgeResponse);
            return;
        }
        
        KnowledgeBaseParams build = KnowledgeBaseParams.builder()
                .embeddingId(1)
                .name("test")
                .description("Test")
                .icon("question")
                .background("blue")
                .build();
        // Set relevant properties of params
        KnowledgeResponse apply = new KnowledgeClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .knowledgeCreate(build)
                .apply(client);

        logger.info("knowledgeCreate result: {}", apply);
        knowledgeResponse = apply;
        // Add assertions here to verify the return result
        Assertions.assertNotNull(apply);
    }

    @Test
    @Order(2)
    public void testKnowledgeModify() {
        // Check if using real API key, skip test if using test key
        Assumptions.assumeTrue(ZHIPUAI_API_KEY != null && !ZHIPUAI_API_KEY.contains("test-api-key"), 
                "Skipping test: Using test API key, real API key required for this test");
        
        KnowledgeBaseParams build = KnowledgeBaseParams.builder()
                .knowledgeId(knowledgeResponse.getData().getId())
                .embeddingId(1)
                .name("Test1")
                .description("Test")
                .icon("question")
                .background("blue")
                .build();
        KnowledgeEditResponse apply = new KnowledgeClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .knowledgeModify(build)
                .apply(client);


        logger.info("knowledgeModify result: {}", apply);
        // Add assertions here to verify the return result
        Assertions.assertNotNull(apply);
    }


    @Test
    @Order(3)
    public void testCreateDocumentFile() throws JsonProcessingException {
        // Check if using real API key, skip test if using test key
        Assumptions.assumeTrue(ZHIPUAI_API_KEY != null && !ZHIPUAI_API_KEY.contains("test-api-key"), 
                "Skipping test: Using test API key, real API key required for this test");
        
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
        // Set relevant properties of params
        DocumentObjectResponse apply = new DocumentClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .createDocument(build)
                .apply(client);

        logger.info("createDocument result: {}", apply);
        documentObjectResponse = apply;
        // Add assertions here to verify the return result
        Assertions.assertNotNull(apply);

    }

    @Test
    @Order(4)
    public void testCreateDocumentUploadDetail() throws JsonProcessingException {
        // Check if using real API key, skip test if using test key
        Assumptions.assumeTrue(ZHIPUAI_API_KEY != null && !ZHIPUAI_API_KEY.contains("test-api-key"), 
                "Skipping test: Using test API key, real API key required for this test");
        
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
        // Set relevant properties of params
        DocumentObjectResponse apply = new DocumentClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .createDocument(build)
                .apply(client);

        logger.info("testCreateDocumentUploadDetail result: {}", apply);
        // Add assertions here to verify the return result
        Assertions.assertNotNull(apply);

    }
    @Test
    @Order(5)
    public void testKnowledgeQuery() {
        QueryKnowledgeRequest build = QueryKnowledgeRequest.builder()
                .page(1)
                .size(10)
                .build();
        // Set relevant properties of params
        QueryKnowledgeApiResponse apply = new KnowledgeClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .knowledgeQuery(build)
                .apply(client);

        logger.info("knowledgeQuery result: {}", apply);
        // Add assertions here to verify the return result
        Assertions.assertNotNull(apply);
    }

    @Test
    @Order(5)
    public void testModifyDocument() {
        // Check if using real API key, skip test if using test key
        Assumptions.assumeTrue(ZHIPUAI_API_KEY != null && !ZHIPUAI_API_KEY.contains("test-api-key"), 
                "Skipping test: Using test API key, real API key required for this test");
        
        DocumentEditParams build = DocumentEditParams.builder()
                 .id(documentObjectResponse.getData().getSuccessInfos().get(0).getDocumentId())
                 .knowledgeType(1)
                 .sentenceSize(202)
                 .build();
        // Set relevant properties of params
        DocumentEditResponse apply = new DocumentClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .modifyDocument(build)
                .apply(client);

        logger.info("modifyDocument result: {}", apply);
        // Add assertions here to verify the return result
        Assertions.assertNotNull(apply);

    }
    @Test
    @Order(7)
    public void testRetrieveDocument() {
        // Check if using real API key, skip test if using test key
        Assumptions.assumeTrue(ZHIPUAI_API_KEY != null && !ZHIPUAI_API_KEY.contains("test-api-key"), 
                "Skip test: Using test API key, real API key required to execute this test");
        
        DocumentEditParams build = DocumentEditParams.builder()
                .id(documentObjectResponse.getData().getSuccessInfos().get(0).getDocumentId())
                .sentenceSize(203)
                .build();
        // Set relevant properties of params
        DocumentDataResponse apply = new DocumentClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .retrieveDocument(build)
                .apply(client);

        logger.info("retrieveDocument result: {}", apply);
        // Add assertions here to verify the return result
        Assertions.assertNotNull(apply);

    }

    @Test
    @Order(7)
    public void testQueryDocumentList() {
        // Check if using test API key, skip real API call if so
        if (ZHIPUAI_API_KEY != null && ZHIPUAI_API_KEY.contains("test-api-key")) {
            logger.info("Using test API key, skipping real API call");
            return;
        }
        
        QueryDocumentRequest build = QueryDocumentRequest.builder()
                .knowledgeId(knowledgeResponse.getData().getId())
                .purpose("retrieval")
                .page(1)
                .limit(10)
                .build();
        // Set relevant properties of params
        QueryDocumentApiResponse apply = new DocumentClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .queryDocumentList(build)
                .apply(client);

        logger.info("queryDocumentList result: {}", apply);
        // Add assertions here to verify the return result
        Assertions.assertNotNull(apply);

    }

    @Test
    @Order(997)
    public void testDeleteDocument() {
        // Check if using real API key, skip test if using test key
        Assumptions.assumeTrue(ZHIPUAI_API_KEY != null && !ZHIPUAI_API_KEY.contains("test-api-key"), 
                "Skip test: Using test API key, real API key required to execute this test");
        
        DocumentEditParams build = DocumentEditParams.builder()
                .id(documentObjectResponse.getData().getSuccessInfos().get(0).getDocumentId())
                .build();
        // Set relevant properties of params
        DocumentEditResponse apply = new DocumentClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .deleteDocument(build)
                .apply(client);

        logger.info("deleteDocument result: {}", apply);
        // Add assertions here to verify the return result
        Assertions.assertNotNull(apply);
    }

    @Test
    @Order(998)
    public void testKnowledgeDelete() {
        // Check if using real API key, skip test if using test key
        Assumptions.assumeTrue(ZHIPUAI_API_KEY != null && !ZHIPUAI_API_KEY.contains("test-api-key"), 
                "Skip test: Using test API key, real API key required to execute this test");
        
        KnowledgeBaseParams build = KnowledgeBaseParams.builder()
                .knowledgeId(knowledgeResponse.getData().getId())
                .build();
        // Set relevant properties of params
        KnowledgeEditResponse apply = new KnowledgeClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .knowledgeDelete(build)
                .apply(client);

        logger.info("knowledgeDelete result: {}", apply);
        // Add assertions here to verify the return result
        Assertions.assertNotNull(apply);
    }

    @Test
    @Order(6)
    public void testKnowledgeUsed() {
        KnowledgeUsedResponse apply = new KnowledgeClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .knowledgeUsed()
                .apply(client);

        logger.info("knowledgeUsed result: {}", apply);
        // Add assertions here to verify the return result
        Assertions.assertNotNull(apply);
    }
}
