package com.zhipu.oapi.service.v4.model;

import com.zhipu.oapi.core.model.ClientResponse;
import lombok.Data;

@Data
public class QueryModelResultResponse  implements ClientResponse<ModelData> {

    private int code;
    private String msg;
    private boolean success;
    private ModelData data;

    private ChatError error;

}
