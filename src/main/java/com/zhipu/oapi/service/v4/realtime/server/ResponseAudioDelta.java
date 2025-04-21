package com.zhipu.oapi.service.v4.realtime.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.realtime.RealtimeServerEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ResponseAudioDelta extends RealtimeServerEvent {
    @JsonProperty("response_id")
    private String responseId;

    @JsonProperty("item_id")
    private String itemId;

    @JsonProperty("output_index")
    private Integer outputIndex;

    @JsonProperty("content_index")
    private Integer contentIndex;

    @JsonProperty("delta")
    private String delta;

    public ResponseAudioDelta() {
        super();
        this.setType("response.audio.delta");
        // 初始化所有字段为默认值
        this.responseId = "";
        this.itemId = "";
        this.outputIndex = 0;
        this.contentIndex = 0;
        this.delta = "";
    }
}
