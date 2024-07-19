package com.zhipu.oapi.service.v4.api.videos;

import com.zhipu.oapi.service.v4.tools.WebSearchPro;
import com.zhipu.oapi.service.v4.videos.VideoCreateParams;
import com.zhipu.oapi.service.v4.videos.VideoObject;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface VideosApi {


    @POST("videos/generations")
    Single<VideoObject> videoGenerations(@Body VideoCreateParams request);

    @GET("async-result/{id}")
    Single<VideoObject> videoGenerationsResult(@Path("id") String id);
}
