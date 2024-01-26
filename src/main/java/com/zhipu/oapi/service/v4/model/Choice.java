package com.zhipu.oapi.service.v4.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
//@AllArgsConstructor
public class Choice {

    @SerializedName("finish_reason")
    @JsonProperty("finish_reason")
    private String finishReason;
    @SerializedName("index")
    @JsonProperty("index")
    private Long index;

    @SerializedName("message")
    @JsonProperty("message")
    private ChatMessage message;

    @SerializedName("delta")
    @JsonProperty("delta")
    private Delta delta;


    public Choice(String finishReason, Long index, Delta delta) {
        this.finishReason =finishReason;
        this.index = index;
        this.delta =delta;
    }
}