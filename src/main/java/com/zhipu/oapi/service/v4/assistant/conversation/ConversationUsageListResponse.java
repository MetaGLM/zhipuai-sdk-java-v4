package com.zhipu.oapi.service.v4.assistant.conversation;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.assistant.query_support.AssistantSupportStatus;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

@Data
public class ConversationUsageListResponse implements ClientResponse<ConversationUsageListStatus> {
    private int code;
    private String msg;
    private boolean success;

    private ConversationUsageListStatus data;

    private ChatError error;

    public ConversationUsageListResponse() {
    }

    public ConversationUsageListResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
