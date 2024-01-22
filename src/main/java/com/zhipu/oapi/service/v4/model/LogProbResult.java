package com.zhipu.oapi.service.v4.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Log probabilities of different token options
 * Returned if { } is greater than zero
 */
@Data
public class LogProbResult {

    /**
     * The tokens chosen by the completion api
     */
    List<String> tokens;

    /**
     * The log probability of each token in { }
     */
    @JsonProperty ( "token_logprobs")
    List<Double> tokenLogprobs;

    /**
     * A map for each index in the completion result.
     * The map contains the top { } tokens and their probabilities
     */
    @JsonProperty("top_logprobs")
    List<Map<String, Double>> topLogprobs;

    /**
     * The character offset from the start of the returned text for each of the chosen tokens.
     */
    List<Integer> textOffset;
}
