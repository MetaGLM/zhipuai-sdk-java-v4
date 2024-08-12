package com.zhipu.oapi.service.v4.assistant.message.tools.web_browser;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * This class represents the input and outputs of a web browser search.
 */
public class WebBrowser {

    /**
     * The input query for the web browser search.
     */
    @JsonProperty("input")
    private String input;

    /**
     * A list of search results returned by the web browser.
     */
    @JsonProperty("outputs")
    private List<WebBrowserOutput> outputs;

    // Getters and Setters

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public List<WebBrowserOutput> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<WebBrowserOutput> outputs) {
        this.outputs = outputs;
    }
}
