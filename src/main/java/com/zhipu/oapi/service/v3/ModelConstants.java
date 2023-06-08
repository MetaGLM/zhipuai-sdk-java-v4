package com.zhipu.oapi.service.v3;

public class ModelConstants {

    // assistant role，模型生成的内容，role位assistant
    public static final String roleAssistant = "assistant";
    // user role, 用户输入的内容，role位user
    public static final String roleUser = "user";

    // sseformat, 用于兼容解决sse增量模式okhttpsse截取data:后面空格问题, [data: hello]。
    public static final String sseFormat = "data";
}
