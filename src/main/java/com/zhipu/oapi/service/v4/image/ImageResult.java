package com.zhipu.oapi.service.v4.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.image.ImageResultDeserializer;
import com.zhipu.oapi.service.v4.deserialize.videos.VideoObjectDeserializer;
import com.zhipu.oapi.service.v4.model.ChatError;
import com.zhipu.oapi.service.v4.videos.VideoResult;
import lombok.Data;
import lombok.Getter;

import java.util.Iterator;
import java.util.List;

/**
 * An object with a list of image results.
 */

@Getter
@JsonDeserialize(using = ImageResultDeserializer.class)
public class ImageResult extends ObjectNode {

    /**
     * The creation time in epoch seconds.
     */
    @JsonProperty("created")
    Long created;

    /**
     * List of image results.
     */
    List<Image> data;


    /**
     * 用户在客户端请求时提交的任务编号或者平台生成的任务编号
     */
    @JsonProperty("request_id")
    private String requestId;


    public ImageResult() {
        super(JsonNodeFactory.instance);
    }

    public ImageResult(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("created") != null) {
            this.setCreated(objectNode.get("created").asLong());
        } else {
            this.setCreated(null);
        }
        if (objectNode.get("data") != null) {
            List<Image> data = objectMapper.convertValue(objectNode.get("data"), new TypeReference<List<Image>>() {});

            this.setData(data);
        } else {
            this.setData(null);
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

    public void setCreated(Long created) {
        this.created = created;
        this.put("created", created);
    }

    public void setData(List<Image> data) {
        this.data = data;
        ArrayNode jsonNodes = this.putArray("data");
        if (data == null) {
            jsonNodes.removeAll();
        }
        else {

            for (Image image : data) {
                jsonNodes.add(image);
            }
        }
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
        this.put("request_id", requestId);
    }
}
