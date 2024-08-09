package com.zhipu.oapi.service.v4.knowledge.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.knowledge.document.DocumentDataDeserializer;

import java.util.Iterator;
import java.util.List;

/**
 * This class represents the document data, including metadata and processing status.
 */
@JsonDeserialize(using = DocumentDataDeserializer.class)
public class DocumentData extends ObjectNode {

    /**
     * 知识唯一id
     */
    @JsonProperty("id")
    private String id;

    /**
     * 切片规则
     */
    @JsonProperty("custom_separator")
    private List<String> customSeparator;

    /**
     * 切片大小
     */
    @JsonProperty("sentence_size")
    private String sentenceSize;

    /**
     * 文件大小（字节）
     */
    @JsonProperty("length")
    private Integer length;

    /**
     * 文件字数
     */
    @JsonProperty("word_num")
    private Integer wordNum;

    /**
     * 文件名
     */
    @JsonProperty("name")
    private String name;

    /**
     * 文件下载链接
     */
    @JsonProperty("url")
    private String url;

    /**
     * 向量化状态
     * 0: 向量化中
     * 1: 向量化完成
     * 2: 向量化失败
     */
    @JsonProperty("embedding_stat")
    private Integer embeddingStat;

    /**
     * 失败原因，向量化失败时会有此值
     */
    @JsonProperty("failInfo")
    private DocumentDataFailInfo failInfo;


    public DocumentData() {
        super(JsonNodeFactory.instance);
    }

    public DocumentData(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);

        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode == null) {
            return;
        }
        if (objectNode.get("id") != null) {
            this.setId(objectNode.get("id").asText());
        }else {
            this.setId(null);
        }
        if (objectNode.get("custom_separator") != null) {
            List<String> customSeparator = objectNode.findValuesAsText("custom_separator");
            this.setCustomSeparator(customSeparator);
        }else {
            this.setCustomSeparator(null);
        }
        if (objectNode.get("sentence_size") != null) {
            this.setSentenceSize(objectNode.get("sentence_size").asText());
        }else {
            this.setSentenceSize(null);
        }
        if (objectNode.get("length") != null) {
            this.setLength(objectNode.get("length").asInt());
        }else {
            this.setLength(null);
        }
        if (objectNode.get("word_num") != null) {
            this.setWordNum(objectNode.get("word_num").asInt());
        }else {
            this.setWordNum(null);
        }
        if (objectNode.get("name") != null) {
            this.setName(objectNode.get("name").asText());
        }else {
            this.setName(null);
        }
        if (objectNode.get("url") != null) {
            this.setUrl(objectNode.get("url").asText());
        }else {
            this.setUrl(null);
        }
        if (objectNode.get("embedding_stat") != null) {
            this.setEmbeddingStat(objectNode.get("embedding_stat").asInt());
        }else {
            this.setEmbeddingStat(null);
        }

        if (objectNode.get("failInfo") != null) {
            DocumentDataFailInfo failInfo = objectMapper.convertValue(objectNode.get("failInfo"), DocumentDataFailInfo.class);
            this.setFailInfo(failInfo);
        }else {
            this.setFailInfo(null);
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

    public List<String> getCustomSeparator() {
        return customSeparator;
    }

    public void setCustomSeparator(List<String> customSeparator) {
        this.customSeparator = customSeparator;
        ArrayNode jsonNodes = this.putArray("customSeparator");
        if (customSeparator == null) {
            jsonNodes.removeAll();
        }
        else {
            for (String item : customSeparator) {
                jsonNodes.add(item);
            }
        }
    }

    public String getSentenceSize() {
        return sentenceSize;
    }

    public void setSentenceSize(String sentenceSize) {
        this.sentenceSize = sentenceSize;
        this.put("sentenceSize", sentenceSize);
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
        this.put("length", length);
    }

    public Integer getWordNum() {
        return wordNum;
    }

    public void setWordNum(Integer wordNum) {
        this.wordNum = wordNum;
        this.put("wordNum", wordNum);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.put("name", name);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        this.put("url", url);
    }

    public Integer getEmbeddingStat() {
        return embeddingStat;
    }

    public void setEmbeddingStat(Integer embeddingStat) {
        this.embeddingStat = embeddingStat;
        this.put("embeddingStat", embeddingStat);
    }

    public DocumentDataFailInfo getFailInfo() {
        return failInfo;
    }

    public void setFailInfo(DocumentDataFailInfo failInfo) {
        this.failInfo = failInfo;
        this.putPOJO("failInfo", failInfo);
    }
}
