package com.zhipu.oapi.service.v4.batchs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Map;

@Getter
public class BatchCreateParams {

    @JsonProperty("completion_window")
    private String completionWindow;  // 必须是 "24h"
    @JsonProperty("endpoint")
    private String endpoint;  // 必须是 "/v4/chat/completions" 或 "/v4/embeddings"
    @JsonProperty("input_file_id")
    private String inputFileId;  // 必须是上传文件的ID
    @JsonProperty("metadata")
    private Map<String, String> metadata;  // 可选的自定义元数据

    public BatchCreateParams(String completionWindow, String endpoint, String inputFileId, Map<String, String> metadata) {
        if (!"24h".equals(completionWindow)) {
            throw new IllegalArgumentException("completionWindow must be '24h'");
        }
        if (!"/v4/chat/completions".equals(endpoint) && !"/v4/embeddings".equals(endpoint)) {
            throw new IllegalArgumentException("endpoint must be '/v4/chat/completions' or '/v4/embeddings'");
        }
        this.completionWindow = completionWindow;
        this.endpoint = endpoint;
        this.inputFileId = inputFileId;
        this.metadata = metadata;
    }

}
