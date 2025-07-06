package com.zhipu.oapi.service.v4.realtime.server;

import com.zhipu.oapi.service.v4.realtime.RealtimeServerEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class RealtimeHeartBeat extends RealtimeServerEvent {
    public RealtimeHeartBeat() {
        super.setType("heartbeat");
    }
}
