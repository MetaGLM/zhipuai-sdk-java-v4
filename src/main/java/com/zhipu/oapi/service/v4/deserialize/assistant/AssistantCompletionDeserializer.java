package com.zhipu.oapi.service.v4.deserialize.assistant;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.assistant.AssistantCompletion;
import com.zhipu.oapi.service.v4.deserialize.BaseNodeDeserializer;
import com.zhipu.oapi.service.v4.deserialize.ObjectDeserializer;

import java.io.IOException;

/**
 * Deserializer that can build instances of {@link AssistantCompletion} from any
 * JSON content, using appropriate {@link AssistantCompletion} type.
 */
public class AssistantCompletionDeserializer extends BaseNodeDeserializer<AssistantCompletion> {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    private final static AssistantCompletionDeserializer instance = new AssistantCompletionDeserializer();

    public AssistantCompletionDeserializer() {
        super(AssistantCompletion.class, null);
    }

    public static JsonDeserializer<? extends JsonNode> getDeserializer(Class<?> nodeClass) {
        if (nodeClass == ObjectNode.class) {
            return ObjectDeserializer.getInstance();
        }
        return instance;
    }

    @Override
    public AssistantCompletion getNullValue(DeserializationContext ctxt) {
        return null;
    }

    @Override
    public AssistantCompletion deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.currentTokenId() == JsonTokenId.ID_START_OBJECT) {
            ObjectNode jsonNodes = deserializeObject(p, ctxt, ctxt.getNodeFactory());
            return new AssistantCompletion(jsonNodes);
        }
        return null;
    }
}
