package com.zhipu.oapi.service.v4.deserialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zhipu.oapi.service.v4.deserialize.tools.*;
import com.zhipu.oapi.service.v4.model.*;
import com.zhipu.oapi.service.v4.model.params.CodeGeexContext;
import com.zhipu.oapi.service.v4.model.params.CodeGeexExtra;
import com.zhipu.oapi.service.v4.model.params.CodeGeexTarget;
import com.zhipu.oapi.service.v4.tools.*;

public class MessageDeserializeFactory {

    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        SimpleModule module = new SimpleModule();

        module.addDeserializer(ModelData.class, new ModelDataDeserializer());
        module.addDeserializer(Choice.class, new ChoiceDeserializer());
        module.addDeserializer(ChatMessage.class, new ChatMessageDeserializer());
        module.addDeserializer(Delta.class, new DeltaDeserializer());
        module.addDeserializer(ToolCalls.class, new ToolCallsDeserializer());
        module.addDeserializer(ChatFunctionCall.class, new ChatFunctionCallDeserializer());
        module.addDeserializer(CodeGeexContext.class, new CodeGeexContextDeserializer());
        module.addDeserializer(CodeGeexExtra.class, new CodeGeexExtraDeserializer());
        module.addDeserializer(CodeGeexTarget.class, new CodeGeexTargetDeserializer());
        module.addDeserializer(ChoiceDelta.class, new ChoiceDeltaDeserializer());
        module.addDeserializer(ChoiceDeltaToolCall.class, new ChoiceDeltaToolCallDeserializer());
        module.addDeserializer(SearchChatMessage.class, new SearchChatMessageDeserializer());
        module.addDeserializer(SearchIntent.class, new SearchIntentDeserializer());
        module.addDeserializer(SearchRecommend.class, new SearchRecommendDeserializer());
        module.addDeserializer(SearchResult.class, new SearchResultDeserializer());
        module.addDeserializer(WebSearchChoice.class, new WebSearchChoiceDeserializer());
        module.addDeserializer(WebSearchMessage.class, new WebSearchMessageDeserializer());
        module.addDeserializer(WebSearchMessageToolCall.class, new WebSearchMessageToolCallDeserializer());
        module.addDeserializer(WebSearchPro.class, new WebSearchProDeserializer());
        mapper.registerModule(module);

        return mapper;
    }

}
