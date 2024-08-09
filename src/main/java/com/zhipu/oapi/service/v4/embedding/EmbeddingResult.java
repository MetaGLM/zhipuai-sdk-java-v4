package com.zhipu.oapi.service.v4.embedding;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.embedding.EmbeddingResultDeserializer;
import com.zhipu.oapi.service.v4.model.Usage;
import lombok.Data;
import lombok.Getter;

import java.util.Iterator;
import java.util.List;

/**
 * An object containing a response from the answer api
 */
@Getter
@JsonDeserialize(using = EmbeddingResultDeserializer.class)
public class EmbeddingResult extends ObjectNode {

    /**
     * The GLMmodel used for generating embeddings
     */
    String model;

    /**
     * The type of object returned, should be "list"
     */
    String object;

    /**
     * A list of the calculated embeddings
     */
    List<Embedding> data;

    /**
     * The API usage for this request
     */
    Usage usage;

    public EmbeddingResult() {
        super(JsonNodeFactory.instance);
    }

    public EmbeddingResult(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("model") != null) {
            this.setModel(objectNode.get("model").asText());
        } else {
            this.setModel(null);
        }
        if (objectNode.get("object") != null) {
            this.setObject(objectNode.get("object").asText());
        } else {
            this.setObject(null);
        }
        if (objectNode.get("data") != null) {
            List<Embedding> data1 = objectMapper.convertValue(objectNode.get("data"), new com.fasterxml.jackson.core.type.TypeReference<List<Embedding>>() {
            });
            this.setData(data1);
        } else {
            this.setData(null);
        }
        if (objectNode.get("usage") != null) {
            this.setUsage(objectMapper.convertValue(objectNode.get("usage"), Usage.class));
        } else {
            this.setUsage(null);
        }

        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }

    public void setModel(String model) {
        this.model = model;
        this.put("model", model);
    }
    public void setObject(String object) {
        this.object = object;
        this.put("object", object);
    }
    public void setData(List<Embedding> data) {
        this.data = data;
        this.putPOJO("data", data);
    }
    public void setUsage(Usage usage) {
        this.usage = usage;
        this.putPOJO("usage", usage);
    }
}
