package com.zhipu.oapi.service.v4.knowledge.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DocumentListParams {

    @JsonProperty("purpose")
    private String purpose; // 文件用途

    @JsonProperty("knowledge_id")
    private String knowledgeId; // 当文件用途为 retrieval 时，需要提供查询的知识库ID

    @JsonProperty("page")
    private Integer page; // 页，默认1

    @JsonProperty("limit")
    private Integer limit; // 查询文件列表数，默认10

    @JsonProperty("after")
    private String after; // 查询指定fileID之后的文件列表（当文件用途为 fine-tune 时需要）

    @JsonProperty("order")
    private String order; // 排序规则，可选值['desc', 'asc']，默认desc（当文件用途为 fine-tune 时需要）

}
