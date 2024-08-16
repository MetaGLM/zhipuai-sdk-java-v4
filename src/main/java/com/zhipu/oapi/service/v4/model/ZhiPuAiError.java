package com.zhipu.oapi.service.v4.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents the error body when an ZhiPuAI request fails
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZhiPuAiError {

    public ZhiPuAiErrorDetails error;

    @JsonProperty("contentFilter")
    public List<ContentFilter> contentFilter;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ZhiPuAiErrorDetails {
        /**
         * Human-readable error message
         */
        String message;

        String type;

        String param;

        /**
         * ZhiPuAI error code, for example "invalid_api_key"
         */
        String code;
    }

    /**
     * 敏感词
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ContentFilter {
        String level;

        String role;

    }
}
