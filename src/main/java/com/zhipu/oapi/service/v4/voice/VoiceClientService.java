package com.zhipu.oapi.service.v4.voice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhipu.oapi.service.v4.api.voice.VoiceApi;
import com.zhipu.oapi.service.v4.voice.model.AddVoiceResponse;
import com.zhipu.oapi.service.v4.voice.model.TtsVoiceListResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.File;
import java.io.IOException;

public class VoiceClientService {
    private final VoiceApi voiceApi;

    public VoiceClientService(OkHttpClient client, String baseUrl, ObjectMapper objectMapper) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
        this.voiceApi = retrofit.create(VoiceApi.class);
    }

    public TtsVoiceListResponse listTtsVoices(String token) throws IOException {
        Call<TtsVoiceListResponse> call = voiceApi.listTtsVoices(token);
        Response<TtsVoiceListResponse> response = call.execute();
        if (!response.isSuccessful()) {
            throw new IOException("Request failed: " + response.code());
        }
        return response.body();
    }

    public AddVoiceResponse addVoice(String token, String voiceName, String voiceText, File file) throws IOException {
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), voiceName);
        RequestBody textBody = RequestBody.create(MediaType.parse("text/plain"), voiceText);
        RequestBody fileBody = RequestBody.create(MediaType.parse("audio/wav"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), fileBody);

        Call<AddVoiceResponse> call = voiceApi.addVoice(token, nameBody, textBody, filePart);
        Response<AddVoiceResponse> response = call.execute();
        if (!response.isSuccessful()) {
            throw new IOException("Request failed: " + response.code());
        }
        return response.body();
    }
} 