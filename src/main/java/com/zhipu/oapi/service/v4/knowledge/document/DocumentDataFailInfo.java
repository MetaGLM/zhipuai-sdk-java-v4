package com.zhipu.oapi.service.v4.knowledge.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DocumentDataFailInfo  {

    @JsonProperty("embedding_code")
    private Integer embeddingCode; // 失败码 10001：知识不可用，知识库空间已达上限 10002：知识不可用，知识库空间已达上限(字数超出限制)

    @JsonProperty("embedding_msg")
    private String embeddingMsg; // 失败原因

}
