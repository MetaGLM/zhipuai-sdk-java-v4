package com.zhipu.oapi.service.v4.knowledge;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.model.ChatError;
import com.zhipu.oapi.service.v4.videos.VideoObject;
import lombok.Data;

@Data
public class KnowledgeResponse implements ClientResponse<KnowledgeInfo> {
    private int code;
    private String msg;
    private boolean success;

    private KnowledgeInfo data;

    private ChatError error;
}
