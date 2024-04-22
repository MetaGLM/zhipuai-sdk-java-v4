package com.zhipu.oapi.core.httpclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.core.request.RawRequest;
import com.zhipu.oapi.core.response.RawResponse;
import com.zhipu.oapi.service.v4.model.*;
import com.zhipu.oapi.service.v4.api.ChatApiService;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


@Slf4j
public class OkHttpTransport extends BaseHttpTransport {

    private OkHttpClient okHttpClient;




    public OkHttpTransport(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override
    public RawResponse executePost(RawRequest request) throws Exception {
        String reqBodyStr =  objectMapper.writeValueAsString(request.getBody());
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

        Request okHttpReq = new Request.Builder()
                .url(request.getReqUrl())
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
     * @return RawResponse
     * @throws Exception
     */
    @Override
    public RawResponse sseExecute(RawRequest request) throws Exception {
        ChatApiService service = new ChatApiService(request.getToken());
        RawResponse resp = new RawResponse();
        Flowable<ModelData> flowable;
        try {
            flowable  = service.streamChatCompletion(request.getBody());
        } catch (Exception e) {
            System.out.println("streamChatCompletion error:" + e.getMessage());
            resp.setStatusCode(500);
            resp.setSuccess(false);
            return resp;
        }
        resp.setSuccess(true);
        resp.setStatusCode(200);
        resp.setFlowable(flowable);
        return resp;
    }


}
