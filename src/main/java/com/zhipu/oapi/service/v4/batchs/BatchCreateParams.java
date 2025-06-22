package com.zhipu.oapi.service.v4.batchs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.core.model.ClientRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Data
public class BatchCreateParams  implements ClientRequest<BatchCreateParams> {

    @JsonProperty("completion_window")
    private String completionWindow;
    @JsonProperty("endpoint")
    private String endpoint;
    @JsonProperty("input_file_id")
    private String inputFileId;  // 必须是上传文件的ID
    @JsonProperty("metadata")
    private Map<String, String> metadata;  // 可选的自定义元数据

    public BatchCreateParams() {
    }

    public BatchCreateParams(String completionWindow, String endpoint, String inputFileId, Map<String, String> metadata) {
        this.completionWindow = completionWindow;
        this.endpoint = endpoint;
        this.inputFileId = inputFileId;
        this.metadata = metadata;
    }

    @Override
    public BatchCreateParams getOptions() {
        return this;
    }
}
