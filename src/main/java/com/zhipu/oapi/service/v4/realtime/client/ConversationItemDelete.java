package com.zhipu.oapi.service.v4.realtime.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.realtime.RealtimeClientEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ConversationItemDelete extends RealtimeClientEvent {
    @JsonProperty("item_id")
    private String itemId;

    public ConversationItemDelete() {
        super();
        super.setType("conversation.item.delete");
        this.itemId = "";
    }
}
