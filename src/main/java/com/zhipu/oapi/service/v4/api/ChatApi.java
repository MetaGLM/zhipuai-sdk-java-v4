package com.zhipu.oapi.service.v4.api;


import com.zhipu.oapi.service.v4.batchs.Batch;
import com.zhipu.oapi.service.v4.batchs.BatchCreateParams;
import com.zhipu.oapi.service.v4.batchs.BatchPage;
import com.zhipu.oapi.service.v4.file.FileDeleted;
import com.zhipu.oapi.service.v4.fine_turning.*;
import com.zhipu.oapi.service.v4.embedding.EmbeddingResult;
import com.zhipu.oapi.service.v4.file.File;
import com.zhipu.oapi.service.v4.file.QueryFileResult;
import com.zhipu.oapi.service.v4.image.ImageResult;
import com.zhipu.oapi.service.v4.model.ModelData;
import io.reactivex.Single;
import lombok.Getter;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.Map;

public interface ChatApi {


    @Streaming
    @POST("chat/completions")
    Call<ResponseBody> createChatCompletionStream(@Body Map<String,Object> request);



    @POST("async/chat/completions")
    Single<ModelData> createChatCompletionAsync(@Body Map<String,Object> request);


    @POST("chat/completions")
    Single<ModelData> createChatCompletion(@Body Map<String,Object> request);


    @GET("async-result/{id}")
    Single<ModelData> queryAsyncResult(@Path("id") String id);


    @POST("images/generations")
    Single<ImageResult> createImage(@Body Map<String,Object> request);


    @POST("embeddings")
    Single<EmbeddingResult> createEmbeddings(@Body Map<String,Object> request);



    @POST("files")
    Single<File> uploadFile(@Body MultipartBody multipartBody);


    @GET("files/{file_id}")
    Single<File> retrieveFile(@Path("file_id") String fileId);

    @DELETE("files/{file_id}")
    Single<FileDeleted> deletedFile(@Path("file_id") String fileId);


    @GET("files")
    Single<QueryFileResult> queryFileList(@Query("after") String after
            , @Query("purpose") String purpose,@Query("order") String order,@Query("limit") Integer limit);

    @Streaming
    @GET("files/{file_id}/content")
    Call<ResponseBody> fileContent(@Path("file_id") String fileId);


    @POST("fine_tuning/jobs")
    Single<FineTuningJob> createFineTuningJob(@Body FineTuningJobRequest request);

    @GET("fine_tuning/jobs/{fine_tuning_job_id}/events")
    Single<FineTuningEvent> listFineTuningJobEvents(@Path("fine_tuning_job_id") String fineTuningJobId,@Query("limit") Integer limit,@Query("after") String after);

    @GET("fine_tuning/jobs/{fine_tuning_job_id}")
    Single<FineTuningJob> retrieveFineTuningJob(@Path("fine_tuning_job_id") String fineTuningJobId,@Query("limit") Integer limit,@Query("after") String after);


    @GET("fine_tuning/jobs")
    Single<PersonalFineTuningJob> queryPersonalFineTuningJobs(@Query("limit") Integer limit
            , @Query("after") String after);



    @POST("fine_tuning/jobs/{fine_tuning_job_id}/cancel")
    Single<FineTuningJob> cancelFineTuningJob(@Path("fine_tuning_job_id") String fineTuningJobId);


    @DELETE("fine_tuning/jobs/{fine_tuning_job_id}")
    Single<FineTuningJob> deleteFineTuningJob(@Path("fine_tuning_job_id") String fineTuningJobId);

    @DELETE("fine_tuning/fine_tuned_models/{fine_tuned_model}")
    Single<FineTunedModelsStatus> deleteFineTuningModel(@Path("fine_tuned_model") String fineTunedModel);


    @POST("batches")
    Single<Batch> batchesCreate(@Body BatchCreateParams batchCreateParams);


    @GET("batches/{batch_id}")
    Single<Batch> batchesRetrieve(@Path("batch_id") String batchId);

    @GET("batches")
    Single<BatchPage> batchesList(@Query("after") String after, @Query("limit") Integer limit);


    @POST("batches/{batch_id}/cancel")
    Single<Batch> batchesCancel(@Path("batch_id") String batchId);



}




