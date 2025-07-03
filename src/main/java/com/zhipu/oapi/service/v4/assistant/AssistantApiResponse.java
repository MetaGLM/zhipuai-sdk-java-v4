package com.zhipu.oapi.service.v4.assistant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhipu.oapi.core.model.FlowableClientResponse;
import com.zhipu.oapi.service.v4.model.ChatError;
import io.reactivex.Flowable;
import lombok.Data;

@Data
public class AssistantApiResponse implements FlowableClientResponse<AssistantCompletion> {
    private int code;
    private String msg;
    private boolean success;

    private AssistantCompletion data;

    @JsonIgnore
    private Flowable<AssistantCompletion> flowable;

    private ChatError error;

    public AssistantApiResponse() {
    }

    public AssistantApiResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
