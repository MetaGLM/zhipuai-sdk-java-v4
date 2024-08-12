package com.zhipu.oapi.service.v4.assistant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.core.model.ClientRequest;
import com.zhipu.oapi.service.v4.CommonRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents the parameters for an assistant, including optional fields.
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssistantParameters  extends CommonRequest implements ClientRequest<Map<String, Object>> {
    /**
     * The ID of the assistant.
     */
    @JsonProperty("assistant_id")
    private String assistantId;

    /**
     * The conversation ID. If not provided, a new conversation is created.
     */
    @JsonProperty("conversation_id")
    private String conversationId;

    /**
     * The name of the model, default is 'GLM-4-Assistant'.
     */
    @JsonProperty("model")
    private String model;

    /**
     * Whether to support streaming SSE, should be set to True.
     */
    @JsonProperty("stream")
    private boolean stream;

    /**
     * The list of conversation messages.
     */
    @JsonProperty("messages")
    private List<ConversationMessage> messages;

    /**
     * The list of file attachments for the conversation, optional.
     */
    @JsonProperty("attachments")
    private List<AssistantAttachments> attachments;

    /**
     * Metadata or additional fields, optional.
     */
    @JsonProperty("metadata")
    private Map<String, Object> metadata;

    @Override
    public Map<String, Object> getOptions() {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("request_id", this.getRequestId());
        paramsMap.put("user_id", this.getUserId());
        paramsMap.put("assistant_id", this.getAssistantId());
        paramsMap.put("conversation_id", this.getConversationId());
        paramsMap.put("model", this.getModel());
        paramsMap.put("stream", this.isStream());
        paramsMap.put("messages", this.getMessages());
        paramsMap.put("attachments", this.getAttachments());
        paramsMap.put("metadata", this.getMetadata());
        return paramsMap;
    }
}
