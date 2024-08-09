package com.zhipu.oapi.service.v4.knowledge.document;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;
import retrofit2.Response;

@Data
public class DocumentEditResponse implements ClientResponse<Response<Void>> {
    private int code;
    private String msg;
    private boolean success;

    private Response<Void> data;

    private ChatError error;
}
