package com.zhipu.oapi.service.v4.realtime.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.realtime.RealtimeClientEvent;
import com.zhipu.oapi.service.v4.realtime.object.ItemObj;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ConversationItemCreate extends RealtimeClientEvent {
    @JsonProperty("previous_item_id")
    private String previousItemId;

    @JsonProperty("item")
    private ItemObj item;

    public ConversationItemCreate() {
        super();
        super.setType("conversation.item.create");
        this.previousItemId = "";
        this.item = new ItemObj();
    }
}
