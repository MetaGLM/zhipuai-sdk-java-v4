package com.zhipu.oapi.service.v3;

import com.zhipu.oapi.service.TaskStatus;
import com.zhipu.oapi.service.v2.QueryRequestTaskResultResponse;

public class QueryModelResultResponse {

    private int code;
    private String msg;
    private boolean success;
    private ModelData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ModelData getData() {
        return data;
    }

    public void setData(ModelData data) {
        this.data = data;
    }

}
