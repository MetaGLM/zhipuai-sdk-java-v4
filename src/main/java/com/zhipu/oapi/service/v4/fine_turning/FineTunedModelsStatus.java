package com.zhipu.oapi.service.v4.fine_turning;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FineTunedModelsStatus {
    /**
     * 请求id
     */
    @JsonProperty("request_id")
    private String requestId;
    /**
     *模型名称
     */
    @JsonProperty("model_name")
    private String modelName;

    /**
     *  #删除状态 deleting（删除中）, deleted （已删除）
     */
    @JsonProperty("delete_status")
    private String deleteStatus;

}
