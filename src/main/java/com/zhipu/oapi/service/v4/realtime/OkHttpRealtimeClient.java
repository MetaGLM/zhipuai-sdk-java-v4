package com.zhipu.oapi.service.v4.realtime;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public final class OkHttpRealtimeClient implements Closeable {
    private static final Logger logger = LoggerFactory.getLogger(OkHttpRealtimeClient.class);
    private final OkHttpClient client;
    private final CommunicationProvider communicationProvider;
    private final AtomicBoolean isDisposed = new AtomicBoolean(false);
    private final AtomicReference<WebSocket> webSocketRef = new AtomicReference<>();
    private final Consumer<RealtimeServerEvent> serverEventHandler;
    private final ConnectivityMonitor connectivityMonitor = new ConnectivityMonitor();
    private final boolean closeClientOnClose;


    public OkHttpRealtimeClient(CommunicationProvider communicationProvider, Consumer<RealtimeServerEvent> serverEventHandler, OkHttpClient client) {
        this.client = client;
        this.communicationProvider = communicationProvider;
        this.serverEventHandler = serverEventHandler;
        this.closeClientOnClose = false;
    }

    public OkHttpRealtimeClient(CommunicationProvider communicationProvider, Consumer<RealtimeServerEvent> serverEventHandler, OkHttpClient client, boolean closeClientOnClose) {
        this.client = client;
        this.communicationProvider = communicationProvider;
        this.serverEventHandler = serverEventHandler;
        this.closeClientOnClose = false;
    }

    public OkHttpRealtimeClient(CommunicationProvider communicationProvider, Consumer<RealtimeServerEvent> serverEventHandler) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Dispatcher dispatcher = new Dispatcher(executorService);
        dispatcher.setMaxRequests(4);
        dispatcher.setMaxRequestsPerHost(2);
        this.client = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS) // 设置连接超时时间为 5 秒
                .readTimeout(20, TimeUnit.SECONDS) // 设置读取超时时间为 20 秒
                .writeTimeout(20, TimeUnit.SECONDS) // 设置写入超时时间为 20 秒
                .callTimeout(40, TimeUnit.SECONDS) // 设置总的调用超时时间为 40 秒
                .pingInterval(10, TimeUnit.SECONDS) // 设置心跳间隔为 10 秒
                .dispatcher(dispatcher) // 设置请求分发器
                .build();
        this.communicationProvider = communicationProvider;
        this.serverEventHandler = serverEventHandler;
        this.closeClientOnClose = true;
    }

    public void start() {
        if (isDisposed.get()) {
            throw new IllegalStateException("客户端已关闭");
        }

        EnumSet<ConnectivityState> allowedStates = EnumSet.of(ConnectivityState.STOPPED, ConnectivityState.DISCONNECTED);

        if (!connectivityMonitor.changeStateOnAnyOf(allowedStates, ConnectivityState.CONNECTING)) {
            throw new IllegalStateException("无法在状态 " + connectivityMonitor.get() + " 下启动连接");
        }

        WebSocketListener listener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                logger.info("WebSocket连接已建立");
                webSocketRef.set(webSocket);
                connectivityMonitor.changeState(ConnectivityState.CONNECTED);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                logger.debug("收到消息: {}", text);
                RealtimeServerEvent serverEvent = JasonUtil.fromJsonToServerEvent(text);
                if (serverEvent == null) {
                    logger.error("无法解析服务器事件: {}", text);
                    return;
                }
                serverEventHandler.accept(serverEvent);
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                logger.info("连接正常关闭，原因：{}", reason);
                webSocketRef.set(null);
                if (!isStoppingState()) {
                    connectivityMonitor.changeState(ConnectivityState.DISCONNECTED);
                }
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                logger.error("连接异常", t);
                if (response != null) {
                    logger.error("异常响应码：{}，响应内容：{}", response.code(), response.body() != null ? response.body().toString() : "空内容");
                }
                webSocketRef.set(null);
                if (!isStoppingState()) {
                    connectivityMonitor.changeState(ConnectivityState.DISCONNECTED);
                }
            }

            private boolean isStoppingState() {
                ConnectivityState state = connectivityMonitor.get();
                return state == ConnectivityState.STOPPING || state == ConnectivityState.CLOSED;
            }
        };

        Request request = new Request.Builder() //
                .url(communicationProvider.getWebSocketUrl()) //
                .addHeader("Authorization", "Bearer " + communicationProvider.getAuthToken()) //
                .build();
        request.url().redact();
        client.newWebSocket(request, listener);
        client.dispatcher().executorService().submit(() -> logger.info("WebSocket连接线程已启动"));
    }

    public void stop() {
        ConnectivityState currentState = connectivityMonitor.get();
        if (currentState == ConnectivityState.CLOSED) {
            throw new IllegalStateException("客户端已关闭");
        }

        if (connectivityMonitor.changeStateOn(ConnectivityState.CONNECTED, ConnectivityState.STOPPING)) {
            WebSocket webSocket = webSocketRef.get();
            if (webSocket != null) {
                webSocket.close(1000, "正常关闭");
            }
        } else {
            logger.warn("停止失败，当前状态：{}", connectivityMonitor.get());
        }
    }

    public void waitForConnection() throws InterruptedException {
        while (connectivityMonitor.get() != ConnectivityState.CONNECTED) {
            Thread.sleep(100);
        }
    }

    public void sendMessage(RealtimeClientEvent clientEvent) {
        ConnectivityState state = connectivityMonitor.get();
        if (state != ConnectivityState.CONNECTED) {
            throw new IllegalStateException("连接未就绪，当前状态：" + state);
        }

        WebSocket webSocket = webSocketRef.get();
        if (webSocket == null) {
            throw new IllegalStateException("WebSocket连接未建立");
        }

        String jsonMessage = JasonUtil.toJsonFromClientEvent(clientEvent);
        if (jsonMessage == null) {
            logger.error("无法序列化客户端事件: type={}, event_id={}", clientEvent.getType(), clientEvent.getEventId());
            return;
        }
        webSocket.send(jsonMessage);
    }

    @Override
    public void close() throws IOException {
        if (isDisposed.compareAndSet(false, true)) {
            connectivityMonitor.changeState(ConnectivityState.CLOSED);
            WebSocket webSocket = webSocketRef.get();
            if (webSocket != null) {
                webSocket.close(1000, "客户端关闭");
                webSocketRef.set(null);
            }
            if (closeClientOnClose) {
                client.dispatcher().executorService().shutdown();
            }
        }
    }

    public enum ConnectivityState {
        STOPPED, CONNECTING, CONNECTED, DISCONNECTED, STOPPING, CLOSED
    }

    public interface CommunicationProvider {

        String getWebSocketUrl();

        String getAuthToken();
    }

    public static final class ConnectivityMonitor {
        private final AtomicReference<ConnectivityState> clientState = new AtomicReference<>(ConnectivityState.STOPPED);

        public ConnectivityState get() {
            return clientState.get();
        }

        public boolean changeStateOn(ConnectivityState expected, ConnectivityState newState) {
            boolean updated = clientState.compareAndSet(expected, newState);
            if (updated) {
                logger.info("状态变更：{} -> {}", expected, newState);
            }
            return updated;
        }

        public boolean changeStateOnAnyOf(EnumSet<ConnectivityState> expecteds, ConnectivityState newState) {
            for (; ; ) {
                ConnectivityState current = clientState.get();
                if (!expecteds.contains(current)) return false;
                if (clientState.compareAndSet(current, newState)) {
                    logger.info("状态变更：{} -> {}", current, newState);
                    return true;
                }
            }
        }

        public void changeState(ConnectivityState newState) {
            ConnectivityState prev = clientState.getAndSet(newState);
            logger.info("状态变更：{} -> {}", prev, newState);
        }
    }
}