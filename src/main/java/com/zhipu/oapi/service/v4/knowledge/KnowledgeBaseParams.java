package com.zhipu.oapi.service.v4.knowledge;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.CommonRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class KnowledgeBaseParams extends CommonRequest {

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
