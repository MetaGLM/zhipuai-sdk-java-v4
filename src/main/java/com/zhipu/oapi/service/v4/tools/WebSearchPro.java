package com.zhipu.oapi.service.v4.tools;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Getter;

import java.util.Iterator;
import java.util.List;

public class WebSearchPro extends ObjectNode {

    /**
     * 创建时间
     */
    @JsonProperty("created")
    private Integer created;

    /**
     * 选择项
     */
    @JsonProperty("choices")
    private List<WebSearchChoice> choices;

    /**
     * 请求ID
     */
    @JsonProperty("request_id")
    private String requestId;

    /**
     * ID
     */
    @JsonProperty("id")
    private String id;

    @Getter
    private ChatError error;

    public WebSearchPro() {
        super(JsonNodeFactory.instance);
    }

    public WebSearchPro(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("created") != null) {
            this.setCreated(objectNode.get("created").asInt());
        } else {
            this.setCreated(null);
        }

        if (objectNode.get("choices") != null) {
            this.setChoices(objectMapper.convertValue(objectNode.get("choices"), new TypeReference<List<WebSearchChoice>>() {}));
        } else {
            this.setChoices(null);
        }
        if (objectNode.get("request_id") != null) {
            this.setRequestId(objectNode.get("request_id").asText());
        } else {
            this.setRequestId(null);
        }
        if (objectNode.get("id") != null) {
            this.setId(objectNode.get("id").asText());
        } else {
            this.setId(null);
        }

        if(objectNode.get("error") != null) {
            this.setError(objectMapper.convertValue(objectNode.get("error"), ChatError.class));
        } else {
            this.setError(null);
        }

        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }

    // Getters and Setters

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
        this.put("created", created);
    }

    public List<WebSearchChoice> getChoices() {
        return choices;
    }

    public void setChoices(List<WebSearchChoice> choices) {
        this.choices = choices;
        this.putPOJO("choices", choices);
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
        this.put("request_id", requestId);
    }

    public void setId(String id) {
        this.id = id;
        this.put("id", id);
    }

    public void setError(ChatError error) {
        this.error = error;
        this.putPOJO("error", error);
    }
}
