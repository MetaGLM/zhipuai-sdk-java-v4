package com.zhipu.oapi.service.v4.videos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.core.model.ClientRequest;
import com.zhipu.oapi.service.v4.batchs.BatchCreateParams;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@Data
public class VideoCreateParams implements ClientRequest<VideoCreateParams> {

    /**
     * 模型编码
     */
    @JsonProperty("id")
    private String id;
    /**
     * 模型编码
     */
    @JsonProperty("model")
    private String model;

    /**
     * 所需视频的文本描述
     */
    @JsonProperty("prompt")
    private String prompt;

    /**
     * 由用户端传参，需保证唯一性；用于区分每次请求的唯一标识，用户端不传时平台会默认生成。
     */
    @JsonProperty("request_id")
    private String requestId;

    @Override
    public VideoCreateParams getOptions() {
        return this;
    }
}