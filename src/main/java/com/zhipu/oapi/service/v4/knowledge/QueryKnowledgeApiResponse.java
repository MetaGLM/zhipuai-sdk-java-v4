package com.zhipu.oapi.service.v4.knowledge;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.file.QueryFileResult;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

@Data
public class QueryKnowledgeApiResponse implements ClientResponse<KnowledgePage> {
    private int code;
    private String msg;
    private boolean success;

    private KnowledgePage data;
    private ChatError error;

}
