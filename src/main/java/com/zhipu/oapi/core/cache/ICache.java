package com.zhipu.oapi.core.cache;

import java.util.concurrent.TimeUnit;

/**
 * token缓存接口，默认提供LocalCache实现，可根据需求替换为分布式缓存（如redis）
 */
public interface ICache {

    // 获取缓存值
    String get(String key);

    // 设置缓存值
    void set(String key, String value, int expire, TimeUnit timeUnit);
}
