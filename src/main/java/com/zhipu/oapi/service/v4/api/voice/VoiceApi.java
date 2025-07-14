package com.zhipu.oapi.service.v4.api.voice;

import com.zhipu.oapi.service.v4.voice.model.AddVoiceResponse;
import com.zhipu.oapi.service.v4.voice.model.TtsVoiceListResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface VoiceApi {
    @POST("v4/voice/list")
    Call<TtsVoiceListResponse> listTtsVoices(@Header("Authorization") String authorization);

    @Multipart
    @POST("v4/voice/add")
    Call<AddVoiceResponse> addVoice(
        @Header("Authorization") String authorization,
        @Part("voiceName") RequestBody voiceName,
        @Part("voiceText") RequestBody voiceText,
        @Part MultipartBody.Part file
    );
} 