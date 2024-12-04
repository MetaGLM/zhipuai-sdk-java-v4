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
    private static final String ZHIPUAI_API_KEY = Constants.getApiKey();
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
                .prompt("一个开船的人")
                .model("cogvideox")
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
                .prompt("一个开船的人")
                .model("cogvideox")
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
                          "这个场景描绘了一个充满魔法气息的场景。在一个被绿色植物和橙色小花环绕的石桌上，一本古老的书籍摊开着，书页似乎刚刚被翻动过。书籍的旁边放置着一个透明的魔法球，魔法球中充满了闪烁的光点和流动的光影，仿佛有一种神秘的能量在其中流转。魔法球下方的底座散发出微弱的光芒，周围的空气中漂浮着一些细小的光粒，增强了场景的神秘感和魔幻氛围。背景中隐约可见一些模糊的建筑结构，进一步烘托出这个场景的奇幻与神秘。")
                .imageUrl(imageUrl)
                .model("cogvideox")
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
