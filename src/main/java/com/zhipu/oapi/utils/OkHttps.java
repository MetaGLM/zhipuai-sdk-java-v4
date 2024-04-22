package com.zhipu.oapi.utils;

import com.zhipu.oapi.service.v4.model.AuthenticationInterceptor;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class OkHttps {

    public static OkHttpClient create(String token, long callTimeout, TimeUnit timeUnit) {
        return new OkHttpClient.Builder()
                .addInterceptor(new AuthenticationInterceptor(token))
                .callTimeout(callTimeout, timeUnit)
                .build();
    }
}
