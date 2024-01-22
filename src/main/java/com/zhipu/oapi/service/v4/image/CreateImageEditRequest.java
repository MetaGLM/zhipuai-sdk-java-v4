package com.zhipu.oapi.service.v4.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * All fields except prompt are optional
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateImageEditRequest {

    /**
     * A text description of the desired image(s). The maximum length in 1000 characters.
     */
    @NonNull
    String prompt;

    /**
     * The model to use for image generation. Only dall-e-2 is supported at this time. Defaults to dall-e-2.
     */
    String model;

    /**
     * The number of images to generate. Must be between 1 and 10. Defaults to 1.
     */
    Integer n;

    /**
     * The size of the generated images. Must be one of "256x256", "512x512", or "1024x1024". Defaults to "1024x1024".
     */
    String size;

    /**
     * The format in which the generated images are returned. Must be one of url or b64_json. Defaults to url.
     */
    @JsonProperty("response_format")
    String responseFormat;

    /**
     * A unique identifier representing your end-user, which will help ZhiPuAI to monitor and detect abuse.
     */
    String user;
}
