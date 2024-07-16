package com.zhipu.oapi.service.v4.tools;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;

import java.util.Iterator;
import java.util.List;

public class ChoiceDelta extends ObjectNode {

    /**
     * 角色
     */
    @JsonProperty("role")
    private String role;

    /**
     * 工具调用列表
     */
    @JsonProperty("tool_calls")
    private List<ChoiceDeltaToolCall> toolCalls;

    public ChoiceDelta() {
        super(JsonNodeFactory.instance);
    }

    public ChoiceDelta(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.has("role")) {
            this.setRole(objectNode.get("role").asText());
        } else {
            this.setRole(null);
        }
        if (objectNode.has("tool_calls")) {
            this.setToolCalls(objectMapper.convertValue(objectNode.get("tool_calls"), new TypeReference<List<ChoiceDeltaToolCall>>() {}));
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

    public List<ChoiceDeltaToolCall> getToolCalls() {
        return toolCalls;
    }

    public void setToolCalls(List<ChoiceDeltaToolCall> toolCalls) {
        this.toolCalls = toolCalls;
        this.putPOJO("tool_calls", toolCalls);
    }
}
