package com.zhipu.oapi.service.v4.file;

import com.zhipu.oapi.service.v4.embedding.EmbeddingResult;

public class FileApiResponse {
    private int code;
    private String msg;
    private boolean success;

    private File data;

    public FileApiResponse() {
    }

    public FileApiResponse(int code, String msg) {
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

    public File getData() {
        return data;
    }

    public void setData(File data) {
        this.data = data;
    }
}
