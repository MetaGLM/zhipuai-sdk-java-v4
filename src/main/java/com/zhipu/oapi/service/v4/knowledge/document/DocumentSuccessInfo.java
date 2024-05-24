package com.zhipu.oapi.service.v4.knowledge.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class DocumentSuccessInfo  {

    @JsonProperty("id")
    private String id; // 文件唯一 ID

    @JsonProperty("documentId")
    private String documentId; // 文件 ID

    @JsonProperty("filename")
    private String filename; // 文件名称

    @JsonProperty("knowledge_id")
    private String knowledgeId; // 知识库 ID

}
