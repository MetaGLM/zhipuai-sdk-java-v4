package com.zhipu.oapi.service.v4.assistant.message.tools.drawing_tool;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents the output of a drawing tool, containing the generated image.
 */
public class DrawingToolOutput {

    /**
     * The generated image in a string format.
     */
    @JsonProperty("image")
    private String image;

    // Getters and Setters

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
