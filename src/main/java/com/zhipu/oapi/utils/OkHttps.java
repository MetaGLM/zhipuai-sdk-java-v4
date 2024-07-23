package com.zhipu.oapi.utils;

import com.zhipu.oapi.core.ConfigV4;
import com.zhipu.oapi.core.token.AuthenticationInterceptor;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

public class OkHttps {

    /**
     *  创建一个OkHttpClient
     * @param config config
     * @return OkHttpClient
     */
    public static OkHttpClient create(ConfigV4 config)
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new AuthenticationInterceptor(config));

        if(config.getRequestTimeOut() > 0) {
            builder.callTimeout(config.getRequestTimeOut(), config.getTimeOutTimeUnit());
        } else {
            builder.callTimeout(30, TimeUnit.SECONDS);
        }
        if (config.getConnectTimeout() > 0) {
            builder.connectTimeout(config.getConnectTimeout(), config.getTimeOutTimeUnit());
        } else {
            builder.connectTimeout(10, TimeUnit.SECONDS);
        }
        if (config.getReadTimeout() > 0) {
            builder.readTimeout(config.getReadTimeout(), config.getTimeOutTimeUnit());
        } else {
            builder.readTimeout(10, TimeUnit.SECONDS);
        }
        if (config.getWriteTimeout() > 0) {
            builder.writeTimeout(config.getWriteTimeout(), config.getTimeOutTimeUnit());
        } else {
            builder.writeTimeout(10, TimeUnit.SECONDS);
        }
        if (config.getConnectionPool() != null) {
            builder.connectionPool(config.getConnectionPool());
        }else {

            builder.connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS));
        }

//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        builder.addInterceptor(logging);


        return builder.build();
    }
}
