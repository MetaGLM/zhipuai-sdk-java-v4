package com.zhipu.oapi.core.httpclient;

import com.google.gson.Gson;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.core.request.RawRequest;
import com.zhipu.oapi.core.response.RawResponse;
import com.zhipu.oapi.service.v4.model.TaskStatus;
import com.zhipu.oapi.service.v4.api.ChatApiService;
import com.zhipu.oapi.service.v4.model.ChatMessageAccumulator;
import com.zhipu.oapi.service.v4.model.Choice;
import com.zhipu.oapi.service.v4.model.ModelData;
import io.reactivex.Flowable;
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
        Map<String, Object> data = new HashMap<>();
        data.put("request_id", request.getBody().get("request_id"));
        try {
            flowable = service.streamChatCompletion(request.getBody());
        } catch (Exception e) {
            System.out.println("streamChatCompletion error:" + e.getMessage());
            resp.setStatusCode(500);
            resp.setSuccess(false);
            data.put("task_status", TaskStatus.FAIL);
            resp.setBody(new Gson().toJson(data));
            return resp;
        }
        AtomicBoolean isFirst = new AtomicBoolean(true);
        ChatMessageAccumulator chatMessageAccumulator = service.mapStreamToAccumulator(flowable)
                .doOnNext(accumulator -> {
                    {
                        if (isFirst.getAndSet(false)) {
                            System.out.print("Response: ");
                        }
                        if (accumulator.getDelta() != null && accumulator.getDelta().getContent() != null) {
                            System.out.print(accumulator.getDelta().getContent());
                        }
                    }
                })
                .doOnComplete(System.out::println)
                .lastElement()
                .blockingGet();
        resp.setSuccess(true);
        resp.setStatusCode(200);
        Choice choice = new Choice("stop", 0L, chatMessageAccumulator.getDelta());
        List<Choice> choices = new ArrayList<>();
        choices.add(choice);
        data.put("choices", choices);
        data.put("task_status", TaskStatus.SUCCESS);
        data.put("usage", chatMessageAccumulator.getUsage());
        data.put("id", chatMessageAccumulator.getId());
        data.put("created", chatMessageAccumulator.getCreated());
        resp.setBody(new Gson().toJson(data));
        return resp;
    }


}
