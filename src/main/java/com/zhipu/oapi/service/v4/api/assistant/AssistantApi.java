package com.zhipu.oapi.service.v4.api.assistant;

import com.zhipu.oapi.service.v4.assistant.AssistantCompletion;
import com.zhipu.oapi.service.v4.assistant.conversation.ConversationUsageListStatus;
import com.zhipu.oapi.service.v4.assistant.query_support.AssistantSupportStatus;
import com.zhipu.oapi.service.v4.assistant.conversation.ConversationParameters;
import com.zhipu.oapi.service.v4.assistant.query_support.QuerySupportParams;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Streaming;

import java.util.Map;

public interface AssistantApi {


    @Streaming
    @POST("assistant")
    Call<ResponseBody> assistantCompletionStream(@Body Map<String,Object> request);

    @POST("assistant")
    Single<AssistantCompletion> assistantCompletion(@Body Map<String,Object> request);

    @POST("assistant/list")
    Single<AssistantSupportStatus> querySupport(@Body QuerySupportParams request);


    @POST("assistant/conversation/list")
    Single<ConversationUsageListStatus> queryConversationUsage(@Body ConversationParameters request);


}
