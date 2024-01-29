package com.zhipu.oapi.service.v4.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ChatMessage {


    private String role;
    private Object content;

    private String name;

    @JsonProperty("tool_calls")
    private List<ToolCalls> tool_calls;

    private String tool_call_id;


    public ChatMessage(String role, Object content) {
        this.role = role;
        this.content = content;
    }


    public ChatMessage(String role, Object content, String name) {
        this.role = role;
        this.content = content;
        this.name = name;
    }


    public ChatMessage(String role, Object content, String name,String tool_call_id) {
        this.role = role;
        this.content = content;
        this.name = name;
        this.tool_call_id = tool_call_id;
    }

}
