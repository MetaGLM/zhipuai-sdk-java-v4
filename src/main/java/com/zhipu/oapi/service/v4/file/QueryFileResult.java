package com.zhipu.oapi.service.v4.file;

import lombok.Data;

import java.util.List;

@Data
public class QueryFileResult {

    private String object;

    private List<File> data;
}
