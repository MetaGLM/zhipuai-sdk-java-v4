package com.zhipu.oapi.core;

import com.zhipu.oapi.core.cache.ICache;
import com.zhipu.oapi.core.httpclient.IHttpTransport;

import java.util.concurrent.TimeUnit;

public class Config {
    private String apiKey;
    private String pubKey;
    private int requestTimeOut;
    private TimeUnit timeOutTimeUnit;
    private ICache cache;
    private boolean disableTokenCache;

    private IHttpTransport httpTransport;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

    public boolean isDisableTokenCache() {
        return disableTokenCache;
    }

    public void setDisableTokenCache(boolean disableTokenCache) {
        this.disableTokenCache = disableTokenCache;
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
}
