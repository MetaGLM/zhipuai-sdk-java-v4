package com.zhipu.oapi.service.v4.web_search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebSearchDTO {

    /**
     * 创建时间
     */
    @JsonProperty("created")
    private Integer created;

    /**
     * ID
     */
    @JsonProperty("id")
    private String id;

    /**
     * 请求ID
     */
    @JsonProperty("request_id")
    private String requestId;

    @JsonProperty("search_result")
    private List<WebSearchResp> webSearchResp;


    @JsonProperty("search_intent")
    private List<SearchIntentResp> searchIntentResp;

}
