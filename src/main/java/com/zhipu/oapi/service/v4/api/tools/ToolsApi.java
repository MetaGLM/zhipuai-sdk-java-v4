package com.zhipu.oapi.service.v4.api.tools;

import com.zhipu.oapi.service.v4.tools.WebSearchPro;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface ToolsApi {


    @Streaming
    @POST("tools")
    Call<ResponseBody> webSearchStreaming(@Body Map<String,Object> request);



    @POST("tools")
    Single<WebSearchPro> webSearch(@Body Map<String,Object> request);


}
