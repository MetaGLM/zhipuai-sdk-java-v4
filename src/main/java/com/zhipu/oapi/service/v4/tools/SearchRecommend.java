package com.zhipu.oapi.service.v4.tools;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;

import java.util.Iterator;

public class SearchRecommend extends ObjectNode {

    /**
     * 搜索轮次，默认为 0
     */
    @JsonProperty("index")
    private int index;

    /**
     * 推荐query
     */
    @JsonProperty("query")
    private String query;

    public SearchRecommend() {
        super(JsonNodeFactory.instance);
    }

    public SearchRecommend(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("index") != null) {
            this.setIndex(objectNode.get("index").asInt());
        } else {
            this.setIndex(0);
        }
        if (objectNode.get("query") != null) {
            this.setQuery(objectNode.get("query").asText());
        } else {
            this.setQuery(null);
        }

        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }

    // Getters and Setters

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        this.put("index", index);
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;

        this.put("query", query);
    }

}