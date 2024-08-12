package com.zhipu.oapi.service.v4.assistant.message.tools.retrieval;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the output of a retrieval tool.
 */
public class RetrievalToolOutput {

    /**
     * The text snippet retrieved from the knowledge base.
     */
    @JsonProperty("text")
    private String text;

    /**
     * The name of the document from which the text snippet was retrieved, returned only in intelligent configuration.
     */
    @JsonProperty("document")
    private String document;

    // Getters and Setters

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}
