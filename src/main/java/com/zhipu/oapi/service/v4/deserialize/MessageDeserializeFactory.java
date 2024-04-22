package com.zhipu.oapi.service.v4.deserialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zhipu.oapi.service.v4.model.*;

public class MessageDeserializeFactory {

    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        SimpleModule module = new SimpleModule();

        module.addDeserializer(Choice.class, new ChoiceDeserializer());
        module.addDeserializer(ChatMessage.class, new ChatMessageDeserializer());
        module.addDeserializer(Delta.class, new DeltaDeserializer());
        module.addDeserializer(ToolCalls.class, new ToolCallsDeserializer());
        module.addDeserializer(ChatFunctionCall.class, new ChatFunctionCallDeserializer());
        mapper.registerModule(module);

        return mapper;
    }

}
