package com.zhipu.oapi.service.v4.web_search;


import lombok.Data;

@Data
public class SearchIntentResp {

    private String query;

    private String intent;

    private String keywords;

}
