package com.zhipu.oapi.function;

import com.fasterxml.jackson.databind.JsonNode;


public interface Functions {
    /**
     * 函数方法实现接口
     * @Param 入参
     */
    String invoke(JsonNode arguments);
}
