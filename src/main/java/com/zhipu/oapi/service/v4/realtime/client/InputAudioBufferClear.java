package com.zhipu.oapi.service.v4.realtime.client;

import com.zhipu.oapi.service.v4.realtime.RealtimeClientEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class InputAudioBufferClear extends RealtimeClientEvent {

    public InputAudioBufferClear() {
        super();
        this.setType("input_audio_buffer.clear");
    }
}
