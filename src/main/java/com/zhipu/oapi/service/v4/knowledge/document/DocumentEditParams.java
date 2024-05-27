package com.zhipu.oapi.service.v4.knowledge.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DocumentEditParams {

    @JsonProperty("id")
    private String id; // 知识ID

    @JsonProperty("knowledge_type")
    private int knowledgeType; // 知识类型: 1:文章知识: 支持pdf,url,docx 2:问答知识-文档: 支持pdf,url,docx 3:问答知识-表格: 支持xlsx 4:商品库-表格: 支持xlsx 5:自定义: 支持pdf,url,docx

    @JsonProperty("custom_separator")
    private List<String> customSeparator; // 当前知识类型为自定义(knowledge_type=5)时的切片规则，默认\n

    @JsonProperty("sentence_size")
    private Integer sentenceSize; // 当前知识类型为自定义(knowledge_type=5)时的切片字数，取值范围: 20-2000，默认300

    @JsonProperty("callback_url")
    private String callbackUrl; // 回调地址

    @JsonProperty("callback_header")
    private Map<String, String> callbackHeader; // 回调时携带的header
}
