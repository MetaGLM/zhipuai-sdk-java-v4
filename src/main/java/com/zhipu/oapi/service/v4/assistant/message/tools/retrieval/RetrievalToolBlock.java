package com.zhipu.oapi.service.v4.assistant.message.tools.retrieval;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.deserialize.JsonTypeField;

/**
 * This class represents a block for invoking the retrieval tool.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeField("retrieval")
public class RetrievalToolBlock {

    /**
     * An instance of the RetrievalTool class containing the retrieval outputs.
     */
    @JsonProperty("retrieval")
    private RetrievalTool retrieval;

    /**
     * The type of tool being used, always set to "retrieval".
     */
    @JsonProperty("type")
    private String type = "retrieval";

    // Getters and Setters

    public RetrievalTool getRetrieval() {
        return retrieval;
    }

    public void setRetrieval(RetrievalTool retrieval) {
        this.retrieval = retrieval;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
