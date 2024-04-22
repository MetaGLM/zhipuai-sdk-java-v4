package com.zhipu.oapi.service.v4.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
//@AllArgsConstructor
public class Choice {

    @JsonProperty("finish_reason")
    private String finishReason;
    @JsonProperty("index")
    private Long index;

    @JsonProperty("message")
    private ChatMessage message;

    @JsonProperty("delta")
    private Delta delta;


    public Choice(String finishReason, Long index, Delta delta) {
        this.finishReason =finishReason;
        this.index = index;
        this.delta =delta;
    }
}