package com.zhipu.oapi;

import com.zhipu.oapi.service.v4.api.VideosClientApiService;
import com.zhipu.oapi.service.v4.model.SensitiveWordCheckRequest;
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
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.TimeUnit;


@Testcontainers
public class TestVideosClientApiService {
    private final static Logger logger = LoggerFactory.getLogger(V4Test.class);
    private static final String ZHIPUAI_API_KEY = Constants.getApiKey() != null ? Constants.getApiKey() : "test-api-key.test-api-secret";
    private static final String ZHIPUAI_BASE_URL = Constants.getBaseUrl();
    private static ClientV4 client = null;
    static {
        if (StringUtils.isNotEmpty(ZHIPUAI_BASE_URL)){

            client = new ClientV4.Builder(ZHIPUAI_BASE_URL,ZHIPUAI_API_KEY)
                    .enableTokenCache()
                    .networkConfig(300, 100, 100, 100, TimeUnit.SECONDS)
                    .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
                    .build();
        }else {
            client = new ClientV4.Builder(ZHIPUAI_API_KEY)
                    .enableTokenCache()
                    .networkConfig(300, 100, 100, 100, TimeUnit.SECONDS)
                    .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
                    .build();
        }

    }

    @Test
    public void testVideo(){

        VideoCreateParams build = VideoCreateParams.builder()
                .prompt("A person driving a boat")
                .model("cogvideox-3")
                .withAudio(Boolean.TRUE)
                .quality("quality")
                .build();
        VideosResponse apply = new VideosClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .videoGenerations(build)
                .apply(client);

        logger.info("apply:{}",apply);
    }

    @Test
    public void testVideoSensitiveWordCheck(){
        SensitiveWordCheckRequest sensitiveWordCheckRequest = SensitiveWordCheckRequest.builder()
                .type("ALL")
                .status("DISABLE")
                .build();

        VideoCreateParams build = VideoCreateParams.builder()
                .prompt("A person driving a boat")
                .model("cogvideox-3")
                .sensitiveWordCheck(sensitiveWordCheckRequest)
                .build();
        VideosResponse apply = new VideosClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .videoGenerations(build)
                .apply(client);

        logger.info("apply:{}",apply);
    }

    @Test
    public void testVideoByImage() throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        String file = ClassLoader.getSystemResource("20.png").getFile();
        byte[] bytes = FileUtils.readFileToByteArray(new File(file));
        String imageUrl  = encoder.encodeToString( bytes);
        VideoCreateParams build = VideoCreateParams.builder()
                .prompt(
                          "This scene depicts a magical atmosphere. On a stone table surrounded by green plants and orange flowers, an ancient book lies open, its pages seemingly just turned. Next to the book sits a transparent magic orb filled with twinkling lights and flowing shadows, as if mysterious energy flows within. The base beneath the magic orb emits a faint glow, while tiny light particles float in the surrounding air, enhancing the scene's mystique and magical ambiance. Vague architectural structures can be seen in the background, further emphasizing the fantasy and mystery of this scene.")
                .imageUrl(imageUrl)
                .model("cogvideox-3")
                .build();
        VideosResponse apply = new VideosClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .videoGenerations(build)
                .apply(client);

        logger.info("apply:{}",apply);
    }

    @Test
    public void testVideoGenerationsResult(){

        VideoCreateParams build = VideoCreateParams.builder()

                .id("1000088872446167827091899")
                .build();
        VideosResponse apply = new VideosClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .videoGenerationsResult(build)
                .apply(client);

        logger.info("apply:{}",apply);
    }
}
