package com.zhipu.oapi.service.v4.fine_turning;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // 忽略未知的属性
public class FineTuningEventData{
    /**
     * The type of object returned, should be "fine-tuneing.job.event".
     */
    private String object;

    /**
     * The ID of the fine-tuning event.
     */
    private String id;

    /**
     * The creation time in epoch seconds.
     */
    @JsonProperty("created_at")
    private Long createdAt;

    /**
     * The log level of this message.
     */
    private String level;

    /**
     * The event message.
     */
    private String message;

    /**
     * The type of event, i.e. "message"
     */
    private String type;
    /**
     * The data of the event.
     */
    private FineTuningEventMetric data;
}