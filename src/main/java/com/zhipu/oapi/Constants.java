package com.zhipu.oapi;

import com.zhipu.oapi.utils.StringUtils;
import okhttp3.MediaType;

import java.time.Duration;

public class Constants {

    public static final String BASE_URL = "https://open.bigmodel.cn/api/paas/v4/";

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(300);

    // 模型枚举model enumeration，
    // 文本模型
    // 新模型
    public static final String ModelChatGLM4Plus = "glm-4-plus";
    public static final String ModelChatGLM4Air = "glm-4-air";
    public static final String ModelChatGLM4Flash = "glm-4-flash";
    public static final String ModelChatGLM4 = "glm-4";
    public static final String ModelChatGLM40520 = "glm-4-0520";
    public static final String ModelChatGLM4Airx = "glm-4-airx";
    public static final String ModelChatGLMLong = "glm-4-long";

    // 图片理解
    public static final String ModelChatGLM4VPlus = "glm-4v-plus";
    public static final String ModelChatGLM4V = "glm-4v";

    // 图片生成
    public static final String ModelCogView3Plus = "cogview-3-plus";
    public static final String ModelCogView = "cogview-3";

    // embedding模型
    public static final String ModelEmbedding2 = "embedding-2";
    public static final String ModelEmbedding3 = "embedding-3";

    // 拟人模型
    public static final String ModelCharGLM3= "charglm-3";

    // 历史模型，建议尽快迁移至信模型
    @Deprecated
    public static final String ModelChatGLM3TURBO= "glm-3-turbo";

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

    // 环境变量
    private static final String ENV_BASE_URL_KEY = "ZHIPUAI_BASE_URL";
    private static final String ENV_ZHIPUAI_API_KEY = "ZHIPUAI_API_KEY";

    /**
     * 尝试从系统环境获取base_url
     * 1. 先尝试从环境变量中获取
     * 2. 从JVM启动参数获取（安全原因不推荐，兼容老代码）
     * @return
     */
    public static String getBaseUrl() {
        String baseUrl = System.getenv(ENV_BASE_URL_KEY);
        if (StringUtils.isNotEmpty(baseUrl)) {
            return baseUrl;
        }
        baseUrl = System.getProperty(ENV_BASE_URL_KEY);
        if (StringUtils.isNotEmpty(baseUrl)) {
            return baseUrl;
        }
        return null;
    }

    public static String getApiKey() {
        String apiKey = System.getenv(ENV_ZHIPUAI_API_KEY);
        if (StringUtils.isNotEmpty(apiKey)) {
            return apiKey;
        }
        apiKey = System.getProperty(ENV_ZHIPUAI_API_KEY);
        if (StringUtils.isNotEmpty(apiKey)) {
            return apiKey;
        }
        return null;
    }
}
