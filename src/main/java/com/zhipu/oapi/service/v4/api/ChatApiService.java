package com.zhipu.oapi.service.v4.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zhipu.oapi.core.response.HttpxBinaryResponseContent;
import com.zhipu.oapi.core.response.RawResponse;
import com.zhipu.oapi.service.v4.deserialize.ModelDataDeserializer;
import com.zhipu.oapi.service.v4.file.UploadFileRequest;
import com.zhipu.oapi.service.v4.fine_turning.*;
import com.zhipu.oapi.service.v4.model.*;
import com.zhipu.oapi.service.v4.embedding.EmbeddingResult;
import com.zhipu.oapi.service.v4.file.QueryFileResult;
import com.zhipu.oapi.service.v4.file.QueryFilesRequest;
import com.zhipu.oapi.service.v4.image.ImageResult;
import com.zhipu.oapi.utils.StringUtils;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.HttpException;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import static com.zhipu.oapi.Constants.BASE_URL;


public class ChatApiService {

    private static final ObjectMapper mapper = defaultObjectMapper();
    private final static Logger logger = LoggerFactory.getLogger(ChatApiService.class);

    private final ChatApi api;
    private final ExecutorService executorService;

    /**
     * Creates a new ChatApiService that wraps ChatApi
     * @param client retrofit instance
     */
    public ChatApiService(final OkHttpClient client) {

        Retrofit retrofit = defaultRetrofit(BASE_URL, client, mapper);
        this.api = retrofit.create(ChatApi.class);
        this.executorService = client.dispatcher().executorService();
    }
    /**
     * Creates a new ChatApiService that wraps ChatApi
     * @param client retrofit instance
     * @param baseUrl base url of the api
     */
    public ChatApiService(final OkHttpClient client, final String baseUrl) {

        Retrofit retrofit = defaultRetrofit(baseUrl, client, mapper);
        this.api = retrofit.create(ChatApi.class);
        this.executorService = client.dispatcher().executorService();
    }

    public static <T> T execute(Single<T> apiCall) {
        try {
            return apiCall.blockingGet();
        } catch (HttpException e) {
            logger.error("HTTP exception: {}", e.getMessage());
            try {
                if (e.response() == null || e.response().errorBody() == null) {
                    throw e;
                }
                String errorBody = e.response().errorBody().string();

                ZhiPuAiError error = mapper.readValue(errorBody, ZhiPuAiError.class);

                throw new ZhiPuAiHttpException(error, e, e.code());
            } catch (IOException ex) {
                // couldn't parse ZhiPuAiError error
                throw e;
            }
        }
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
        return stream(api.createChatCompletionStream(request), ModelData.class);
    }



    public ModelData createChatCompletionAsync(Map<String,Object> request) {
        return execute(api.createChatCompletionAsync(request));
    }


    public ModelData createChatCompletion(Map<String,Object> request) {
        return execute(api.createChatCompletion(request));
    }


    public EmbeddingResult createEmbeddings( Map<String, Object> request) {
        return execute(api.createEmbeddings(request));
    }

    public QueryFileResult queryFileList(QueryFilesRequest queryFilesRequest) {
        return execute(api.queryFileList(queryFilesRequest.getAfter(),queryFilesRequest.getPurpose(),queryFilesRequest.getOrder(),queryFilesRequest.getLimit()));
    }

    public HttpxBinaryResponseContent fileContent(String fileId) throws IOException {
        return fileWrapper(api.fileContent(fileId));
    }


    public FineTuningEvent listFineTuningJobEvents(String fineTuningJobId,Integer limit,String after) {
        return execute(api.listFineTuningJobEvents(fineTuningJobId,limit,after));
    }

    public FineTuningJob retrieveFineTuningJob(String fineTuningJobId,Integer limit,String after) {
        return execute(api.retrieveFineTuningJob(fineTuningJobId,limit,after));
    }


    public PersonalFineTuningJob queryPersonalFineTuningJobs(Integer limit,String after) {
        return execute(api.queryPersonalFineTuningJobs(limit,after));
    }

    public ModelData queryAsyncResult(String id) {
        return execute(api.queryAsyncResult(id));
    }

    public FineTuningJob cancelFineTuningJob(String fineTuningJobId) {
        return execute(api.cancelFineTuningJob(fineTuningJobId));
    }

    public FineTuningJob deleteFineTuningJob(String fineTuningJobId) {
        return execute(api.deleteFineTuningJob(fineTuningJobId));
    }

    public FineTunedModelsStatus deleteFineTuningModel(String fineTunedModel) {
        return execute(api.deleteFineTuningModel(fineTunedModel));
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
        return execute(api.uploadFile(multipartBody));
    }

    public ImageResult createImage(Map<String,Object> request) {
        return execute(api.createImage(request));
    }


    public FineTuningJob createFineTuningJob(FineTuningJobRequest request) {
        return execute(api.createFineTuningJob(request));
    }


    private HttpxBinaryResponseContent fileWrapper(retrofit2.Call<ResponseBody> response) throws IOException {
        Response<ResponseBody> execute = response.execute();
        if (!execute.isSuccessful() || execute.body() == null) {
            throw new IOException("Failed to get the file content");
        }
        return new HttpxBinaryResponseContent(execute);
    }
    private Flowable<ModelData> stream(retrofit2.Call<ResponseBody> apiCall, Class<ModelData> cl) {
        return  stream(apiCall).map(sse -> mapper.readValue(sse.getData(), cl));
    }


    public static Flowable<SSE> stream(retrofit2.Call<ResponseBody> apiCall) {
        return stream(apiCall, false);
    }


    public static Flowable<SSE> stream(retrofit2.Call<ResponseBody> apiCall, boolean emitDone) {
        return Flowable.create(emitter -> apiCall.enqueue(new ResponseBodyCallback(emitter, emitDone)), BackpressureStrategy.BUFFER);
    }
    /**
     * Creates a new ChatApiService that wraps ChatApi.
     * Use this if you need more customization, but use ChatApiService(api, executorService) if you use streaming and
     * want to shut down instantly
     *
     * @param api ChatApi instance to use for all methods
     */
    public ChatApiService(final ChatApi api) {
        this.api = api;
        this.executorService = null;
    }

    /**
     * Creates a new ChatApiService that wraps ChatApi.
     * The ExecutorService must be the one you get from the client you created the api with
     * otherwise shutdownExecutor() won't work.
     * <p>
     * Use this if you need more customization.
     *
     * @param api             ChatApi instance to use for all methods
     * @param executorService the ExecutorService from client.dispatcher().executorService()
     */
    public ChatApiService(final ChatApi api, final ExecutorService executorService) {
        this.api = api;
        this.executorService = executorService;
    }

    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        SimpleModule module = new SimpleModule();

        module.addDeserializer(ModelData.class, new ModelDataDeserializer());
        mapper.registerModule(module);

        return mapper;
    }



    public static Retrofit defaultRetrofit(final String baseUrl,
                                           OkHttpClient client,
                                           ObjectMapper mapper) {
        return new Retrofit.Builder()
                .baseUrl(StringUtils.isEmpty(baseUrl) ? BASE_URL:baseUrl)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }



}
