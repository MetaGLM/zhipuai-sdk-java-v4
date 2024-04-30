package com.zhipu.oapi.utils;

import com.zhipu.oapi.service.v4.model.AuthenticationInterceptor;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class OkHttps {

    /**
     *  创建一个OkHttpClient
     * @param token 智谱 token
     * @param callTimeout  @see OkHttpClient.Builder#callTimeout(long, TimeUnit)
     * @param connectTimeout @see OkHttpClient.Builder#connectTimeout(long, TimeUnit)
     * @param readTimeout @see OkHttpClient.Builder#readTimeout(long, TimeUnit)
     * @param writeTimeout @see OkHttpClient.Builder#writeTimeout(long, TimeUnit)
     * @param timeUnit @see TimeUnit
     * @return OkHttpClient
     */
    public static OkHttpClient create(String token,
                                      long callTimeout,
                                      long connectTimeout,
                                      long readTimeout,
                                      long writeTimeout,
                                      TimeUnit timeUnit) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new AuthenticationInterceptor(token));

        if(callTimeout > 0) {
            builder.callTimeout(callTimeout, timeUnit);
        } else {
            builder.callTimeout(30, TimeUnit.SECONDS);
        }
        if (connectTimeout > 0) {
            builder.connectTimeout(connectTimeout, timeUnit);
        } else {
            builder.connectTimeout(10, TimeUnit.SECONDS);
        }
        if (readTimeout > 0) {
            builder.readTimeout(readTimeout, timeUnit);
        } else {
            builder.readTimeout(10, TimeUnit.SECONDS);
        }
        if (writeTimeout > 0) {
            builder.writeTimeout(writeTimeout, timeUnit);
        } else {
            builder.writeTimeout(10, TimeUnit.SECONDS);
        }

        return builder.build();
    }
}
