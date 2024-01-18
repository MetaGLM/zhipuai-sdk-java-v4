package com.zhipu.oapi.service.v4.model;

import lombok.Data;

/**
 * Object containing a response from the chat completions api.
 */
@Data
public class ChatCompletionAsyncResult {

    /**
     * 智谱 AI 开放平台生成的任务订单号，调用请求结果接口时请使用此订单号
     */
    String id;

    /**
     * 模型编码
     */
    String model;

    /**
     * 用户在客户端请求时提交的任务编号或者平台生成的任务编号
     */
    String request_id;
    
    /**
     * 处理状态，PROCESSING（处理中），SUCCESS（成功），FAIL（失败）。需通过查询获取结果
     */
    String task_status;



}
