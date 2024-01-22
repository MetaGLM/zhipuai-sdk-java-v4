package com.zhipu.oapi.service.v4.model;

public class ZhiPuAiHttpException extends RuntimeException {

    /**
     * HTTP status code
     */
    public final int statusCode;

    /**
     * ZhiPuAI error code, for example "invalid_api_key"
     */
    public final String code;


    public final String param;

    /**
     * ZhiPuAI error type, for example "invalid_request_error"
     */
    public final String type;

    public ZhiPuAiHttpException(ZhiPuAiError error, Exception parent, int statusCode) {
        super(error.error.message, parent);
        this.statusCode = statusCode;
        this.code = error.error.code;
        this.param = error.error.param;
        this.type = error.error.type;
    }
}
