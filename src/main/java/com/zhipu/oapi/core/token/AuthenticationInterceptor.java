package com.zhipu.oapi.core.token;

import com.zhipu.oapi.core.ConfigV4;
import com.zhipu.oapi.utils.StringUtils;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

/**
 * OkHttp Interceptor that adds an authorization token header
 */
public class
AuthenticationInterceptor implements Interceptor {

    private final ConfigV4 config;

    public AuthenticationInterceptor(ConfigV4 config) {
        Objects.requireNonNull(config.getApiSecretKey(), "ZhiPuAI token required");
        this.config = config;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String accessToken = null;
        if(this.config.isDisableTokenCache()){
            accessToken = this.config.getApiSecretKey();
        }else {
            TokenManagerV4 tokenManagerV4 = GlobalTokenManager.getTokenManagerV4();

            accessToken = tokenManagerV4.getToken(this.config);
        }
        String source_channel = "java-sdk";
        if(StringUtils.isNotEmpty(config.getSource_channel())){
            source_channel = config.getSource_channel();
        }
        Request request = chain.request()
                .newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .header("x-source-channel", source_channel)
                .build();
        return chain.proceed(request);
    }
}
