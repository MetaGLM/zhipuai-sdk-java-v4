package com.zhipu.oapi.core.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zhipu.oapi.core.ConfigV4;
import com.zhipu.oapi.core.cache.ICache;
import com.zhipu.oapi.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TokenManagerV4 {

    private static final Logger logger = LoggerFactory.getLogger(TokenManagerV4.class);
    private ICache cache;
    private static final String tokenV4KeyPrefix = "zhipu_oapi_token_v4";

    public TokenManagerV4(ICache cache) {
        this.cache = cache;
    }

    public String getToken(ConfigV4 config) {
        String tokenCacheKey = genTokenCacheKey(config.getApiKey());
        String cacheToken = cache.get(tokenCacheKey);
        if (StringUtils.isNotEmpty(cacheToken)) {
            return cacheToken;
        }
        String newToken = createJwt(config);
        cache.set(tokenCacheKey, newToken, config.getExpireMillis(), TimeUnit.MILLISECONDS);
        return newToken;
    }

    private static String createJwt(ConfigV4 config) {
        Algorithm alg;
        String algId = config.getAlg();
        if ("HS256".equals(algId)) {
            //alg = Algorithm.HMAC256(config.getApiSecret());
            try {
                alg = Algorithm.HMAC256(config.getApiSecret().getBytes("utf-8"));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            // 目前仅支持HS256
            logger.error("algorithm: %s not supported", algId);
            return null;
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("api_key", config.getApiKey());
        payload.put("exp", System.currentTimeMillis() + config.getExpireMillis());
        payload.put("timestamp", Calendar.getInstance().getTimeInMillis());
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("alg", "HS256");
        headerClaims.put("sign_type", "SIGN");
        String token = JWT.create().withPayload(payload).withHeader(headerClaims).sign(alg);
        return token;
    }

    private String genTokenCacheKey(String apiKey) {
        return String.format("%s-%s", tokenV4KeyPrefix, apiKey);
    }
}
