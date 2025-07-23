package com.zhipu.oapi.service.v4.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@Getter
public class MCPTool extends ObjectNode {
    /**
     * mcp server 的标识，用于区分不同的 mcp server，必填
     */
    private String server_label;

    /**
     * mcp server 的 url，非必填
     * 默认（若该字段为空）：以 server_label 作为 mcpCode，连接智谱AI的 mcp servers,
     */
    private String server_url;

    /**
     * mcp 调用的传输方式：sse/streamable-http，默认为 streamable-http
     */
    private String transport_type;

    /**
     * 允许调用的工具列表，默认为空，即允许所有工具
     */
    private Set<String> allowed_tools;

    /**
     * 连接 mcp server 的 headers，鉴权使用
     */
    private Map<String, String> headers;

    public MCPTool() {
        super(JsonNodeFactory.instance);
    }

    public MCPTool(JsonNodeFactory nc, Map<String, JsonNode> kids) {
        super(nc, kids);
    }

    public void setServer_label(String server_label) {
        this.server_label = server_label;
        this.put("server_label", server_label);
    }

    public void setServer_url(String server_url) {
        this.server_url = server_url;
        this.put("server_url", server_url);
    }

    public void setTransport_type(String transport_type) {
        this.transport_type = transport_type;
        this.put("transport_type", transport_type);
    }

    public void setAllowed_tools(Set<String> allowed_tools) {
        this.allowed_tools = allowed_tools;
        this.putPOJO("allowed_tools", allowed_tools);
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
        this.putPOJO("headers", headers);
    }
}
