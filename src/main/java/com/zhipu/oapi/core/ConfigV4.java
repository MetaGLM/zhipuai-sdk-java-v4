package com.zhipu.oapi.core;

import com.zhipu.oapi.core.cache.ICache;
import com.zhipu.oapi.core.httpclient.IHttpTransport;

import java.util.concurrent.TimeUnit;

public class ConfigV4 {

    // api credentials
    // apiSecretKey = {apiKey}.{apiSecret}
    private String apiSecretKey="120dd8b9ce50a9dd7b0583909dd49822.N3VMdpTH7RqkHIFT";
    private String apiKey="120dd8b9ce50a9dd7b0583909dd49822";
    private String apiSecret = "CwMTq2c3mGGQqs9U";

    // jwt config
    // jwt过期时间，默认30分钟
    private int expireMillis = 30 * 60 * 1000;
    // jwt加密算法
    private String alg = "HS256";
    private boolean disableTokenCache;

    // 缓存
    private ICache cache;
    // 传输层
    private IHttpTransport httpTransport;
    private int requestTimeOut;
    private TimeUnit timeOutTimeUnit;

    // 开发能力
    private boolean devMode;

    public ConfigV4() {
    }

    public ConfigV4(String apiSecretKey) {
        this.apiSecretKey = apiSecretKey;
        String[] arrStr = apiSecretKey.split("\\.");
        if (arrStr.length != 2) {
            throw new RuntimeException("invalid apiSecretKey");
        }
        this.apiKey = arrStr[0];
        this.apiSecret = arrStr[1];
    }

    public ConfigV4(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.apiSecretKey = String.format("%s.%s", apiKey, apiSecret);
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public int getExpireMillis() {
        return expireMillis;
    }

    public void setExpireMillis(int expireMillis) {
        this.expireMillis = expireMillis;
    }

    public String getAlg() {
        return alg;
    }

    public void setAlg(String alg) {
        this.alg = alg;
    }

    public boolean isDisableTokenCache() {
        return disableTokenCache;
    }

    public void setDisableTokenCache(boolean disableTokenCache) {
        this.disableTokenCache = disableTokenCache;
    }

    public ICache getCache() {
        return cache;
    }

    public void setCache(ICache cache) {
        this.cache = cache;
    }

    public IHttpTransport getHttpTransport() {
        return httpTransport;
    }

    public void setHttpTransport(IHttpTransport httpTransport) {
        this.httpTransport = httpTransport;
    }

    public int getRequestTimeOut() {
        return requestTimeOut;
    }

    public void setRequestTimeOut(int requestTimeOut) {
        this.requestTimeOut = requestTimeOut;
    }

    public TimeUnit getTimeOutTimeUnit() {
        return timeOutTimeUnit;
    }

    public void setTimeOutTimeUnit(TimeUnit timeOutTimeUnit) {
        this.timeOutTimeUnit = timeOutTimeUnit;
    }

    public String getApiSecretKey() {
        return apiSecretKey;
    }

    public void setApiSecretKey(String apiSecretKey) {
        this.apiSecretKey = apiSecretKey;
        String[] arrStr = apiSecretKey.split("\\.");
        if (arrStr.length != 2) {
            throw new RuntimeException("invalid apiSecretKey");
        }
        this.apiKey = arrStr[0];
        this.apiSecret = arrStr[1];
    }

    public boolean isDevMode() {
        return devMode;
    }

    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }
}
