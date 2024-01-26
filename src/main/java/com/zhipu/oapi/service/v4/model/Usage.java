package com.zhipu.oapi.service.v4.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class Usage {
    @SerializedName("prompt_tokens")
    @JsonProperty("prompt_tokens")
    private int promptTokens;
    @SerializedName("completion_tokens")
    @JsonProperty("completion_tokens")
    private int completionTokens;
    @SerializedName("total_tokens")
    @JsonProperty("total_tokens")
    private int totalTokens;
//    @SerializedName("prompt_chars")
//    private int promptChars;
//    @SerializedName("completion_chars")
//    private int completionChars;
//    @SerializedName("total_chars")
//    private int totalChars;

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