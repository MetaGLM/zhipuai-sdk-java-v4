package com.zhipu.oapi.service.v4.assistant.conversation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * This class represents the usage data for a specific conversation.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationUsage {

    /**
     * The conversation ID.
     */
    @JsonProperty("id")
    private String id;

    /**
     * The Assistant ID.
     */
    @JsonProperty("assistant_id")
    private String assistantId;

    /**
     * The creation time of the conversation.
     */
    @JsonProperty("create_time")
    private String createTime;

    /**
     * The last update time of the conversation.
     */
    @JsonProperty("update_time")
    private String updateTime;

    /**
     * The usage statistics for the conversation.
     */
    @JsonProperty("usage")
    private Usage usage;

}
