package com.zhipu.oapi.service.v4.audio;

import com.zhipu.oapi.core.model.ClientRequest;
import com.zhipu.oapi.service.v4.CommonRequest;
import com.zhipu.oapi.service.v4.model.SensitiveWordCheckRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AudioCustomizationRequest extends CommonRequest implements ClientRequest<Map<String, Object>> {


    /**
     * 需要生成音频的文本
     */
    private String input;

    /**
     * 所要调用的模型编码
     */
    private String model;

    /**
     * 需要克隆的原音频文本描述
     */
    private String voiceText;

    /**
     * 需要克隆的原音频文件
     */
    private File voiceData;

    /**
     * 音频返回格式
     */
    private String responseFormat;

    /**
     * 敏感词检测控制
     */
    private SensitiveWordCheckRequest sensitiveWordCheck;


    @Override
    public Map<String, Object> getOptions() {
        Map<String, Object> paramsMap = new HashMap<>();
        if(this.getRequestId() !=null){
            paramsMap.put("request_id", this.getRequestId());
        }
        if(this.getUserId() !=null){
            paramsMap.put("user_id", this.getUserId());
        }
        if(this.getModel() !=null){
            paramsMap.put("model", this.getModel());
        }
        if(this.getInput() !=null){
            paramsMap.put("input", this.getInput());
        }
        if(this.getVoiceText() !=null){
            paramsMap.put("voice_text", this.getVoiceText());

        }
        if(this.getVoiceData() !=null){
            paramsMap.put("voice_data", this.getVoiceData());
        }
        if(this.getResponseFormat() !=null){
            paramsMap.put("response_format", this.getResponseFormat());
        }
        if (this.getSensitiveWordCheck() != null) {
            paramsMap.put("sensitive_word_check", this.getSensitiveWordCheck());
        }
        if(this.getExtraJson() !=null){
            paramsMap.putAll(this.getExtraJson());
        }
        return paramsMap;
    }
}
