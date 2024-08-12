package com.zhipu.oapi.service.v4.api;

import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.service.v4.api.assistant.AssistantApi;
import com.zhipu.oapi.service.v4.assistant.AssistantApiResponse;
import com.zhipu.oapi.service.v4.assistant.AssistantCompletion;
import com.zhipu.oapi.service.v4.assistant.AssistantParameters;
import com.zhipu.oapi.utils.FlowableRequestSupplier;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;

import java.util.Map;

public class AssistantClientService extends ClientBaseService {
    private final AssistantApi assistantApi;
    /**
     * Creates a new ClientBaseService that wraps OkHttpClient
     *
     * @param client  retrofit instance
     * @param baseUrl base url of the api
     */
    public AssistantClientService(OkHttpClient client, String baseUrl) {
        super(client, baseUrl);
        this.assistantApi = super.retrofit.create(AssistantApi.class);
    }



    public AssistantGenerationChain assistantCompletionStream(AssistantParameters request) {

        Call<ResponseBody> responseBodyCall = assistantApi.assistantCompletionStream(request.getOptions());
        return new AssistantGenerationChain(request, responseBodyCall);
    }


    public static class AssistantGenerationChain extends GenerationChain<AssistantCompletion, AssistantApiResponse> {
        private final AssistantParameters params;
        private final Call<ResponseBody> responseBodyCall;

        public AssistantGenerationChain(AssistantParameters params,
                                        Call<ResponseBody> responseBodyCall
                                        ) {
            this.params = params;
            this.responseBodyCall = responseBodyCall;
        }

        public  AssistantApiResponse apply(final ClientV4 clientV4) {
            FlowableRequestSupplier<Map<String,Object>, retrofit2.Call<ResponseBody>>  supplier = (params) -> {
                return responseBodyCall;
            };
            return clientV4.streamRequest(
                    params,
                    supplier,
                    AssistantApiResponse.class,
                    AssistantCompletion.class
            );
        }

    }
}
