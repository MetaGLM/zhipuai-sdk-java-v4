package com.zhipu.oapi.service.v4.assistant.query_support;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.core.model.FlowableClientResponse;
import com.zhipu.oapi.service.v4.assistant.AssistantCompletion;
import com.zhipu.oapi.service.v4.model.ChatError;
import io.reactivex.Flowable;
import lombok.Data;

@Data
public class AssistantSupportResponse implements ClientResponse<AssistantSupportStatus> {
    private int code;
    private String msg;
    private boolean success;

    private AssistantSupportStatus data;

    private ChatError error;

    public AssistantSupportResponse() {
    }

    public AssistantSupportResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
