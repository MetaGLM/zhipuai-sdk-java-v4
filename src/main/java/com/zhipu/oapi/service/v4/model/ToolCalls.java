package com.zhipu.oapi.service.v4.model;


import com.fasterxml.jackson.annotation.JsonProperty;
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


    @JsonProperty("function")
    private ChatFunctionCall function;

    /**
     * 命中函数的唯一标识符
     */
    @JsonProperty("id")
    private String id;


    /**
     * 模型调用工具的类型,目前仅支持functon
     */
    @JsonProperty("type")
    private String type;
}
