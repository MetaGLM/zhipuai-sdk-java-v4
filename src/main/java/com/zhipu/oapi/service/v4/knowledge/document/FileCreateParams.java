package com.zhipu.oapi.service.v4.knowledge.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.CommonRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class FileCreateParams extends CommonRequest {

    /**
     * local file
     */
    @JsonIgnore
    private String filePath;

    @JsonProperty("upload_detail")
    private List<UploadDetail> uploadDetail; // file和upload_detail二选一必填

    @JsonProperty("purpose")
    private String purpose; // 上传文件的用途，支持 "fine-tune"和 "retrieval"

    @JsonProperty("knowledge_id")
    private String knowledgeId; // 当文件上传目的为 retrieval 时，需要指定知识库ID进行上传

    @JsonProperty("sentence_size")
    private int sentenceSize; // 当文件上传目的为 retrieval 时，需要指定句子大小

}
