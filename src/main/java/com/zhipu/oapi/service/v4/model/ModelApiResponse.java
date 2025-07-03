package com.zhipu.oapi.service.v4.model;

import com.zhipu.oapi.core.model.FlowableClientResponse;
import io.reactivex.Flowable;
import lombok.Data;

@Data
public class ModelApiResponse  implements FlowableClientResponse<ModelData> {
    private int code;
    private String msg;
    private boolean success;

    private ModelData data;

    private Flowable<ModelData> flowable;

    private ChatError error;

    public ModelApiResponse() {
    }

    public ModelApiResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
