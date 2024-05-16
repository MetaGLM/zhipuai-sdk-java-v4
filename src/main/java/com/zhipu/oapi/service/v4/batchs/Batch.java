package com.zhipu.oapi.service.v4.batchs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class Batch {
    @JsonProperty("id")
    private String id;

    @JsonProperty("completion_window")
    private String completionWindow;

    @JsonProperty("created_at")
    private int createdAt;

    @JsonProperty("endpoint")
    private String endpoint;

    @JsonProperty("input_file_id")
    private String inputFileId;

    @JsonProperty("object")
    private String object;

    @JsonProperty("status")
    private String status;

    @JsonProperty("cancelled_at")
    private Integer cancelledAt;

    @JsonProperty("cancelling_at")
    private Integer cancellingAt;

    @JsonProperty("completed_at")
    private Integer completedAt;

    @JsonProperty("error_file_id")
    private String errorFileId;

    @JsonProperty("errors")
    private Errors errors;

    @JsonProperty("expired_at")
    private Integer expiredAt;

    @JsonProperty("expires_at")
    private Integer expiresAt;

    @JsonProperty("failed_at")
    private Integer failedAt;

    @JsonProperty("finalizing_at")
    private Integer finalizingAt;

    @JsonProperty("in_progress_at")
    private Integer inProgressAt;

    @JsonProperty("metadata")
    private Object metadata;

    @JsonProperty("output_file_id")
    private String outputFileId;

    @JsonProperty("request_counts")
    private BatchRequestCounts requestCounts;

    // Getters and setters for all fields here
}

@Data
class Errors {
    @JsonProperty("data")
    private List<BatchError> data;

    @JsonProperty("object")
    private String object;

}

@Data
class BatchRequestCounts {
    @JsonProperty("completed")
    private int completed;

    @JsonProperty("failed")
    private int failed;

    @JsonProperty("total")
    private int total;

}

@Data
class BatchError {
    @JsonProperty("code")
    private String code;

    @JsonProperty("line")
    private Integer line;

    @JsonProperty("message")
    private String message;

    @JsonProperty("param")
    private String param;

}