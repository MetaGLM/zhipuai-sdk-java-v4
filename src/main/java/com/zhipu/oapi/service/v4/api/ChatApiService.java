package com.zhipu.oapi.service.v4.api;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.fine_turning.FineTuningEvent;
import com.zhipu.oapi.service.v4.fine_turning.FineTuningJob;
import com.zhipu.oapi.service.v4.fine_turning.FineTuningJobRequest;
import com.zhipu.oapi.service.v4.fine_turning.PersonalFineTuningJob;
import com.zhipu.oapi.service.v4.model.*;
import com.zhipu.oapi.service.v4.embedding.EmbeddingRequest;
import com.zhipu.oapi.service.v4.embedding.EmbeddingResult;
import com.zhipu.oapi.service.v4.file.QueryFileResult;
import com.zhipu.oapi.service.v4.file.QueryFilesRequest;
import com.zhipu.oapi.service.v4.image.ImageResult;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import okhttp3.*;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.HttpException;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;



public class ChatApiService {

    private static final String BASE_URL = "https://open.bigmodel.cn/";
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);
    private static final ObjectMapper mapper = defaultObjectMapper();

    private final ChatApi api;
    private final ExecutorService executorService;

    /**
     * Creates a new ChatApiService that wraps ChatApi
     */
    public ChatApiService(final String token) {
        this(token, DEFAULT_TIMEOUT);
    }

    /**
     * Creates a new ChatApiService that wraps ChatApi
     * @param token
     * @param timeout http read timeout, Duration.ZERO means no timeout
     */
    public ChatApiService(final String token, final Duration timeout) {
        ObjectMapper mapper = defaultObjectMapper();
        OkHttpClient client = defaultClient(token, timeout);
        Retrofit retrofit = defaultRetrofit(client, mapper);

        this.api = retrofit.create(ChatApi.class);
        this.executorService = client.dispatcher().executorService();
    }

    public static <T> T execute(Single<T> apiCall) {
        try {
            return apiCall.blockingGet();
        } catch (HttpException e) {
            try {
                if (e.response() == null || e.response().errorBody() == null) {
                    throw e;
                }
                String errorBody = e.response().errorBody().string();

                ZhiPuAiError error = mapper.readValue(errorBody, ZhiPuAiError.class);
                String message = error.getError().getMessage();
                message+="&"+error.getError().getCode()+"&"+e.code();
                error.getError().setMessage(message);
                throw new ZhiPuAiHttpException(error, e, e.code());
            } catch (IOException ex) {
                // couldn't parse ZhiPuAiError error
                throw e;
            }
        }
    }
    public Flowable<ModelData> streamChatCompletion(Map<String,Object> request) {
        return stream(api.createChatCompletionStream(request), ModelData.class);
    }


    public ChatCompletionAsyncResult createChatCompletionAsync(Map<String,Object> request) {
        return execute(api.createChatCompletionAsync(request));
    }


    public ChatCompletionResult createChatCompletion(Map<String,Object> request) {
        return execute(api.createChatCompletion(request));
    }


    public EmbeddingResult createEmbeddings(EmbeddingRequest request) {
        return execute(api.createEmbeddings(request));
    }

    public QueryFileResult queryFileList(QueryFilesRequest queryFilesRequest) {
        return execute(api.queryFileList(queryFilesRequest.getAfter(),queryFilesRequest.getPurpose(),queryFilesRequest.getOrder(),queryFilesRequest.getLimit()));
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

    public ChatCompletionResult queryAsyncResult(String id) {
        return execute(api.queryAsyncResult(id));
    }


    public com.zhipu.oapi.service.v4.file.File uploadFile(String purpose, String filepath) {
        java.io.File file = new java.io.File(filepath);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
        MultipartBody.Builder formBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        formBodyBuilder.addPart(filePart);
        formBodyBuilder.addFormDataPart("purpose", purpose);
        MultipartBody multipartBody = formBodyBuilder.build();
        return execute(api.uploadFile(multipartBody));
    }

    public ImageResult createImage(Map<String,Object> request) {
        return execute(api.createImage(request));
    }


    public FineTuningJob createFineTuningJob(FineTuningJobRequest request) {
        return execute(api.createFineTuningJob(request));
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
        mapper.addMixIn(ChatFunction.class, ChatFunctionMixIn.class);
        mapper.addMixIn(ChatCompletionRequest.class, ChatCompletionRequestMixIn.class);
        mapper.addMixIn(ChatFunctionCall.class, ChatFunctionCallMixIn.class);
        return mapper;
    }

    public static OkHttpClient defaultClient(String token, Duration timeout) {
        return new OkHttpClient.Builder()
                .addInterceptor(new AuthenticationInterceptor(token))
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
                .readTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS)
                .build();
    }

    public static Retrofit defaultRetrofit(OkHttpClient client, ObjectMapper mapper) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Flowable<ChatMessageAccumulator> mapStreamToAccumulator(Flowable<ModelData> flowable) {
        return flowable.map(chunk -> {
            return new ChatMessageAccumulator(chunk.getChoices().get(0).getDelta(), null,chunk.getChoices().get(0),chunk.getUsage(),chunk.getCreated(),chunk.getId());
        });
    }



}
