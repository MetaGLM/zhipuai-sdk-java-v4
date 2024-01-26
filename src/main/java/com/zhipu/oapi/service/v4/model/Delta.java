package com.zhipu.oapi.service.v4.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Delta {

    private String role;

    private String content;


    @JsonProperty("tool_calls")
    @SerializedName("tool_calls")
    private List<ToolCalls> tool_calls;

}
