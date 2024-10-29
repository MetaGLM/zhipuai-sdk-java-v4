package com.zhipu.oapi.service.v4.tools;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.tools.WebSearchMessageDeserializer;
import com.zhipu.oapi.service.v4.deserialize.tools.WebSearchMessageToolCallDeserializer;

import java.util.Iterator;
import java.util.List;

@JsonDeserialize(using = WebSearchMessageToolCallDeserializer.class)
public class WebSearchMessageToolCall extends ObjectNode {

    /**
     * Tool call ID
     */
    @JsonProperty("id")
    private String id;

    /**
     * 搜索意图
     */
    @JsonProperty("search_intent")
    private List<SearchIntent> searchIntent;

    /**
     * 搜索结果
     */
    @JsonProperty("search_result")
    private List<SearchResult> searchResult;

    /**
     * 推荐query
     */
    @JsonProperty("search_recommend")
    private SearchRecommend searchRecommend;

    /**
     * Tool call 类型
     */
    @JsonProperty("type")
    private String type;

    public WebSearchMessageToolCall() {
        super(JsonNodeFactory.instance);
    }

    public WebSearchMessageToolCall(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("id") != null) {
            this.setId(objectNode.get("id").asText());
        } else {
            this.setId(null);
        }
        if (objectNode.get("search_intent") != null) {

            this.setSearchIntent(objectMapper.convertValue(objectNode.get("search_intent"), new TypeReference<List<SearchIntent>>() {}));

        } else {
            this.setSearchIntent(null);
        }

        if (objectNode.get("search_result") != null) {

            this.setSearchResult(objectMapper.convertValue(objectNode.get("search_result"), new TypeReference<List<SearchResult>>() {}));
        } else {
            this.setSearchResult(null);
        }
        if (objectNode.get("search_recommend") != null) {
            this.setSearchRecommend(objectMapper.convertValue(objectNode.get("search_recommend"), SearchRecommend.class));
        } else {
            this.setSearchRecommend(null);
        }
        if (objectNode.get("type") != null) {
            this.setType(objectNode.get("type").asText());
        } else {
            this.setType(null);
        }

        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.put("id", id);
    }

    public List<SearchIntent> getSearchIntent() {
        return searchIntent;
    }

    public void setSearchIntent(List<SearchIntent> searchIntent) {
        this.searchIntent = searchIntent;
        this.putPOJO("search_intent", searchIntent);
    }

    public List<SearchResult> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<SearchResult> searchResult) {
        this.searchResult = searchResult;
        this.putPOJO("search_result", searchResult);
    }

    public SearchRecommend getSearchRecommend() {
        return searchRecommend;
    }

    public void setSearchRecommend(SearchRecommend searchRecommend) {
        this.searchRecommend = searchRecommend;
        this.set("search_recommend", searchRecommend);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        this.put("type", type);
    }
}
