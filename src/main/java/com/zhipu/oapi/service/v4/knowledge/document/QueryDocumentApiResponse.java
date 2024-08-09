package com.zhipu.oapi.service.v4.knowledge.document;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.knowledge.KnowledgePage;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

@Data
public class QueryDocumentApiResponse implements ClientResponse<DocumentPage> {
    private int code;
    private String msg;
    private boolean success;

    private DocumentPage data;
    private ChatError error;

}
