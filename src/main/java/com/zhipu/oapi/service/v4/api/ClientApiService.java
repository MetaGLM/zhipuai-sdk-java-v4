package com.zhipu.oapi.service.v4.api;

import com.fasterxml.jackson.core.*;
import com.zhipu.oapi.core.response.HttpxBinaryResponseContent;
import com.zhipu.oapi.service.v4.api.agents.AgentsApi;
import com.zhipu.oapi.service.v4.api.audio.AudioApi;
import com.zhipu.oapi.service.v4.api.batches.BatchesApi;
import com.zhipu.oapi.service.v4.api.chat.ChatApi;
import com.zhipu.oapi.service.v4.api.embedding.EmbeddingApi;
import com.zhipu.oapi.service.v4.api.file.FileApi;
import com.zhipu.oapi.service.v4.api.fine_tuning.FineTuningApi;
import com.zhipu.oapi.service.v4.api.images.ImagesApi;
import com.zhipu.oapi.service.v4.api.tools.ToolsApi;
import com.zhipu.oapi.service.v4.api.web_search.WebSearchApi;
import com.zhipu.oapi.service.v4.batchs.Batch;
import com.zhipu.oapi.service.v4.batchs.BatchCreateParams;
import com.zhipu.oapi.service.v4.batchs.BatchPage;
import com.zhipu.oapi.service.v4.file.*;
import com.zhipu.oapi.service.v4.fine_turning.*;
import com.zhipu.oapi.service.v4.model.*;
import com.zhipu.oapi.service.v4.embedding.EmbeddingResult;
import com.zhipu.oapi.service.v4.image.ImageResult;
import com.zhipu.oapi.service.v4.tools.WebSearchPro;
import com.zhipu.oapi.service.v4.web_search.WebSearchDTO;
import com.zhipu.oapi.service.v4.web_search.WebSearchRequest;
import io.reactivex.Single;
import okhttp3.*;
import org.apache.tika.Tika;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class ClientApiService extends ClientBaseService {

    private final ChatApi chatApi;
    private final BatchesApi batchesApi;
    private final EmbeddingApi embeddingApi;
    private final FileApi fileApi;
    private final FineTuningApi fineTuningApi;
    private final ImagesApi imagesApi;
    private final ToolsApi toolsApi;
    private final WebSearchApi webSearchApi;

    private final AudioApi audioApi;

    private final AgentsApi agentsApi;

    public ClientApiService(final OkHttpClient client, final String baseUrl) {
        super(client, baseUrl);
        this.chatApi = super.retrofit.create(ChatApi.class);
        this.batchesApi = super.retrofit.create(BatchesApi.class);
        this.embeddingApi = super.retrofit.create(EmbeddingApi.class);
        this.fileApi = super.retrofit.create(FileApi.class);
        this.fineTuningApi = super.retrofit.create(FineTuningApi.class);
        this.imagesApi = super.retrofit.create(ImagesApi.class);
        this.toolsApi = super.retrofit.create(ToolsApi.class);
        this.audioApi = super.retrofit.create(AudioApi.class);
        this.webSearchApi = super.retrofit.create(WebSearchApi.class);
        this.agentsApi = super.retrofit.create(AgentsApi.class);
    }


    public Call<ResponseBody> streamChatCompletion(Map<String,Object> request) {
        return chatApi.createChatCompletionStream(request);
    }



    public Single<ModelData> createChatCompletionAsync(Map<String,Object> request) {
        return chatApi.createChatCompletionAsync(request);
    }

    public Call<ResponseBody> streamAgentsCompletion(Map<String,Object> request) {
        return agentsApi.agentsCompletionStream(request);
    }

    public Single<ModelData> agentsCompletion(Map<String,Object> request) {
        return agentsApi.agentsCompletionSync(request);
    }

    public Single<ModelData> queryAgentsAsyncResult(Map<String,Object> request) {
        return agentsApi.queryAgentsAsyncResult(request);
    }


    public Single<ModelData> createChatCompletion(Map<String,Object> request) {
        return chatApi.createChatCompletion(request);
    }

    public Single<ModelData> queryAsyncResult(String id) {
        return chatApi.queryAsyncResult(id);
    }


    public Single<EmbeddingResult> createEmbeddings( Map<String, Object> request) {
        return embeddingApi.createEmbeddings(request);
    }

    public Single<QueryFileResult> queryFileList(QueryFilesRequest queryFilesRequest) {
        return fileApi.queryFileList(queryFilesRequest.getAfter(),queryFilesRequest.getPurpose(),queryFilesRequest.getOrder(),queryFilesRequest.getLimit());
    }

    public HttpxBinaryResponseContent fileContent(String fileId) throws IOException {
        return fileWrapper(fileApi.fileContent(fileId));
    }

    public  Single<com.zhipu.oapi.service.v4.file.File> retrieveFile(String fileId) {
        return fileApi.retrieveFile(fileId);
    }

    public  Single<FileDeleted> deletedFile(String fileId) {
        return fileApi.deletedFile(fileId);
    }


    public  Single<FineTuningEvent> listFineTuningJobEvents(String fineTuningJobId,Integer limit,String after) {
        return fineTuningApi.listFineTuningJobEvents(fineTuningJobId,limit,after);
    }

    public Single<FineTuningJob> retrieveFineTuningJob(String fineTuningJobId,Integer limit,String after) {
        return fineTuningApi.retrieveFineTuningJob(fineTuningJobId,limit,after);
    }


    public Single<PersonalFineTuningJob> queryPersonalFineTuningJobs(Integer limit,String after) {
        return fineTuningApi.queryPersonalFineTuningJobs(limit,after);
    }

    public Single<FineTuningJob> cancelFineTuningJob(String fineTuningJobId) {
        return fineTuningApi.cancelFineTuningJob(fineTuningJobId);
    }

    public Single<FineTuningJob> deleteFineTuningJob(String fineTuningJobId) {
        return fineTuningApi.deleteFineTuningJob(fineTuningJobId);
    }

    public Single<FineTunedModelsStatus> deleteFineTuningModel(String fineTunedModel) {
        return fineTuningApi.deleteFineTuningModel(fineTunedModel);
    }

    public Single<FineTuningJob>  createFineTuningJob(FineTuningJobRequest request) {
        return fineTuningApi.createFineTuningJob(request);
    }

    public Single<com.zhipu.oapi.service.v4.file.File> uploadFile(UploadFileRequest request) throws JsonProcessingException {
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
        return fileApi.uploadFile(multipartBody);
    }

    public Single<ImageResult> createImage(Map<String,Object> request) {
        return imagesApi.createImage(request);
    }


    public Single<Batch> batchesCreate(BatchCreateParams batchCreateParams) {
        return batchesApi.batchesCreate(batchCreateParams);
    }

    public Single<Batch> batchesRetrieve(String batchId) {
        return batchesApi.batchesRetrieve(batchId);
    }

    public Single<BatchPage> batchesList(Integer limit, String after) {
        return batchesApi.batchesList(after,limit);
    }

    public Single<Batch> batchesCancel(String batchId) {
        return batchesApi.batchesCancel(batchId);
    }



    public Call<ResponseBody> webSearchProStreaming(Map<String,Object> request) {
        return toolsApi.webSearchStreaming(request);
    }


    public Single<WebSearchPro> webSearchPro(Map<String,Object> request) {
        return toolsApi.webSearch(request);
    }

    public Single<java.io.File> audioSpeech(Map<String,Object> request) throws IOException {
        Single<ResponseBody> responseBody = audioApi.audioSpeech(request);
        Path tempDirectory = Files.createTempFile("audio_speech" + UUID.randomUUID(),".wav");
        java.io.File file = tempDirectory.toFile();
        writeResponseBodyToFile(responseBody.blockingGet(), file);
        return Single.just(file);
    }

    public Single<java.io.File> audioCustomization(Map<String,Object> request) throws IOException {
        java.io.File voiceFile = (java.io.File)request.get("voice_data");
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), voiceFile);
        MultipartBody.Part voiceData = MultipartBody.Part.createFormData("voice_data", voiceFile.getName(), requestFile);
        request.remove("voice_data");
        Map<String, RequestBody> requestMap = new HashMap<>();
        for (String key : request.keySet()) {
            requestMap.put(key, RequestBody.create(MediaType.parse("text/plain"), request.get(key).toString()));

        }
        Single<ResponseBody> responseBody = audioApi.audioCustomization(requestMap,voiceData);
        Path tempDirectory = Files.createTempFile("audio_customization" + UUID.randomUUID(),".wav");
        java.io.File file = tempDirectory.toFile();
        writeResponseBodyToFile(responseBody.blockingGet(), file);
        return Single.just(file);
    }

    private void writeResponseBodyToFile(ResponseBody body, java.io.File file) {
        try (InputStream inputStream = body.byteStream();
             OutputStream outputStream = Files.newOutputStream(file.toPath())) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        } catch (IOException e) {
            logger.error("writeResponseBodyToFile error,msg:{}",e.getMessage(),e);
            e.printStackTrace();
        }
    }


    public Call<ResponseBody> audioTranscriptionsStream(Map<String,Object> request) throws IOException {
        java.io.File file = (java.io.File)request.get("file");
        Tika tika = new Tika();
        String contentType = tika.detect(file);
        RequestBody requestFile = RequestBody.create(MediaType.parse(contentType), file);
        MultipartBody.Part fileData = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        request.remove("file");
        Map<String, RequestBody> requestMap = new HashMap<>();
        for (String key : request.keySet()) {
            if(request.get(key) != null){
                requestMap.put(key, RequestBody.create(MediaType.parse("text/plain"), request.get(key).toString()));
            }
        }
        return audioApi.audioTranscriptionsStream(requestMap, fileData);
    }


    public Single<ModelData> audioTranscriptions(Map<String,Object> request) throws IOException {
        java.io.File file = (java.io.File)request.get("file");
        Tika tika = new Tika();
        String contentType = tika.detect(file);
        RequestBody requestFile = RequestBody.create(MediaType.parse(contentType), file);
        MultipartBody.Part fileData = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        request.remove("file");
        Map<String, RequestBody> requestMap = new HashMap<>();
        for (String key : request.keySet()) {
            if(request.get(key) != null){
                requestMap.put(key, RequestBody.create(MediaType.parse("text/plain"), request.get(key).toString()));
            }
        }
        return audioApi.audioTranscriptions(requestMap, fileData);
    }

    public Single<WebSearchDTO> webSearch(WebSearchRequest request) {
        return webSearchApi.webSearch(request);
    }


    private HttpxBinaryResponseContent fileWrapper(retrofit2.Call<ResponseBody> response) throws IOException {
        Response<ResponseBody> execute = response.execute();
        if (!execute.isSuccessful() || execute.body() == null) {
            throw new IOException("Failed to get the file content");
        }
        return new HttpxBinaryResponseContent(execute);
    }




}
