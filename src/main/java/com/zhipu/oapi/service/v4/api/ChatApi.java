package com.zhipu.oapi.service.v4.api;


import com.zhipu.oapi.service.v4.fine_turning.FineTuningEvent;
import com.zhipu.oapi.service.v4.fine_turning.FineTuningJob;
import com.zhipu.oapi.service.v4.fine_turning.FineTuningJobRequest;
import com.zhipu.oapi.service.v4.fine_turning.PersonalFineTuningJob;
import com.zhipu.oapi.service.v4.model.ChatCompletionAsyncResult;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatCompletionResult;
import com.zhipu.oapi.service.v4.embedding.EmbeddingRequest;
import com.zhipu.oapi.service.v4.embedding.EmbeddingResult;
import com.zhipu.oapi.service.v4.file.File;
import com.zhipu.oapi.service.v4.file.QueryFileResult;
import com.zhipu.oapi.service.v4.image.ImageResult;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface ChatApi {


    @Streaming
    @POST("/api/paas/v4/chat/completions")
    Call<ResponseBody> createChatCompletionStream(@Body Map<String,Object> request);



    @POST("/api/paas/v4/async/chat/completions")
    Single<ChatCompletionAsyncResult> createChatCompletionAsync(@Body Map<String,Object> request);


    @POST("/api/paas/v4/chat/completions")
    Single<ChatCompletionResult> createChatCompletion(@Body Map<String,Object> request);


    @GET("/api/paas/v4/async-result/{id}")
    Single<ChatCompletionResult> queryAsyncResult(@Path("id") String id);


    @POST("/api/paas/v4/images/generations")
    Single<ImageResult> createImage(@Body Map<String,Object> request);


    @POST("/api/paas/v4/embeddings")
    Single<EmbeddingResult> createEmbeddings(@Body EmbeddingRequest request);



    @POST("/api/paas/v4/files")
    Single<File> uploadFile(@Body MultipartBody multipartBody);


    @GET("/api/paas/v4/files")
    Single<QueryFileResult> queryFileList(@Query("after") String after
            , @Query("purpose") String purpose,@Query("order") String order,@Query("limit") Integer limit);


    @POST("/api/paas/v4/fine_tuning/jobs")
    Single<FineTuningJob> createFineTuningJob(@Body FineTuningJobRequest request);

    @GET("/api/paas/v4/fine_tuning/jobs/{fine_tuning_job_id}/events")
    Single<FineTuningEvent> listFineTuningJobEvents(@Path("fine_tuning_job_id") String fineTuningJobId,@Query("limit") Integer limit,@Query("after") Integer after);

    @GET("/api/paas/v4/fine_tuning/jobs/{fine_tuning_job_id}")
    Single<FineTuningJob> retrieveFineTuningJob(@Path("fine_tuning_job_id") String fineTuningJobId,@Query("limit") Integer limit,@Query("after") Integer after);


    @GET("/api/paas/v4/fine_tuning/jobs")
    Single<PersonalFineTuningJob> queryPersonalFineTuningJobs(@Query("limit") Integer limit
            , @Query("after") String after);

}




