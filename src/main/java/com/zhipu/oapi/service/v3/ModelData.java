package com.zhipu.oapi.service.v3;

import com.google.gson.annotations.SerializedName;
import com.zhipu.oapi.service.TaskStatus;

import java.util.List;

public final class ModelData {
    @SerializedName("choices")
    private List<Choice> choices;
    @SerializedName("usage")
    private Usage usage;
    @SerializedName("task_id")
    private String taskId;
    @SerializedName("request_id")
    private String requestId;
    @SerializedName("task_status")
    private TaskStatus taskStatus;

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

}
