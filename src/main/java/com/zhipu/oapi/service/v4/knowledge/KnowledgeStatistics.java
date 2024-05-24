package com.zhipu.oapi.service.v4.knowledge;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KnowledgeStatistics {

    @JsonProperty("word_num")
    private Integer wordNum; // 使用量统计 - 单词数量

    @JsonProperty("length")
    private Integer length; // 使用量统计 - 长度

}
