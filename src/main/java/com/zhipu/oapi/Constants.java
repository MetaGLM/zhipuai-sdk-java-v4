package com.zhipu.oapi;

import okhttp3.MediaType;

public class Constants {

    // req urls
    public static final String chatGlm6BReqUrl = "https://maas.aminer.cn/api/paas/model/v2/open/engines/chatglm_qa_6b/chatglm_6b";
    public static final String  queryRequestTaskResultUrl = "https://maas.aminer.cn/api/paas/request-task/query-request-task-result/{taskOrderNo}";

    // v3 req urls
    public static final String invokeModelV3Url = "https://open.bigmodel.cn/api/paas/v3/model-api/{model}/{invoke_method}";
    public static final String invokeModelV3UrlDev = "https://test-maas.aminer.cn/stage-api/paas/v3/model-api/{model}/{invoke_method}";
    public static final String queryResultV3Url = "https://open.bigmodel.cn/api/paas/v3/model-api/-/async-invoke/{task_id}";
    public static final String queryResultV3UrlDev = "https://test-maas.aminer.cn/stage-api/paas/v3/model-api/-/async-invoke/{task_id}";

    // model ids
    public static final String ModelChatGLM6B = "chatGLM_6b_SSE";
    public static final String ModelArticle = "article-model";
    public static final String ModelTitleGeneration = "title-creation";
    // 代码生成
    public static final String ModelMultiLingualCodeGenerate = "multilingual-code-generate";
    // 多行代码生成
    public static final String ModelCodegeex = "codegeex-generate-block";
    public static final String ModelCodeMultiLingualCodeTranslate = "multilingual-code-translate";
    public static final String ModelText2Image = "text2image";
    public static final String ModelTest= "example-model";

    // invoke methods
    public static final String invokeMethodAsync = "async-invoke";
    public static final String invokeMethodSse = "sse-invoke";

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
    public static final String resultKeySuccess = "success";
    public static final String resultKeyData = "data";
}
