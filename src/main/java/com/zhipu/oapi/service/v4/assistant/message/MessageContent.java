package com.zhipu.oapi.service.v4.assistant.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zhipu.oapi.service.v4.deserialize.JsonTypeMapping;
import com.zhipu.oapi.service.v4.deserialize.assistant.message.MessageContentDeserializer;

@JsonTypeMapping({ToolsDeltaBlock.class, TextContentBlock.class})
@JsonDeserialize(using = MessageContentDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class MessageContent {

}
