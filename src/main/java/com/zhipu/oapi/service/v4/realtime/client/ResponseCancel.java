package com.zhipu.oapi.service.v4.realtime.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.realtime.RealtimeClientEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ResponseCancel extends RealtimeClientEvent {
    @JsonProperty("response_id")
    private String responseId;

    public ResponseCancel() {
        super();
        this.setType("response.cancel");
        this.responseId = "";
    }
}
