package com.zhipu.oapi.service.v4.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolCalls {



    private ChatFunctionCall function;

    /**
     * 命中函数的唯一标识符
     */
    private String id;


    /**
     * 模型调用工具的类型,目前仅支持functon
     */
    private String type;
}
