package com.zhipu.oapi.service.v4;

import com.zhipu.oapi.service.v4.ModelData;

public class QueryModelResultResponse {

    private int code;
    private String msg;
    private boolean success;
    private com.zhipu.oapi.service.v4.ModelData data;

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

    public com.zhipu.oapi.service.v4.ModelData getData() {
        return data;
    }

    public void setData(ModelData data) {
        this.data = data;
    }

}
