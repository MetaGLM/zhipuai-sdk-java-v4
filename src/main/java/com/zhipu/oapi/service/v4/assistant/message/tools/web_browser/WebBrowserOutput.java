package com.zhipu.oapi.service.v4.assistant.message.tools.web_browser;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the output of a web browser search result.
 */
public class WebBrowserOutput {

    /**
     * The title of the search result.
     */
    @JsonProperty("title")
    private String title;

    /**
     * The URL link to the search result's webpage.
     */
    @JsonProperty("link")
    private String link;

    /**
     * The textual content extracted from the search result.
     */
    @JsonProperty("content")
    private String content;

    /**
     * Any error message encountered during the search or retrieval process.
     */
    @JsonProperty("error_msg")
    private String errorMsg;

    // Getters and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
