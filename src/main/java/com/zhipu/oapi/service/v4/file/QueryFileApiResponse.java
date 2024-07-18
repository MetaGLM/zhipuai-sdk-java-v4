package com.zhipu.oapi.service.v4.file;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.fine_turning.FineTuningJob;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

@Data
public class QueryFileApiResponse   implements ClientResponse<QueryFileResult> {
    private int code;
    private String msg;
    private boolean success;

    private QueryFileResult data;
    private ChatError error;

}
