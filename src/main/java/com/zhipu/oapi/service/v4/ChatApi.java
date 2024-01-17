package com.zhipu.oapi.service.v4;


import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface ChatApi {


    @Streaming
    @POST("/api/paas/v4/chat/completions")
    Call<ResponseBody> createChatCompletionStream(@Body Map<String,Object> request);



    @POST("/api/paas/v4/async/chat/completions")
    Single<ChatCompletionAsyncResult> createChatCompletionAsync(@Body Map<String,Object> request);


    @POST("/api/paas/v4/chat/completions")
    Single<ChatCompletionResult> createChatCompletion(@Body Map<String,Object> request);


    @GET("/api/paas/v4/async-result/{id}")
    Single<ChatCompletionResult> queryAsyncResult(@Path("id") String id);

}




