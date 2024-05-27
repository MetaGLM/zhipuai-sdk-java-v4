package com.zhipu.oapi.service.v4.knowledge.document;

import com.zhipu.oapi.service.v4.knowledge.KnowledgeInfo;
import lombok.Data;

@Data
public class DocumentDataResponse {

    private int code;
    private String msg;
    private boolean success;
    private DocumentData data;
}
