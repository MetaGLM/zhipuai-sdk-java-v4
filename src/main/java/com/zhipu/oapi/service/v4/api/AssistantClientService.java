package com.zhipu.oapi.service.v4.api;

import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.service.v4.api.assistant.AssistantApi;
import com.zhipu.oapi.service.v4.assistant.AssistantApiResponse;
import com.zhipu.oapi.service.v4.assistant.AssistantCompletion;
import com.zhipu.oapi.service.v4.assistant.AssistantParameters;
import com.zhipu.oapi.service.v4.assistant.conversation.ConversationParameters;
import com.zhipu.oapi.service.v4.assistant.conversation.ConversationUsageListResponse;
import com.zhipu.oapi.service.v4.assistant.conversation.ConversationUsageListStatus;
import com.zhipu.oapi.service.v4.assistant.query_support.AssistantSupportResponse;
import com.zhipu.oapi.service.v4.assistant.query_support.AssistantSupportStatus;
import com.zhipu.oapi.service.v4.assistant.query_support.QuerySupportParams;
import com.zhipu.oapi.utils.FlowableRequestSupplier;
import com.zhipu.oapi.utils.RequestSupplier;
import io.reactivex.Single;
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


    // 修改 assistantCompletionStream 方法，使其返回 AssistantGenerationChain
    public AssistantGenerationChain assistantCompletionStream(AssistantParameters request) {

        Call<ResponseBody> responseBodyCall = assistantApi.assistantCompletionStream(request.getOptions());
        return new AssistantGenerationChain(request, responseBodyCall);
    }


    // 修改 querySupport 方法，使其返回 AssistantSupportChain
    public AssistantSupportChain querySupport(QuerySupportParams request) {
        Single<AssistantSupportStatus> assistantSupportStatusSingle = assistantApi.querySupport(request.getOptions());
        return new AssistantSupportChain(request, assistantSupportStatusSingle);
    }

    // 修改 queryConversationUsage 方法，使其返回 ConversationChain
    public ConversationChain queryConversationUsage(ConversationParameters request) {
        Single<ConversationUsageListStatus> statusSingle = assistantApi.queryConversationUsage(request.getOptions());
        return new ConversationChain(request, statusSingle);
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
    public static class AssistantSupportChain extends GenerationChain<AssistantSupportStatus, AssistantSupportResponse> {
        private final QuerySupportParams params;
        private final Single<AssistantSupportStatus> objectSingle;

        public AssistantSupportChain(QuerySupportParams params,
                                           Single<AssistantSupportStatus> objectSingle
                                        ) {
            this.params = params;
            this.objectSingle = objectSingle;
        }

        public  AssistantSupportResponse apply(final ClientV4 clientV4) {
            RequestSupplier<QuerySupportParams, AssistantSupportStatus> supplier = (params) -> {
                return objectSingle;
            };
            return clientV4.executeRequest(params, supplier, AssistantSupportResponse.class);

        }

    }

    public static class ConversationChain extends GenerationChain<ConversationUsageListStatus, ConversationUsageListResponse> {
        private final ConversationParameters params;
        private final Single<ConversationUsageListStatus> objectSingle;

        public ConversationChain(ConversationParameters params,
                                 Single<ConversationUsageListStatus> objectSingle
                                        ) {
            this.params = params;
            this.objectSingle = objectSingle;
        }

        public  ConversationUsageListResponse apply(final ClientV4 clientV4) {
            RequestSupplier<ConversationParameters, ConversationUsageListStatus> supplier = (params) -> {
                return objectSingle;
            };
            return clientV4.executeRequest(params, supplier, ConversationUsageListResponse.class);

        }

    }
}
