package com.zhipu.oapi.service.v4.knowledge.document;

import lombok.Data;

@Data
public class DocumentResponse {
    private int code;
    private String msg;
    private boolean success;

    private DocumentObject data;
}
