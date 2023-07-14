package com.zhipu.oapi.service.v3;

import com.google.gson.annotations.SerializedName;
import com.zhipu.oapi.service.TaskStatus;

import java.util.List;

public class ModelApiResponse {
    private int code;
    private String msg;
    private boolean success;

    private ModelData data;

    public ModelApiResponse() {
    }

    public ModelApiResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
        if (this.code == 200) {
            setSuccess(true);
        } else {
            setSuccess(false);
        }
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
