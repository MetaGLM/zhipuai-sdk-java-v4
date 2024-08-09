package com.zhipu.oapi.service.v4.knowledge.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.core.model.ClientRequest;
import com.zhipu.oapi.service.v4.CommonRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * This class represents the parameters for editing a document in the knowledge base.
 */
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DocumentEditParams implements ClientRequest<DocumentEditParams> {


    /**
     * 知识ID
     */
    @JsonProperty("id")
    private String id;

    /**
     * 知识类型:
     * <ul>
     *   <li>1: 文章知识: 支持pdf,url,docx</li>
     *   <li>2: 问答知识-文档: 支持pdf,url,docx</li>
     *   <li>3: 问答知识-表格: 支持xlsx</li>
     *   <li>4: 商品库-表格: 支持xlsx</li>
     *   <li>5: 自定义: 支持pdf,url,docx</li>
     * </ul>
     */
    @JsonProperty("knowledge_type")
    private Integer knowledgeType;

    /**
     * 当前知识类型为自定义(knowledge_type=5)时的切片规则，默认\n
     */
    @JsonProperty("custom_separator")
    private List<String> customSeparator;

    /**
     * 当前知识类型为自定义(knowledge_type=5)时的切片字数，取值范围: 20-2000，默认300
     */
    @JsonProperty("sentence_size")
    private Integer sentenceSize;

    /**
     * 回调地址
     */
    @JsonProperty("callback_url")
    private String callbackUrl;

    /**
     * 回调时携带的header
     */
    @JsonProperty("callback_header")
    private Map<String, String> callbackHeader;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getKnowledgeType() {
        return knowledgeType;
    }

    public void setKnowledgeType(Integer knowledgeType) {
        this.knowledgeType = knowledgeType;
    }

    public List<String> getCustomSeparator() {
        return customSeparator;
    }

    public void setCustomSeparator(List<String> customSeparator) {
        this.customSeparator = customSeparator;
    }

    public Integer getSentenceSize() {
        return sentenceSize;
    }

    public void setSentenceSize(Integer sentenceSize) {
        this.sentenceSize = sentenceSize;
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

    @Override
    public DocumentEditParams getOptions() {
        return this;
    }
}
