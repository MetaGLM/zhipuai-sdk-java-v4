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
     * 支持 URL 或者 Base64、传入 image 奖进行图生视频
     * 图片格式：
     *   图片大小：
     */
    @JsonProperty("image_url")
    private String imageUrl;

    /**
     * 调用指定模型进行对 prompt 优化，推荐使用 GLM-4-Air 和 GLM-4-Flash。如未指定，则直接使用原始 prompt。
     */
    @JsonProperty("prompt_opt_model")
    private String promptPptModel;

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