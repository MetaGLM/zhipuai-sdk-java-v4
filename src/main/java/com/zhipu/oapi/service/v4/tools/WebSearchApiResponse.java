package com.zhipu.oapi.service.v4.tools;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.core.model.FlowableClientResponse;
import com.zhipu.oapi.service.v4.model.ChatError;
import com.zhipu.oapi.service.v4.model.ModelData;
import io.reactivex.Flowable;
import lombok.Data;

@Data
public class WebSearchApiResponse implements FlowableClientResponse<WebSearchPro> {
    private int code;
    private String msg;
    private boolean success;

    private WebSearchPro data;
    private ChatError error;

    private Flowable<WebSearchPro> flowable;

}
