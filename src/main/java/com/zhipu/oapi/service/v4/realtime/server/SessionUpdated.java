package com.zhipu.oapi.service.v4.realtime.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.realtime.RealtimeServerEvent;
import com.zhipu.oapi.service.v4.realtime.object.SessionObj;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SessionUpdated extends RealtimeServerEvent {
    @JsonProperty("session")
    private SessionObj session;

    public SessionUpdated() {
        super.setType("session.updated");
        this.session = new SessionObj();
    }
}
