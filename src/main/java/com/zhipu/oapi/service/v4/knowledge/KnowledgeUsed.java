package com.zhipu.oapi.service.v4.knowledge;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KnowledgeUsed  {

    @JsonProperty("used")
    private KnowledgeStatistics used; // 已使用量

    @JsonProperty("total")
    private KnowledgeStatistics total; // 知识库总量

}
