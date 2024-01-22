package com.zhipu.oapi.service.v4.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the error body when an ZhiPuAI request fails
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZhiPuAiError {

    public ZhiPuAiErrorDetails error;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
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
}
