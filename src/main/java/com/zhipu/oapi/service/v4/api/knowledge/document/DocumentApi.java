package com.zhipu.oapi.service.v4.api.knowledge.document;

import com.zhipu.oapi.service.v4.knowledge.document.DocumentData;
import com.zhipu.oapi.service.v4.knowledge.document.DocumentEditParams;
import com.zhipu.oapi.service.v4.knowledge.document.DocumentObject;
import com.zhipu.oapi.service.v4.knowledge.document.DocumentPage;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.Map;

public interface DocumentApi {

    @POST("files")
    Single<DocumentObject> createDocument(@Body MultipartBody document);

    @PUT("document/{document_id}")
    Single<Response<Void>> modifyDocument(@Path("document_id") String documentId,
                                          @Body DocumentEditParams documentEditParams
    );

    @DELETE("document/{document_id}")
    Single<Response<Void>> deleteDocument(@Path("document_id") String documentId);

    @GET("files")
    Single<DocumentPage> queryDocumentList(
            @QueryMap Map<String, Object> options
            );

    @GET("document/{document_id}")
    Single<DocumentData> retrieveDocument(@Path("document_id") String documentId);
}
