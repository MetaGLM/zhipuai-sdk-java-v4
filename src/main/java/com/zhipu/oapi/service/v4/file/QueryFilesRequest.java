package com.zhipu.oapi.service.v4.file;


import lombok.Data;

@Data
public class QueryFilesRequest {


    private String purpose;

    private Integer limit;

    private String after;

    private String order;


}
