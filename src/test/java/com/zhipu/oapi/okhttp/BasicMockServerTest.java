package com.zhipu.oapi.okhttp;

import com.zhipu.oapi.core.ConfigV4;
import com.zhipu.oapi.core.cache.ICache;
import com.zhipu.oapi.core.cache.LocalCache;
import com.zhipu.oapi.core.token.TokenManagerV4;
import com.zhipu.oapi.utils.OkHttps;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;


import org.mockserver.configuration.Configuration;
import org.mockserver.logging.MockServerLogger;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.socket.tls.KeyStoreFactory;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.security.KeyStore;
import java.util.Arrays;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;

@Testcontainers
public class BasicMockServerTest {
    @Container
    public MockServerContainer mockServer = new MockServerContainer(MOCKSERVER_IMAGE);
    static ConfigV4 configV4 = null;
    static OkHttpClient client = null;
    static {
        configV4 = new ConfigV4();
        configV4.setApiSecretKey("a.b");
        client = OkHttps.create(configV4);

    }

    @Test
    public void testRequestAuthorization() throws Exception {
        try (MockServerClient mockServerClient = new MockServerClient(mockServer.getHost(), mockServer.getServerPort())) {
            mockServerClient
                    .when(
                            request().withPath("/person")
                                    .withQueryStringParameter("name", "peter")
                    )


                    .respond(request -> {
                        if (request.getHeader("Authorization") != null) {
                            return response()
                                    .withStatusCode(200)
                                    .withBody("{\"message\": \"Authorized\"}");
                        } else {
                            return response()
                                    .withStatusCode(401)
                                    .withBody("{\"message\": \"Unauthorized\"}");
                        }
                    });

            Request request = new Request.Builder()
                    .url(HttpUrl.get(mockServer.getEndpoint() + "/person?name=peter"))
                    .build();

            try (okhttp3.Response response = client.newCall(request).execute()) {
                assertThat("Unauthorized",response.body().string().equals("{\"message\": \"Authorized\"}"));
            }
        }
    }
    private static final DockerImageName MOCKSERVER_IMAGE = DockerImageName
            .parse("mockserver/mockserver")
            .withTag("mockserver-5.15.0");


}
