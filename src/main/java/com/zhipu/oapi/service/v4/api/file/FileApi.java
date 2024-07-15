package com.zhipu.oapi.service.v4.api.file;

import com.zhipu.oapi.service.v4.file.File;
import com.zhipu.oapi.service.v4.file.FileDeleted;
import com.zhipu.oapi.service.v4.file.QueryFileResult;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;


public interface FileApi {



    @POST("files")
    Single<File> uploadFile(@Body MultipartBody multipartBody);


    @GET("files/{file_id}")
    Single<File> retrieveFile(@Path("file_id") String fileId);

    @DELETE("files/{file_id}")
    Single<FileDeleted> deletedFile(@Path("file_id") String fileId);


    @GET("files")
    Single<QueryFileResult> queryFileList(@Query("after") String after
            , @Query("purpose") String purpose, @Query("order") String order, @Query("limit") Integer limit);

    @Streaming
    @GET("files/{file_id}/content")
    Call<ResponseBody> fileContent(@Path("file_id") String fileId);


}
