package com.zhipu.oapi.service.v4.web_search;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.core.model.FlowableClientResponse;
import com.zhipu.oapi.service.v4.file.QueryFileResult;
import com.zhipu.oapi.service.v4.model.ChatError;
import com.zhipu.oapi.service.v4.tools.WebSearchPro;
import io.reactivex.Flowable;
import lombok.Data;

@Data
public class WebSearchResponse implements ClientResponse<WebSearchDTO> {
    private int code;
    private String msg;
    private boolean success;

    private WebSearchDTO data;
    private ChatError error;

}
