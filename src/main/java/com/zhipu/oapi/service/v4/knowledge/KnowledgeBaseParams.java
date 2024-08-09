package com.zhipu.oapi.service.v4.knowledge;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.core.model.ClientRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;
import java.util.Optional;

@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class KnowledgeBaseParams implements ClientRequest<KnowledgeBaseParams> {
    /**
     * 知识库参数类型定义
     * <p>
     * Attributes:
     * embedding_id (int): 知识库绑定的向量化模型ID
     * name (String): 知识库名称，限制100字
     * customer_identifier (String): 用户标识，长度32位以内
     * description (String): 知识库描述，限制500字
     * background (String): 背景颜色
     * icon (String): 知识库图标
     * bucket_id (String): 桶ID，限制32位
     */

    @JsonProperty("embedding_id")
    private int embeddingId;

    @JsonProperty("name")
    private String name;
    @JsonProperty("knowledge_id")
    private String knowledgeId;

    @JsonProperty("customer_identifier")
    private String customerIdentifier;

    @JsonProperty("description")
    private String description;

    @JsonProperty("background")
    private String background;

    @JsonProperty("icon")
    private String icon;

    @JsonProperty("bucket_id")
    private String bucketId;

    @Override
    public KnowledgeBaseParams getOptions() {
        return this;
    }
}