package com.zhipu.oapi.service.v4.knowledge.document;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;
import retrofit2.Response;

@Data
public class DocumentDataResponse implements ClientResponse<DocumentData> {
    private int code;
    private String msg;
    private boolean success;

    private DocumentData data;

    private ChatError error;
}
