package com.zhipu.oapi.service.v4.tools;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;

import java.util.Iterator;

public class WebSearchChoice extends ObjectNode {

    /**
     * 索引
     */
    @JsonProperty("index")
    private int index;

    /**
     * 完成原因
     */
    @JsonProperty("finish_reason")
    private String finishReason;

    /**
     * 消息
     */
    @JsonProperty("message")
    private WebSearchMessage message;

    /**
     * delta
     */
    @JsonProperty("delta")
    private ChoiceDelta delta;

    public WebSearchChoice() {
        super(JsonNodeFactory.instance);
    }

    public WebSearchChoice(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();

        if (objectNode.get("index") != null) {
            this.setIndex(objectNode.get("index").asInt());
        } else {
            this.setIndex(0);
        }
        if (objectNode.get("finish_reason") != null) {
            this.setFinishReason(objectNode.get("finish_reason").asText());
        } else {
            this.setFinishReason(null);
        }

        if (objectNode.get("message") != null) {
            this.setMessage(objectMapper.convertValue(objectNode.get("message"), WebSearchMessage.class));
        } else {
            this.setMessage(null);
        }

        if (objectNode.get("delta") != null) {
            this.setDelta(objectMapper.convertValue(objectNode.get("delta"), ChoiceDelta.class));
        } else {
            this.setDelta(null);
        }
        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }

    // Getters and Setters

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        this.put("index", index);
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
        this.put("finish_reason", finishReason);
    }

    public WebSearchMessage getMessage() {
        return message;
    }

    public void setMessage(WebSearchMessage message) {
        this.message = message;
        this.set("message", message);
    }
    public ChoiceDelta getDelta() {
        return delta;
    }

    public void setDelta(ChoiceDelta delta) {
        this.delta = delta;
        this.set("delta", delta);
    }
}
