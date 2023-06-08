package com.zhipu.oapi.core.token;

import com.zhipu.oapi.core.Config;
import com.zhipu.oapi.core.cache.ICache;
import com.zhipu.oapi.utils.StringUtils;
import com.zhipu.oapi.utils.WuDaoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TokenManager {

    private static final Logger log = LoggerFactory.getLogger(TokenManager.class);
    // token默认超时为8小时,留10s的buffer
    private static final int expireSeconds = 8 * 60 * 60 - 10;
    private static final String TokenKeyPrefix = "zhipu_oapi_token";
    private static final String GetTokenUrl = "https://maas.aminer.cn/api/paas/passApiToken/createApiToken";
    private ICache cache;

    public TokenManager(ICache cache) {
        this.cache = cache;
    }

    public String getToken(Config config) throws Exception {
        String key = genTokenCacheKey(config.getApiKey());
        String cacheToken = cache.get(key);
        if (!StringUtils.isEmpty(cacheToken)) {
            return cacheToken;
        }
        String token = getTokenFromServer(config);
        if (StringUtils.isEmpty(token)) {
            log.error("get token fail, token is empty!");
            return token;
        }
        cache.set(key, token, expireSeconds, TimeUnit.SECONDS);
        return token;
    }

    private String genTokenCacheKey(String apiKey) {
        return String.format("%s-%s", TokenKeyPrefix, apiKey);
    }

    private String getTokenFromServer(Config config) throws Exception {
        Map<String, Object> resultMap = WuDaoUtils.getToken(GetTokenUrl, config.getApiKey(), config.getPubKey());
        Double code = (Double) resultMap.get("code");
        if (code.intValue() == 200) {
            String token = String.valueOf(resultMap.get("data"));
            return token;
        } else {
            log.error("get token fail! code: %s, msg: %s", code.intValue(), resultMap.get("msg"));
            return null;
        }
    }

    public ICache getCache() {
        return cache;
    }

    public void setCache(ICache cache) {
        this.cache = cache;
    }
}
