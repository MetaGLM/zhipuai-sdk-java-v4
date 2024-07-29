package com.zhipu.oapi.service.v4.knowledge;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

@Data
public class KnowledgeUsedResponse implements ClientResponse<KnowledgeUsed> {
    private int code;
    private String msg;
    private boolean success;

    private KnowledgeUsed data;
    private ChatError error;

}
