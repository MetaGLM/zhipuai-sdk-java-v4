package com.zhipu.oapi.service.v4.assistant.conversation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * This class represents the usage statistics for a conversation.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usage {

    /**
     * The number of tokens in the user's input.
     */
    @JsonProperty("prompt_tokens")
    private int promptTokens;

    /**
     * The number of tokens in the model's input.
     */
    @JsonProperty("completion_tokens")
    private int completionTokens;

    /**
     * The total number of tokens.
     */
    @JsonProperty("total_tokens")
    private int totalTokens;

}
