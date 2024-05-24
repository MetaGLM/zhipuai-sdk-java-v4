package com.zhipu.oapi.service.v4.knowledge;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KnowledgeInfo {

    @JsonProperty("id")
    private String id; // 知识库唯一 id

    @JsonProperty("embedding_id")
    private String embeddingId; // 知识库绑定的向量化模型 ID

    @JsonProperty("name")
    private String name; // 知识库名称，100字限制

    @JsonProperty("customer_identifier")
    private String customerIdentifier; // 用户标识，长度32位以内

    @JsonProperty("description")
    private String description; // 知识库描述，500字限制

    @JsonProperty("background")
    private String background; // 背景颜色 'blue', 'red', 'orange', 'purple', 'sky'

    @JsonProperty("icon")
    private String icon; // 知识库图标 'question', 'book', 'seal', 'wrench', 'tag', 'horn', 'house'

    @JsonProperty("bucket_id")
    private String bucketId; // 桶id，限制32位
}
