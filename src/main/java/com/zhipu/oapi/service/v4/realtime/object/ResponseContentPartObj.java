package com.zhipu.oapi.service.v4.realtime.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ResponseContentPartObj {
    @JsonProperty("audio")
    private String audio;

    @JsonProperty("text")
    private String text;

    @JsonProperty("transcript")
    private String transcript;

    @JsonProperty("type")
    private String type;

    public ResponseContentPartObj() {
        this.audio = "";
        this.text = "";
        this.transcript = "";
        this.type = "";
    }
}
