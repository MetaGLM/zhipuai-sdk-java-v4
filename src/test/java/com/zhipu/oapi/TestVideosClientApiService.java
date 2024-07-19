package com.zhipu.oapi;

import com.zhipu.oapi.service.v4.api.VideosClientApiService;
import com.zhipu.oapi.service.v4.videos.VideoCreateParams;
import com.zhipu.oapi.service.v4.videos.VideosResponse;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.TimeUnit;


@Testcontainers
public class TestVideosClientApiService {
    private final static Logger logger = LoggerFactory.getLogger(V4Test.class);
    private static final String API_SECRET_KEY = System.getProperty("ZHIPUAI_API_KEY");


    private static final ClientV4 client = new ClientV4.Builder(API_SECRET_KEY)
            .enableTokenCache()
            .networkConfig(300, 100, 100, 100, TimeUnit.SECONDS)
            .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
            .build();


    @Test
    public void testVideo(){

        VideoCreateParams build = VideoCreateParams.builder()
                .prompt("一个开船的人")
                .model("cogvideo")
                .build();
        VideosResponse apply = new VideosClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .videoGenerations(build)
                .apply(client);

        logger.info("apply:{}",apply);
    }

    @Test
    public void testVideoGenerationsResult(){

        VideoCreateParams build = VideoCreateParams.builder()
                .id("test")
                .build();
        VideosResponse apply = new VideosClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .videoGenerationsResult(build)
                .apply(client);

        logger.info("apply:{}",apply);
    }
}
