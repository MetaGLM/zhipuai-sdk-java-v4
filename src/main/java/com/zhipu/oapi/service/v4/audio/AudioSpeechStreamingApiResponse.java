package com.zhipu.oapi.service.v4.audio;

import com.zhipu.oapi.core.model.FlowableClientResponse;
import com.zhipu.oapi.service.v4.model.ChatError;

import io.reactivex.Flowable;

import lombok.Data;

@Data
public class AudioSpeechStreamingApiResponse implements FlowableClientResponse<AudioSpeechPro> {

    private int code;

    private String msg;

    private boolean success;

    private AudioSpeechPro data;

    private ChatError error;

    private Flowable<AudioSpeechPro> flowable;
}
