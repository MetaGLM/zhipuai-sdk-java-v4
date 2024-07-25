package com.zhipu.oapi.service.v4.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.image.ImageDeserializer;
import lombok.Data;
import lombok.Getter;

import java.util.Iterator;

/**
 * An object containing either a URL or a base 64 encoded image.
 */

@Getter
@JsonDeserialize(using = ImageDeserializer.class)
public class Image extends ObjectNode {
    /**
     * The URL where the image can be accessed.
     */
    @JsonProperty("url")
    String url;


    /**
     * Base64 encoded image string.
     */
    @JsonProperty("b64_json")
    String b64Json;

    /**
     * The prompt that was used to generate the image, if there was any revision to the prompt.
     */
    @JsonProperty("revised_prompt")
    String revisedPrompt;


    public Image() {
        super(JsonNodeFactory.instance);
    }

    public Image(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("url") != null) {
            this.setUrl(objectNode.get("url").asText());
        }else {
            this.setUrl(null);
        }
        if (objectNode.get("b64_json") != null) {
            this.setB64Json(objectNode.get("b64_json").asText());
        } else {
            this.setB64Json(null);
        }
        if (objectNode.get("revised_prompt") != null) {
            this.setRevisedPrompt(objectNode.get("revised_prompt").asText());
        } else {
            this.setRevisedPrompt(null);
        }

        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }

    public void setUrl(String url) {
        this.url = url;
        this.put("url", url);
    }

    public void setB64Json(String b64Json) {
        this.b64Json = b64Json;
        this.put("b64_json", b64Json);
    }

    public void setRevisedPrompt(String revisedPrompt) {
        this.revisedPrompt = revisedPrompt;
        this.put("revised_prompt", revisedPrompt);
    }
}
