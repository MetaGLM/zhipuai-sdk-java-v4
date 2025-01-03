package com.zhipu.oapi.utils;

import io.reactivex.Single;

import java.io.IOException;

// 函数式接口，返回 Single<Data>
@FunctionalInterface
public interface RequestSupplier<Param, Data> {
   Single<Data> get(Param params);
}