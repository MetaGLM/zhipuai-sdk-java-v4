package com.zhipu.oapi.service.v4.knowledge.document;

import com.zhipu.oapi.service.v4.knowledge.KnowledgeInfoPage;
import lombok.Data;

@Data
public class QueryDocumentResponse {
    private int code;
    private String msg;
    private boolean success;

    private DocumentDataPage data;
}
