package com.zhipu.oapi.service.v4.realtime.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.realtime.RealtimeServerEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class InputAudioBufferSpeechStarted extends RealtimeServerEvent {
    @JsonProperty("audio_start_ms")
    private int audioStartMs;

    @JsonProperty("item_id")
    private String itemId;

    public InputAudioBufferSpeechStarted() {
        super.setType("input_audio_buffer.speech_started");
        this.audioStartMs = 0;
        this.itemId = "";
    }
}
