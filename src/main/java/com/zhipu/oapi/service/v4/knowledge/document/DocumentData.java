package com.zhipu.oapi.service.v4.knowledge.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

import java.util.List;

@Data
public class DocumentData {

    @JsonProperty("id")
    private String id; // 知识唯一id

    @JsonProperty("custom_separator")
    private List<String> customSeparator; // 切片规则

    @JsonProperty("sentence_size")
    private String sentenceSize; // 切片大小

    @JsonProperty("bytes")
    private Integer bytes; // 文件大小（字节）

    @JsonProperty("word_num")
    private Integer wordNum; // 文件字数

    @JsonProperty("filename")
    private String filename; // 文件名

    @JsonProperty("url")
    private String url; // 文件下载链接

    @JsonProperty("embedding_stat")
    private Integer embeddingStat; // 0:向量化中 1:向量化完成 2:向量化失败

    @JsonProperty("failInfo")
    private DocumentDataFailInfo failInfo; // 失败原因 向量化失败embedding_stat=2的时候 会有此值

    private ChatError error;
}