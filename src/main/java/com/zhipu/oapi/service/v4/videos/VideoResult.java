package com.zhipu.oapi.service.v4.videos;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.videos.VideoObjectDeserializer;
import com.zhipu.oapi.service.v4.deserialize.videos.VideoResultDeserializer;
import lombok.Getter;

/**
 * This class represents the result of a video creation process.
 */
@Getter
@JsonDeserialize(using = VideoResultDeserializer.class)
public class VideoResult extends ObjectNode {

    /**
     * 视频url
     */
    @JsonProperty("url")
    private String url;

    public VideoResult() {
        super(JsonNodeFactory.instance);
    }

    public VideoResult(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("url") != null) {
            this.setUrl(objectNode.get("url").asText());
        } else {
            this.setUrl(null);
        }
    }

    // Getters and Setters

    public void setUrl(String url) {
        this.url = url;
        this.put("url", url);
    }
}
