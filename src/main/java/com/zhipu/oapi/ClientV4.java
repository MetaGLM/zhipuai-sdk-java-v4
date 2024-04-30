package com.zhipu.oapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhipu.oapi.core.ConfigV4;
import com.zhipu.oapi.core.cache.ICache;
import com.zhipu.oapi.core.cache.LocalCache;
import com.zhipu.oapi.core.response.RawResponse;
import com.zhipu.oapi.core.token.GlobalTokenManager;
import com.zhipu.oapi.core.token.TokenManagerV4;
import com.zhipu.oapi.service.v4.fine_turning.*;
import com.zhipu.oapi.service.v4.model.*;
import com.zhipu.oapi.service.v4.api.ChatApiService;
import com.zhipu.oapi.service.v4.embedding.EmbeddingApiResponse;
import com.zhipu.oapi.service.v4.embedding.EmbeddingRequest;
import com.zhipu.oapi.service.v4.embedding.EmbeddingResult;
import com.zhipu.oapi.service.v4.file.*;
import com.zhipu.oapi.service.v4.image.CreateImageRequest;
import com.zhipu.oapi.service.v4.image.ImageApiResponse;
import com.zhipu.oapi.service.v4.image.ImageResult;
import com.zhipu.oapi.utils.OkHttps;
import com.zhipu.oapi.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.zhipu.oapi.Constants.BASE_URL;
import static com.zhipu.oapi.Constants.TEST_BASE_URL;


public class ClientV4 {

    private static final Logger logger = LoggerFactory.getLogger(ClientV4.class);

    private static final ObjectMapper mapper = ChatApiService.defaultObjectMapper();

    @Setter
    @Getter
    private ConfigV4 config;
    @Setter
    @Getter
    private ChatApiService chatApiService;


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


    private ModelApiResponse sseInvoke(ChatCompletionRequest request) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("request_id", request.getRequestId());
        paramsMap.put("user_id", request.getUserId());
        paramsMap.put("messages", request.getMessages());
        paramsMap.put("model", request.getModel());
        paramsMap.put("stream", true);
        paramsMap.put("tools", request.getTools());
        paramsMap.put("tool_choice", request.getToolChoice());
        paramsMap.put("temperature", request.getTemperature());
        paramsMap.put("top_p", request.getTopP());
        paramsMap.put("sensitive_word_check", request.getSensitiveWordCheck());
        paramsMap.put("do_sample", request.getDoSample());
        paramsMap.put("max_tokens", request.getMaxTokens());
        paramsMap.put("stop", request.getStop());

        if(request.getExtraJson() !=null){
            paramsMap.putAll(request.getExtraJson());
        }

        ModelApiResponse resp = new ModelApiResponse();
        try {
            RawResponse rawResp = chatApiService.sseExecute(paramsMap);
            resp.setCode(rawResp.getStatusCode());
            resp.setMsg(rawResp.getMsg());
            resp.setSuccess(rawResp.isSuccess());
            if(resp.isSuccess()){
                resp.setFlowable(rawResp.getFlowable());
            }
            return resp;
        } catch (Exception e) {
            logger.error("sse invoke model fail!", e);
            String[] error = e.getMessage().split("&");
            resp.setCode(error.length >= 3 ? Integer.parseInt(error[2]) : 500);
            resp.setMsg("调用失败，异常:" + error[0]);
            resp.setSuccess(false);
            ChatError chatError = new ChatError();
            chatError.setCode(error.length >= 2 ? Integer.parseInt(error[1]) : 500);
            chatError.setMessage(error[0]);
            ModelData modelData = new ModelData();
            modelData.setError(chatError);
            resp.setData(modelData);
        }
        return resp;
    }

    private ModelApiResponse invoke(ChatCompletionRequest request) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("request_id", request.getRequestId());
        paramsMap.put("user_id", request.getUserId());
        paramsMap.put("messages", request.getMessages());
        paramsMap.put("model", request.getModel());
        paramsMap.put("stream", false);
        paramsMap.put("tools", request.getTools());
        paramsMap.put("tool_choice", request.getToolChoice());
        paramsMap.put("temperature", request.getTemperature());
        paramsMap.put("top_p", request.getTopP());
        paramsMap.put("sensitive_word_check", request.getSensitiveWordCheck());
        paramsMap.put("do_sample", request.getDoSample());
        paramsMap.put("max_tokens", request.getMaxTokens());
        paramsMap.put("stop", request.getStop());
        if(request.getExtraJson() !=null){
            paramsMap.putAll(request.getExtraJson());
        }

        ModelApiResponse resp = new ModelApiResponse();
        try {
            ModelData modelData = chatApiService.createChatCompletion(paramsMap);
            if (modelData != null) {
                resp.setCode(200);
                resp.setMsg("调用成功");
                resp.setData(modelData);
            }
            return resp;
        } catch (Exception e) {
            logger.error("invoke model fail!", e);
            String[] error = e.getMessage().split("&");
            resp.setCode(error.length >= 3 ? Integer.parseInt(error[2]) : 500);
            resp.setMsg("调用失败，异常:" + error[0]);
            resp.setSuccess(false);
            ChatError chatError = new ChatError();
            chatError.setCode(error.length >= 2 ? Integer.parseInt(error[1]) : 500);
            chatError.setMessage(error[0]);
            ModelData modelData = new ModelData();
            modelData.setError(chatError);
            resp.setData(modelData);
        }
        return resp;
    }


    private ModelApiResponse asyncInvoke(ChatCompletionRequest request) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("request_id", request.getRequestId());
        paramsMap.put("user_id", request.getUserId());
        paramsMap.put("messages", request.getMessages());
        paramsMap.put("model", request.getModel());
        paramsMap.put("stream", false);
        paramsMap.put("tools", request.getTools());
        paramsMap.put("tool_choice", request.getToolChoice());
        paramsMap.put("temperature", request.getTemperature());
        paramsMap.put("top_p", request.getTopP());
        paramsMap.put("sensitive_word_check", request.getSensitiveWordCheck());
        paramsMap.put("do_sample", request.getDoSample());
        paramsMap.put("max_tokens", request.getMaxTokens());
        paramsMap.put("stop", request.getStop());
        if(request.getExtraJson() !=null){
            paramsMap.putAll(request.getExtraJson());
        }

        ModelApiResponse resp = new ModelApiResponse();
        try {
            ModelData modelData = chatApiService.createChatCompletionAsync(paramsMap);
            if (modelData != null) {
                resp.setCode(200);
                resp.setMsg("调用成功");
                resp.setData(modelData);
            }
        } catch (Exception e) {
            logger.error("async invoke model fail!", e);
            String[] error = e.getMessage().split("&");
            resp.setCode(error.length >= 3 ? Integer.parseInt(error[2]) : 500);
            resp.setMsg("调用失败，异常:" + error[0]);
            resp.setSuccess(false);
            ChatError chatError = new ChatError();
            chatError.setCode(error.length >= 2 ? Integer.parseInt(error[1]) : 500);
            chatError.setMessage(error[0]);
            ModelData modelData = new ModelData();
            modelData.setError(chatError);
            resp.setData(modelData);
        }
        return resp;

    }

    public QueryModelResultResponse queryModelResult(QueryModelResultRequest request) {
        QueryModelResultResponse resp = new QueryModelResultResponse();

        try {
            ModelData modelData = chatApiService.queryAsyncResult(request.getTaskId());
            if (modelData != null) {
                resp.setCode(200);
                resp.setMsg("调用成功");
                resp.setSuccess(true);
                modelData.setCreated(null);
                modelData.setModel(modelData.getModel());
                modelData.setRequestId(modelData.getRequestId());
                modelData.setTaskStatus(modelData.getTaskStatus());
                resp.setData(modelData);
            }
        } catch (Exception e) {
            logger.error("query result fail", e);
            String[] error = e.getMessage().split("&");
            resp.setCode(error.length >= 3 ? Integer.parseInt(error[2]) : 500);
            resp.setMsg("调用失败，异常:" + error[0]);
            resp.setSuccess(false);
            ChatError chatError = new ChatError();
            chatError.setCode(error.length >= 2 ? Integer.parseInt(error[1]) : 500);
            chatError.setMessage(error[0]);
            ModelData modelData = new ModelData();
            modelData.setError(chatError);
            resp.setData(modelData);
            return resp;
        }
        return resp;
    }

    public ImageApiResponse createImage(CreateImageRequest createImageRequest) {
        ImageApiResponse imageApiResponse = new ImageApiResponse();

        Map<String, Object> request = new HashMap<>();
        request.put("request_id", createImageRequest.getRequestId());
        request.put("user_id", createImageRequest.getUserId());
        request.put("prompt", createImageRequest.getPrompt());
        request.put("model", createImageRequest.getModel());
        try {
            if(createImageRequest.getExtraJson() !=null){
                request.replaceAll((s, v) -> createImageRequest.getExtraJson().get(s));
            }
            ImageResult image = chatApiService.createImage(request);
            if (image != null) {
                imageApiResponse.setMsg("调用成功");
                imageApiResponse.setCode(200);
                imageApiResponse.setSuccess(true);
                imageApiResponse.setData(image);
            }
        } catch (Exception e) {
            logger.error("createImageResult:", e);
            String[] error = e.getMessage().split("&");
            imageApiResponse.setCode(error.length >= 3 ? Integer.parseInt(error[2]) : 500);
            imageApiResponse.setMsg("调用失败，异常:" + error[0]);
            imageApiResponse.setSuccess(false);
            ChatError chatError = new ChatError();
            chatError.setCode(error.length >= 2 ? Integer.parseInt(error[1]) : 500);
            chatError.setMessage(error[0]);
            ImageResult imageResult = new ImageResult();
            imageResult.setError(chatError);
            imageApiResponse.setData(imageResult);
        }
        return imageApiResponse;
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
        EmbeddingApiResponse embeddingApiResponse = new EmbeddingApiResponse();

        try {
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("request_id", request.getRequestId());
            paramsMap.put("user_id", request.getUserId());
            paramsMap.put("input", request.getInput());
            paramsMap.put("model", request.getModel());
            if(request.getExtraJson() !=null){
                paramsMap.putAll(request.getExtraJson());
            }
            EmbeddingResult embeddingResult = chatApiService.createEmbeddings(paramsMap);
            if (embeddingResult != null) {
                embeddingApiResponse.setCode(200);
                embeddingApiResponse.setMsg("调用成功");
                embeddingApiResponse.setSuccess(true);
                embeddingApiResponse.setData(embeddingResult);
            }
        } catch (Exception e) {
            logger.error("createEmbeddings:", e);
            String[] error = e.getMessage().split("&");
            embeddingApiResponse.setCode(error.length >= 3 ? Integer.parseInt(error[2]) : 500);
            embeddingApiResponse.setMsg("调用失败，异常:" + error[0]);
            embeddingApiResponse.setSuccess(false);
            ChatError chatError = new ChatError();
            chatError.setCode(error.length >= 2 ? Integer.parseInt(error[1]) : 500);
            chatError.setMessage(error[0]);
            EmbeddingResult embeddingResult = new EmbeddingResult();
            embeddingResult.setError(chatError);
            embeddingApiResponse.setData(embeddingResult);
        }
        return embeddingApiResponse;
    }

    public FileApiResponse invokeUploadFileApi(UploadFileRequest request) {
        FileApiResponse fileApiResponse = new FileApiResponse();

        try {
            File file = chatApiService.uploadFile(request);
            if (file != null) {
                fileApiResponse.setCode(200);
                fileApiResponse.setSuccess(true);
                fileApiResponse.setMsg("调用成功");
                fileApiResponse.setData(file);
            }
        } catch (Exception e) {
            String[] error = e.getMessage().split("&");
            fileApiResponse.setCode(error.length >= 3 ? Integer.parseInt(error[2]) : 500);
            fileApiResponse.setMsg("调用失败，异常:" + error[0]);
            fileApiResponse.setSuccess(false);
            ChatError chatError = new ChatError();
            chatError.setCode(error.length >= 2 ? Integer.parseInt(error[1]) : 500);
            chatError.setMessage(error[0]);
            File file = new File();
            file.setError(chatError);
            fileApiResponse.setData(file);
        }
        return fileApiResponse;
    }

    public QueryFileApiResponse queryFilesApi(QueryFilesRequest queryFilesRequest) {
        QueryFileApiResponse queryFileApiResponse = new QueryFileApiResponse();

        try {
            QueryFileResult queryFileResult = chatApiService.queryFileList(queryFilesRequest);
            if (queryFileResult != null) {
                queryFileApiResponse.setCode(200);
                queryFileApiResponse.setSuccess(true);
                queryFileApiResponse.setMsg("调用成功");
                queryFileApiResponse.setData(queryFileResult);
            }
        } catch (Exception e) {
            String[] error = e.getMessage().split("&");
            queryFileApiResponse.setCode(error.length >= 3 ? Integer.parseInt(error[2]) : 500);
            queryFileApiResponse.setMsg("调用失败，异常:" + error[0]);
            queryFileApiResponse.setSuccess(false);
            ChatError chatError = new ChatError();
            chatError.setCode(error.length >= 2 ? Integer.parseInt(error[1]) : 500);
            chatError.setMessage(error[0]);
            QueryFileResult queryFileResult = new QueryFileResult();
            queryFileResult.setError(chatError);
            queryFileApiResponse.setData(queryFileResult);
        }
        return queryFileApiResponse;
    }

    public CreateFineTuningJobApiResponse createFineTuningJob(FineTuningJobRequest request) {
        CreateFineTuningJobApiResponse createFineTuningJobApiResponse = new CreateFineTuningJobApiResponse();

        FineTuningJob fineTuningJob = null;
        try {
            fineTuningJob = chatApiService.createFineTuningJob(request);
            if (fineTuningJob != null) {
                createFineTuningJobApiResponse.setMsg("调用成功");
                createFineTuningJobApiResponse.setData(fineTuningJob);
                createFineTuningJobApiResponse.setSuccess(true);
                createFineTuningJobApiResponse.setCode(200);
            }
        } catch (Exception e) {
            String[] error = e.getMessage().split("&");
            createFineTuningJobApiResponse.setCode(error.length >= 3 ? Integer.parseInt(error[2]) : 500);
            createFineTuningJobApiResponse.setMsg("调用失败，异常:" + error[0]);
            createFineTuningJobApiResponse.setSuccess(false);
            ChatError chatError = new ChatError();
            chatError.setCode(error.length >= 2 ? Integer.parseInt(error[1]) : 500);
            chatError.setMessage(error[0]);
            fineTuningJob = new FineTuningJob();
            fineTuningJob.setError(chatError);
            createFineTuningJobApiResponse.setData(fineTuningJob);
        }
        return createFineTuningJobApiResponse;
    }

    public QueryFineTuningEventApiResponse queryFineTuningJobsEvents(QueryFineTuningJobRequest queryFineTuningJobRequest) {
        QueryFineTuningEventApiResponse queryFineTuningEventApiResponse = new QueryFineTuningEventApiResponse();

        try {
            FineTuningEvent fineTuningEvent = chatApiService.listFineTuningJobEvents(queryFineTuningJobRequest.getJobId(),queryFineTuningJobRequest.getLimit(),queryFineTuningJobRequest.getAfter());
            if (fineTuningEvent != null) {
                queryFineTuningEventApiResponse.setSuccess(true);
                queryFineTuningEventApiResponse.setData(fineTuningEvent);
                queryFineTuningEventApiResponse.setCode(200);
                queryFineTuningEventApiResponse.setMsg("调用成功");
            }
        } catch (Exception e) {
            String[] error = e.getMessage().split("&");
            queryFineTuningEventApiResponse.setCode(error.length >= 3 ? Integer.parseInt(error[2]) : 500);
            queryFineTuningEventApiResponse.setMsg("调用失败，异常:" + error[0]);
            queryFineTuningEventApiResponse.setSuccess(false);
            ChatError chatError = new ChatError();
            chatError.setCode(error.length >= 2 ? Integer.parseInt(error[1]) : 500);
            chatError.setMessage(error[0]);
            FineTuningEvent fineTuningEvent = new FineTuningEvent();
            fineTuningEvent.setError(chatError);
            queryFineTuningEventApiResponse.setData(fineTuningEvent);
        }
        return queryFineTuningEventApiResponse;
    }

    public QueryFineTuningJobApiResponse retrieveFineTuningJobs(QueryFineTuningJobRequest queryFineTuningJobRequest) {
        QueryFineTuningJobApiResponse queryFineTuningJobApiResponse = new QueryFineTuningJobApiResponse();

        try {
            FineTuningJob fineTuningJob = chatApiService.retrieveFineTuningJob(queryFineTuningJobRequest.getJobId(),queryFineTuningJobRequest.getLimit(),queryFineTuningJobRequest.getAfter());
            if (fineTuningJob != null) {
                queryFineTuningJobApiResponse.setSuccess(true);
                queryFineTuningJobApiResponse.setData(fineTuningJob);
                queryFineTuningJobApiResponse.setCode(200);
                queryFineTuningJobApiResponse.setMsg("调用成功");
            }
        } catch (Exception e) {
            String[] error = e.getMessage().split("&");
            queryFineTuningJobApiResponse.setCode(error.length >= 3 ? Integer.parseInt(error[2]) : 500);
            queryFineTuningJobApiResponse.setMsg("调用失败，异常:" + error[0]);
            queryFineTuningJobApiResponse.setSuccess(false);
            ChatError chatError = new ChatError();
            chatError.setCode(error.length >= 2 ? Integer.parseInt(error[1]) : 500);
            chatError.setMessage(error[0]);
            FineTuningJob fineTuningJob = new FineTuningJob();
            fineTuningJob.setError(chatError);
            queryFineTuningJobApiResponse.setData(fineTuningJob);
        }

        return queryFineTuningJobApiResponse;
    }

    public QueryPersonalFineTuningJobApiResponse queryPersonalFineTuningJobs(QueryPersonalFineTuningJobRequest queryPersonalFineTuningJobRequest) {
        QueryPersonalFineTuningJobApiResponse queryPersonalFineTuningJobApiResponse = new QueryPersonalFineTuningJobApiResponse();

        try {
            PersonalFineTuningJob personalFineTuningJob = chatApiService.queryPersonalFineTuningJobs(queryPersonalFineTuningJobRequest.getLimit(), queryPersonalFineTuningJobRequest.getAfter());
            if (personalFineTuningJob != null) {
                queryPersonalFineTuningJobApiResponse.setSuccess(true);
                queryPersonalFineTuningJobApiResponse.setData(personalFineTuningJob);
                queryPersonalFineTuningJobApiResponse.setMsg("调用成功");
                queryPersonalFineTuningJobApiResponse.setCode(200);
            }
        } catch (Exception e) {
            String[] error = e.getMessage().split("&");
            queryPersonalFineTuningJobApiResponse.setCode(error.length >= 3 ? Integer.parseInt(error[2]) : 500);
            queryPersonalFineTuningJobApiResponse.setMsg("调用失败，异常:" + error[0]);
            queryPersonalFineTuningJobApiResponse.setSuccess(false);
            ChatError chatError = new ChatError();
            chatError.setCode(error.length >= 2 ? Integer.parseInt(error[1]) : 500);
            chatError.setMessage(error[0]);
            PersonalFineTuningJob personalFineTuningJob = new PersonalFineTuningJob();
            personalFineTuningJob.setError(chatError);
            queryPersonalFineTuningJobApiResponse.setData(personalFineTuningJob);
        }
        return queryPersonalFineTuningJobApiResponse;
    }


    public static final class Builder {
        private final ConfigV4 config = new ConfigV4();

        public Builder(String apiSecretKey) {
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
         * @return
         */
        public Builder disableTokenCache() {
            config.setDisableTokenCache(true);
            return this;
        }

        /**
         * 使用accessToken请求 默认false
         * @return
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


        /**
         * 设置是否开发模式
         * @param devMode 是否开发模式
         * @return Builder
         */
        public Builder devMode(boolean devMode) {
            config.setDevMode(devMode);
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
            if (config.isDevMode()){
                baseUrl = TEST_BASE_URL;
            }else {
                baseUrl = BASE_URL;
            }
            client.setChatApiService(new ChatApiService(config.getHttpClient(),baseUrl));
            return client;
        }
    }


}
