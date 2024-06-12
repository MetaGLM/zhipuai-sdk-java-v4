package com.zhipu.oapi.core;

import com.zhipu.oapi.core.cache.ICache;
import com.zhipu.oapi.service.v4.api.ChatApiService;
import lombok.Getter;
import lombok.Setter;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;


public class ConfigV4 {
    @Getter
    @Setter
    private String baseUrl;
    // api credentials
    // apiSecretKey = {apiKey}.{apiSecret}
    @Getter
    private String apiSecretKey="";
    @Setter
    @Getter
    private String apiKey="";
    @Setter
    @Getter
    private String apiSecret = "";

    // jwt config
    // jwt过期时间，默认30分钟
    @Setter
    @Getter
    private int expireMillis = 30 * 60 * 1000;
    // jwt加密算法
    @Setter
    @Getter
    private String alg = "HS256";
    @Setter
    @Getter
    private boolean disableTokenCache;

    // 缓存
    @Setter
    @Getter
    private ICache cache;
    // 传输层
    @Setter
    @Getter
    private OkHttpClient httpClient;

    /**
     * @see OkHttpClient.Builder#connectionPool(ConnectionPool)
     */
    @Setter
    @Getter
    private okhttp3.ConnectionPool connectionPool;
    /**
     * @see OkHttpClient.Builder#callTimeout(long, TimeUnit)
     */
    @Setter
    @Getter
    private int requestTimeOut;
    /**
     * @see OkHttpClient.Builder#connectTimeout(long, TimeUnit)
     */
    @Setter
    @Getter
    private int connectTimeout;

    /**
     * @see OkHttpClient.Builder#readTimeout(long, TimeUnit)
     */
    @Setter
    @Getter
    private int readTimeout;

    /**
     * @see OkHttpClient.Builder#writeTimeout(long, TimeUnit)
     */
    @Setter
    @Getter
    private int writeTimeout;
    @Setter
    @Getter
    private TimeUnit timeOutTimeUnit;

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

    public void setApiSecretKey(String apiSecretKey) {
        this.apiSecretKey = apiSecretKey;
        String[] arrStr = apiSecretKey.split("\\.");
        if (arrStr.length != 2) {
            throw new RuntimeException("invalid apiSecretKey");
        }
        this.apiKey = arrStr[0];
        this.apiSecret = arrStr[1];
    }

}
