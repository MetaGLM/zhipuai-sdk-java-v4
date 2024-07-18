package com.zhipu.oapi.utils;

import io.reactivex.Flowable;

// 函数式接口，返回 Flowable<Data>
@FunctionalInterface
public interface FlowableRequestSupplier<Param, Data> {
   Data get(Param params);
}