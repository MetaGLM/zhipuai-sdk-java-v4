package com.zhipu.oapi.service.v4.knowledge;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

@Data
public class KnowledgeUsed  {

    @JsonProperty("used")
    private KnowledgeStatistics used; // 已使用量

    @JsonProperty("total")
    private KnowledgeStatistics total; // 知识库总量


    private ChatError error;
}
