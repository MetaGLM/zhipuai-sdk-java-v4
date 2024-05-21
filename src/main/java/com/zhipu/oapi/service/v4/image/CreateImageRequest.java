package com.zhipu.oapi.service.v4.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.CommonRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * A request for ZhiPuAi to create an image based on a prompt
 * All fields except prompt are optional
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateImageRequest extends CommonRequest {

    /**
     * A text description of the desired image(s). The maximum length is 1000 characters for dall-e-2 and 4000 characters for dall-e-3.
     */
    @NonNull
    private String prompt;

    /**
     * The model to use for image generation. Defaults to "dall-e-2".
     */
    private String model;

}
