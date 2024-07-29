package com.zhipu.oapi.service.v4.api;

import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.core.model.ClientRequest;
import com.zhipu.oapi.service.v4.api.knowledge.KnowledgeApi;
import com.zhipu.oapi.service.v4.api.videos.VideosApi;
import com.zhipu.oapi.service.v4.knowledge.*;
import com.zhipu.oapi.service.v4.videos.VideoCreateParams;
import com.zhipu.oapi.service.v4.videos.VideoObject;
import com.zhipu.oapi.service.v4.videos.VideosResponse;
import com.zhipu.oapi.utils.RequestSupplier;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Response;

import java.util.Map;

public class KnowledgeClientApiService  extends ClientBaseService {

    private final KnowledgeApi knowledgeApi;
    /**
     * Creates a new KnowledgeClientApiService that wraps OkHttpClient
     *
     * @param client  retrofit instance
     * @param baseUrl base url of the api
     */
    public KnowledgeClientApiService(OkHttpClient client, String baseUrl) {
        super(client, baseUrl);
        this.knowledgeApi = super.retrofit.create(KnowledgeApi.class);
    }


    // 修改 knowledgeCreate 方法，使其返回 KnowledgeGenerationChain
    public KnowledgeGenerationChain knowledgeCreate(KnowledgeBaseParams params) {
        Single<KnowledgeInfo> single = knowledgeApi.knowledgeCreate(params);
        return new KnowledgeGenerationChain(params, single);
    }

    // 修改 knowledgeModify 方法，使其返回 KnowledgeEditGenerationChain
    public KnowledgeEditGenerationChain knowledgeModify(KnowledgeBaseParams params) {
        Single<Response<Void>> single = knowledgeApi.knowledgeModify(params.getKnowledgeId(),params);
        return new KnowledgeEditGenerationChain(params, single);
    }

    // 修改 knowledgeQuery 方法，使其返回 KnowledgePageGenerationChain
    public KnowledgePageGenerationChain knowledgeQuery(QueryKnowledgeRequest params) {
        Single<KnowledgePage> knowledgePageSingle = knowledgeApi.knowledgeQuery(params.getPage(), params.getSize());
        return new KnowledgePageGenerationChain(params, knowledgePageSingle);
    }
    // 修改 knowledgeDelete 方法，使其返回 KnowledgeEditGenerationChain
    public KnowledgeEditGenerationChain knowledgeDelete(KnowledgeBaseParams params) {
        Single<Response<Void>> knowledgePageSingle = knowledgeApi.knowledgeDelete(params.getKnowledgeId());
        return new KnowledgeEditGenerationChain(params, knowledgePageSingle);
    }

    // 修改 knowledgeUsed 方法，使其返回 KnowledgeUsedGenerationChain
    public KnowledgeUsedGenerationChain knowledgeUsed() {
        Single<KnowledgeUsed> knowledgeUsedSingle = knowledgeApi.knowledgeUsed();
        return new KnowledgeUsedGenerationChain(knowledgeUsedSingle);
    }


    public static class KnowledgeGenerationChain extends GenerationChain<KnowledgeInfo, KnowledgeResponse> {
        private final KnowledgeBaseParams params;
        private final Single<KnowledgeInfo> objectSingle;

        public KnowledgeGenerationChain(KnowledgeBaseParams params, Single<KnowledgeInfo> objectSingle) {
            this.params = params;
            this.objectSingle = objectSingle;
        }

        public  KnowledgeResponse apply(final ClientV4 clientV4) {
            RequestSupplier<KnowledgeBaseParams, KnowledgeInfo> supplier = (params) -> {
                return objectSingle;
            };
            return clientV4.executeRequest(params, supplier, KnowledgeResponse.class);
        }

    }

    public static class KnowledgeEditGenerationChain extends GenerationChain<Response<Void>, KnowledgeEditResponse> {
        private final KnowledgeBaseParams params;
        private final Single<Response<Void>> objectSingle;

        public KnowledgeEditGenerationChain(KnowledgeBaseParams params, Single<Response<Void>> objectSingle) {
            this.params = params;
            this.objectSingle = objectSingle;
        }

        public  KnowledgeEditResponse apply(final ClientV4 clientV4) {
            RequestSupplier<KnowledgeBaseParams, Response<Void>> supplier = (params) -> {
                return objectSingle;
            };
            return clientV4.executeRequest(params, supplier, KnowledgeEditResponse.class);
        }

    }



    public static class KnowledgePageGenerationChain extends GenerationChain<KnowledgePage, QueryKnowledgeApiResponse> {
        private final QueryKnowledgeRequest params;
        private final Single<KnowledgePage> objectSingle;

        public KnowledgePageGenerationChain(QueryKnowledgeRequest params, Single<KnowledgePage> objectSingle) {
            this.params = params;
            this.objectSingle = objectSingle;
        }

        public  QueryKnowledgeApiResponse apply(final ClientV4 clientV4) {
            RequestSupplier<Map<String, Object>, KnowledgePage> supplier = (params) -> {
                return objectSingle;
            };
            return clientV4.executeRequest(params, supplier, QueryKnowledgeApiResponse.class);
        }

    }


    public static class KnowledgeUsedGenerationChain extends GenerationChain<KnowledgeUsed, KnowledgeUsedResponse> {
        private final Single<KnowledgeUsed> objectSingle;

        public KnowledgeUsedGenerationChain(Single<KnowledgeUsed> objectSingle) {
            this.objectSingle = objectSingle;
        }

        public  KnowledgeUsedResponse apply(final ClientV4 clientV4) {
            RequestSupplier<String, KnowledgeUsed> supplier = (params) -> {
                return objectSingle;
            };
            ClientRequest<String> params = (ClientRequest) () -> "null";
            return clientV4.executeRequest(params, supplier, KnowledgeUsedResponse.class);
        }

    }

}
