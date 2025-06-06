package com.zhipu.oapi.service.v4.web_search;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchIntentResp {

    private String query;

    private String intent;

    private String keywords;

}
