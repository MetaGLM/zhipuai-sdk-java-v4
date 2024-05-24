package com.zhipu.oapi.service.v4.knowledge;

import lombok.Data;

@Data
public class QueryKnowledgeResponse {
    private int code;
    private String msg;
    private boolean success;

    private KnowledgeInfoPage data;
}
