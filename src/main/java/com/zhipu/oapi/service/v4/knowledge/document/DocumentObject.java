package com.zhipu.oapi.service.v4.knowledge.document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.knowledge.document.DocumentFailedInfoDeserializer;
import com.zhipu.oapi.service.v4.deserialize.knowledge.document.DocumentObjectDeserializer;
import com.zhipu.oapi.service.v4.model.Choice;

import java.util.Iterator;
import java.util.List;

/**
 * This class represents the document information including success and failure details.
 */
@JsonDeserialize(using = DocumentObjectDeserializer.class)
public class DocumentObject extends ObjectNode {

    /**
     * 上传成功的文件信息
     */
    @JsonProperty("successInfos")
    private List<DocumentSuccessInfo> successInfos;

    /**
     * 上传失败的文件信息
     */
    @JsonProperty("failedInfos")
    private List<DocumentFailedInfo> failedInfos;

    public DocumentObject() {
        super(JsonNodeFactory.instance);
    }

    public DocumentObject(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);


        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("successInfos") != null) {
            List<DocumentSuccessInfo> successInfos = objectMapper.convertValue(objectNode.get("successInfos"), new com.fasterxml.jackson.core.type.TypeReference<List<DocumentSuccessInfo>>() {
            });

            this.setSuccessInfos(successInfos);
        } else {
            this.setSuccessInfos(null);
        }
        if (objectNode.get("failedInfos") != null) {
            List<DocumentFailedInfo> failedInfos = objectMapper.convertValue(objectNode.get("failedInfos"), new com.fasterxml.jackson.core.type.TypeReference<List<DocumentFailedInfo>>() {
            });

            this.setFailedInfos(failedInfos);
        } else {
            this.setFailedInfos(null);
        }

        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }
    // Getters and Setters

    public List<DocumentSuccessInfo> getSuccessInfos() {
        return successInfos;
    }

    public void setSuccessInfos(List<DocumentSuccessInfo> successInfos) {
        this.successInfos = successInfos;
        ArrayNode jsonNodes = this.putArray("successInfos");
        if (successInfos == null) {
            jsonNodes.removeAll();
        }
        else {

            for (DocumentSuccessInfo successInfo : successInfos) {
                jsonNodes.add(successInfo);
            }
        }
    }

    public List<DocumentFailedInfo> getFailedInfos() {
        return failedInfos;
    }

    public void setFailedInfos(List<DocumentFailedInfo> failedInfos) {
        this.failedInfos = failedInfos;
        ArrayNode jsonNodes = this.putArray("failedInfos");
        if (successInfos == null) {
            jsonNodes.removeAll();
        }
        else {

            for (DocumentFailedInfo failedInfo : failedInfos) {
                jsonNodes.add(failedInfo);
            }
        }
    }
}
