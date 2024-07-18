package com.zhipu.oapi.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ClientRequest<T> {
    @JsonIgnore
    public T getOptions();

}
