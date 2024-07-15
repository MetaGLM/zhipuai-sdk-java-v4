package com.zhipu.oapi.service.v4.tools;

import com.zhipu.oapi.service.v4.model.ModelData;
import io.reactivex.Flowable;
import lombok.Data;

@Data
public class WebSearchApiResponse {
    private int code;
    private String msg;
    private boolean success;

    private WebSearchPro data;

    private Flowable<WebSearchPro> flowable;

}
