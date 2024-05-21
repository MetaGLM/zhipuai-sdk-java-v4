package com.zhipu.oapi.service.v4.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Iterator;


@Getter
public class Choice extends ObjectNode {

    @JsonProperty("finish_reason")
    private String finishReason;
    @JsonProperty("index")
    private Long index;

    @JsonProperty("message")
    private ChatMessage message;

    @JsonProperty("delta")
    private Delta delta;

    public Choice() {
        super(JsonNodeFactory.instance);
    }

    public Choice(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if(objectNode.get("finish_reason") != null) {
            this.setFinishReason(objectNode.get("finish_reason").asText());
        } else {
            this.setFinishReason(null);
        }
        if(objectNode.get("index") != null) {
            this.setIndex(objectNode.get("index").asLong());
        } else {
            this.setIndex(null);
        }
        if(objectNode.get("message") != null) {
            this.setMessage(objectMapper.convertValue(objectNode.get("message"), ChatMessage.class));
        } else {
            this.setMessage(null);
        }
        if(objectNode.get("delta") != null) {
            this.setDelta(objectMapper.convertValue(objectNode.get("delta"), Delta.class));
        } else {
            this.setDelta(null);
        }


        Iterator<String> fieldNames = objectNode.fieldNames();

        while(fieldNames.hasNext()) {
            String fieldName = fieldNames.next();

            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
        this.put("finish_reason", finishReason);
    }

    public void setIndex(Long index) {
        this.index = index;
        this.put("index", index);
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
        this.putPOJO("message", message);
    }

    public void setDelta(Delta delta) {
        this.delta = delta;
        this.putPOJO("delta", delta);
    }
}