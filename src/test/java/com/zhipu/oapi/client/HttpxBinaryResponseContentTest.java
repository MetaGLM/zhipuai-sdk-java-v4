package com.zhipu.oapi.client;

import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.core.response.HttpxBinaryResponseContent;

import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;

@Testcontainers
class HttpxBinaryResponseContentTest {
    @Container
    public MockServerContainer mockServer = new MockServerContainer(MOCKSERVER_IMAGE);

    private static final DockerImageName MOCKSERVER_IMAGE = DockerImageName
            .parse("mockserver/mockserver")
            .withTag("mockserver-5.15.0");
    private static final String API_SECRET_KEY = "a.b";

    @Test
    void testGetText() throws IOException {
        try (MockServerClient mockServerClient = new MockServerClient(mockServer.getHost(), mockServer.getServerPort())) {
            mockServerClient
                    .when(
                            request().withPath("/stage-api/paas/v4/files/1/content")
                    )


                    .respond(request -> {
                        return response()
                                .withStatusCode(401)
                                .withBody("file info");
                    });

            ClientV4 client = new ClientV4.Builder(mockServer.getEndpoint() +"/stage-api/paas/v4/",API_SECRET_KEY)
                    .enableTokenCache()
                    .networkConfig(30, 10, 10, 10, TimeUnit.SECONDS)
                    .connectionPool(new okhttp3.ConnectionPool(8, 1, TimeUnit.SECONDS))
                    .build();


            try (HttpxBinaryResponseContent httpxBinaryResponseContent = client.fileContent("1");) {
                assertThat("file error",httpxBinaryResponseContent.getText().equals("file info"));
            }
        }

    }

}
