package com.zhipu.oapi.client;

import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.core.ConfigV4;
import com.zhipu.oapi.core.cache.ICache;
import com.zhipu.oapi.core.cache.LocalCache;
import com.zhipu.oapi.core.token.TokenManagerV4;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;


@Testcontainers
public class ClientV4Test {
    static ConfigV4 configV4 = null;
    static OkHttpClient client = null;
    static {
        configV4 = new ConfigV4();
        configV4.setApiSecretKey("a.b");
    }

    @Test
    public void testTokenManagerV4() {
        ICache cache = LocalCache.getInstance();
        TokenManagerV4 tokenManagerV4 = new TokenManagerV4(cache);
        String token = tokenManagerV4.getToken(configV4);
        assert token != null;

    }
    @Test
    public void testTokenManagerV4Cache() {
        String tokenCacheKey = String.format("%s-%s", "zhipu_oapi_token_v4", configV4.getApiSecretKey());
        ICache cache = LocalCache.getInstance();
        TokenManagerV4 tokenManagerV4 = new TokenManagerV4(cache);
        String token = tokenManagerV4.getToken(configV4);
        assert token != null;
        assert cache.get(tokenCacheKey) != null;

    }
    @Test
    public void testClientV4ApiSecretKey() {
        ClientV4 client = new ClientV4.Builder("a.b")
                .enableTokenCache()
                .networkConfig(30, 10, 10, 10, TimeUnit.SECONDS)
                .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
                .build();

        ConfigV4 config = client.getConfig();
        assertEquals("a.b", config.getApiSecretKey());
        assert !config.isDisableTokenCache();
        assertThat("networkConfig.requestTimeOut is not equal to 30",
                config.getRequestTimeOut() == 30);
        assertThat("networkConfig.connectTimeout is not equal to 10",
                config.getConnectTimeout() == 10);
        assertThat("networkConfig.readTimeout is not equal to 10",
                config.getReadTimeout() == 10);
        assertThat("networkConfig.writeTimeout is not equal to 10",
                config.getWriteTimeout() == 10);
        assertThat("networkConfig.connectionPool is null",
                config.getConnectionPool() != null);
    }


}
