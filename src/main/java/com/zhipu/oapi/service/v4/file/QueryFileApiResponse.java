package com.zhipu.oapi.service.v4.file;

import lombok.Data;

@Data
public class QueryFileApiResponse {
    private int code;
    private String msg;
    private boolean success;

    private QueryFileResult data;

}
