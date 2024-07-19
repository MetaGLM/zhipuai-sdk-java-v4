package com.zhipu.oapi.service.v4.api;

import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.core.model.ClientRequest;
import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.api.chat.ChatApi;
import com.zhipu.oapi.service.v4.api.videos.VideosApi;
import com.zhipu.oapi.service.v4.batchs.Batch;
import com.zhipu.oapi.service.v4.batchs.BatchResponse;
import com.zhipu.oapi.service.v4.model.ModelData;
import com.zhipu.oapi.service.v4.videos.VideoCreateParams;
import com.zhipu.oapi.service.v4.videos.VideoObject;
import com.zhipu.oapi.service.v4.videos.VideosResponse;
import com.zhipu.oapi.utils.RequestSupplier;
import io.reactivex.Single;
import okhttp3.OkHttpClient;

import java.util.Map;

public class VideosClientApiService extends ClientBaseService {

    private final VideosApi videosApi;
    /**
     * Creates a new ClientBaseService that wraps OkHttpClient
     *
     * @param client  retrofit instance
     * @param baseUrl base url of the api
     */
    public VideosClientApiService(OkHttpClient client, String baseUrl) {
        super(client, baseUrl);
        this.videosApi = super.retrofit.create(VideosApi.class);

    }


    // 修改 videoGenerations 方法，使其返回 VideoGenerationChain
    public VideoGenerationChain videoGenerations(VideoCreateParams params) {
        Single<VideoObject> videoObjectSingle = videosApi.videoGenerations(params);
        return new VideoGenerationChain(params, videoObjectSingle);
    }


    // 修改 videoGenerationsResult 方法，使其返回 VideoGenerationChain
    public VideoGenerationChain videoGenerationsResult(VideoCreateParams params) {
        Single<VideoObject> videoObjectSingle = videosApi.videoGenerationsResult(params.getId());
        return new VideoGenerationChain(params, videoObjectSingle);
    }


    public static class VideoGenerationChain extends GenerationChain<VideoObject, VideosResponse> {
        private final VideoCreateParams params;
        private final Single<VideoObject> videoObjectSingle;

        public VideoGenerationChain(VideoCreateParams params, Single<VideoObject> videoObjectSingle) {
            this.params = params;
            this.videoObjectSingle = videoObjectSingle;
        }

        public VideosResponse apply(final ClientV4 clientV4) {
            RequestSupplier<VideoCreateParams, VideoObject> supplier = (params) -> {
                return videoObjectSingle;
            };
            return clientV4.executeRequest(params, supplier, VideosResponse.class);
        }

    }

}
