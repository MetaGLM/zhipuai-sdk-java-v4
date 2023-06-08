package com.zhipu.oapi.core.token;

import com.zhipu.oapi.core.cache.LocalCache;

public class GlobalTokenManager {

    private static volatile TokenManager globalTokenManager = new TokenManager(
            LocalCache.getInstance());

    public static TokenManager getTokenManager() {
        return globalTokenManager;
    }

    public static void setTokenManager(TokenManager tokenManager) {
        globalTokenManager = tokenManager;
    }

    // v3 token
    private static volatile TokenManagerV3 globalTokenManagerV3 = new TokenManagerV3(
            LocalCache.getInstance());

    public static TokenManagerV3 getTokenManagerV3() {
        return globalTokenManagerV3;
    }

    public static void setTokenManager(TokenManagerV3 tokenManager) {
        globalTokenManagerV3 = tokenManager;
    }}
