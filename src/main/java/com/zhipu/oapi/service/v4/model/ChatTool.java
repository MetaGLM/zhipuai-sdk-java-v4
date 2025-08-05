package com.zhipu.oapi.service.v4.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.*;

import java.util.Map;


@Getter
public class ChatTool extends ObjectNode {
    private String type;

    private ChatFunction function;


    private Retrieval retrieval;

    @JsonProperty("web_search")
    private WebSearch web_search;

    private MCPTool mcp;

    public ChatTool(){
        super(JsonNodeFactory.instance);
    }
    public ChatTool(JsonNodeFactory nc, Map<String, JsonNode> kids) {
        super(nc, kids);
    }


    public void setType(String type){
        this.type = type;
        this.put("type",type);
    }
    public void setFunction(ChatFunction function){
        this.function = function;
        this.putPOJO("function",function);
    }

    public void setRetrieval(Retrieval retrieval){
        this.retrieval = retrieval;
        this.putPOJO("retrieval",retrieval);
    }

    public void setWeb_search(WebSearch web_search) {
        this.web_search = web_search;
        this.putPOJO("web_search",web_search);
    }

    public void setMcp(MCPTool mcp) {
        this.mcp = mcp;
        this.putPOJO("mcp", mcp);
    }
}
