package com.zhipu.oapi.service.v4.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ToolCalls {


    @SerializedName("function")
    @JsonProperty("function")
    private ChatFunctionCall function;

    /**
     * 命中函数的唯一标识符
     */
    @SerializedName("id")
    @JsonProperty("id")
    private String id;


    /**
     * 模型调用工具的类型,目前仅支持functon
     */
    @SerializedName("type")
    @JsonProperty("type")
    private String type;
}
