package com.zhipu.oapi.service.v4.api.agents;


import com.zhipu.oapi.service.v4.model.ModelData;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface AgentsApi {

    @Streaming
    @POST("v1/agents")
    Call<ResponseBody> agentsCompletionStream(@Body Map<String,Object> request);

    @POST("v1/agents")
    Single<ModelData> agentsCompletionSync(@Body Map<String,Object> request);


    @POST("v1/agents/async-result")
    Single<ModelData> queryAgentsAsyncResult(@Body Map<String,Object> request);




}




