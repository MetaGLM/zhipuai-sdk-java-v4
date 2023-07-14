package com.zhipu.oapi.service.v3;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * sse事件推送finish事件时，会推送一份meta信息，方便开发者对任务状态、任务id、计费信息等进行记录集后续使用
 * SseMeta定义了meta的信息结构
 */
public class SseMeta {

    @SerializedName("request_id")
    private String requestId;

    @SerializedName("task_id")
    private String taskId;

    @SerializedName("task_status")
    private String taskStatus;

    @SerializedName("usage")
    private Usage usage;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }
}