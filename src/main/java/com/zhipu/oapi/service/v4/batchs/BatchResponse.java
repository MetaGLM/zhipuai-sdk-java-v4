package com.zhipu.oapi.service.v4.batchs;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

@Data
public class BatchResponse implements ClientResponse<Batch> {
    private int code;
    private String msg;
    private boolean success;

    private Batch data;

    private ChatError error;
}
