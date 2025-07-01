package com.zhipu.oapi.service.v4.realtime.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.realtime.RealtimeClientEvent;
import com.zhipu.oapi.service.v4.realtime.object.ResponseObj;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ResponseCreate extends RealtimeClientEvent {
    @JsonProperty("response")
    private ResponseObj response;

    public ResponseCreate() {
        super();
        this.setType("response.create");
        this.response = new ResponseObj();
    }
}
