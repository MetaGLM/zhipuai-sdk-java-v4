package com.zhipu.oapi.service.v4.knowledge.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

import java.util.List;

@Data
public class DocumentObject {

    @JsonProperty("successInfos")
    private List<DocumentSuccessInfo> successInfos; // 上传成功的文件信息

    @JsonProperty("failedInfos")
    private List<DocumentFailedInfo> failedInfos; // 上传失败的文件信息

    @JsonProperty("purpose")
    private String purpose; // 目的


    private ChatError error;
}
