package com.zhipu.oapi.service.v4.audio;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.audio.AudioSpeechChoiceDeserializer;

import lombok.Getter;

import java.util.Iterator;

@Getter
@JsonDeserialize(using = AudioSpeechChoiceDeserializer.class)
public class AudioSpeechChoice extends ObjectNode {

    /** 索引 */
    @JsonProperty("index")
    private int index;

    /** 完成原因 */
    @JsonProperty("finish_reason")
    private String finishReason;

    /** delta */
    @JsonProperty("delta")
    private AudioSpeechDelta delta;

    public AudioSpeechChoice() {
        super(JsonNodeFactory.instance);
    }

    public AudioSpeechChoice(ObjectNode objectNode) {
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

        if (objectNode.get("delta") != null) {
            this.setDelta(
                    objectMapper.convertValue(objectNode.get("delta"), AudioSpeechDelta.class));
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

    // Setters

    public void setIndex(int index) {
        this.index = index;
        this.put("index", index);
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
        this.put("finish_reason", finishReason);
    }

    public void setDelta(AudioSpeechDelta delta) {
        this.delta = delta;
        this.set("delta", delta);
    }
}
