package com.zhipu.oapi.service.v4.web_search;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebSearchResp {

    private String refer;

    private String title;

    private String link;

    private String media;

    private String content;

    private String icon;

    private String publish_date;
}
