package com.zhipu.oapi.service.v4.assistant.message.tools.web_browser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.assistant.message.tools.ToolsType;
import com.zhipu.oapi.service.v4.deserialize.JsonTypeField;

/**
 * This class represents a block for invoking the web browser tool.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeField("web_browser")
public class WebBrowserToolBlock extends ToolsType {

    /**
     * An instance of the WebBrowser class containing the search input and outputs.
     */
    @JsonProperty("web_browser")
    private WebBrowser webBrowser;

    /**
     * The type of tool being used, always set to "web_browser".
     */
    @JsonProperty("type")
    private String type = "web_browser";

    // Getters and Setters

    public WebBrowser getWebBrowser() {
        return webBrowser;
    }

    public void setWebBrowser(WebBrowser webBrowser) {
        this.webBrowser = webBrowser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
