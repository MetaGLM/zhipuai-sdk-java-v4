package com.zhipu.oapi.service.v4.tools;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.tools.WebSearchChoiceDeserializer;
import com.zhipu.oapi.service.v4.deserialize.tools.WebSearchMessageDeserializer;

import java.util.Iterator;
import java.util.List;

@JsonDeserialize(using = WebSearchMessageDeserializer.class)
public class WebSearchMessage extends ObjectNode {

    /**
     * 角色
     */
    @JsonProperty("role")
    private String role;

    /**
     * 工具调用列表
     */
    @JsonProperty("tool_calls")
    private List<WebSearchMessageToolCall> toolCalls;

    public WebSearchMessage() {
        super(JsonNodeFactory.instance);
    }

    public WebSearchMessage(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("role") != null) {
            this.setRole(objectNode.get("role").asText());
        } else {
            this.setRole(null);
        }
        if (objectNode.get("tool_calls") != null) {
            this.setToolCalls(objectMapper.convertValue(objectNode.get("tool_calls"), new TypeReference<List<WebSearchMessageToolCall>>() {}));
        } else {
            this.setToolCalls(null);
        }



        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }

    // Getters and Setters

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
        this.put("role", role);
    }

    public List<WebSearchMessageToolCall> getToolCalls() {
        return toolCalls;
    }

    public void setToolCalls(List<WebSearchMessageToolCall> toolCalls) {
        this.toolCalls = toolCalls;
        this.putPOJO("tool_calls", toolCalls);
    }
}
