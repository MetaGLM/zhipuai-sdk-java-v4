package com.zhipu.oapi.service.v4.realtime.server;

import com.zhipu.oapi.service.v4.realtime.RealtimeServerEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class InputAudioBufferCleared extends RealtimeServerEvent {
    public InputAudioBufferCleared() {
        super.setType("input_audio_buffer.cleared");
    }
}
