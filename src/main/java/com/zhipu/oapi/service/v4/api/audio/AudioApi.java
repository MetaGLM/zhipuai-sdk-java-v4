package com.zhipu.oapi.service.v4.api.audio;

import com.zhipu.oapi.service.v4.model.ModelData;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;


public interface AudioApi {

    @Streaming
    @POST("audio/transcriptions")
    Call<ResponseBody> audioTranscriptionsStream(@PartMap Map<String, RequestBody> request,
                                                 @Part MultipartBody.Part file);


    @POST("audio/transcriptions")
    Single<ModelData> audioTranscriptions(@PartMap Map<String, RequestBody> request,
                                                 @Part MultipartBody.Part file);

}