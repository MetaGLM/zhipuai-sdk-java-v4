package com.zhipu.oapi.service.v4.assistant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * This class represents a conversation message body.
 */
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConversationMessage {

    /**
     * The role of the user input, e.g., 'user'.
     */
    @JsonProperty("role")
    private String role;

    /**
     * The content of the conversation message.
     */
    @JsonProperty("content")
    private List<MessageTextContent> content;

}
