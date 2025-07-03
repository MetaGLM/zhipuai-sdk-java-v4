package com.zhipu.oapi.service.v4.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usage {
    @JsonProperty("prompt_tokens")
    private int promptTokens;
    @JsonProperty("completion_tokens")
    private int completionTokens;
    @JsonProperty("total_tokens")
    private int totalTokens;
//    @SerializedName("prompt_chars")
//    private int promptChars;
//    @SerializedName("completion_chars")
//    private int completionChars;
//    @SerializedName("total_chars")
//    private int totalChars;

    @JsonProperty("total_calls")
    private int totalCalls;

    public int getPromptTokens() {
        return promptTokens;
    }

    public void setPromptTokens(int promptTokens) {
        this.promptTokens = promptTokens;
    }

    public int getCompletionTokens() {
        return completionTokens;
    }

    public void setCompletionTokens(int completionTokens) {
        this.completionTokens = completionTokens;
    }

    public int getTotalTokens() {
        return totalTokens;
    }

    public void setTotalTokens(int totalTokens) {
        this.totalTokens = totalTokens;
    }

//    public int getPromptChars() {
//        return promptChars;
//    }
//
//    public void setPromptChars(int promptChars) {
//        this.promptChars = promptChars;
//    }
//
//    public int getCompletionChars() {
//        return completionChars;
//    }
//
//    public void setCompletionChars(int completionChars) {
//        this.completionChars = completionChars;
//    }
//
//    public int getTotalChars() {
//        return totalChars;
//    }
//
//    public void setTotalChars(int totalChars) {
//        this.totalChars = totalChars;
//    }

}