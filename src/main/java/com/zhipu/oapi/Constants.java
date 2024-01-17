package com.zhipu.oapi;

import okhttp3.MediaType;

public class Constants {

    public static final String ModelChatGLM4 = "GLM-4";

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
    // 返回标准对JSON字符串
    public static final String RETURN_TYPE_JSON = "json_string";
    // 返回原始对文本内容
    public static final String RETURN_TYPE_TEXT = "text";
}
