package com.zhipu.oapi.service.v4.model.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.CodeGeexContextDeserializer;
import com.zhipu.oapi.service.v4.deserialize.CodeGeexExtraDeserializer;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.model.ToolCalls;
import lombok.Getter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@JsonDeserialize(using = CodeGeexExtraDeserializer.class)
public class CodeGeexExtra extends ObjectNode {
    @JsonProperty("target")
    private CodeGeexTarget target;

    @JsonProperty("contexts")
    private List<CodeGeexContext> contexts;

    public CodeGeexExtra() {
        super(JsonNodeFactory.instance);
    }

    public CodeGeexExtra(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();

        if (objectNode.get("target") != null) {
            this.setTarget(objectMapper.convertValue(objectNode.get("target"), CodeGeexTarget.class));
        } else {
            this.setTarget(null);
        }
        if (objectNode.get("contexts") != null) {
            this.setContexts(objectMapper.convertValue(objectNode.get("tool_calls"), new com.fasterxml.jackson.core.type.TypeReference<List<CodeGeexContext>>() {
            }));
        } else {
            this.setContexts(null);
        }
        Iterator<String> fieldNames = objectNode.fieldNames();

        while(fieldNames.hasNext()) {
            String fieldName = fieldNames.next();

            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }
    public CodeGeexExtra(JsonNodeFactory nc, Map<String, JsonNode> kids) {
        super(nc, kids);
    }

    public void setTarget(CodeGeexTarget target) {
        this.target = target;
        this.putPOJO("target", target);
    }

    public void setContexts(List<CodeGeexContext> contexts) {
        this.contexts = contexts;
        this.putPOJO("contexts", contexts);
    }
}