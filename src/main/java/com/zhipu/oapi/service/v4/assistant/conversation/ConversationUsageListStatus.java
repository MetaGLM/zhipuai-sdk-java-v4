package com.zhipu.oapi.service.v4.assistant.conversation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * This class represents the response containing a list of conversation usage data.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationUsageListStatus {

    /**
     * The response code.
     */
    @JsonProperty("code")
    private int code;

    /**
     * The response message.
     */
    @JsonProperty("msg")
    private String msg;

    /**
     * The data containing the conversation usage list.
     */
    @JsonProperty("data")
    private ConversationUsageList data;

}
