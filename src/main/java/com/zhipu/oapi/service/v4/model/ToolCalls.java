package com.zhipu.oapi.service.v4.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import lombok.*;

import java.util.Iterator;
import java.util.Map;


@Getter
public class ToolCalls extends ObjectNode {


    @JsonProperty("function")
    private ChatFunctionCall function;

    /**
     * 命中函数的唯一标识符
     */
    @JsonProperty("id")
    private String id;


    /**
     * 模型调用工具的类型,目前仅支持functon
     */
    @JsonProperty("type")
    private String type;

    public ToolCalls() {
        super(JsonNodeFactory.instance);
    }

    public ToolCalls(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("function") != null) {
            this.setFunction(objectMapper.convertValue(objectNode.get("function"), ChatFunctionCall.class));
        } else {
            this.setFunction(null);
        }
        if (objectNode.get("id") != null) {
            this.setId(objectNode.get("id").asText());
        } else {
            this.setId(null);
        }
        if (objectNode.get("type") != null) {
            this.setType(objectNode.get("type").asText());
        } else {
            this.setType(null);
        }

        Iterator<String> fieldNames = objectNode.fieldNames();

        while(fieldNames.hasNext()) {
            String fieldName = fieldNames.next();

            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }

    }

    public void setFunction(ChatFunctionCall function) {
        this.function = function;
        this.putPOJO("function", function);
    }

    public void setId(String id) {
        this.id = id;
        this.put("id", id);
    }

    public void setType(String type) {
        this.type = type;
        this.put("type", type);
    }
}