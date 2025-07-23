package com.zhipu.oapi.service.v4.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum McpToolTransportType {

    SSE("sse", "SSE"),
    STREAMABLE_HTTP("streamable-http", "可流式传输的HTTP");

    private final String code;
    private final String value;

}
