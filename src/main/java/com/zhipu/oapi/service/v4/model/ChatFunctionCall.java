package com.zhipu.oapi.service.v4.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Iterator;


@Getter
public class ChatFunctionCall extends ObjectNode {

    /**
     * 模型生成的应调用函数的名称
     */
    String name;

    /**
     * 模型生成的 JSON 格式的函数调用参数。请注意，模型生成的 JSON 并不总是有效的，可能会出现函数模式未定义的参数。在调用函数之前，请在代码中验证参数
     */
    JsonNode arguments;


    public ChatFunctionCall() {
        super(JsonNodeFactory.instance);
    }

    public ChatFunctionCall(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("name") != null) {
            this.setName(objectNode.get("name").asText());
        } else {
            this.setName(null);
        }
        if (objectNode.get("arguments") != null) {
            this.setArguments(objectNode.get("arguments"));
        } else {
            this.setArguments(null);
        }

        Iterator<String> fieldNames = objectNode.fieldNames();

        while(fieldNames.hasNext()) {
            String fieldName = fieldNames.next();

            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }

    }

    public void setName(String name) {
        this.name = name;
        this.put("name", name);
    }

    public void setArguments(JsonNode arguments) {
        this.arguments = arguments;
        this.set("arguments", arguments);
    }
}
