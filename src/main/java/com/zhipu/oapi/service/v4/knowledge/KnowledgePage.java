package com.zhipu.oapi.service.v4.knowledge;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.knowledge.KnowledgePageDeserializer;
import com.zhipu.oapi.service.v4.deserialize.knowledge.KnowledgeUsedDeserializer;
import com.zhipu.oapi.service.v4.model.Choice;

import java.util.Iterator;
import java.util.List;

/**
 * This class represents a page of knowledge base information.
 */
@JsonDeserialize(using = KnowledgePageDeserializer.class)
public class KnowledgePage extends ObjectNode {

    /**
     * 知识库信息列表
     */
    @JsonProperty("list")
    private List<KnowledgeInfo> list;

    /**
     * 对象类型
     */
    @JsonProperty("total")
    private Integer total;

    public KnowledgePage() {
        super(JsonNodeFactory.instance);
    }

    public KnowledgePage(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("list") != null) {
            List<KnowledgeInfo> list = objectMapper.convertValue(objectNode.get("list"), new TypeReference<List<KnowledgeInfo>>() {
            });

            this.setList(list);
        } else {
            this.setList(null);
        }
        if (objectNode.get("total") != null) {
            this.setTotal(objectNode.get("total").asInt());
        } else {
            this.setTotal(null);
        }

        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }

    // Getters and Setters

    public List<KnowledgeInfo> getList() {
        return list;
    }

    public void setList(List<KnowledgeInfo> list) {
        this.list = list;
        ArrayNode jsonNodes = this.putArray("list");
        if (list == null) {
            jsonNodes.removeAll();
        }
        else {

            for (KnowledgeInfo item : list) {
                jsonNodes.add(item);
            }
        }

    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
        this.put("total", total);
    }
}
