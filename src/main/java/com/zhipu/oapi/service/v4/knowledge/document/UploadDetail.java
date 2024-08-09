package com.zhipu.oapi.service.v4.knowledge.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 * This class represents the details required for uploading a file to the knowledge base.
 */
public class UploadDetail {

    /**
     * URL of the file to be uploaded.
     */
    @JsonProperty("url")
    private String url;

    /**
     * Knowledge type identifier.
     */
    @JsonProperty("knowledge_type")
    private int knowledgeType;

    /**
     * Optional file name.
     */
    @JsonProperty("file_name")
    private String fileName;

    /**
     * Optional sentence size for processing.
     */
    @JsonProperty("sentence_size")
    private Integer sentenceSize;

    /**
     * Optional list of custom separators.
     */
    @JsonProperty("custom_separator")
    private List<String> customSeparator;

    /**
     * Optional callback URL for notifications.
     */
    @JsonProperty("callback_url")
    private String callbackUrl;

    /**
     * Optional callback headers for the callback request.
     */
    @JsonProperty("callback_header")
    private Map<String, String> callbackHeader;

    // Getters and Setters

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getKnowledgeType() {
        return knowledgeType;
    }

    public void setKnowledgeType(int knowledgeType) {
        this.knowledgeType = knowledgeType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getSentenceSize() {
        return sentenceSize;
    }

    public void setSentenceSize(Integer sentenceSize) {
        this.sentenceSize = sentenceSize;
    }

    public List<String> getCustomSeparator() {
        return customSeparator;
    }

    public void setCustomSeparator(List<String> customSeparator) {
        this.customSeparator = customSeparator;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public Map<String, String> getCallbackHeader() {
        return callbackHeader;
    }

    public void setCallbackHeader(Map<String, String> callbackHeader) {
        this.callbackHeader = callbackHeader;
    }

}