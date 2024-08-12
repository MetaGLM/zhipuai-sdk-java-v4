package com.zhipu.oapi.service.v4.assistant.conversation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.core.model.ClientRequest;
import com.zhipu.oapi.service.v4.CommonRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * This class represents the parameters for a conversation, including pagination.
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConversationParameters extends CommonRequest implements ClientRequest<ConversationParameters> {

    /**
     * The Assistant ID.
     */
    @JsonProperty("assistant_id")
    private String assistantId;

    /**
     * The current page number for pagination.
     */
    @JsonProperty("page")
    private int page;

    /**
     * The number of items per page for pagination.
     */
    @JsonProperty("page_size")
    private int pageSize;

    @Override
    public ConversationParameters getOptions() {
        return this;
    }
}
