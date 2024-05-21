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
public class FineTuningEventMetric {
    @JsonProperty("epoch")
    private String epoch;
    @JsonProperty("current_steps")
    private Integer currentSteps;
    @JsonProperty("total_steps")
    private Integer totalSteps;
    @JsonProperty("elapsed_time")
    private String elapsedTime;
    @JsonProperty("remaining_time")
    private String remainingTime;
    @JsonProperty("trained_tokens")
    private Integer trainedTokens;
    @JsonProperty("loss")
    private Float loss;
    @JsonProperty("eval_loss")
    private Float evalLoss;
    @JsonProperty("acc")
    private Float acc;
    @JsonProperty("eval_acc")
    private Float evalAcc;
    @JsonProperty("learning_rate")
    private Float learningRate;


}