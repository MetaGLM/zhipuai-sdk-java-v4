package com.zhipu.oapi.service.v4.realtime.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.realtime.RealtimeServerEvent;
import com.zhipu.oapi.service.v4.realtime.object.ErrorObj;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class RealtimeError extends RealtimeServerEvent {
    @JsonProperty("item_id")
    private String itemId;

    @JsonProperty("content_index")
    private int contentIndex;

    @JsonProperty("error")
    private ErrorObj error;

    public RealtimeError() {
        super.setType("error");
        this.itemId = "";
        this.contentIndex = 0;
        this.error = new ErrorObj();
    }
}
