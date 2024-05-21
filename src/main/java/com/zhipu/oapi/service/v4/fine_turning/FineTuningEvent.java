package com.zhipu.oapi.service.v4.fine_turning;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * An object representing an event in the lifecycle of a fine-tuning job
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // 忽略未知的属性
public class FineTuningEvent {

    private String object;

    @JsonProperty("has_more")
    private Boolean hasMore;

    private List<FineTuningEventData> data;

    private ChatError error;


}
