package com.zhipu.oapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhipu.oapi.core.ConfigV4;
import com.zhipu.oapi.core.cache.ICache;
import com.zhipu.oapi.core.cache.LocalCache;
import com.zhipu.oapi.core.model.ClientRequest;
import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.core.model.FlowableClientResponse;
import com.zhipu.oapi.core.response.HttpxBinaryResponseContent;
import com.zhipu.oapi.core.response.RawResponse;
import com.zhipu.oapi.core.token.GlobalTokenManager;
import com.zhipu.oapi.core.token.TokenManagerV4;
import com.zhipu.oapi.service.v4.api.ClientBaseService;
import com.zhipu.oapi.service.v4.batchs.*;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.fine_turning.*;
import com.zhipu.oapi.service.v4.model.*;
import com.zhipu.oapi.service.v4.api.ClientApiService;
import com.zhipu.oapi.service.v4.embedding.EmbeddingApiResponse;
import com.zhipu.oapi.service.v4.embedding.EmbeddingRequest;
import com.zhipu.oapi.service.v4.embedding.EmbeddingResult;
import com.zhipu.oapi.service.v4.file.*;
import com.zhipu.oapi.service.v4.image.CreateImageRequest;
import com.zhipu.oapi.service.v4.image.ImageApiResponse;
import com.zhipu.oapi.service.v4.image.ImageResult;
import com.zhipu.oapi.service.v4.tools.WebSearchApiResponse;
import com.zhipu.oapi.service.v4.tools.WebSearchParamsRequest;
import com.zhipu.oapi.service.v4.tools.WebSearchPro;
import com.zhipu.oapi.utils.FlowableRequestSupplier;
import com.zhipu.oapi.utils.OkHttps;
import com.zhipu.oapi.utils.RequestSupplier;
import com.zhipu.oapi.utils.StringUtils;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import lombok.Getter;
import lombok.Setter;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collector;

import static com.zhipu.oapi.Constants.BASE_URL;

// 抽象类
abstract class AbstractClientBaseService {

    protected final static Logger logger = LoggerFactory.getLogger(AbstractClientBaseService.class);

    protected static final ObjectMapper mapper = MessageDeserializeFactory.defaultObjectMapper();

    // 抽象方法，接受 TReq 和 RequestSupplier，返回 TResp
    public abstract  <Data, Param, TReq extends ClientRequest<Param>, TResp extends ClientResponse<Data>>
                        TResp executeRequest(TReq request,
                                         RequestSupplier<Param, Data> requestSupplier,
                                         Class<TResp> tRespClass);

    // 抽象方法，接受 TReq 和 RequestSupplier，返回 TResp
    public abstract  <Data, Param, TReq extends ClientRequest<Param>, TResp extends FlowableClientResponse<Data>>
                        TResp streamRequest(TReq request,
                                            FlowableRequestSupplier<Param, retrofit2.Call<ResponseBody>> requestSupplier,
                                            Class<TResp> tRespClass,
                                            Class<Data> tDataClass);

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

    public  <T> Flowable<T> stream(retrofit2.Call<ResponseBody> apiCall, Class<T> cl) {
        return  stream(apiCall).map(sse -> mapper.readValue(sse.getData(), cl));
    }


    public static Flowable<SSE> stream(retrofit2.Call<ResponseBody> apiCall) {
        return stream(apiCall, false);
    }


    public static Flowable<SSE> stream(retrofit2.Call<ResponseBody> apiCall, boolean emitDone) {
        return Flowable.create(emitter -> apiCall.enqueue(new ResponseBodyCallback(emitter, emitDone)), BackpressureStrategy.BUFFER);
    }

}

public class ClientV4 extends AbstractClientBaseService{

    private static final Logger logger = LoggerFactory.getLogger(ClientV4.class);


    @Setter
    @Getter
    private ConfigV4 config;
    @Setter
    @Getter
    private ClientApiService chatApiService;


    public ModelApiResponse invokeModelApi(ChatCompletionRequest request) {
        String paramMsg = validateParams(request);
        if (StringUtils.isNotEmpty(paramMsg)) {
            return new ModelApiResponse(-100, String.format("invalid param: %s", paramMsg));
        }
        if (request.getStream()) {
            return sseInvoke(request);
        } else if (Constants.invokeMethod.equals(request.getInvokeMethod())) {
            return invoke(request);
        } else if (Constants.invokeMethodAsync.equals(request.getInvokeMethod())) {
            return asyncInvoke(request);
        }
        return null;
    }
//

    private ModelApiResponse sseInvoke(ChatCompletionRequest request) {

        FlowableRequestSupplier<Map<String,Object>, retrofit2.Call<ResponseBody>> supplier = params ->  chatApiService.streamChatCompletion(params);
        return streamRequest(
                request,
                supplier,
                ModelApiResponse.class,
                ModelData.class
        );
    }

    private ModelApiResponse invoke(ChatCompletionRequest request) {

        RequestSupplier< Map<String, Object>, ModelData> supplier = (params) -> chatApiService.createChatCompletion(
                params
        );
        // 处理响应
        return this.executeRequest(request, supplier, ModelApiResponse.class);
    }


    private ModelApiResponse asyncInvoke(ChatCompletionRequest request) {


        RequestSupplier< Map<String, Object>, ModelData> supplier = (params) -> chatApiService.createChatCompletionAsync(
                params
        );
        // 处理响应
        return this.executeRequest(request, supplier, ModelApiResponse.class);

    }

    public QueryModelResultResponse queryModelResult(QueryModelResultRequest request) {


        RequestSupplier<String, ModelData> supplier = (params) -> chatApiService.queryAsyncResult(
                params
        );
        // 处理响应
        return this.executeRequest(request, supplier, QueryModelResultResponse.class);
    }

    public ImageApiResponse createImage(CreateImageRequest createImageRequest) {

        RequestSupplier<Map<String, Object>, ImageResult> supplier = (params) -> chatApiService.createImage(
                params
        );
        // 处理响应
        return this.executeRequest(createImageRequest, supplier, ImageApiResponse.class);
    }

    private String validateParams(ChatCompletionRequest request) {
        if (request == null) {
            return "request can not be null";
        }
        if(StringUtils.isEmpty(config.getApiKey())){
            return "apikey can not be empty";
        }
        if(StringUtils.isEmpty(config.getApiSecret())){
            return "apiSecret can not be empty";
        }
        if (!request.getStream() && StringUtils.isEmpty(request.getInvokeMethod())) {
            return "invoke method can not be empty";
        }
        if (request.getMessages() == null || request.getMessages().isEmpty()) {
            return "message can not be empty";
        }
        if (request.getModel() == null) {
            return "model can not be empty";
        }
        return null;
    }

    public EmbeddingApiResponse invokeEmbeddingsApi(EmbeddingRequest request) {

        RequestSupplier<Map<String, Object>, EmbeddingResult> supplier = (params) -> chatApiService.createEmbeddings(
                params
        );
        // 处理响应
        return this.executeRequest(request, supplier, EmbeddingApiResponse.class);
    }

    public FileApiResponse invokeUploadFileApi(UploadFileRequest request) {
        RequestSupplier<UploadFileRequest, File> supplier = (params) -> {
            try {
                return chatApiService.uploadFile(
                        params
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
        // 处理响应
        return this.executeRequest(request, supplier, FileApiResponse.class);

    }

    public QueryFileApiResponse queryFilesApi(QueryFilesRequest queryFilesRequest) {


        RequestSupplier<QueryFilesRequest, QueryFileResult> supplier = (params) -> chatApiService.queryFileList(
                params
        );
        // 处理响应
        return this.executeRequest(queryFilesRequest, supplier, QueryFileApiResponse.class);
    }


    public HttpxBinaryResponseContent fileContent(String fileId) throws IOException {
        return chatApiService.fileContent(fileId);
    }
//
////
////    public  FileApiResponse retrieveFile(String fileId) throws IOException {
////
////        FileApiResponse fileApiResponse = new FileApiResponse();
////
////        try {
////            File file = chatApiService.retrieveFile(fileId);
////            if (file != null) {
////                fileApiResponse.setCode(200);
////                fileApiResponse.setSuccess(true);
////                fileApiResponse.setMsg("调用成功");
////                fileApiResponse.setData(file);
////            }
////        } catch (ZhiPuAiHttpException e) {
////            logger.error("业务出错", e);
////            fileApiResponse.setCode(e.statusCode);
////            fileApiResponse.setMsg("业务出错");
////            fileApiResponse.setSuccess(false);
////            ChatError chatError = new ChatError();
////            chatError.setCode(Integer.parseInt(e.code));
////            chatError.setMessage(e.getMessage());
////            File file = new File();
////            file.setError(chatError);
////            fileApiResponse.setData(file);
////        }
////        return fileApiResponse;
////    }
//
////    public  FileDelResponse deletedFile(String fileId) throws IOException {
////        FileDelResponse fileDelResponse = new FileDelResponse();
////
////        try {
////            FileDeleted deleted = chatApiService.deletedFile(fileId);
////            if (deleted != null) {
////                fileDelResponse.setCode(200);
////                fileDelResponse.setSuccess(true);
////                fileDelResponse.setMsg("调用成功");
////                fileDelResponse.setData(deleted);
////            }
////        } catch (ZhiPuAiHttpException e) {
////            logger.error("业务出错", e);
////            fileDelResponse.setCode(e.statusCode);
////            fileDelResponse.setMsg("业务出错");
////            fileDelResponse.setSuccess(false);
////            ChatError chatError = new ChatError();
////            chatError.setCode(Integer.parseInt(e.code));
////            chatError.setMessage(e.getMessage());
////            FileDeleted file = new FileDeleted();
////            file.setError(chatError);
////            fileDelResponse.setData(file);
////        }
////        return fileDelResponse;
////    }
//

    public CreateFineTuningJobApiResponse createFineTuningJob(FineTuningJobRequest request) {

        RequestSupplier<FineTuningJobRequest, FineTuningJob> supplier = (params) -> chatApiService.createFineTuningJob(
                params
        );
        // 处理响应
        return this.executeRequest(request, supplier, CreateFineTuningJobApiResponse.class);
    }

    /**
     * 查询微调任务列表
     * @param queryFineTuningJobRequest queryFineTuningJobRequest
     * @return QueryFineTuningEventApiResponse
     */
    public QueryFineTuningEventApiResponse queryFineTuningJobsEvents(QueryFineTuningJobRequest queryFineTuningJobRequest) {

        RequestSupplier<Map<String,Object>, FineTuningEvent> supplier = (params) -> chatApiService.listFineTuningJobEvents(
                (String) params.get("job_id"),
                (Integer) params.get("limit"),
                (String) params.get("after")
        );
        // 处理响应
        return this.executeRequest(queryFineTuningJobRequest, supplier, QueryFineTuningEventApiResponse.class);
    }

    /**
     * 查询微调任务
     * @param queryFineTuningJobRequest queryFineTuningJobRequest
     * @return QueryFineTuningJobApiResponse
     */
    public QueryFineTuningJobApiResponse retrieveFineTuningJobs(QueryFineTuningJobRequest queryFineTuningJobRequest) {

        RequestSupplier<Map<String,Object>, FineTuningJob> supplier = (params) -> chatApiService.retrieveFineTuningJob(
                (String) params.get("job_id"),
                (Integer) params.get("limit"),
                (String) params.get("after")
        );
        // 处理响应
        return this.executeRequest(queryFineTuningJobRequest, supplier, QueryFineTuningJobApiResponse.class);
    }

    /**
     * 查询微调任务列表
     * @param queryPersonalFineTuningJobRequest queryPersonalFineTuningJobRequest
     * @return QueryPersonalFineTuningJobApiResponse
     */
    public QueryPersonalFineTuningJobApiResponse queryPersonalFineTuningJobs(QueryPersonalFineTuningJobRequest queryPersonalFineTuningJobRequest) {
        RequestSupplier<Map<String,Object>, PersonalFineTuningJob> supplier = (params) -> chatApiService.queryPersonalFineTuningJobs(
                (Integer) params.get("limit"),
                (String) params.get("after")
        );
        // 处理响应
        return this.executeRequest(queryPersonalFineTuningJobRequest, supplier, QueryPersonalFineTuningJobApiResponse.class);
    }


    /**
     * 取消微调任务
     * @param fineTuningJobIdRequest fineTuningJobIdRequest
     * @return QueryFineTuningJobApiResponse
     */
    public QueryFineTuningJobApiResponse cancelFineTuningJob(FineTuningJobIdRequest fineTuningJobIdRequest) {

        RequestSupplier<Map<String,Object>, FineTuningJob> supplier = (params) -> chatApiService.cancelFineTuningJob((String) params.get("job_id"));
        // 处理响应
        return this.executeRequest(fineTuningJobIdRequest, supplier, QueryFineTuningJobApiResponse.class);
    }


    /**
     * 删除微调任务
     * @param fineTuningJobIdRequest fineTuningJobIdRequest
     * @return QueryFineTuningJobApiResponse
     */
    public QueryFineTuningJobApiResponse deleteFineTuningJob(FineTuningJobIdRequest fineTuningJobIdRequest) {


        RequestSupplier<Map<String,Object>, FineTuningJob> supplier = (params) -> chatApiService.deleteFineTuningJob((String) params.get("job_id"));
        // 处理响应
        return this.executeRequest(fineTuningJobIdRequest, supplier, QueryFineTuningJobApiResponse.class);
    }


    /**
     * 删除微调模型
     * @param fineTuningJobModelRequest fineTuningJobIdRequest
     * @return FineTunedModelsStatusResponse
     */
    public FineTunedModelsStatusResponse deleteFineTuningModel(FineTuningJobModelRequest fineTuningJobModelRequest) {
        RequestSupplier<Map<String,Object>, FineTunedModelsStatus> supplier = (params) -> chatApiService.deleteFineTuningModel((String) params.get("fine_tuned_model"));
        // 处理响应
        return this.executeRequest(fineTuningJobModelRequest, supplier, FineTunedModelsStatusResponse.class);
    }

    /**
     * 发起批量请求
     * @param batchCreateParams batchCreateParams
     * @return BatchResponse
     */
    public BatchResponse batchesCreate(BatchCreateParams batchCreateParams) {

        RequestSupplier<BatchCreateParams, Batch> supplier = (params) -> chatApiService.batchesCreate(params);
        // 处理响应
        return this.executeRequest(batchCreateParams, supplier, BatchResponse.class);
    }
    /**
     * 检索批量请求
     * @param batchId batchId
     * @return BatchResponse
     */
    public BatchResponse batchesRetrieve(String batchId) {

        BatchRequest request = BatchRequest.builder().batchId(batchId).build();
        RequestSupplier<Map<String,Object>, Batch> supplier = (params) -> chatApiService.batchesRetrieve((String) params.get("batchId"));
        // 处理响应
        return this.executeRequest(request, supplier, BatchResponse.class);
    }

    /**
     * 查询批量请求列表
     * @param queryBatchRequest queryBatchRequest
     * @return QueryBatchResponse
     */
    public QueryBatchResponse batchesList(QueryBatchRequest queryBatchRequest) {

        RequestSupplier<Map<String,Object>, BatchPage> supplier = (params) -> chatApiService.batchesList(
                (Integer) params.get("limit"),
                (String) params.get("after")
        );;
        // 处理响应
        return this.executeRequest(queryBatchRequest, supplier, QueryBatchResponse.class);
    }


    /**
     * 检索批量请求
     * @param batchId batchId
     * @return BatchResponse
     */
    public BatchResponse batchesCancel(String batchId) {
        BatchRequest request = BatchRequest.builder().batchId(batchId).build();
        RequestSupplier<Map<String,Object>, Batch> supplier = (params) -> chatApiService.batchesCancel((String) params.get("batchId"));
        // 处理响应
        return this.executeRequest(request, supplier, BatchResponse.class);
    }




    public WebSearchApiResponse webSearchProStreamingInvoke(WebSearchParamsRequest request) {
        FlowableRequestSupplier<Map<String,Object>, retrofit2.Call<ResponseBody>> supplier = params ->  chatApiService.webSearchProStreaming(params);
        return streamRequest(
                request,
                supplier,
                WebSearchApiResponse.class,
                WebSearchPro.class
        );
    }

    public WebSearchApiResponse invokeWebSearchPro(WebSearchParamsRequest request) {
        RequestSupplier<Map<String,Object>, WebSearchPro> supplier = (params) -> chatApiService.webSearchPro(params);

        // 处理响应
        return this.executeRequest(request, supplier, WebSearchApiResponse.class);
    }

    @Override
    public  <Data, Param, TReq extends ClientRequest<Param>, TResp extends ClientResponse<Data>>
    TResp executeRequest(TReq request,
                         RequestSupplier<Param,Data> requestSupplier,
                         Class<TResp> tRespClass){
        Single<Data> apiCall = requestSupplier.get(request.getOptions());


        TResp tResp =  convertToClientResponse(tRespClass);
        try {


            // 使用 execute 方法执行 API 调用
            Data response = execute(apiCall);

            tResp.setCode(200);
            tResp.setMsg("调用成功");
            tResp.setData(response);
        } catch (ZhiPuAiHttpException e) {
            logger.error("业务出错", e);
            tResp.setCode(e.statusCode);
            tResp.setMsg("业务出错");
            tResp.setSuccess(false);
            ChatError chatError = new ChatError();
            chatError.setCode(Integer.parseInt(e.code));
            chatError.setMessage(e.getMessage());
            tResp.setError(chatError);
        }
        return tResp;
    }


    @Override
    public  <Data, Param, TReq extends ClientRequest<Param>, TResp extends FlowableClientResponse<Data>>
    TResp streamRequest(TReq request,
                        FlowableRequestSupplier<Param,retrofit2.Call<ResponseBody>> requestSupplier,
                        Class<TResp> tRespClass,
                        Class<Data> tDataClass){
        retrofit2.Call<ResponseBody> apiCall = requestSupplier.get(request.getOptions());

        TResp tResp =  convertToClientResponse(tRespClass);
        try {
            // 使用 stream 方法执行 API 调用
            Flowable<Data> stream = stream(apiCall, tDataClass);
            tResp.setCode(200);
            tResp.setMsg("调用成功");
            tResp.setCode(200);
            tResp.setMsg("成功");
            tResp.setSuccess(true);
            tResp.setFlowable(stream);

        } catch (ZhiPuAiHttpException e) {
            logger.error("业务出错", e);
            tResp.setCode(e.statusCode);
            tResp.setMsg("业务出错");
            tResp.setSuccess(false);
            ChatError chatError = new ChatError();
            chatError.setCode(Integer.parseInt(e.code));
            chatError.setMessage(e.getMessage());
            tResp.setError(chatError);
        }
        return tResp;
    }
    // 转换方法
    private <Data, TResp extends ClientResponse<Data>> TResp convertToClientResponse(Class<TResp> tRespClass) {
        try {
            // 根据实际情况填充 clientResponse 对象的字段
            return tRespClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException("Failed to create response object", e);
        }
    }
    public static final class Builder {
        private final ConfigV4 config = new ConfigV4();

        public Builder(String apiSecretKey) {
            config.setApiSecretKey(apiSecretKey);
        }
        public Builder(String baseUrl, String apiSecretKey) {
            config.setBaseUrl(baseUrl);
            config.setApiSecretKey(apiSecretKey);
        }

        public Builder setTokenKey(String apiKey, String apiSecret) {
            config.setApiKey(apiKey);
            config.setApiSecret(apiSecret);
            config.setDisableTokenCache(true);
            return this;
        }

        /**
         * 使用apikey直接请求 默认true
         * @return QueryFineTuningEventApiResponse
         */
        public Builder disableTokenCache() {
            config.setDisableTokenCache(true);
            return this;
        }

        /**
         * 使用accessToken请求 默认false
         * @return QueryFineTuningEventApiResponse
         */
        public Builder enableTokenCache() {
            config.setDisableTokenCache(false);
            return this;
        }


        public Builder tokenCache(ICache cache) {
            config.setCache(cache);
            return this;
        }

        public Builder tokenExpire(int expireMillis) {
            config.setExpireMillis(expireMillis);
            return this;
        }

        /**
         * 设置网络请求超时时间
         * @param requestTimeOut @see OkHttpClient.Builder#callTimeout(long, TimeUnit)
         * @param connectTimeout @see OkHttpClient.Builder#connectTimeout(long, TimeUnit)
         * @param readTimeout @see OkHttpClient.Builder#readTimeout(long, TimeUnit)
         * @param writeTimeout @see OkHttpClient.Builder#writeTimeout(long, TimeUnit)
         * @param timeUnit @see TimeUnit
         * @return Builder
         */
        public Builder networkConfig(int requestTimeOut,
                                     int connectTimeout,
                                     int readTimeout,
                                     int writeTimeout,
                                     TimeUnit timeUnit) {
            config.setRequestTimeOut(requestTimeOut);
            config.setConnectTimeout(connectTimeout);
            config.setReadTimeout(readTimeout);
            config.setWriteTimeout(writeTimeout);
            config.setTimeOutTimeUnit(timeUnit);
            return this;
        }

        /**
         * 设置连接池
         * @param connectionPool @see OkHttpClient.Builder#connectionPool(ConnectionPool)
         * @return Builder
         */
        public Builder connectionPool(ConnectionPool connectionPool) {
            config.setConnectionPool(connectionPool);
            return this;
        }



        private void initCache(ConfigV4 config) {
            if (config.getCache() != null) {
                GlobalTokenManager.setTokenManager(new TokenManagerV4(config.getCache()));
            } else {
                ICache cache = LocalCache.getInstance();
                GlobalTokenManager.setTokenManager(new TokenManagerV4(cache));
            }
        }

        private void initHttpTransport(ConfigV4 config) {
            if (config.getHttpClient() == null) {
                if (StringUtils.isEmpty(config.getApiSecretKey())){
                    throw new RuntimeException("apiSecretKey can not be empty");
                }
                 
                OkHttpClient okHttpClient = OkHttps.create(config);
                config.setHttpClient(okHttpClient);
            }
            
        }

        public ClientV4 build() {
            ClientV4 client = new ClientV4();
            client.setConfig(config);
            
            initCache(config);
            initHttpTransport(config);

            String baseUrl = null;
            if(StringUtils.isEmpty(config.getBaseUrl())){

                baseUrl = BASE_URL;
            }else{
                baseUrl = config.getBaseUrl();
            }
            client.setChatApiService(new ClientApiService(config.getHttpClient(),baseUrl));
            return client;
        }
    }


}
