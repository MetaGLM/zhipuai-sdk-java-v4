package com.zhipu.oapi.service.v4.realtime.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.realtime.RealtimeClientEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class InputVideoBufferAppend extends RealtimeClientEvent {
    @JsonProperty("video_frame")
    private String videoFrame;

    public InputVideoBufferAppend() {
        super();
        this.setType("input_audio_buffer.append_video_frame");
        this.videoFrame = "";
    }
}
