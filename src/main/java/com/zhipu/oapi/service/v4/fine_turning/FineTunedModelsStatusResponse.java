package com.zhipu.oapi.service.v4.fine_turning;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.batchs.Batch;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

@Data
public class FineTunedModelsStatusResponse implements ClientResponse<FineTunedModelsStatus> {
    private int code;
    private String msg;
    private boolean success;

    private FineTunedModelsStatus data;
    private ChatError error;
}
