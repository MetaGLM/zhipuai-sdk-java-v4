package com.zhipu.oapi;

import okhttp3.MediaType;

import java.time.Duration;

public class Constants {

    public static final String BASE_URL = "https://open.bigmodel.cn/api/paas/v4/";
    public static final String TEST_BASE_URL = "https://test.bigmodel.cn/stage-api/paas/v4/";

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(300);

    public static final String ModelChatGLM4 = "GLM-4";

    public static final String ModelCogView = "cogview-3";

    public static final String ModelChatGLM4V = "glm-4v";

    public static final String ModelEmbedding2 = "embedding-2";

    public static final String ModelChatGLM3TURBO= "glm-3-turbo";
    public static final String ModelCharGLM3= "charglm-3";

    // invoke methods
    public static final String invokeMethodAsync = "async-invoke";
    public static final String invokeMethodSse = "sse-invoke";

    public static final String invokeMethod = "invoke";

    // http keywords
    public static final String authHeaderKey = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String ACCEPT = "Accept";
    public static final String SSE_CONTENT_TYPE = "text/event-stream";
    public static final String USER_AGENT = "User-Agent";
    public static final String DEFAULT_USER_AGENT = "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)";
    public static final String APPLICATION_JSON = "application/json";
    public static final String JSON_CONTENT_TYPE = APPLICATION_JSON + "; charset=utf-8";
    public static final MediaType jsonMediaType = MediaType.get(JSON_CONTENT_TYPE);
    public static final String resultKeyStatusCode = "code";
    public static final String resultKeyMsg = "msg";
    public static final String resultKeyError = "error";
    public static final String resultKeySuccess = "success";
    public static final String resultKeyData = "data";


}
