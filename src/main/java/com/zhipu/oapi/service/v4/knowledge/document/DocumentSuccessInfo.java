package com.zhipu.oapi.service.v4.knowledge.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.knowledge.document.DocumentObjectDeserializer;
import com.zhipu.oapi.service.v4.deserialize.knowledge.document.DocumentSuccessInfoDeserializer;

import java.util.Iterator;
import java.util.Map;

/**
 * This class represents the information of a successfully uploaded document.
 */
@JsonDeserialize(using = DocumentSuccessInfoDeserializer.class)
public class DocumentSuccessInfo extends ObjectNode {

    /**
     * 文件id
     */
    @JsonProperty("documentId")
    private String documentId;

    /**
     * 文件名称
     */
    @JsonProperty("filename")
    private String filename;


    public DocumentSuccessInfo() {
        super(JsonNodeFactory.instance);
    }

    public DocumentSuccessInfo(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        if (objectNode == null) {
            return;
        }
        if (objectNode.has("documentId")) {
            this.setDocumentId(objectNode.get("documentId").asText());
        }else{
            this.setDocumentId(null);
        }
        if (objectNode.has("filename")) {
            this.setFilename(objectNode.get("filename").asText());
        }else {
            this.setFilename(null);
        }
        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
        this.put("documentId", documentId);
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
        this.put("filename", filename);
    }
}
