package com.zhipu.oapi;

import cn.hutool.core.util.IdUtil;
import com.google.gson.Gson;
import com.zhipu.oapi.core.Config;
import com.zhipu.oapi.core.cache.ICache;
import com.zhipu.oapi.core.cache.LocalCache;
import com.zhipu.oapi.core.httpclient.IHttpTransport;
import com.zhipu.oapi.core.httpclient.OkHttpTransport;
import com.zhipu.oapi.core.request.RawRequest;
import com.zhipu.oapi.core.response.RawResponse;
import com.zhipu.oapi.core.token.GlobalTokenManager;
import com.zhipu.oapi.core.token.TokenManager;
import com.zhipu.oapi.service.v2.ChatGlm6BReq;
import com.zhipu.oapi.service.v2.ChatGlm6BResp;
import com.zhipu.oapi.service.v2.QueryRequestTaskResultRequest;
import com.zhipu.oapi.service.v2.QueryRequestTaskResultResponse;
import com.zhipu.oapi.utils.OkHttps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Client {

    private static final Logger log = LoggerFactory.getLogger(Client.class);

    private Config config;

    public ChatGlm6BResp chatGlm6B(ChatGlm6BReq req) {
        ChatGlm6BResp resp = new ChatGlm6BResp();
        try {
            RawRequest rawReq = new RawRequest();
            String requestTaskNo = IdUtil.getSnowflake(1, 2).nextIdStr();
            Map<String, Object> paramsMap = new HashMap();
            paramsMap.put("requestTaskNo", requestTaskNo);
            paramsMap.put("prompt", req.getPrompt());
            paramsMap.put("history", req.getHistory());
            rawReq.setBody(paramsMap);
            rawReq.setReqUrl(Constants.chatGlm6BReqUrl);
            rawReq.setConfig(config);
            String token = GlobalTokenManager.getTokenManager().getToken(config);
            rawReq.setToken(token);

            RawResponse rawResp = this.getConfig().getHttpTransport().executePost(rawReq);
            resp.setCode(rawResp.getStatusCode());
            resp.setMsg(rawResp.getMsg());
            Gson gson = new Gson();
            ChatGlm6BResp.ChatGlm6BData data = gson.fromJson(new String(rawResp.getBody()), ChatGlm6BResp.ChatGlm6BData.class);
            resp.setData(data);
            return resp;
        } catch (Exception e) {
            log.error("call chatgl6b fail", e);
            resp.setCode(500);
            resp.setMsg("internal error, please check log");
            return resp;
        }
    }

    public QueryRequestTaskResultResponse queryRequestTaskResult(QueryRequestTaskResultRequest request) {

        QueryRequestTaskResultResponse resp = new QueryRequestTaskResultResponse();

        try {
            RawRequest rawReq = new RawRequest();
            rawReq.setConfig(config);
            rawReq.setReqUrl(Constants.queryRequestTaskResultUrl);
            Map<String, String> pathParams = new HashMap<>();
            pathParams.put("taskOrderNo", request.getTaskOrderNo());
            rawReq.setPathParams(pathParams);
            String token = GlobalTokenManager.getTokenManager().getToken(config);
            rawReq.setToken(token);
            RawResponse rawResp = this.getConfig().getHttpTransport().executeGet(rawReq);
            resp.setCode(rawResp.getStatusCode());
            resp.setMsg(rawResp.getMsg());
            // TODO 转换成实体类，换行符会导致报错，先转为Map
            Gson gson = new Gson();
            QueryRequestTaskResultResponse.QueryRequestTaskResultData data = gson.fromJson(new String(rawResp.getBody()), QueryRequestTaskResultResponse.QueryRequestTaskResultData.class);
            resp.setData(data);
            return resp;
        } catch (Exception e) {
            log.error("call query result fail", e);
            resp.setCode(500);
            resp.setMsg("internal error, please check log");
            return resp;
        }
    }


    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public static final class Builder {
        private Config config = new Config();

        public Builder(String apiKey, String pubKey) {
            config.setApiKey(apiKey);
            config.setPubKey(pubKey);
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

        public Builder requestTimeout(int timeout, TimeUnit timeUnit) {
            config.setRequestTimeOut(timeout);
            config.setTimeOutTimeUnit(timeUnit);
            return this;
        }

        public Builder httpTransport(IHttpTransport httpTransport) {
            config.setHttpTransport(httpTransport);
            return this;
        }

        private void initCache(Config config) {
            if (config.getCache() != null) {
                GlobalTokenManager.setTokenManager(new TokenManager(config.getCache()));
            } else {
                ICache cache = LocalCache.getInstance();
                GlobalTokenManager.setTokenManager(new TokenManager(cache));
            }
        }

        private void initHttpTransport(Config config) {
            if (config.getHttpTransport() == null) {
                if (config.getRequestTimeOut() > 0) {
                    config.setHttpTransport(new OkHttpTransport(OkHttps.create(config.getRequestTimeOut(), config.getTimeOutTimeUnit())));
                } else {
                    config.setHttpTransport(new OkHttpTransport(OkHttps.defaultClient));
                }
            }
        }

        public Client build() {
            Client client = new Client();
            client.setConfig(config);
            initCache(config);
            initHttpTransport(config);
            return client;
        }
    }
}
