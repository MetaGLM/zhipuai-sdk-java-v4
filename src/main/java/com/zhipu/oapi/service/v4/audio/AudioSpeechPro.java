package com.zhipu.oapi.service.v4.audio;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.audio.AudioSpeechProDeserializer;
import lombok.Getter;

import java.util.Iterator;
import java.util.List;

@JsonDeserialize(using = AudioSpeechProDeserializer.class)
@Getter
public final class AudioSpeechPro extends ObjectNode {

    /** 创建时间 */
    @JsonProperty("created")
    private Integer created;

    /** 选择项 */
    @JsonProperty("choices")
    private List<AudioSpeechChoice> choices;

    /** 请求ID */
    @JsonProperty("request_id")
    private String requestId;


    public AudioSpeechPro() {
        super(JsonNodeFactory.instance);
    }

    public AudioSpeechPro(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("created") != null) {
            this.setCreated(objectNode.get("created").asInt());
        } else {
            this.setCreated(null);
        }

        if (objectNode.get("choices") != null) {
            this.setChoices(
                    objectMapper.convertValue(
                            objectNode.get("choices"),
                            new TypeReference<List<AudioSpeechChoice>>() {}));
        } else {
            this.setChoices(null);
        }
        if (objectNode.get("request_id") != null) {
            this.setRequestId(objectNode.get("request_id").asText());
        } else {
            this.setRequestId(null);
        }

        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }

    // Setters
    public void setCreated(Integer created) {
        this.created = created;
        this.put("created", created);
    }


    public void setChoices(List<AudioSpeechChoice> choices) {
        this.choices = choices;
        this.putPOJO("choices", choices);
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
        this.put("request_id", requestId);
    }
}
