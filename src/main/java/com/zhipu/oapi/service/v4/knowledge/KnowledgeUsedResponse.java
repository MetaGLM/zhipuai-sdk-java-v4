package com.zhipu.oapi.service.v4.knowledge;

import lombok.Data;

@Data
public class KnowledgeUsedResponse {
    private int code;
    private String msg;
    private boolean success;

    private KnowledgeUsed data;
}
