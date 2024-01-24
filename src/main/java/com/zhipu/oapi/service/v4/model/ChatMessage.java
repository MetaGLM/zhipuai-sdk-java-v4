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

    String name;

    @JsonProperty("tool_calls")
    List<ToolCalls> tool_calls;


    public ChatMessage(String role, Object content) {
        this.role = role;
        this.content = content;
    }


    public ChatMessage(String role, Object content, String name) {
        this.role = role;
        this.content = content;
        this.name = name;
    }


}
