package com.zhipu.oapi;

import cn.hutool.core.bean.BeanUtil;
import com.google.gson.Gson;
import com.zhipu.oapi.core.ConfigV4;
import com.zhipu.oapi.core.cache.ICache;
import com.zhipu.oapi.core.cache.LocalCache;
import com.zhipu.oapi.core.httpclient.OkHttpTransport;
import com.zhipu.oapi.core.request.RawRequest;
import com.zhipu.oapi.core.response.RawResponse;
import com.zhipu.oapi.core.token.GlobalTokenManager;
import com.zhipu.oapi.core.token.TokenManager;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * v3接口client，API系统进行了重构，无法跟之前的兼容，因此重新实现Client层
 * 新的接口地址：open.bigmodel.cn/api/paas/v3/model-api/{model}/{invoke_method}
 * 文档地址：https://test.bigmodel.cn/doc/api#sdk（测试中）
 * 支持模型包括：只有语言模型支持sse，其他均为async调用，需要结合query接口一起使用
 * - chatGLM_6b_SSE
 * - article-model
 * - title-creation
 * - multilingual-code-generate
 * - codegeex-generate-block
 * - multilingual-code-translate
 * - text2image
 * 结果查询接口:maas.aminer.cn/api/paas/request-task/query-request-task-result/{taskOrderNo}
 * - 有新接口吗？
 */
public class ClientV4 {

    private static final Logger logger = LoggerFactory.getLogger(ClientV4.class);

    private ConfigV4 config;

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
        RawRequest rawReq = new RawRequest();
        Map<String, Object> paramsMap = new HashMap();
        paramsMap.put("request_id", request.getRequestId());
        paramsMap.put("messages", request.getMessages());
        paramsMap.put("model", request.getModel());
        paramsMap.put("stream", true);
        paramsMap.put("tools", request.getTools());
        paramsMap.put("tool_choice", request.getToolChoice());
        paramsMap.put("temperature", request.getTemperature());
        paramsMap.put("top_p", request.getTopP());
        paramsMap.put("sensitive_word_check", request.getSensitiveWordCheck());
        paramsMap.put("do_sample", request.getDoSample());
        rawReq.setBody(paramsMap);
        // token
        String token = GlobalTokenManager.getTokenManagerV4().getToken(config);
        rawReq.setToken(token);
        ModelApiResponse resp = new ModelApiResponse();
        try {
            RawResponse rawResp = this.getConfig().getHttpTransport().sseExecute(rawReq);
            resp.setCode(rawResp.getStatusCode());
            resp.setMsg(rawResp.getMsg());
            Gson gson = new Gson();
            ModelData data = gson.fromJson(new String(rawResp.getBody()), ModelData.class);
            resp.setData(data);
            return resp;
        } catch (Exception e) {
            logger.error("sse invoke model fail!", e);
            resp.setCode(500);
            resp.setMsg("调用失败，异常:" + e.getMessage());
        }
        return resp;
    }

    private ModelApiResponse invoke(ChatCompletionRequest request) {
        Map<String, Object> paramsMap = new HashMap();
        paramsMap.put("request_id", request.getRequestId());
        paramsMap.put("messages", request.getMessages());
        paramsMap.put("model", request.getModel());
        paramsMap.put("stream", false);
        paramsMap.put("tools", request.getTools());
        paramsMap.put("tool_choice", request.getToolChoice());
        paramsMap.put("temperature", request.getTemperature());
        paramsMap.put("top_p", request.getTopP());
        paramsMap.put("sensitive_word_check", request.getSensitiveWordCheck());
        paramsMap.put("do_sample", request.getDoSample());
        // token
        String token = GlobalTokenManager.getTokenManagerV4().getToken(config);
        ChatApiService service = new ChatApiService(token);
        ModelApiResponse resp = new ModelApiResponse();
        try {
            ChatCompletionResult chatCompletionResult = service.createChatCompletion(paramsMap);
            if (chatCompletionResult != null) {
                resp.setCode(200);
                resp.setMsg("调用成功");
                ModelData modelData = new ModelData();
                BeanUtil.copyProperties(chatCompletionResult, modelData);
                resp.setData(modelData);
            }
            return resp;
        } catch (Exception e) {
            logger.error("invoke model fail!", e);
            resp.setCode(500);
            resp.setMsg("调用失败，异常:" + e.getMessage());
        }
        return resp;
    }


    private ModelApiResponse asyncInvoke(ChatCompletionRequest request) {
        RawRequest rawReq = new RawRequest();
        Map<String, Object> paramsMap = new HashMap();
        paramsMap.put("request_id", request.getRequestId());
        paramsMap.put("messages", request.getMessages());
        paramsMap.put("model", request.getModel());
        paramsMap.put("stream", false);
        paramsMap.put("tools", request.getTools());
        paramsMap.put("tool_choice", request.getToolChoice());
        paramsMap.put("temperature", request.getTemperature());
        paramsMap.put("top_p", request.getTopP());
        paramsMap.put("sensitive_word_check", request.getSensitiveWordCheck());
        paramsMap.put("do_sample", request.getDoSample());
        rawReq.setBody(paramsMap);
        // token
        String token = GlobalTokenManager.getTokenManagerV4().getToken(config);
        ChatApiService service = new ChatApiService(token);
        ModelApiResponse resp = new ModelApiResponse();
        try {
            ChatCompletionAsyncResult chatCompletionAsyncResult = service.createChatCompletionAsync(rawReq.getBody());
            if (chatCompletionAsyncResult != null) {
                resp.setCode(200);
                resp.setMsg("调用成功");
                ModelData modelData = new ModelData();
                modelData.setModel(chatCompletionAsyncResult.getModel());
                modelData.setTaskId(chatCompletionAsyncResult.getId());
                modelData.setTaskStatus(TaskStatus.valueOf(chatCompletionAsyncResult.getTask_status()));
                modelData.setRequestId(chatCompletionAsyncResult.getRequest_id());
                resp.setData(modelData);
            }
        } catch (Exception e) {
            logger.error("async invoke model fail!", e);
            resp.setCode(500);
            resp.setMsg("调用失败，异常:" + e.getMessage());
        }
        return resp;

    }

    public QueryModelResultResponse queryModelResult(QueryModelResultRequest request) {
        QueryModelResultResponse resp = new QueryModelResultResponse();
        String token = GlobalTokenManager.getTokenManagerV4().getToken(config);
        ChatApiService service = new ChatApiService(token);
        try {
            ChatCompletionResult chatCompletionResult = service.queryAsyncResult(request.getTaskId());
            if (chatCompletionResult != null) {
                resp.setCode(200);
                resp.setMsg("调用成功");
                ModelData modelData = new ModelData();
                BeanUtil.copyProperties(chatCompletionResult, modelData);
                resp.setData(modelData);
            }
        } catch (Exception e) {
            logger.error("query result fail", e);
            resp.setCode(500);
            resp.setMsg("调用失败，异常:" + e.getMessage());
            return resp;
        }
        return resp;
    }

    public ImageApiResponse createImage(CreateImageRequest createImageRequest) {
        ImageApiResponse imageApiResponse = new ImageApiResponse();
        String token = GlobalTokenManager.getTokenManagerV4().getToken(config);
        ChatApiService chatApiService = new ChatApiService(token);
        Map<String, Object> request = new HashMap<>();
        request.put("prompt", createImageRequest.getPrompt());
        request.put("model", createImageRequest.getModel());
        try {
            ImageResult image = chatApiService.createImage(request);
            if (image != null) {
                imageApiResponse.setMsg("调用成功");
                imageApiResponse.setCode(200);
                imageApiResponse.setSuccess(true);
                imageApiResponse.setData(image);
            }
        } catch (Exception e) {
            logger.error("createImageResult:", e);
            imageApiResponse.setCode(500);
            imageApiResponse.setSuccess(false);
            imageApiResponse.setMsg("调用失败，异常:" + e.getMessage());
        }
        return imageApiResponse;
    }

    private String validateParams(ChatCompletionRequest request) {
        if (request == null) {
            return "request can not be null";
        }
        if (!request.getStream() && StringUtils.isEmpty(request.getInvokeMethod())) {
            return "invoke method can not be empty";
        }
        if (request.getMessages() == null || request.getMessages().size() == 0) {
            return "message can not be empty";
        }
        if (request.getModel() == null) {
            return "model can not be empty";
        }
        return null;
    }

    public EmbeddingApiResponse invokeEmbeddingsApi(EmbeddingRequest embeddingRequest) {
        EmbeddingApiResponse embeddingApiResponse = new EmbeddingApiResponse();
        String token = GlobalTokenManager.getTokenManagerV4().getToken(config);
        ChatApiService service = new ChatApiService(token);
        try {
            EmbeddingResult embeddingResult = service.createEmbeddings(embeddingRequest);
            if (embeddingResult != null) {
                embeddingApiResponse.setCode(200);
                embeddingApiResponse.setMsg("调用成功");
                embeddingApiResponse.setSuccess(true);
                embeddingApiResponse.setData(embeddingResult);
            }
        } catch (Exception e) {
            logger.error("createEmbeddings:", e);
            embeddingApiResponse.setCode(500);
            embeddingApiResponse.setSuccess(false);
            embeddingApiResponse.setMsg("调用失败，异常:" + e.getMessage());
        }
        return embeddingApiResponse;
    }

    public FileApiResponse invokeUploadFileApi(String purpose, String filePath) {
        FileApiResponse fileApiResponse = new FileApiResponse();
        String token = GlobalTokenManager.getTokenManagerV4().getToken(config);
        ChatApiService service = new ChatApiService(token);
        try {
            File file = service.uploadFile(purpose, filePath);
            if (file != null) {
                fileApiResponse.setCode(500);
                fileApiResponse.setSuccess(false);
                fileApiResponse.setMsg("调用成功");
                fileApiResponse.setData(file);
            }
        } catch (Exception e) {
            fileApiResponse.setCode(500);
            fileApiResponse.setSuccess(false);
            fileApiResponse.setMsg("调用失败,异常:" + e.getMessage());
        }
        return fileApiResponse;
    }

    public QueryFileApiResponse queryFilesApi(QueryFilesRequest queryFilesRequest) {
        QueryFileApiResponse queryFileApiResponse = new QueryFileApiResponse();
        String token = GlobalTokenManager.getTokenManagerV4().getToken(config);
        ChatApiService service = new ChatApiService(token);
        try {
            QueryFileResult queryFileResult = service.queryFileList(queryFilesRequest);
            if(queryFileResult!=null){
                queryFileApiResponse.setCode(500);
                queryFileApiResponse.setSuccess(false);
                queryFileApiResponse.setMsg("调用成功");
                queryFileApiResponse.setData(queryFileResult);
            }
        } catch (Exception e) {
            queryFileApiResponse.setCode(500);
            queryFileApiResponse.setSuccess(false);
            queryFileApiResponse.setMsg("调用失败,异常:" + e.getMessage());
        }
        return queryFileApiResponse;
    }

    public CreateFineTuningJobApiResponse createFineTuningJob(FineTuningJobRequest request) {
        CreateFineTuningJobApiResponse createFineTuningJobApiResponse = new CreateFineTuningJobApiResponse();
        String token = GlobalTokenManager.getTokenManagerV4().getToken(config);
        ChatApiService service = new ChatApiService(token);
        FineTuningJob fineTuningJob = null;
        try {
            fineTuningJob = service.createFineTuningJob(request);
            if(fineTuningJob!=null){
                createFineTuningJobApiResponse.setMsg("调用成功");
                createFineTuningJobApiResponse.setData(fineTuningJob);
                createFineTuningJobApiResponse.setSuccess(true);
                createFineTuningJobApiResponse.setCode(200);
            }
        } catch (Exception e) {
            createFineTuningJobApiResponse.setCode(500);
            createFineTuningJobApiResponse.setSuccess(false);
            createFineTuningJobApiResponse.setMsg("调用失败，异常:"+e.getMessage());
        }
        return createFineTuningJobApiResponse;
    }

    public QueryFineTuningEventApiResponse queryFineTuningJobsEvents(String fineTuningJobId) {
        QueryFineTuningEventApiResponse queryFineTuningEventApiResponse = new QueryFineTuningEventApiResponse();
        String token = GlobalTokenManager.getTokenManagerV4().getToken(config);
        ChatApiService service = new ChatApiService(token);
        try {
            FineTuningEvent fineTuningEvent = service.listFineTuningJobEvents(fineTuningJobId);
            if(fineTuningEvent!=null){
                queryFineTuningEventApiResponse.setSuccess(true);
                queryFineTuningEventApiResponse.setData(fineTuningEvent);
                queryFineTuningEventApiResponse.setCode(200);
                queryFineTuningEventApiResponse.setMsg("调用成功");
            }
        } catch (Exception e) {
            queryFineTuningEventApiResponse.setCode(500);
            queryFineTuningEventApiResponse.setMsg("调用失败，异常:"+e.getMessage());
            queryFineTuningEventApiResponse.setSuccess(false);
        }
        return queryFineTuningEventApiResponse;
    }

    public QueryFineTuningJobApiResponse retrieveFineTuningJobs(String fineTuningJobId) {
        QueryFineTuningJobApiResponse queryFineTuningJobApiResponse = new QueryFineTuningJobApiResponse();
        String token = GlobalTokenManager.getTokenManagerV4().getToken(config);
        ChatApiService service = new ChatApiService(token);
        try {
            FineTuningJob fineTuningJob = service.retrieveFineTuningJob(fineTuningJobId);
            if(fineTuningJob!=null){
                queryFineTuningJobApiResponse.setSuccess(true);
                queryFineTuningJobApiResponse.setData(fineTuningJob);
                queryFineTuningJobApiResponse.setCode(200);
                queryFineTuningJobApiResponse.setMsg("调用成功");
            }
        } catch (Exception e) {
            queryFineTuningJobApiResponse.setCode(500);
            queryFineTuningJobApiResponse.setMsg("调用失败，异常:"+e.getMessage());
            queryFineTuningJobApiResponse.setSuccess(false);
        }

        return queryFineTuningJobApiResponse;
    }

    public QueryPersonalFineTuningJobApiResponse queryPersonalFineTuningJobs(QueryPersonalFineTuningJobRequest queryPersonalFineTuningJobRequest) {
        QueryPersonalFineTuningJobApiResponse queryPersonalFineTuningJobApiResponse = new QueryPersonalFineTuningJobApiResponse();
        String token = GlobalTokenManager.getTokenManagerV4().getToken(config);
        ChatApiService service = new ChatApiService(token);
        try {
            PersonalFineTuningJob personalFineTuningJob = service.queryPersonalFineTuningJobs(queryPersonalFineTuningJobRequest.getLimit(), queryPersonalFineTuningJobRequest.getAfter());
            if(personalFineTuningJob!=null){
                queryPersonalFineTuningJobApiResponse.setSuccess(true);
                queryPersonalFineTuningJobApiResponse.setData(personalFineTuningJob);
                queryPersonalFineTuningJobApiResponse.setMsg("调用成功");
                queryPersonalFineTuningJobApiResponse.setCode(200);
            }
        } catch (Exception e) {
            queryPersonalFineTuningJobApiResponse.setCode(500);
            queryPersonalFineTuningJobApiResponse.setMsg("调用失败,异常:"+e.getMessage());
            queryPersonalFineTuningJobApiResponse.setSuccess(false);
        }
        return queryPersonalFineTuningJobApiResponse;
    }


    public static final class Builder {
        private ConfigV4 config = new ConfigV4();

        public Builder(String apiSecretKey) {
            config.setApiSecretKey(apiSecretKey);
        }

        public Builder(String apiKey, String apiSecret) {
            config.setApiKey(apiKey);
            config.setApiSecret(apiSecret);
            config.setDisableTokenCache(false);
        }

        public Builder disableTokenCache() {
            config.setDisableTokenCache(true);
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


        public Builder devMode(boolean devMode) {
            config.setDevMode(devMode);
            return this;
        }


        private void initCache(ConfigV4 config) {
            if (config.getCache() != null) {
                GlobalTokenManager.setTokenManager(new TokenManager(config.getCache()));
            } else {
                ICache cache = LocalCache.getInstance();
                GlobalTokenManager.setTokenManager(new TokenManager(cache));
            }
        }

        private void initHttpTransport(ConfigV4 config) {
            if (config.getHttpTransport() == null) {
                if (config.getRequestTimeOut() > 0) {
                    config.setHttpTransport(new OkHttpTransport(OkHttps.create(config.getRequestTimeOut(), config.getTimeOutTimeUnit())));
                } else {
                    config.setHttpTransport(new OkHttpTransport(OkHttps.defaultClient));
                }
            }
        }

        public ClientV4 build() {
            ClientV4 client = new ClientV4();
            client.setConfig(config);
            initCache(config);
            initHttpTransport(config);
            return client;
        }
    }

    public ConfigV4 getConfig() {
        return config;
    }

    public void setConfig(ConfigV4 config) {
        this.config = config;
    }


}
