package com.zhipu.oapi.service.v4.api.embedding;

import com.zhipu.oapi.service.v4.embedding.EmbeddingResult;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.Map;

public interface EmbeddingApi {


    @POST("embeddings")
    Single<EmbeddingResult> createEmbeddings(@Body Map<String,Object> request);


}
