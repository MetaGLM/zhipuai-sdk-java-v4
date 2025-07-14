package com.zhipu.oapi.service.v4.audio;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.audio.AudioSpeechDeltaDeserializer;

import lombok.Getter;

import java.util.Iterator;

@Getter
@JsonDeserialize(using = AudioSpeechDeltaDeserializer.class)
public class AudioSpeechDelta extends ObjectNode {

    /** 角色 */
    @JsonProperty("role")
    private String role;

    /** 工具调用列表 */
    @JsonProperty("content")
    private String content;

    public AudioSpeechDelta() {
        super(JsonNodeFactory.instance);
    }

    public AudioSpeechDelta(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.has("role")) {
            this.setRole(objectNode.get("role").asText());
        } else {
            this.setRole(null);
        }
        if (objectNode.has("content")) {
            this.setContent(objectNode.get("content").asText());
        } else {
            this.setContent(null);
        }

        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }

    // Getters and Setters

    public void setRole(String role) {
        this.role = role;
        this.put("role", role);
    }

    public void setContent(String content) {
        this.content = content;
        this.put("content", content);
    }
}
