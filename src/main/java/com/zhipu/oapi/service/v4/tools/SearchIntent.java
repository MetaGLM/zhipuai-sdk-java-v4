package com.zhipu.oapi.service.v4.tools;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.tools.SearchChatMessageDeserializer;
import com.zhipu.oapi.service.v4.deserialize.tools.SearchIntentDeserializer;

import java.util.Iterator;

@JsonDeserialize(using = SearchIntentDeserializer.class)
public class SearchIntent extends ObjectNode {

    /**
     * 搜索轮次，默认为 0
     */
    @JsonProperty("index")
    private int index;

    /**
     * 搜索优化 query
     */
    @JsonProperty("query")
    private String query;

    /**
     * 判断的意图类型
     */
    @JsonProperty("intent")
    private String intent;

    /**
     * 搜索关键词
     */
    @JsonProperty("keywords")
    private String keywords;

    public SearchIntent() {
        super(JsonNodeFactory.instance);
    }

    public SearchIntent(ObjectNode objectNode) {
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
        if (objectNode.get("intent") != null) {
            this.setIntent(objectNode.get("intent").asText());
        } else {
            this.setIntent(null);
        }
        if (objectNode.get("keywords") != null) {
            this.setKeywords(objectNode.get("keywords").asText());
        } else {
            this.setKeywords(null);
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

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
        this.put("intent", intent);
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
        this.put("keywords", keywords);
    }
}
