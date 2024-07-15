package com.zhipu.oapi.service.v4.api.images;

import com.zhipu.oapi.service.v4.image.ImageResult;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.Map;

public interface ImagesApi {


    @POST("images/generations")
    Single<ImageResult> createImage(@Body Map<String,Object> request);


}
