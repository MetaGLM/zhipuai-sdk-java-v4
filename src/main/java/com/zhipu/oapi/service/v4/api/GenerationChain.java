package com.zhipu.oapi.service.v4.api;

import com.zhipu.oapi.core.model.ClientResponse;

import com.zhipu.oapi.ClientV4;

public abstract  class GenerationChain<Data, TResp extends ClientResponse<Data>>{
 

    abstract TResp apply(final ClientV4 clientV4);
}