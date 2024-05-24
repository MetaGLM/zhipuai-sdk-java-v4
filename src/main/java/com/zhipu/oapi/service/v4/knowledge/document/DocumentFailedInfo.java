package com.zhipu.oapi.service.v4.knowledge.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DocumentFailedInfo {

    @JsonProperty("failReason")
    private String failReason; // 上传失败的原因，包括：文件格式不支持、文件大小超出限制、知识库容量已满、容量上限为 50 万字。

    @JsonProperty("filename")
    private String filename; // 文件名称

    @JsonProperty("knowledge_id")
    private String knowledgeId; // 知识库 ID

}
