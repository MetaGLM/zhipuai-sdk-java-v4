package com.zhipu.oapi.service.v4.fine_turning;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

import java.util.List;

/**
 * An object representing an event in the lifecycle of a fine-tuning job
 */
@Data
public class FineTuningEvent {

    private String object;

    private Boolean hasMore;

    private List<FineTuningEventData> data;

    private ChatError error;

    @Data
    public static class FineTuningEventData{
        /**
         * The type of object returned, should be "fine-tuneing.job.event".
         */
        String object;

        /**
         * The ID of the fine-tuning event.
         */
        String id;

        /**
         * The creation time in epoch seconds.
         */
        @JsonProperty("created_at")
        Long createdAt;

        /**
         * The log level of this message.
         */
        String level;

        /**
         * The event message.
         */
        String message;

        /**
         * The type of event, i.e. "message"
         */
        String type;
    }
}
