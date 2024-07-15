package com.zhipu.oapi.core.logger;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // Log the request
        System.out.println("Sending request to " + request.url());
        System.out.println("Request headers: " + request.headers());
        if (request.body() != null) {
            System.out.println("Request body: " + request.body().toString());
        }

        Response response = chain.proceed(request);

        // Log the response
        ResponseBody responseBody = response.peekBody(Long.MAX_VALUE);
        System.out.println("Response code: " + response.code());
        System.out.println("Response body: " + responseBody.string());

        return response;
    }

}