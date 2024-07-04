package com.zhipu.oapi.service.v4.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.model.ToolCalls;
import com.zhipu.oapi.service.v4.model.params.CodeGeexTarget;

import java.io.IOException;

class CodeGeexTargetDeserializer extends BaseNodeDeserializer<CodeGeexTarget> {

    private static final CodeGeexTargetDeserializer instance = new CodeGeexTargetDeserializer();

    public CodeGeexTargetDeserializer() {
        super(CodeGeexTarget.class, null);
    }

    public static JsonDeserializer<? extends JsonNode> getDeserializer(Class<?> nodeClass) {
        if (nodeClass == ObjectNode.class) {
            return ObjectDeserializer.getInstance();
        }
        return instance;
    }

    @Override
    public CodeGeexTarget getNullValue(DeserializationContext ctxt) {
        return null;
    }

    @Override
    public CodeGeexTarget deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.currentTokenId() == JsonTokenId.ID_START_OBJECT) {
            ObjectNode jsonNodes = deserializeObject(p, ctxt, ctxt.getNodeFactory());
            return new CodeGeexTarget(jsonNodes);
        }
        return null;
    }
}