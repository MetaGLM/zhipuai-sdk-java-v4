package com.zhipu.oapi.service.v4.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.DeltaDeserializer;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import lombok.*;

import java.util.Iterator;
import java.util.List;


@Getter
@JsonDeserialize(using = DeltaDeserializer.class)
public class Delta extends ObjectNode {

    private String role;

    private String content;

    private String reasoning_content;


    @JsonProperty("tool_calls")
    private List<ToolCalls> tool_calls;


    public Delta() {
        super(JsonNodeFactory.instance);
    }

    public Delta(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("role") != null) {
            this.setRole(objectNode.get("role").asText());
        } else {
            this.setRole(null);
        }
        if (objectNode.get("content") != null) {
            this.setContent(objectNode.get("content").asText());
        } else {
            this.setContent(null);
        }
        if (objectNode.get("tool_calls") != null) {
            this.setTool_calls(objectMapper.convertValue(objectNode.get("tool_calls"), new TypeReference<List<ToolCalls>>() {
            }));
        } else {
            this.setTool_calls(null);
        }
        if (objectNode.get("reasoning_content") != null) {
            this.setReasoning_content(objectNode.get("reasoning_content").asText());
        } else {
            this.setReasoning_content(null);
        }

        Iterator<String> fieldNames = objectNode.fieldNames();

        while(fieldNames.hasNext()) {
            String fieldName = fieldNames.next();

            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }

    public void setRole(String role) {
        this.role = role;
        this.put("role", role);
    }

    public void setContent(String content) {
        this.content = content;
        this.put("content", content);
    }

    public void setTool_calls(List<ToolCalls> tool_calls) {
        this.tool_calls = tool_calls;

        ArrayNode toolCalls = this.putArray("tool_calls");
        if (tool_calls == null) {
            toolCalls.removeAll();
        }else {
            for (ToolCalls toolCall : tool_calls) {
                toolCalls.add(toolCall);
            }
        }
    }

    public void setReasoning_content(String reasoning_content) {
        this.reasoning_content = reasoning_content;
        this.put("reasoning_content", reasoning_content);
    }
}
