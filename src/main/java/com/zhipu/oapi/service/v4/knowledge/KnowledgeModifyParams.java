package com.zhipu.oapi.service.v4.knowledge;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KnowledgeModifyParams {
    
    @JsonProperty("id")
    private String id;

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


}
