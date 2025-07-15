package com.zhipu.oapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhipu.oapi.service.v4.voice.VoiceClientService;
import com.zhipu.oapi.service.v4.voice.model.AddVoiceResponse;
import com.zhipu.oapi.service.v4.voice.model.TtsVoiceListResponse;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TestVoiceClientService {
    private static final String BASE_URL = "http://localhost:9203"; // 根据实际情况修改
    private static final String TOKEN = ""; // 替换为实际token

    @Test
    public void testListTtsVoices() throws Exception {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        VoiceClientService service = new VoiceClientService(client, BASE_URL, objectMapper);
        TtsVoiceListResponse response = service.listTtsVoices(TOKEN);
        System.out.println("code: " + response.getCode());
        System.out.println("msg: " + response.getMsg());
        System.out.println("success: " + response.isSuccess());
        System.out.println("data: " + response.getData());
        Assertions.assertTrue(response.isSuccess());
    }

    @Test
    public void testAddVoice() throws Exception {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        VoiceClientService service = new VoiceClientService(client, BASE_URL, objectMapper);
        File file = new File("C:\\Users\\zjq18\\Downloads\\北京今天的天气.mp3"); // 替换为实际音频文件路径
        AddVoiceResponse response = service.addVoice(TOKEN, "磁性嗓音", "今天北京的天气怎么样？", file);
        System.out.println("code: " + response.getCode());
        System.out.println("msg: " + response.getMsg());
        System.out.println("success: " + response.isSuccess());
        System.out.println("data: " + response.getData());
        System.out.println("Add Voice: " + response.getData());
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isSuccess());
    }
} 