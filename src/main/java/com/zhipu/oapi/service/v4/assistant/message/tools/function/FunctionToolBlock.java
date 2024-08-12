package com.zhipu.oapi.service.v4.assistant.message.tools.function;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.assistant.message.tools.ToolsType;
import com.zhipu.oapi.service.v4.deserialize.JsonTypeField;

/**
 * This class represents a block of function tool data.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeField("function")
public class FunctionToolBlock extends ToolsType {

    /**
     * The function tool object that contains the name, arguments, and outputs.
     */
    @JsonProperty("function")
    private FunctionTool function;

    /**
     * The type of tool being called, always "function".
     */
    @JsonProperty("type")
    private String type = "function";

    // Getters and Setters

    public FunctionTool getFunction() {
        return function;
    }

    public void setFunction(FunctionTool function) {
        this.function = function;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
