package com.zhipu.oapi.service.v4.deserialize.knowledge;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.BaseNodeDeserializer;
import com.zhipu.oapi.service.v4.deserialize.ObjectDeserializer;
import com.zhipu.oapi.service.v4.knowledge.KnowledgeUsed;

import java.io.IOException;

/**
 * Deserializer that can build instances of {@link KnowledgeUsed} from any
 * JSON content, using appropriate {@link KnowledgeUsed} type.
 */
public class KnowledgeUsedDeserializer extends BaseNodeDeserializer<KnowledgeUsed> {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    private final static KnowledgeUsedDeserializer instance = new KnowledgeUsedDeserializer();

    public KnowledgeUsedDeserializer() {
        super(KnowledgeUsed.class, null);
    }

    public static JsonDeserializer<? extends JsonNode> getDeserializer(Class<?> nodeClass) {
        if (nodeClass == ObjectNode.class) {
            return ObjectDeserializer.getInstance();
        }
        return instance;
    }

    @Override
    public KnowledgeUsed getNullValue(DeserializationContext ctxt) {
        return null;
    }

    @Override
    public KnowledgeUsed deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.currentTokenId() == JsonTokenId.ID_START_OBJECT) {
            ObjectNode jsonNodes = deserializeObject(p, ctxt, ctxt.getNodeFactory());
            return new KnowledgeUsed(jsonNodes);
        }
        return null;
    }
}
