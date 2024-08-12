package com.zhipu.oapi.service.v4.assistant.conversation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * This class represents a list of conversation usage data.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationUsageList {

    /**
     * The Assistant ID.
     */
    @JsonProperty("assistant_id")
    private String assistantId;

    /**
     * Whether there are more pages of results.
     */
    @JsonProperty("has_more")
    private boolean hasMore;

    /**
     * The list of conversation usage data.
     */
    @JsonProperty("conversation_list")
    private List<ConversationUsage> conversationList;

}
