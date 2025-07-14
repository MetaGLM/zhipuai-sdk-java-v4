package com.zhipu.oapi.service.v4.voice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddVoiceResponse {
    private int code;
    private String msg;
    private boolean success;
    private VoiceItem data;

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public VoiceItem getData() { return data; }
    public void setData(VoiceItem data) { this.data = data; }
} 