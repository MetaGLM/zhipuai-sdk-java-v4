package com.zhipu.oapi.utils;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class OkHttps {

    public static final OkHttpClient defaultClient = create(0, TimeUnit.MILLISECONDS);

    public static OkHttpClient create(long callTimeout, TimeUnit timeUnit) {
        return new OkHttpClient.Builder()
                .callTimeout(callTimeout, timeUnit)
                .build();
    }
}
