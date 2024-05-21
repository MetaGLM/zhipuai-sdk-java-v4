package com.zhipu.oapi.service.v4.fine_turning;

import lombok.Data;

@Data
public class FineTunedModelsStatusResponse {
    private int code;
    private String msg;
    private boolean success;

    private FineTunedModelsStatus data;
}
