package com.zhipu.oapi.service.v4.web_search;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
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

    @JsonProperty("web_search")
    private List<WebSearchResp> webSearchResp;

}
