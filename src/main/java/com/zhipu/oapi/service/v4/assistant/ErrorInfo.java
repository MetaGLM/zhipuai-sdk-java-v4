package com.zhipu.oapi.service.v4.assistant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.assistant.ErrorInfoDeserializer;

import java.util.Iterator;

/**
 * This class represents error information.
 */
@JsonDeserialize(using = ErrorInfoDeserializer.class)
public class ErrorInfo extends ObjectNode {

    /**
     * 错误码
     */
    @JsonProperty("code")
    private String code;

    /**
     * 错误信息
     */
    @JsonProperty("message")
    private String message;

    public ErrorInfo() {
        super(JsonNodeFactory.instance);
    }

    public ErrorInfo(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);

        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();

        if (objectNode.get("code") != null) {
            this.setCode(objectNode.get("code").asText());
        } else {
            this.setCode(null);
        }
        if (objectNode.get("message") != null) {
            this.setMessage(objectNode.get("message").asText());
        } else {
            this.setMessage(null);
        }
        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }
    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
        this.put("code", code);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.put("message", message);
    }
}
