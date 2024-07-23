package com.zhipu.oapi.service.v4.tools;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.tools.SearchRecommendDeserializer;
import com.zhipu.oapi.service.v4.deserialize.tools.SearchResultDeserializer;

import java.util.Iterator;

@JsonDeserialize(using = SearchResultDeserializer.class)
public class SearchResult extends ObjectNode {

    /**
     * 搜索轮次，默认为 0
     */
    @JsonProperty("index")
    private int index;

    /**
     * 标题
     */
    @JsonProperty("title")
    private String title;

    /**
     * 链接
     */
    @JsonProperty("link")
    private String link;

    /**
     * 内容
     */
    @JsonProperty("content")
    private String content;

    /**
     * 图标
     */
    @JsonProperty("icon")
    private String icon;

    /**
     * 来源媒体
     */
    @JsonProperty("media")
    private String media;

    /**
     * 角标序号 [ref_1]
     */
    @JsonProperty("refer")
    private String refer;

    public SearchResult() {
        super(JsonNodeFactory.instance);
    }

    public SearchResult(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("index") != null) {
            this.setIndex(objectNode.get("index").asInt());
        } else {
            this.setIndex(0);
        }
        if (objectNode.get("title") != null) {
            this.setTitle(objectNode.get("title").asText());
        } else {
            this.setTitle(null);
        }
        if (objectNode.get("link") != null) {
            this.setLink(objectNode.get("link").asText());
        } else {
            this.setLink(null);
        }
        if (objectNode.get("content") != null) {
            this.setContent(objectNode.get("content").asText());
        } else {
            this.setContent(null);
        }
        if (objectNode.get("icon") != null) {
            this.setIcon(objectNode.get("icon").asText());
        } else {
            this.setIcon(null);
        }
        if (objectNode.get("media") != null) {
            this.setMedia(objectNode.get("media").asText());
        } else {
            this.setMedia(null);
        }
        if (objectNode.get("refer") != null) {
            this.setRefer(objectNode.get("refer").asText());
        } else {
            this.setRefer(null);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.put("title", title);
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
        this.put("link", link);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.put("content", content);
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
        this.put("icon", icon);
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
        this.put("media", media);
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
        this.put("refer", refer);
    }
}
