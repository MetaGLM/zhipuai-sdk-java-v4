package com.zhipu.oapi.service.v4.knowledge.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UploadDetail  {

    @JsonProperty("url")
    private String url; // URL

    @JsonProperty("knowledge_type")
    private int knowledgeType; // 知识类型

    @JsonProperty("file_name")
    private String fileName; // 文件名称

    @JsonProperty("sentence_size")
    private Integer sentenceSize; // 句子大小

    @JsonProperty("custom_separator")
    private List<String> customSeparator; // 自定义分隔符

    @JsonProperty("callback_url")
    private String callbackUrl; // 回调 URL

    @JsonProperty("callback_header")
    private Map<String, String> callbackHeader; // 回调头
}
