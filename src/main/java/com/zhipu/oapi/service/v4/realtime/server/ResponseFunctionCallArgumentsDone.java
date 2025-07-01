package com.zhipu.oapi.service.v4.realtime.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.realtime.RealtimeServerEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ResponseFunctionCallArgumentsDone extends RealtimeServerEvent {
    @JsonProperty("response_id")
    private String responseId;

    @JsonProperty("item_id")
    private String itemId;

    @JsonProperty("output_index")
    private int outputIndex;

    @JsonProperty("call_id")
    private String callId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("arguments")
    private String arguments;

    public ResponseFunctionCallArgumentsDone() {
        super.setType("response.function_call_arguments.done");
        this.responseId = "";
        this.itemId = "";
        this.outputIndex = 0;
        this.callId = "";
        this.name = "";
        this.arguments = "";
    }
}
