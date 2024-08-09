package com.zhipu.oapi.service.v4.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.service.v4.api.knowledge.KnowledgeApi;
import com.zhipu.oapi.service.v4.api.knowledge.document.DocumentApi;
import com.zhipu.oapi.service.v4.knowledge.KnowledgeBaseParams;
import com.zhipu.oapi.service.v4.knowledge.KnowledgeEditResponse;
import com.zhipu.oapi.service.v4.knowledge.document.*;
import com.zhipu.oapi.utils.RequestSupplier;
import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Response;

import java.util.Date;
import java.util.Map;

public class DocumentClientApiService  extends ClientBaseService {

    private final DocumentApi documentApi;
    /**
     * Creates a new ClientBaseService that wraps OkHttpClient
     *
     * @param client  retrofit instance
     * @param baseUrl base url of the api
     */
    public DocumentClientApiService(OkHttpClient client, String baseUrl) {
        super(client, baseUrl);

        this.documentApi = super.retrofit.create(DocumentApi.class);
    }


    // 修改 createDocument 方法，使其返回 DocumentCreateGenerationChain
    public DocumentCreateGenerationChain createDocument(DocumentCreateParams params) throws JsonProcessingException {

        // getUploadDetail和getFilePath只能存在一个
        if (params.getUploadDetail()!=null && params.getFilePath()!=null){
            throw new RuntimeException("upload detail 和 file path 只能存在一个");
        }
        MultipartBody.Builder formBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        if (params.getFilePath() !=null){

            java.io.File file = new java.io.File(params.getFilePath());
            if(!file.exists()){
                throw new RuntimeException("file not found");
            }
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
            formBodyBuilder.addPart(filePart);
        }

        if (params.getUploadDetail()!=null){
            formBodyBuilder.addFormDataPart("upload_detail", null,
                    RequestBody.create(MediaType.parse("application/json"),
                            mapper.writeValueAsString( params.getUploadDetail())));
        }

        formBodyBuilder.addFormDataPart("knowledge_id", params.getKnowledgeId());
        if (params.getSentenceSize() !=null){

            formBodyBuilder.addFormDataPart("sentence_size", String.valueOf(params.getSentenceSize()));
        }
        formBodyBuilder.addFormDataPart("purpose", params.getPurpose());


        if (params.getCustomSeparator()!=null){

            formBodyBuilder.addFormDataPart("custom_separator", null,
                    RequestBody.create(MediaType.parse("application/json"),
                            mapper.writeValueAsString( params.getCustomSeparator())));
        }


        if (params.getExtraJson()!=null){
            for (String s : params.getExtraJson().keySet()) {
                if(params.getExtraJson().get(s) instanceof String
                        || params.getExtraJson().get(s) instanceof Number
                        || params.getExtraJson().get(s) instanceof Boolean
                        || params.getExtraJson().get(s) instanceof Character

                ) {

                    formBodyBuilder.addFormDataPart(s, params.getExtraJson().get(s).toString());
                }else if(params.getExtraJson().get(s) instanceof Date) {
                    Date date = (Date) params.getExtraJson().get(s);
                    formBodyBuilder.addFormDataPart(s, String.valueOf(date.getTime()));
                }else {

                    formBodyBuilder.addFormDataPart(s, null,
                            RequestBody.create(MediaType.parse("application/json"),
                                    mapper.writeValueAsString(params.getExtraJson().get(s))));

                }

            }
        }
        MultipartBody multipartBody = formBodyBuilder.build();

        Single<DocumentObject> documentObjectSingle = documentApi.createDocument(multipartBody);

        return new DocumentCreateGenerationChain(params, documentObjectSingle);
    }

    // 修改 modifyDocument 方法，使其返回 DocumentEditGenerationChain
    public DocumentEditGenerationChain modifyDocument(DocumentEditParams params) {
        Single<Response<Void>> single = documentApi.modifyDocument(params.getId(),params);
        return new DocumentEditGenerationChain(params, single);
    }

    // 修改 deleteDocument 方法，使其返回 DocumentEditGenerationChain
    public DocumentEditGenerationChain deleteDocument(DocumentEditParams params) {
        Single<Response<Void>> single = documentApi.deleteDocument(params.getId());
        return new DocumentEditGenerationChain(params, single);
    }

    // 修改 queryDocumentList 方法，使其返回 DocumentPageGenerationChain
    public DocumentPageGenerationChain queryDocumentList(QueryDocumentRequest params) {
        Single<DocumentPage> single = documentApi.queryDocumentList(params.getOptions());
        return new DocumentPageGenerationChain(params, single);
    }
    // 修改 queryDocumentList 方法，使其返回 DocumentPageGenerationChain
    public DocumentDataGenerationChain retrieveDocument(DocumentEditParams params) {
        Single<DocumentData> single = documentApi.retrieveDocument(params.getId());
        return new DocumentDataGenerationChain(params, single);
    }


    public static class DocumentCreateGenerationChain extends GenerationChain<DocumentObject, DocumentObjectResponse> {
        private final DocumentCreateParams params;
        private final Single<DocumentObject> objectSingle;

        public DocumentCreateGenerationChain(DocumentCreateParams params, Single<DocumentObject> objectSingle) {
            this.params = params;
            this.objectSingle = objectSingle;
        }

        public  DocumentObjectResponse apply(final ClientV4 clientV4) {
            RequestSupplier<DocumentCreateParams, DocumentObject> supplier = (params) -> {
                return objectSingle;
            };
            return clientV4.executeRequest(params, supplier, DocumentObjectResponse.class);
        }

    }


    public static class DocumentEditGenerationChain extends GenerationChain<Response<Void>, DocumentEditResponse> {
        private final DocumentEditParams params;
        private final Single<Response<Void>> objectSingle;

        public DocumentEditGenerationChain(DocumentEditParams params, Single<Response<Void>> objectSingle) {
            this.params = params;
            this.objectSingle = objectSingle;
        }

        public  DocumentEditResponse apply(final ClientV4 clientV4) {
            RequestSupplier<DocumentEditParams, Response<Void>> supplier = (params) -> {
                return objectSingle;
            };
            return clientV4.executeRequest(params, supplier, DocumentEditResponse.class);
        }

    }

    public static class DocumentPageGenerationChain extends GenerationChain<DocumentPage, QueryDocumentApiResponse> {
        private final QueryDocumentRequest params;
        private final Single<DocumentPage> objectSingle;

        public DocumentPageGenerationChain(QueryDocumentRequest params, Single<DocumentPage> objectSingle) {
            this.params = params;
            this.objectSingle = objectSingle;
        }

        public  QueryDocumentApiResponse apply(final ClientV4 clientV4) {
            RequestSupplier<Map<String, Object>, DocumentPage> supplier = (params) -> {
                return objectSingle;
            };
            return clientV4.executeRequest(params, supplier, QueryDocumentApiResponse.class);
        }

    }





    public static class DocumentDataGenerationChain extends GenerationChain<DocumentData, DocumentDataResponse> {
        private final DocumentEditParams params;
        private final Single<DocumentData> objectSingle;

        public DocumentDataGenerationChain(DocumentEditParams params, Single<DocumentData> objectSingle) {
            this.params = params;
            this.objectSingle = objectSingle;
        }

        public  DocumentDataResponse apply(final ClientV4 clientV4) {
            RequestSupplier<DocumentEditParams, DocumentData> supplier = (params) -> {
                return objectSingle;
            };
            return clientV4.executeRequest(params, supplier, DocumentDataResponse.class);
        }

    }


}
