package com.zhipu.oapi.service.v4.audio;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.core.model.FlowableClientResponse;
import com.zhipu.oapi.service.v4.model.ChatError;

import io.reactivex.Flowable;

import lombok.Data;

@Data
public class AudioSpeechStreamingApiResponse implements FlowableClientResponse<ObjectNode> {

    private int code;

    private String msg;

    private boolean success;

    private ObjectNode data;

    private ChatError error;

    private Flowable<ObjectNode> flowable;
}
