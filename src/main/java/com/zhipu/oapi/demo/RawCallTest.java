package com.zhipu.oapi.demo;

import cn.hutool.core.util.IdUtil;
import com.google.gson.Gson;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.core.ConfigV3;
import com.zhipu.oapi.core.cache.LocalCache;
import com.zhipu.oapi.core.token.GlobalTokenManager;
import com.zhipu.oapi.service.v3.StandardEventSourceListener;
import com.zhipu.oapi.utils.StringUtils;
import com.zhipu.oapi.utils.WuDaoUtils;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.sse.RealEventSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RawCallTest {

    public static void main(String[] args) throws Exception {
        testGlm6b();
        testQueryResult();
        testSseCall();
    }

    public static void testGlm6b() throws Exception{
        String authToken = getToken();
        if (StringUtils.isEmpty(authToken)) {
            throw new Exception("get token fail!");
        }

        String requestTaskNo = IdUtil.getSnowflake(1, 2).nextIdStr();
        Map<String, Object> paramsMap = new HashMap();
        paramsMap.put("requestTaskNo", requestTaskNo);
        paramsMap.put("prompt", "你都可以做些什么事");
        List<Map<String, Object>> history = new ArrayList<>();
        Map<String, Object> qa = new HashMap<>();
        qa.put("query", "你好");
        qa.put("answer", "我是人工智能助手");
        Map<String, Object> qa2 = new HashMap<>();
        qa2.put("query", "你叫什么名字");
        qa2.put("answer", "我叫chatGLM");
        history.add(qa);
        history.add(qa2);
        paramsMap.put("history", history);
        Map resultMap = WuDaoUtils.executePost(TestConstants.MODEL_REQUEST_URL, authToken, paramsMap);
        Double code = (Double) resultMap.get("code");
        if (code.intValue() == 200) {
            String data = String.valueOf(resultMap.get("data"));
            System.out.println(data);
        }
    }

    public static void testQueryResult() throws Exception{
        String authToken = getToken();
        if (StringUtils.isEmpty(authToken)) {
            throw new Exception("get token fail!");
        }

        String taskOrderNo = "75550824942899442967580334428090869692";
        String url = TestConstants.QUERY_RESULT_URL +  "/" + taskOrderNo;
        Map resultMap = WuDaoUtils.executeGet(url, authToken, null);
        Double code = (Double) resultMap.get("code");
        if (code.intValue() == 200) {
            String data = String.valueOf(resultMap.get("data"));
            System.out.println(data);
        }
    }

    public static void testSseCall() throws Exception {
        ConfigV3 config = new ConfigV3();
        config.setApiKey(TestConstants.testKeyV3);
        config.setApiSecret(TestConstants.testSecretV3);
        config.setCache(LocalCache.getInstance());

        // url
        Map<String, String> pathParams = new HashMap<>();
        //pathParams.put("model", Constants.ModelChatGLM6B);
        pathParams.put("model", "example-model");
        String url = StringUtils.formatString(TestConstants.ssModelUrlDev, "{", "}", pathParams);
        // headers
        String token = GlobalTokenManager.getTokenManagerV3().getToken(config);
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.authHeaderKey, token);
        headers.put(Constants.CONTENT_TYPE, Constants.JSON_CONTENT_TYPE);
        headers.put(Constants.USER_AGENT, Constants.DEFAULT_USER_AGENT);
        headers.put(Constants.ACCEPT, Constants.SSE_CONTENT_TYPE);
        // body
        Map<String, Object> body = new HashMap<>();
        //body.put("model", Constants.ModelChatGLM6B);
        body.put("model", "example-model");
        body.put("prompt", "ChatGPT和你哪个更强大");
        Map<String, String> history1 = new HashMap<>();
        history1.put("query", "你好");
        history1.put("answer", "我是人工智能助手");
        Map<String, String> history2 = new HashMap<>();
        history2.put("query", "你叫什么名字");
        history2.put("answer", "我叫chatGLM");
        List<Map> history = new ArrayList<>();
        history.add(history1);
        history.add(history2);
        body.put("history", history);
        String reqBodyStr = new Gson().toJson(body);
        RequestBody formBody = RequestBody.create(Constants.jsonMediaType, reqBodyStr);

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .headers(Headers.of(headers))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.MINUTES)
                .build();

        StandardEventSourceListener listener = new StandardEventSourceListener();

        RealEventSource eventSource = new RealEventSource(request, listener);

        eventSource.connect(okHttpClient);


        Thread.sleep(5 * 1000);
        String output = listener.getOutputText();

        System.out.println("output from response: " + output);

    }

    private static String getToken() {
        try {
            Map<String, Object> resultMap = WuDaoUtils.getToken(TestConstants.AUTH_TOKEN_URL, TestConstants.API_KEY, TestConstants.PUBLIC_KEY);
            Double code = (Double) resultMap.get("code");
            if (code.intValue() == 200) {
                String token = String.valueOf(resultMap.get("data"));
                return token;
            }
        } catch (Exception e) {
            System.out.println("获取token失败");
            e.printStackTrace();
        }
        return null;
    }

}
