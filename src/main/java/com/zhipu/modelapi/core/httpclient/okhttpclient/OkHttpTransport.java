package com.zhipu.modelapi.core.httpclient.okhttpclient;

import com.google.gson.Gson;
import com.zhipu.modelapi.Constants;
import com.zhipu.modelapi.core.httpclient.BaseHttpTransport;
import com.zhipu.modelapi.core.request.RawRequest;
import com.zhipu.modelapi.core.response.RawResponse;
import com.zhipu.modelapi.service.TaskStatus;
import com.zhipu.modelapi.service.v3.Choice;
import com.zhipu.modelapi.service.v3.ModelConstants;
import okhttp3.*;
import okhttp3.internal.sse.RealEventSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OkHttpTransport extends BaseHttpTransport {

    private OkHttpClient okHttpClient;

    public OkHttpTransport(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override
    public RawResponse executePost(RawRequest request) throws Exception {
        String reqBodyStr = new Gson().toJson(request.getBody());
        RequestBody formBody = RequestBody.create(Constants.jsonMediaType, reqBodyStr);
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.authHeaderKey, request.getToken());
        headers.put(Constants.CONTENT_TYPE, Constants.JSON_CONTENT_TYPE);
        headers.put(Constants.USER_AGENT, Constants.DEFAULT_USER_AGENT);
        Request okHttpRequest = new Request.Builder()
                .url(request.getReqUrl())
                .post(formBody)
                .headers(Headers.of(headers))
                .build();
        Response okHttpResp = okHttpClient.newCall(okHttpRequest).execute();
        ResponseBody respBody = okHttpResp.body();
        String resultDataStr = respBody.string();
        RawResponse resp = extractResp(resultDataStr);
        return resp;
    }

    @Override
    public RawResponse executeGet(RawRequest request) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.authHeaderKey, request.getToken());
        headers.put(Constants.CONTENT_TYPE, Constants.JSON_CONTENT_TYPE);
        headers.put(Constants.USER_AGENT, Constants.DEFAULT_USER_AGENT);
        String url = request.getReqUrl();
        Map<String, String> queryStr = request.getQueryStr();
        if (request.getQueryStr() != null && queryStr.size() > 0) {
            url = url + "?";
            int size = queryStr.size();
            int i=0;
            for (Map.Entry<String, String> entry : queryStr.entrySet()) {
                i++;
                url = url + String.format("%s=%s", entry.getKey(), entry.getValue());
                if (i < size) {
                    url = url + "&";
                }
            }
        }

        Request okHttpReq = new Request.Builder()
                .url(url)
                .get()
                .headers(Headers.of(headers))
                .build();
        Response okHttpResp = okHttpClient.newCall(okHttpReq).execute();
        ResponseBody respBody = okHttpResp.body();
        String resultDataStr = respBody.string();
        RawResponse resp = extractResp(resultDataStr);
        return resp;
    }

    /**
     * sse调用只会返回输出结果
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public RawResponse sseExecute(RawRequest request) throws Exception {

        // headers
        String token = request.getToken();
        Map<String, String> headers = new HashMap<>();
        headers.put(Constants.authHeaderKey, token);
        headers.put(Constants.CONTENT_TYPE, Constants.JSON_CONTENT_TYPE);
        headers.put(Constants.USER_AGENT, Constants.DEFAULT_USER_AGENT);
        headers.put(Constants.ACCEPT, Constants.SSE_CONTENT_TYPE);
        // url
        String url = request.getReqUrl();
        // body
        Map<String, Object> body = request.getBody();
        String reqBodyStr = new Gson().toJson(body);
        RequestBody formBody = RequestBody.create(Constants.jsonMediaType, reqBodyStr);
        // connect sse
        Request okHttpReq = new Request.Builder()
                .url(url)
                .post(formBody)
                .headers(Headers.of(headers))
                .build();
        RealEventSource eventSource = new RealEventSource(okHttpReq, request.getSseListener());
        eventSource.connect(okHttpClient);
        // get result
        String output = request.getSseListener().getOutputText();
        RawResponse resp = new RawResponse();
        resp.setSuccess(true);
        resp.setStatusCode(200);
        Map<String, Object> data = new HashMap<>();
        Choice choice = new Choice(ModelConstants.roleUser, output);
        List<Choice> choices = new ArrayList<>();
        choices.add(choice);
        data.put("choices", choices);
        data.put("taskStatus", TaskStatus.SUCCESS);
        resp.setBody(new Gson().toJson(data));
        return resp;
    }
}
