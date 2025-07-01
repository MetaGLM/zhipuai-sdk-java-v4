package com.zhipu.oapi.service.v4.web_search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebSearchResponse implements ClientResponse<WebSearchDTO> {
    private int code;
    private String msg;
    private boolean success;

    private WebSearchDTO data;
    private ChatError error;

}
