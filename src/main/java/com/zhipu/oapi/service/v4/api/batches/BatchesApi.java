package com.zhipu.oapi.service.v4.api.batches;

import com.zhipu.oapi.service.v4.batchs.Batch;
import com.zhipu.oapi.service.v4.batchs.BatchCreateParams;
import com.zhipu.oapi.service.v4.batchs.BatchPage;
import io.reactivex.Single;
import retrofit2.http.*;

public interface BatchesApi {


    @POST("batches")
    Single<Batch> batchesCreate(@Body BatchCreateParams batchCreateParams);


    @GET("batches/{batch_id}")
    Single<Batch> batchesRetrieve(@Path("batch_id") String batchId);

    @GET("batches")
    Single<BatchPage> batchesList(@Query("after") String after, @Query("limit") Integer limit);


    @POST("batches/{batch_id}/cancel")
    Single<Batch> batchesCancel(@Path("batch_id") String batchId);

}
