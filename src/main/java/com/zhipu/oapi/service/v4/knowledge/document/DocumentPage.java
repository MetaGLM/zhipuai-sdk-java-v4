package com.zhipu.oapi.service.v4.knowledge.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.knowledge.document.DocumentPageDeserializer;

import java.util.Iterator;
import java.util.List;

/**
 * This class represents a page of document data, including a list of document entries and the object type.
 */
@JsonDeserialize(using = DocumentPageDeserializer.class)
public class DocumentPage extends ObjectNode {

    /**
     * List of document data entries.
     */
    @JsonProperty("list")
    private List<DocumentData> list;

    /**
     * The object type.
     */
    @JsonProperty("object")
    private String object;

    public DocumentPage() {
        super(JsonNodeFactory.instance);
    }

    public DocumentPage(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);

        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("list") != null) {
            List<DocumentData> list = objectMapper.convertValue(objectNode.get("list"), new com.fasterxml.jackson.core.type.TypeReference<List<DocumentData>>() {
            });

            this.setList(list);
        } else {
            this.setList(null);
        }
        if (objectNode.get("object") != null) {
            this.setObject(objectNode.get("object").asText());
        } else {
            this.setObject(null);
        }

        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }
    // Getters and Setters

    public List<DocumentData> getList() {
        return list;
    }

    public void setList(List<DocumentData> list) {
        this.list = list;
        ArrayNode jsonNodes = this.putArray("list");
        if (list == null) {
            jsonNodes.removeAll();
        }
        else {

            for (DocumentData documentData : list) {
                jsonNodes.add(documentData);
            }
        }
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
        this.put("object", object);
    }
}
