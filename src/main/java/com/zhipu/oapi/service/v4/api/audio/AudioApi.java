package com.zhipu.oapi.service.v4.api.audio;

import io.reactivex.Single;
import okhttp3.ResponseBody;
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
    @POST("audio/customization")
    Single<ResponseBody> audioCustomization(@Body Map<String,Object> request);

}
