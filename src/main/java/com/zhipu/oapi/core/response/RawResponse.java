package com.zhipu.oapi.core.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ModelData;
import io.reactivex.Flowable;

public class RawResponse {
    private int statusCode;

    private String msg;
    private boolean success;
    private String contentType;
    private Map<String, List<String>> headers = new HashMap<>();
    private String body;
    private Flowable<ModelData> flowable;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
        List<String> ct = new ArrayList<>();
        ct.add(contentType);
        this.headers.put(Constants.CONTENT_TYPE, ct);
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


    public Flowable<ModelData>  getFlowable() {
        return flowable;
    }

    public void setFlowable(Flowable<ModelData>  flowable) {
        this.flowable = flowable;
    }
}
