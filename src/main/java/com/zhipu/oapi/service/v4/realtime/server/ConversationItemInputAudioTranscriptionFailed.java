package com.zhipu.oapi.service.v4.realtime.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.realtime.RealtimeServerEvent;
import com.zhipu.oapi.service.v4.realtime.object.ErrorObj;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ConversationItemInputAudioTranscriptionFailed extends RealtimeServerEvent {
    @JsonProperty("item_id")
    private String itemId;

    @JsonProperty("content_index")
    private int contentIndex;

    @JsonProperty("error")
    private ErrorObj error;

    public ConversationItemInputAudioTranscriptionFailed() {
        super.setType("conversation.item.input_audio_transcription.failed");
        this.itemId = "";
        this.contentIndex = 0;
        this.error = new ErrorObj();
    }
}
