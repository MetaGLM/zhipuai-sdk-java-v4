package com.zhipu.oapi.service.v4.assistant.message.tools.code_interpreter;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * This class represents a code interpreter that executes code and returns the results.
 */
public class CodeInterpreter {

    /**
     * The generated code snippet that is input to the code sandbox.
     */
    @JsonProperty("input")
    private String input;

    /**
     * The output results after the code execution.
     */
    @JsonProperty("outputs")
    private List<CodeInterpreterToolOutput> outputs;

    // Getters and Setters

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public List<CodeInterpreterToolOutput> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<CodeInterpreterToolOutput> outputs) {
        this.outputs = outputs;
    }
}
