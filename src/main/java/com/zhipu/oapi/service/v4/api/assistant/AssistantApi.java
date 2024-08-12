package com.zhipu.oapi.service.v4.api.assistant;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Streaming;

import java.util.Map;

public interface AssistantApi {


    @Streaming
    @POST("assistant")
    Call<ResponseBody> assistantCompletionStream(@Body Map<String,Object> request);

}
