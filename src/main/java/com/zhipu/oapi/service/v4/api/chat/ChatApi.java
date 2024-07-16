package com.zhipu.oapi.service.v4.api.chat;


import com.zhipu.oapi.service.v4.model.ModelData;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface ChatApi {


    @Streaming
    @POST("chat/completions")
    Call<ResponseBody> createChatCompletionStream(@Body Map<String,Object> request);



    @POST("async/chat/completions")
    Single<ModelData> createChatCompletionAsync(@Body Map<String,Object> request);


    @POST("chat/completions")
    Single<ModelData> createChatCompletion(@Body Map<String,Object> request);


    @GET("async-result/{id}")
    Single<ModelData> queryAsyncResult(@Path("id") String id);




}




