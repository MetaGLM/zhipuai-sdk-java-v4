package com.zhipu.oapi.service.v4.embedding;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.embedding.EmbeddingDeserializer;
import com.zhipu.oapi.service.v4.deserialize.image.ImageDeserializer;
import lombok.Data;
import lombok.Getter;

import java.util.Iterator;
import java.util.List;

/**
 * Represents an embedding returned by the embedding api
 */

@JsonDeserialize(using = EmbeddingDeserializer.class)
public class Embedding extends ObjectNode {

    /**
     * The type of object returned, should be "embedding"
     */
    @Getter
    String object;

    /**
     * The embedding vector
     */
    @Getter
    List<Double> embedding;

    /**
     * The position of this embedding in the list
     */
    @Getter
    Integer index;

    public Embedding() {
        super(JsonNodeFactory.instance);
    }

    public Embedding(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("object") != null) {
            this.setObject(objectNode.get("object").asText());
        } else {
            this.setObject(null);
        }
        if (objectNode.get("index") != null) {
            this.setIndex(objectNode.get("index").asInt());
        } else {
            this.setIndex(null);
        }

        if (objectNode.get("embedding") != null) {
            List<Double> embedding = objectMapper.convertValue(objectNode.get("embedding"), new TypeReference<List<Double>>() {
            });
            this.setEmbedding(embedding);
        } else {
            this.setEmbedding(null);
        }

        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }

    public void setIndex(Integer index) {
        this.index = index;
        this.put("index", index);
    }

    public void setObject(String object) {
        this.object = object;
        this.put("object", object);
    }

    public void setEmbedding(List<Double> embedding) throws IllegalArgumentException {
        if (embedding == null || embedding.isEmpty()) {
            throw new IllegalArgumentException("Embedding cannot be null or empty");
        }
        this.embedding = embedding;
        this.putPOJO("embedding", embedding);
    }


}
