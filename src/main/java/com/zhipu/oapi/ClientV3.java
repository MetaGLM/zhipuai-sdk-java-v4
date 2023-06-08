package com.zhipu.oapi;

import com.google.gson.Gson;
import com.zhipu.oapi.core.ConfigV3;
import com.zhipu.oapi.core.cache.ICache;
import com.zhipu.oapi.core.cache.LocalCache;
import com.zhipu.oapi.core.httpclient.IHttpTransport;
import com.zhipu.oapi.core.httpclient.OkHttpTransport;
import com.zhipu.oapi.core.request.RawRequest;
import com.zhipu.oapi.core.response.RawResponse;
import com.zhipu.oapi.core.token.GlobalTokenManager;
import com.zhipu.oapi.core.token.TokenManager;
import com.zhipu.oapi.service.v3.*;
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
public class ClientV3 {

    private static final Logger logger = LoggerFactory.getLogger(ClientV3.class);

    private ConfigV3 config;

    public ModelApiResponse invokeModelApi(ModelApiRequest request) {
        String paramMsg = validateParams(request);
        if (StringUtils.isNotEmpty(paramMsg)) {
            return new ModelApiResponse(-100, String.format("invalid param: %s", paramMsg));
        }

        if (Constants.invokeMethodAsync.equals(request.getInvokeMethod())) {
            return asyncInvoke(request);
        } else if (Constants.invokeMethodSse.equals(request.getInvokeMethod())) {
            return sseInvoke(request);
        }
        return null;
    }

    public QueryModelResultResponse queryModelResult(QueryModelResultRequest request) {
        QueryModelResultResponse resp = new QueryModelResultResponse();

        try {
            RawRequest rawReq = new RawRequest();
            rawReq.setConfigV3(config);
            rawReq.setReqUrl(baseQueryResultUrl());
            Map<String, String> pathParams = new HashMap<>();
            pathParams.put("task_id", request.getTaskId());
            rawReq.setPathParams(pathParams);
            String token = GlobalTokenManager.getTokenManagerV3().getToken(config);
            rawReq.setToken(token);
            RawResponse rawResp = this.getConfig().getHttpTransport().executeGet(rawReq);
            resp.setCode(rawResp.getStatusCode());
            resp.setMsg(rawResp.getMsg());
            if (resp.getCode() == 200) {
                Gson gson = new Gson();
                ModelData data = gson.fromJson(new String(rawResp.getBody()), ModelData.class);
                resp.setData(data);
            }
            return resp;
        } catch (Exception e) {
            logger.error("call query result fail", e);
            resp.setCode(500);
            resp.setMsg("internal error, please check log");
            return resp;
        }
    }

    private ModelApiResponse asyncInvoke(ModelApiRequest request) {
        ModelApiResponse resp = new ModelApiResponse();
        try {
            RawRequest rawReq = new RawRequest();
            // String requestTaskNo = IdUtil.getSnowflake(1, 2).nextIdStr();
            Map<String, Object> paramsMap = new HashMap();
            paramsMap.put("request_id", request.getRequestId());
            paramsMap.put("prompt", request.getPrompt());
            paramsMap.put("temperature", request.getTemperature());
            paramsMap.put("top_p", request.getTopP());
            rawReq.setBody(paramsMap);
            rawReq.setReqUrl(baseModelUrl());
            Map<String, String> pathParams = new HashMap<>();
            pathParams.put("model", request.getModelId());
            pathParams.put("invoke_method", Constants.invokeMethodAsync);
            rawReq.setPathParams(pathParams);
            rawReq.setConfigV3(config);
            String token = GlobalTokenManager.getTokenManagerV3().getToken(config);
            rawReq.setToken(token);

            RawResponse rawResp = this.getConfig().getHttpTransport().executePost(rawReq);
            resp.setCode(rawResp.getStatusCode());
            resp.setMsg(rawResp.getMsg());
            if (rawResp.getStatusCode() == 200) {
                Gson gson = new Gson();
                ModelData data = gson.fromJson(new String(rawResp.getBody()), ModelData.class);
                resp.setData(data);
            }
            return resp;
        } catch (Exception e) {
            logger.error("invoke model fail", e);
            resp.setCode(500);
            resp.setMsg("internal error, please check log");
            return resp;
        }
    }

    private ModelApiResponse sseInvoke(ModelApiRequest request) {

        RawRequest rawReq = new RawRequest();
        // body
        // String requestTaskNo = IdUtil.getSnowflake(1, 2).nextIdStr();
        Map<String, Object> paramsMap = new HashMap();
        paramsMap.put("request_id", request.getRequestId());
        paramsMap.put("prompt", request.getPrompt());
        // incremental暂不支持配置，强制为true（增量返回）
        paramsMap.put("incremental", request.isIncremental());
        paramsMap.put("temperature", request.getTemperature());
        paramsMap.put("top_p", request.getTopP());

        // sseformat, 用于兼容解决sse增量模式okhttpsse截取data:后面空格问题, [data: hello]。
        paramsMap.put("sseFormat", ModelConstants.sseFormat);
        rawReq.setBody(paramsMap);


        // sse info
        // TODO: listener放到config更合适
        rawReq.setSseListener(request.getSseListener());

        // url
        rawReq.setReqUrl(baseModelUrl());
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("model", request.getModelId());
        pathParams.put("invoke_method", Constants.invokeMethodSse);
        rawReq.setPathParams(pathParams);

        // token
        String token = GlobalTokenManager.getTokenManagerV3().getToken(config);
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
            resp.setMsg("internal error");
        }

        return resp;
    }

    private String validateParams(ModelApiRequest request) {
        if (request == null) {
            return "request can not be null";
        }
        // 目前仅支持异步和sse接口
        if (!Constants.invokeMethodAsync.equals(request.getInvokeMethod())
                && !Constants.invokeMethodSse.equals(request.getInvokeMethod())) {
            return "invalid invoke method";
        }
        //
        if (request.getPrompt() == null || request.getPrompt().size() == 0) {
            return "prompt can not be empty";
        }
        return null;
    }

    public static final class Builder {
        private ConfigV3 config = new ConfigV3();

        public Builder(String apiSecretKey) {
            config.setApiSecretKey(apiSecretKey);
        }

        public Builder(String apiKey, String apiSecret) {
            config.setApiKey(apiKey);
            config.setApiSecret(apiSecret);
            config.setDisableTokenCache(false);
        }

        public ClientV3.Builder disableTokenCache() {
            config.setDisableTokenCache(true);
            return this;
        }


        public ClientV3.Builder tokenCache(ICache cache) {
            config.setCache(cache);
            return this;
        }

        public ClientV3.Builder tokenExpire(int expireMillis) {
            config.setExpireMillis(expireMillis);
            return this;
        }

        public ClientV3.Builder httpTransport(IHttpTransport httpTransport) {
            config.setHttpTransport(httpTransport);
            return this;
        }

        public ClientV3.Builder devMode(boolean devMode) {
            config.setDevMode(devMode);
            return this;
        }


        private void initCache(ConfigV3 config) {
            if (config.getCache() != null) {
                GlobalTokenManager.setTokenManager(new TokenManager(config.getCache()));
            } else {
                ICache cache = LocalCache.getInstance();
                GlobalTokenManager.setTokenManager(new TokenManager(cache));
            }
        }

        private void initHttpTransport(ConfigV3 config) {
            if (config.getHttpTransport() == null) {
                if (config.getRequestTimeOut() > 0) {
                    config.setHttpTransport(new OkHttpTransport(OkHttps.create(config.getRequestTimeOut(), config.getTimeOutTimeUnit())));
                } else {
                    config.setHttpTransport(new OkHttpTransport(OkHttps.defaultClient));
                }
            }
        }

        public ClientV3 build() {
            ClientV3 client = new ClientV3();
            client.setConfig(config);
            initCache(config);
            initHttpTransport(config);
            return client;
        }
    }

    public ConfigV3 getConfig() {
        return config;
    }

    public void setConfig(ConfigV3 config) {
        this.config = config;
    }

    private String baseModelUrl() {
        if (config.isDevMode()) {
            return Constants.invokeModelV3UrlDev;
        }
        return Constants.invokeModelV3Url;
    }

    private String baseQueryResultUrl() {
        if (config.isDevMode()) {
            return Constants.queryResultV3UrlDev;
        }
        return Constants.queryResultV3Url;
    }
}
