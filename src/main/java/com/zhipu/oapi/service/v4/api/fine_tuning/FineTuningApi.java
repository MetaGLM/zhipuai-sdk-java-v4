package com.zhipu.oapi.service.v4.api.fine_tuning;

import com.zhipu.oapi.service.v4.fine_turning.*;
import io.reactivex.Single;
import retrofit2.http.*;

public interface FineTuningApi {



    @POST("fine_tuning/jobs")
    Single<FineTuningJob> createFineTuningJob(@Body FineTuningJobRequest request);

    @GET("fine_tuning/jobs/{fine_tuning_job_id}/events")
    Single<FineTuningEvent> listFineTuningJobEvents(@Path("fine_tuning_job_id") String fineTuningJobId, @Query("limit") Integer limit, @Query("after") String after);

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

}
