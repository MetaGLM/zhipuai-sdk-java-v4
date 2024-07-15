package com.zhipu.oapi.service.v4.api;

import com.fasterxml.jackson.core.*;
import com.zhipu.oapi.core.response.HttpxBinaryResponseContent;
import com.zhipu.oapi.core.response.RawResponse;
import com.zhipu.oapi.service.v4.api.batches.BatchesApi;
import com.zhipu.oapi.service.v4.api.chat.ChatApi;
import com.zhipu.oapi.service.v4.api.embedding.EmbeddingApi;
import com.zhipu.oapi.service.v4.api.file.FileApi;
import com.zhipu.oapi.service.v4.api.fine_tuning.FineTuningApi;
import com.zhipu.oapi.service.v4.api.images.ImagesApi;
import com.zhipu.oapi.service.v4.api.tools.ToolsApi;
import com.zhipu.oapi.service.v4.batchs.Batch;
import com.zhipu.oapi.service.v4.batchs.BatchCreateParams;
import com.zhipu.oapi.service.v4.batchs.BatchPage;
import com.zhipu.oapi.service.v4.file.FileDeleted;
import com.zhipu.oapi.service.v4.file.UploadFileRequest;
import com.zhipu.oapi.service.v4.fine_turning.*;
import com.zhipu.oapi.service.v4.model.*;
import com.zhipu.oapi.service.v4.embedding.EmbeddingResult;
import com.zhipu.oapi.service.v4.file.QueryFileResult;
import com.zhipu.oapi.service.v4.file.QueryFilesRequest;
import com.zhipu.oapi.service.v4.image.ImageResult;
import com.zhipu.oapi.service.v4.tools.WebSearchPro;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Response;

import java.io.IOException;
import java.util.*;


public class ClientApiService extends ClientBaseService {

    private final ChatApi chatApi;
    private final BatchesApi batchesApi;
    private final EmbeddingApi embeddingApi;
    private final FileApi fileApi;
    private final FineTuningApi fineTuningApi;
    private final ImagesApi imagesApi;
    private final ToolsApi toolsApi;

    public ClientApiService(final OkHttpClient client, final String baseUrl) {
        super(client, baseUrl);
        this.chatApi = super.retrofit.create(ChatApi.class);
        this.batchesApi = super.retrofit.create(BatchesApi.class);
        this.embeddingApi = super.retrofit.create(EmbeddingApi.class);
        this.fileApi = super.retrofit.create(FileApi.class);
        this.fineTuningApi = super.retrofit.create(FineTuningApi.class);
        this.imagesApi = super.retrofit.create(ImagesApi.class);
        this.toolsApi = super.retrofit.create(ToolsApi.class);
    }


    /**
     * sse调用只会返回输出结果
     * @param request
     * @return RawResponse
     */
    public RawResponse sseExecute(Map<String, Object> request){

        RawResponse resp = new RawResponse();
        Flowable<ModelData> flowable;
        try {
            flowable  = this.streamChatCompletion(request);
        } catch (Exception e) {
            logger.error("streamChatCompletion error:{}" , e.getMessage());
            resp.setStatusCode(500);
            resp.setSuccess(false);
            return resp;
        }
        resp.setSuccess(true);
        resp.setStatusCode(200);
        resp.setFlowable(flowable);
        return resp;
    }

    public Flowable<ModelData> streamChatCompletion(Map<String,Object> request) {
        return stream(chatApi.createChatCompletionStream(request), ModelData.class);
    }



    public ModelData createChatCompletionAsync(Map<String,Object> request) {
        return execute(chatApi.createChatCompletionAsync(request));
    }


    public ModelData createChatCompletion(Map<String,Object> request) {
        return execute(chatApi.createChatCompletion(request));
    }

    public ModelData queryAsyncResult(String id) {
        return execute(chatApi.queryAsyncResult(id));
    }


    public EmbeddingResult createEmbeddings( Map<String, Object> request) {
        return execute(embeddingApi.createEmbeddings(request));
    }

    public QueryFileResult queryFileList(QueryFilesRequest queryFilesRequest) {
        return execute(fileApi.queryFileList(queryFilesRequest.getAfter(),queryFilesRequest.getPurpose(),queryFilesRequest.getOrder(),queryFilesRequest.getLimit()));
    }

    public HttpxBinaryResponseContent fileContent(String fileId) throws IOException {
        return fileWrapper(fileApi.fileContent(fileId));
    }

    public com.zhipu.oapi.service.v4.file.File retrieveFile(String fileId) {
        return execute(fileApi.retrieveFile(fileId));
    }

    public FileDeleted deletedFile(String fileId) {
        return execute(fileApi.deletedFile(fileId));
    }


    public FineTuningEvent listFineTuningJobEvents(String fineTuningJobId,Integer limit,String after) {
        return execute(fineTuningApi.listFineTuningJobEvents(fineTuningJobId,limit,after));
    }

    public FineTuningJob retrieveFineTuningJob(String fineTuningJobId,Integer limit,String after) {
        return execute(fineTuningApi.retrieveFineTuningJob(fineTuningJobId,limit,after));
    }


    public PersonalFineTuningJob queryPersonalFineTuningJobs(Integer limit,String after) {
        return execute(fineTuningApi.queryPersonalFineTuningJobs(limit,after));
    }

    public FineTuningJob cancelFineTuningJob(String fineTuningJobId) {
        return execute(fineTuningApi.cancelFineTuningJob(fineTuningJobId));
    }

    public FineTuningJob deleteFineTuningJob(String fineTuningJobId) {
        return execute(fineTuningApi.deleteFineTuningJob(fineTuningJobId));
    }

    public FineTunedModelsStatus deleteFineTuningModel(String fineTunedModel) {
        return execute(fineTuningApi.deleteFineTuningModel(fineTunedModel));
    }

    public FineTuningJob createFineTuningJob(FineTuningJobRequest request) {
        return execute(fineTuningApi.createFineTuningJob(request));
    }

    public com.zhipu.oapi.service.v4.file.File uploadFile(UploadFileRequest request) throws JsonProcessingException {
        java.io.File file = new java.io.File(request.getFilePath());
        if(!file.exists()){
            throw new RuntimeException("file not found");
        }
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
        MultipartBody.Builder formBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        formBodyBuilder.addPart(filePart);
        formBodyBuilder.addFormDataPart("purpose", request.getPurpose());
        if (request.getExtraJson()!=null){
            for (String s : request.getExtraJson().keySet()) {
                if(request.getExtraJson().get(s) instanceof String
                        || request.getExtraJson().get(s) instanceof Number
                        || request.getExtraJson().get(s) instanceof Boolean
                        || request.getExtraJson().get(s) instanceof Character

                ) {

                    formBodyBuilder.addFormDataPart(s, request.getExtraJson().get(s).toString());
                }else if(request.getExtraJson().get(s) instanceof Date) {
                    Date date = (Date) request.getExtraJson().get(s);
                    formBodyBuilder.addFormDataPart(s, String.valueOf(date.getTime()));
                }else {

                    formBodyBuilder.addFormDataPart(s, null,
                            RequestBody.create(MediaType.parse("application/json"),
                                    mapper.writeValueAsString(request.getExtraJson().get(s))));

                }

            }
        }
        MultipartBody multipartBody = formBodyBuilder.build();
        return execute(fileApi.uploadFile(multipartBody));
    }

    public ImageResult createImage(Map<String,Object> request) {
        return execute(imagesApi.createImage(request));
    }


    public Batch batchesCreate(BatchCreateParams batchCreateParams) {
        return execute(batchesApi.batchesCreate(batchCreateParams));
    }

    public Batch batchesRetrieve(String batchId) {
        return execute(batchesApi.batchesRetrieve(batchId));
    }

    public BatchPage batchesList(Integer limit, String after) {
        return execute(batchesApi.batchesList(after,limit));
    }

    public Batch batchesCancel(String batchId) {
        return execute(batchesApi.batchesCancel(batchId));
    }



    public Flowable<WebSearchPro> webSearchProStreaming(Map<String,Object> request) {
        return stream(toolsApi.webSearchStreaming(request), WebSearchPro.class);
    }


    public WebSearchPro webSearchPro(Map<String,Object> request) {
        return execute(toolsApi.webSearch(request));
    }


    private HttpxBinaryResponseContent fileWrapper(retrofit2.Call<ResponseBody> response) throws IOException {
        Response<ResponseBody> execute = response.execute();
        if (!execute.isSuccessful() || execute.body() == null) {
            throw new IOException("Failed to get the file content");
        }
        return new HttpxBinaryResponseContent(execute);
    }
    private <T> Flowable<T> stream(retrofit2.Call<ResponseBody> apiCall, Class<T> cl) {
        return  stream(apiCall).map(sse -> mapper.readValue(sse.getData(), cl));
    }


    public static Flowable<SSE> stream(retrofit2.Call<ResponseBody> apiCall) {
        return stream(apiCall, false);
    }


    public static Flowable<SSE> stream(retrofit2.Call<ResponseBody> apiCall, boolean emitDone) {
        return Flowable.create(emitter -> apiCall.enqueue(new ResponseBodyCallback(emitter, emitDone)), BackpressureStrategy.BUFFER);
    }




}
