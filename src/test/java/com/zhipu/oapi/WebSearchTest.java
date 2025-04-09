package com.zhipu.oapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhipu.oapi.service.v4.tools.*;
import com.zhipu.oapi.service.v4.web_search.WebSearchRequest;
import com.zhipu.oapi.service.v4.web_search.WebSearchResponse;
import com.zhipu.oapi.utils.StringUtils;
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
public class WebSearchTest {

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
    public void testWebSearch() throws JsonProcessingException {

        WebSearchRequest webSearchRequest = WebSearchRequest.builder()
                .searchEngine("search_std")
                .searchQuery("2025年特朗普给中国加了多少关税")
                .requestId("11111111")
                .build();

        WebSearchResponse webSearchResponse = client.invokeWebSearch(webSearchRequest);
        logger.info("webSearch output: {}", mapper.writeValueAsString(webSearchResponse));

    }


}