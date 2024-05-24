package com.zhipu.oapi.service.v4.knowledge;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KnowledgeBaseParams {
    
    @JsonProperty("knowledge_id")
    private String knowledgeId;
    @JsonProperty("embedding_id")
    private int embeddingId;

    @JsonProperty("name")
    private String name;

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

}
