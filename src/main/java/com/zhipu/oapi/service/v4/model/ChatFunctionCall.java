package com.zhipu.oapi.service.v4.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatFunctionCall {

    /**
     * 模型生成的应调用函数的名称
     */
    String name;

    /**
     * 模型生成的 JSON 格式的函数调用参数。请注意，模型生成的 JSON 并不总是有效的，可能会出现函数模式未定义的参数。在调用函数之前，请在代码中验证参数
     */
    JsonNode arguments;



}
