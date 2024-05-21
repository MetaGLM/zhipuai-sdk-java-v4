package com.zhipu.oapi.core.token;

import com.zhipu.oapi.core.cache.LocalCache;

public class GlobalTokenManager {


    private static volatile TokenManagerV4 globalTokenManagerV4 = new TokenManagerV4(
            LocalCache.getInstance());



    public static TokenManagerV4 getTokenManagerV4() {
        return globalTokenManagerV4;
    }

    public static void setTokenManager(TokenManagerV4 tokenManager) {
        globalTokenManagerV4 = tokenManager;
    }}
