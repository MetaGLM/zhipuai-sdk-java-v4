package com.zhipu.oapi.service.v4.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public abstract class ChatFunctionMixIn {

    @JsonSerialize(using = ChatFunctionParametersSerializer.class)
    abstract Class<?> getParametersClass();

}
