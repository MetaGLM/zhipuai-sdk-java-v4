package com.zhipu.oapi.service.v4.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @SerializedName("created")
    private Long created;
    @SerializedName("model")
    private String model;



}
