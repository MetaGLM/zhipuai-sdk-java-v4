package com.zhipu.oapi.service.v4.batchs;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

@Data
public class QueryBatchResponse implements ClientResponse<BatchPage> {
    private int code;
    private String msg;
    private boolean success;

    private BatchPage data;

    private ChatError error;
}
