package com.zhipu.oapi.service.v4.deserialize.assistant;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.assistant.CompletionUsage;
import com.zhipu.oapi.service.v4.deserialize.BaseNodeDeserializer;
import com.zhipu.oapi.service.v4.deserialize.ObjectDeserializer;

import java.io.IOException;

/**
 * Deserializer that can build instances of {@link CompletionUsage} from any
 * JSON content, using appropriate {@link CompletionUsage} type.
 */
public class CompletionUsageDeserializer extends BaseNodeDeserializer<CompletionUsage> {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    private final static CompletionUsageDeserializer instance = new CompletionUsageDeserializer();

    public CompletionUsageDeserializer() {
        super(CompletionUsage.class, null);
    }

    public static JsonDeserializer<? extends JsonNode> getDeserializer(Class<?> nodeClass) {
        if (nodeClass == ObjectNode.class) {
            return ObjectDeserializer.getInstance();
        }
        return instance;
    }

    @Override
    public CompletionUsage getNullValue(DeserializationContext ctxt) {
        return null;
    }

    @Override
    public CompletionUsage deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.currentTokenId() == JsonTokenId.ID_START_OBJECT) {
            ObjectNode jsonNodes = deserializeObject(p, ctxt, ctxt.getNodeFactory());
            return new CompletionUsage(jsonNodes);
        }
        return null;
    }
}
