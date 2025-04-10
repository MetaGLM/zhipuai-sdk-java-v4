package com.zhipu.oapi.realtime;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.zhipu.oapi.service.v4.realtime.JasonUtil;
import com.zhipu.oapi.service.v4.realtime.OkHttpRealtimeClient;
import com.zhipu.oapi.service.v4.realtime.RealtimeClientEvent;
import com.zhipu.oapi.service.v4.realtime.RealtimeServerEvent;
import com.zhipu.oapi.service.v4.realtime.server.RealtimeError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class OkHttpRealtimeClientTest {
    private static final Logger logger = LoggerFactory.getLogger(OkHttpRealtimeClientTest.class);

    public static void main(String[] args) {
        OkHttpRealtimeClient.CommunicationProvider communicationProvider = new OkHttpRealtimeClient.CommunicationProvider() {
            public String getAuthToken() {
                return Optional.ofNullable(System.getenv("API_KEY_01")) //
                        .orElse("TODO");
            }

            public String getWebSocketUrl() {
                return "wss://open.bigmodel.cn/api/paas/v4/realtime";
            }
        };

        Consumer<RealtimeServerEvent> serverEventHandler = event -> {
            logger.info("收到服务器事件: {}, 类型: {}", event.getType(), event.getClass().getSimpleName());
            if (event instanceof RealtimeError realtimeError) {
                logger.error("收到服务器错误事件: {}", JasonUtil.toJsonFromServerEvent(realtimeError));
            }
        };

        OkHttpRealtimeClient client = new OkHttpRealtimeClient(communicationProvider, serverEventHandler);

        try {
            client.start();
            logger.info("客户端已启动");

            client.waitForConnection();
            logger.info("WebSocket连接已建立");

            URL resourceUrl = Resources.getResource("Audio.ServerVad.Input");
            List<String> lines = Resources.readLines(resourceUrl, Charsets.UTF_8);
            for (String text : lines) {
                if (!text.trim().isEmpty()) {
                    RealtimeClientEvent clientEvent = JasonUtil.fromJsonToClientEvent(text);
                    logger.info("解析并发送消息: {}", clientEvent.getType());
                    logger.info("解析并发送消息: {}", JasonUtil.toJsonFromClientEvent(clientEvent));
                    client.sendMessage(clientEvent);
                }
            }

            Thread.sleep(5000);
            client.stop();
            logger.info("客户端已停止");
        } catch (Exception e) {
            logger.error("客户端运行异常", e);
        } finally {
            try {
                client.close();
                logger.info("客户端已关闭");
            } catch (IOException e) {
                logger.error("关闭客户端时发生异常", e);
            }
        }
    }
}