package com.zhipu.oapi.service.v4.assistant.message.tools.code_interpreter;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the output result of a code tool.
 */
public class CodeInterpreterToolOutput {

    /**
     * The type of output, currently only "logs".
     */
    @JsonProperty("type")
    private String type;

    /**
     * The log results from the code execution.
     */
    @JsonProperty("logs")
    private String logs;

    /**
     * Error message if any occurred during code execution.
     */
    @JsonProperty("error_msg")
    private String errorMsg;

    // Getters and Setters

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
