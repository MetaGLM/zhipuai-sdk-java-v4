package com.zhipu.oapi.service.v4.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.ChatMessageDeserializer;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import lombok.*;

import java.util.Iterator;
import java.util.List;

@Getter
@JsonDeserialize(using = ChatMessageDeserializer.class)
public class ChatMessage extends ObjectNode {


    private String role;
    private Object content;
    private Audio audio;
    private String name;

    @JsonProperty("tool_calls")
    private List<ToolCalls> tool_calls;

    private String tool_call_id;

    public ChatMessage() {
        super(JsonNodeFactory.instance);
    }

    public ChatMessage(String role, Object content) {

        super(JsonNodeFactory.instance);
        this.setRole(role);
        this.setContent(content);
    }

    public ChatMessage(ObjectNode objectNode) {
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
        if (objectNode.get("name") != null) {
            this.setName(objectNode.get("name").asText());
        } else {
            this.setName(null);
        }
        if (objectNode.get("tool_calls") != null) {
            this.setTool_calls(objectMapper.convertValue(objectNode.get("tool_calls"), new com.fasterxml.jackson.core.type.TypeReference<List<ToolCalls>>() {
            }));
        } else {
            this.setTool_calls(null);
        }
        if (objectNode.get("tool_call_id") != null) {
            this.setTool_call_id(objectNode.get("tool_call_id").asText());
        } else {
            this.setTool_call_id(null);
        }

        Iterator<String> fieldNames = objectNode.fieldNames();

        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();

            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }

    }

    public void setRole(String role) {
        this.role = role;
        this.put("role", role);
    }

    public void setContent(Object content) {
        this.content = content;
        this.putPOJO("content", content);
    }

    public void setName(String name) {
        this.name = name;
        this.put("name", name);
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

    public void setTool_call_id(String tool_call_id) {
        this.tool_call_id = tool_call_id;
        this.put("tool_call_id", tool_call_id);
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
        this.putPOJO("audio", audio);
    }

    @Data
    public static class Audio {

        private String id;

        private String data;

        private Long expires_at;

    }
}
