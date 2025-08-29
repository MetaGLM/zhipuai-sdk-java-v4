package com.zhipu.oapi.service.v4.audio;

import com.zhipu.oapi.core.model.ClientRequest;
import com.zhipu.oapi.service.v4.CommonRequest;
import com.zhipu.oapi.service.v4.model.SensitiveWordCheckRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AudioSpeechRequest extends CommonRequest implements ClientRequest<Map<String, Object>> {

    /**
     * 所要调用的模型编码
     */
    private String model;

    /**
     * 需要生成语音的文本
     */
    private String input;

    /** 是否流式返回 */
    private boolean stream;

    /**
     * 需要生成语音的音色
     */
    private String voice;

    /** 语速 */
    private Float speed;

    /** 音量 */
    private Float volume;

    /**
     * 需要生成语音文件的格式
     */
    private String responseFormat;

    /**
     * 需要流式返回的音频编码格式
     */
    private String encodeFormat;

    /**
     * 敏感词检测控制
     */
    private SensitiveWordCheckRequest sensitiveWordCheck;

    @Override
    public Map<String, Object> getOptions() {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("request_id", this.getRequestId());
        paramsMap.put("user_id", this.getUserId());
        paramsMap.put("model", this.getModel());
        paramsMap.put("input", this.getInput());
        paramsMap.put("stream", this.isStream());
        paramsMap.put("voice", this.getVoice());
        paramsMap.put("speed", this.getSpeed());
        paramsMap.put("volume", this.getVolume());
        paramsMap.put("response_format", this.getResponseFormat());
        paramsMap.put("encode_format", this.getEncodeFormat());
        paramsMap.put("sensitive_word_check", this.getSensitiveWordCheck());
        if(this.getExtraJson() !=null){
            paramsMap.putAll(this.getExtraJson());
        }
        return paramsMap;
    }
}
