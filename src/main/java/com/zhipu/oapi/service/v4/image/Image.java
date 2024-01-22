package com.zhipu.oapi.service.v4.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * An object containing either a URL or a base 64 encoded image.
 */
@Data
public class Image {
    /**
     * The URL where the image can be accessed.
     */
    String url;


    /**
     * Base64 encoded image string.
     */
    @JsonProperty("b64_json")
    String b64Json;

    /**
     * The prompt that was used to generate the image, if there was any revision to the prompt.
     */
    @JsonProperty("revised_prompt")
    String revisedPrompt;
}
