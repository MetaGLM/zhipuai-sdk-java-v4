package com.zhipu.oapi;

import com.zhipu.oapi.service.v4.api.VideosClientApiService;
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
    private static final String ZHIPUAI_API_KEY = System.getProperty("ZHIPUAI_API_KEY");
    private static final String ZHIPUAI_BASE_URL = System.getProperty("ZHIPUAI_BASE_URL");
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
                .model("cogvideo")
                .build();
        VideosResponse apply = new VideosClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .videoGenerations(build)
                .apply(client);

        logger.info("apply:{}",apply);
    }
    @Test
    public void testVideoByImage() throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        String file = ClassLoader.getSystemResource("test_1.png").getFile();
        byte[] bytes = FileUtils.readFileToByteArray(new File(file));
        String imageUrl  = encoder.encodeToString( bytes);
        VideoCreateParams build = VideoCreateParams.builder()
                .prompt(
                        "她将头上的草帽拿了下来。这顶草帽有编织纹理，宽边，颜色自然，饰有复杂的图案，显得古朴又典雅。草帽的设计简单，但非常精致，材质透气，边缘略显磨损，\n" +
                        "一个坐着的K-pop偶像，她有着粉色的长发，佩戴着日本传统的头饰，肩膀裸露，穿着带有装饰的和服。她的头发飘逸而闪亮，紫色的眼睛如同夜空中的星星，耳朵上挂着闪亮的耳环\n")
                .imageUrl(imageUrl)
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
//                .id("1014908871935376263701590")
                .id("1014908871935376263701654")
                .build();
        VideosResponse apply = new VideosClientApiService(client.getConfig().getHttpClient(), client.getConfig().getBaseUrl())
                .videoGenerationsResult(build)
                .apply(client);

        logger.info("apply:{}",apply);
    }
}
