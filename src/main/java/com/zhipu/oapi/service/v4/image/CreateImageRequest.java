package com.zhipu.oapi.service.v4.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.core.model.ClientRequest;
import com.zhipu.oapi.service.v4.CommonRequest;
import com.zhipu.oapi.service.v4.file.UploadFileRequest;
import com.zhipu.oapi.service.v4.model.SensitiveWordCheckRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A request for ZhiPuAi to create an image based on a prompt
 * All fields except prompt are optional
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateImageRequest extends CommonRequest  implements ClientRequest<Map<String, Object>> {

    /**
     * A text description of the desired image(s). The maximum length is 1000 characters for dall-e-2 and 4000 characters for dall-e-3.
     */
    @NonNull
    private String prompt;

    /**
     * The model to use for image generation. Defaults to "dall-e-2".
     */
    private String model;

    /**
     * The size of the image to generate. Defaults to "256x256".
     */
    private String size;

    /**
     * 敏感词检测控制
     */
    @JsonProperty("sensitive_word_check")
    private SensitiveWordCheckRequest sensitiveWordCheck;

    @Override
    public Map<String, Object> getOptions() {
        Map<String, Object> request = new HashMap<>();
        request.put("request_id", this.getRequestId());
        request.put("user_id", this.getUserId());
        request.put("prompt", this.getPrompt());
        request.put("model", this.getModel());
        request.put("size", this.getSize());
        request.put("sensitive_word_check", this.getSensitiveWordCheck());

        if(this.getExtraJson() !=null){
            request.putAll(this.getExtraJson());
        }
        return request;
    }
}
