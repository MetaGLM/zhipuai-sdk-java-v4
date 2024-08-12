package com.zhipu.oapi.service.v4.assistant.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.deserialize.JsonTypeField;

/**
 * This class represents a block of text content in a conversation.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeField("content")
public class TextContentBlock extends MessageContent{

    /**
     * The content of the text block.
     */
    @JsonProperty("content")
    private String content;

    /**
     * The role of the speaker, default is "assistant".
     */
    @JsonProperty("role")
    private String role = "assistant";



    // Getters and Setters

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
