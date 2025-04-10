package com.zhipu.oapi.service.v4.realtime.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ItemObj {
    @JsonProperty("id")
    private String id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("content")
    private ItemContent content;

    @JsonProperty("call_id")
    private String callId;

    @JsonProperty("arguments")
    private String arguments;

    public ItemObj() {
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    public static class ItemContent {
        @JsonProperty("text")
        private String text;

        @JsonProperty("audio")
        private String audio;

        @JsonProperty("transcript")
        private String transcript;

        public ItemContent() {
            this.text = "";
            this.audio = "";
            this.transcript = "";
        }
    }
}
