package com.zhipu.oapi.service.v4.voice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TtsVoiceListResponse {
    private int code;
    private String msg;
    private boolean success;
    private List<VoiceItem> data;

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public List<VoiceItem> getData() { return data; }
    public void setData(List<VoiceItem> data) { this.data = data; }
} 