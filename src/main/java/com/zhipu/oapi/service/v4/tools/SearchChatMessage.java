package com.zhipu.oapi.service.v4.tools;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import lombok.Getter;

import java.util.Iterator;

@Getter
public class SearchChatMessage extends ObjectNode {


    private String role;
    private Object content;



    public SearchChatMessage() {
        super(JsonNodeFactory.instance);
    }

    public SearchChatMessage(String role, Object content) {

        super(JsonNodeFactory.instance);
        this.setRole(role);
        this.setContent(content);
    }

    public SearchChatMessage(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("role") != null) {
            this.setRole(objectNode.get("role").asText());
        } else {
            this.setRole(null);
        }
        if (objectNode.get("content") != null) {
            this.setContent(objectNode.get("content").asText());
        } else {
            this.setContent(null);
        }

        Iterator<String> fieldNames = objectNode.fieldNames();

        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();

            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }

    }

    public void setRole(String role) {
        this.role = role;
        this.put("role", role);
    }

    public void setContent(Object content) {
        this.content = content;
        this.putPOJO("content", content);
    }


}
