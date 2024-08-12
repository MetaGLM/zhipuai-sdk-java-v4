package com.zhipu.oapi.service.v4.assistant.message.tools.retrieval;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * This class represents the outputs of a retrieval tool.
 */
public class RetrievalTool {

    /**
     * A list of text snippets and their respective document names retrieved from the knowledge base.
     */
    @JsonProperty("outputs")
    private List<RetrievalToolOutput> outputs;

    // Getters and Setters

    public List<RetrievalToolOutput> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<RetrievalToolOutput> outputs) {
        this.outputs = outputs;
    }
}
