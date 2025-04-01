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

    /**
     * tts接口(Text to speech)
     * @param request
     * @return
     */
    @POST("audio/speech")
    Single<ResponseBody> audioSpeech(@Body Map<String,Object> request);


    /**
     * 语音克隆接口
     * @param request
     * @return
     */
    @Multipart
    @POST("audio/customization")
    Single<ResponseBody> audioCustomization(
            @PartMap Map<String, RequestBody> request,
            @Part MultipartBody.Part voiceData);

    @Streaming
    @POST("audio/transcriptions")
    @Multipart
    Call<ResponseBody> audioTranscriptionsStream(@PartMap Map<String, RequestBody> request,
                                                 @Part MultipartBody.Part file);


    @POST("audio/transcriptions")
    @Multipart
    Single<ModelData> audioTranscriptions(@PartMap Map<String, RequestBody> request,
                                          @Part MultipartBody.Part file);
}