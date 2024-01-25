package com.zhipu.oapi.service.v4.fine_turning;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

import java.util.List;

/**
 * Fine-tuning job
 */
@Data
public class PersonalFineTuningJob {

    String object;

    private List<FineTuningJob> data;

    private ChatError error;
}