package com.zhipu.oapi.knowledge;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.V4Test;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.knowledge.KnowledgeBaseParams;
import com.zhipu.oapi.service.v4.knowledge.KnowledgeInfoResponse;
import com.zhipu.oapi.service.v4.knowledge.document.DocumentResponse;
import com.zhipu.oapi.service.v4.knowledge.document.FileCreateParams;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(OrderAnnotation.class)
public class KnowledgeTest {

    private final static Logger logger = LoggerFactory.getLogger(V4Test.class);
    private static final String API_SECRET_KEY = System.getenv("ZHIPUAI_API_KEY");
    private static final String ZHIPUAI_BASE_URL = System.getenv("ZHIPUAI_BASE_URL");
    private static final boolean devMode = false;


    private static final ClientV4 client = new ClientV4.Builder(ZHIPUAI_BASE_URL,API_SECRET_KEY)
            .devMode(devMode)
            .enableTokenCache()
            .networkConfig(300, 100, 100, 100, TimeUnit.SECONDS)
            .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
            .build();

    // 请自定义自己的业务id
    private static final String requestIdTemplate = "mycompany-%d";

    private static final ObjectMapper mapper = MessageDeserializeFactory.defaultObjectMapper();

    private String test_knowledge_id;
    private String knowledgeId;


    @Test
    @Order(1)
    public void testKnowledgeCreate() {
        KnowledgeBaseParams build = KnowledgeBaseParams.builder()
                .embeddingId(1)
                .background("blue")
                .name("test")
                .description("测试")
                .icon("question")
                .build();
        KnowledgeInfoResponse knowledgeInfoResponse = client.knowledgeCreate(build);

        logger.info("knowledgeInfoResponse: {}", knowledgeInfoResponse);
        if(knowledgeInfoResponse != null && knowledgeInfoResponse.isSuccess()) {
            test_knowledge_id = knowledgeInfoResponse.getData().getId();
        }
    }
    @Test
    @Order(1)
    public void testDocumentCreate() {
        String filePath = "demo.jsonl";

        String path = ClassLoader.getSystemResource(filePath).getPath();

        FileCreateParams retrieval = FileCreateParams.builder()
                .knowledgeId(test_knowledge_id)
                .filePath(path)
                .purpose("retrieval")
                .build();

        DocumentResponse documentResponse = client.documentCreate(retrieval);
        logger.info("documentResponse: {}", documentResponse);
    }

    @Test
    @Order(2)
    public void test2() {

    }
}
