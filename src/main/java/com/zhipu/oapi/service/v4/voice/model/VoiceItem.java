package com.zhipu.oapi.service.v4.voice.model;

public class VoiceItem {
    private int id;
    private String voiceId;
    private String voiceName;
    private String voiceType;
    private String voiceStatus;
    private String voiceText;
    private String createTime;
    private String updateTime;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getVoiceId() { return voiceId; }
    public void setVoiceId(String voiceId) { this.voiceId = voiceId; }
    public String getVoiceName() { return voiceName; }
    public void setVoiceName(String voiceName) { this.voiceName = voiceName; }
    public String getVoiceType() { return voiceType; }
    public void setVoiceType(String voiceType) { this.voiceType = voiceType; }
    public String getVoiceStatus() { return voiceStatus; }
    public void setVoiceStatus(String voiceStatus) { this.voiceStatus = voiceStatus; }
    public String getVoiceText() { return voiceText; }
    public void setVoiceText(String voiceText) { this.voiceText = voiceText; }
    public String getCreateTime() { return createTime; }
    public void setCreateTime(String createTime) { this.createTime = createTime; }
    public String getUpdateTime() { return updateTime; }
    public void setUpdateTime(String updateTime) { this.updateTime = updateTime; }
} 