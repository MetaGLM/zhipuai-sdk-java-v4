package com.zhipu.oapi.service.v4.realtime.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.realtime.RealtimeServerEvent;
import com.zhipu.oapi.service.v4.realtime.object.ResponseObj;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ResponseDone extends RealtimeServerEvent {
    @JsonProperty("response")
    private ResponseObj response;

    public ResponseDone() {
        super.setType("response.done");
        this.response = new ResponseObj();
    }
}
