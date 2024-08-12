package com.zhipu.oapi.service.v4.assistant;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.assistant.message.MessageContent;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.assistant.AssistantChoiceDeserializer;

import java.util.Iterator;
import java.util.Map;

/**
 * This class represents an assistant's choice output.
 */
@JsonDeserialize(using = AssistantChoiceDeserializer.class)
public class AssistantChoice extends ObjectNode {

    /**
     * 结果下标
     */
    @JsonProperty("index")
    private int index;

    /**
     * 当前会话输出消息体
     */
    @JsonProperty("delta")
    private MessageContent delta;

    /**
     * 推理结束原因 stop 代表推理自然结束或触发停止词。sensitive 代表模型推理内容被安全审核接口拦截。
     * network_error 代表模型推理服务异常。
     */
    @JsonProperty("finish_reason")
    private String finishReason;

    /**
     * 元信息，拓展字段
     */
    @JsonProperty("metadata")
    private Map<String, Object> metadata;
    public AssistantChoice() {
        super(JsonNodeFactory.instance);
    }

    public AssistantChoice(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);

        if (objectNode == null) {
            return;
        }

        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.has("index")) {
            this.setIndex(objectNode.get("index").asInt());
        }else {
            this.setIndex(0);
        }
        if (objectNode.has("delta")) {
            MessageContent delta = objectMapper.convertValue(objectNode.get("delta"), MessageContent.class);
            this.setDelta(delta);
        }else {
            this.setDelta(null);
        }
        if (objectNode.has("finish_reason")) {
            this.setFinishReason(objectNode.get("finish_reason").asText());

        }else {
            this.setFinishReason(null);
        }
        if (objectNode.has("metadata")) {
            Map<String,Object> metadata = objectMapper.convertValue(objectNode.get("metadata"),  new TypeReference<Map<String,Object>>() {});

            this.setMetadata(metadata);
        }else {
            this.setMetadata(null);
        }


        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }
    // Getters and Setters

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
        this.put("index", index);
    }

    public MessageContent getDelta() {
        return delta;
    }

    public void setDelta(MessageContent delta) {
        this.delta = delta;
        this.putPOJO("delta", delta);
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
        this.put("finish_reason", finishReason);
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
        this.putPOJO("metadata", metadata);
    }
}
