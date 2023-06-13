package com.zhipu.modelapi.utils;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class OkHttps {

    // 设置默认超时连接时间200s
    public static final OkHttpClient defaultClient = create(200, TimeUnit.SECONDS);

    public static OkHttpClient create(long connectTimeout, TimeUnit timeUnit) {
        return new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, timeUnit)
                .build();
    }
}
