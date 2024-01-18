package com.zhipu.oapi.service.v4.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public abstract class ChatCompletionRequestMixIn {

    @JsonSerialize(using = ChatCompletionRequestSerializerAndDeserializer.Serializer.class)
    @JsonDeserialize(using = ChatCompletionRequestSerializerAndDeserializer.Deserializer.class)
    abstract ChatCompletionRequest.ChatCompletionRequestFunctionCall getFunctionCall();

}
