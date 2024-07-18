package com.zhipu.oapi.core.model;

import io.reactivex.Flowable;

public interface FlowableClientResponse<T> extends ClientResponse<T> {


    void setFlowable(Flowable<T> stream);
}
