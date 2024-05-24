package com.zhipu.oapi.service.v4.batchs;

import lombok.Data;

@Data
public class QueryBatchResponse {
    private int code;
    private String msg;
    private boolean success;

    private BatchPage data;
}
