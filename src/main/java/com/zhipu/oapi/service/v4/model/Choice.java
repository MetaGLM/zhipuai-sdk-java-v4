package com.zhipu.oapi.service.v4.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Choice {

    @SerializedName("finish_reason")
    private String finish_reason;

    @SerializedName("index")
    private Long index;

    @SerializedName("message")
    private ChatMessage message;

    @SerializedName("delta")
    private Delta delta;


    public Choice(String finish_reason, Long index, Delta delta) {
        this.finish_reason =finish_reason;
        this.index = index;
        this.delta =delta;
    }
}