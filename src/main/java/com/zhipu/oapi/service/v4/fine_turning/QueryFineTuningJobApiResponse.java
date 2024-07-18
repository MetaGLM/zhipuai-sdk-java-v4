package com.zhipu.oapi.service.v4.fine_turning;


import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

@Data
public class QueryFineTuningJobApiResponse implements ClientResponse<FineTuningJob> {
    private int code;
    private String msg;
    private boolean success;

    private FineTuningJob data;

    private ChatError error;
}
