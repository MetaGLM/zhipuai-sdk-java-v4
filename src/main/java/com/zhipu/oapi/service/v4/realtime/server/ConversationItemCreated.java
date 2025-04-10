package com.zhipu.oapi.service.v4.realtime.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.realtime.RealtimeServerEvent;
import com.zhipu.oapi.service.v4.realtime.object.ItemObj;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ConversationItemCreated extends RealtimeServerEvent {
    @JsonProperty("previous_item_id")
    private String previousItemId;

    @JsonProperty("item")
    private ItemObj item;

    public ConversationItemCreated() {
        super.setType("conversation.item.created");
        this.previousItemId = "";
        this.item = new ItemObj();
    }
}
