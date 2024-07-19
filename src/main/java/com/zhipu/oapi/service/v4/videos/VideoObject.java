package com.zhipu.oapi.service.v4.videos;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import lombok.Getter;

import java.util.Iterator;
import java.util.List;

/**
 * This class represents the video object containing details about the video generation task.
 */

@Getter
public class VideoObject extends ObjectNode {

    /**
     * 智谱 AI 开放平台生成的任务订单号，调用请求结果接口时请使用此订单号
     */
    @JsonProperty("id")
    private String id;

    /**
     * 模型名称
     */
    @JsonProperty("model")
    private String model;

    /**
     * 视频生成结果
     */
    @JsonProperty("video_result")
    private List<VideoResult> videoResult;

    /**
     * 处理状态，PROCESSING（处理中），SUCCESS（成功），FAIL（失败）
     * 注：处理中状态需通过查询获取结果
     */
    @JsonProperty("task_status")
    private String taskStatus;

    /**
     * 用户在客户端请求时提交的任务编号或者平台生成的任务编号
     */
    @JsonProperty("request_id")
    private String requestId;

    public VideoObject() {
        super(JsonNodeFactory.instance);
    }

    public VideoObject(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("video_result") != null) {
            List<VideoResult> videoResults = objectMapper.convertValue(objectNode.get("video_result"), new TypeReference<List<VideoResult>>() {});
            this.setVideoResult(videoResults);
        } else {
            this.setVideoResult(null);
        }
        if (objectNode.get("id") != null) {
            this.setId(objectNode.get("id").asText());
        } else {
            this.setId(null);
        }
        if (objectNode.get("model") != null) {
            this.setModel(objectNode.get("model").asText());
        } else {
            this.setModel(null);
        }
        if (objectNode.get("task_status") != null) {
            this.setTaskStatus(objectNode.get("task_status").asText());
        } else {
            this.setTaskStatus(null);
        }
        if (objectNode.get("request_id") != null) {
            this.setRequestId(objectNode.get("request_id").asText());
        } else {
            this.setRequestId(null);
        }

        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }

    // Getters and Setters


    public void setId(String id) {
        this.id = id;
        this.put("id", id);
    }


    public void setModel(String model) {
        this.model = model;
        this.put("model", model);
    }


    public void setVideoResult(List<VideoResult> videoResult) {
        this.videoResult = videoResult;
        ArrayNode jsonNodes = this.putArray("video_result");
        if (videoResult == null) {
            jsonNodes.removeAll();
        }
        else {

            for (VideoResult video : videoResult) {
                jsonNodes.add(video);
            }
        }
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
        this.put("task_status", taskStatus);
    }


    public void setRequestId(String requestId) {
        this.requestId = requestId;
        this.put("request_id", requestId);
    }
}
