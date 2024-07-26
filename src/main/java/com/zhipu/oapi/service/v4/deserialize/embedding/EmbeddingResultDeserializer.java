package com.zhipu.oapi.service.v4.deserialize.embedding;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.BaseNodeDeserializer;
import com.zhipu.oapi.service.v4.deserialize.ObjectDeserializer;
import com.zhipu.oapi.service.v4.embedding.EmbeddingResult;

import java.io.IOException;

/**
 * Deserializer that can build instances of {@link EmbeddingResult} from any
 * JSON content, using appropriate {@link EmbeddingResult} type.
 */
public class EmbeddingResultDeserializer extends BaseNodeDeserializer<EmbeddingResult> {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    private final static EmbeddingResultDeserializer instance = new EmbeddingResultDeserializer();

    public EmbeddingResultDeserializer() {
        super(EmbeddingResult.class, null);
    }

    public static JsonDeserializer<? extends JsonNode> getDeserializer(Class<?> nodeClass) {
        if (nodeClass == ObjectNode.class) {
            return ObjectDeserializer.getInstance();
        }
        return instance;
    }

    @Override
    public EmbeddingResult getNullValue(DeserializationContext ctxt) {
        return null;
    }

    @Override
    public EmbeddingResult deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.currentTokenId() == JsonTokenId.ID_START_OBJECT) {
            ObjectNode jsonNodes = deserializeObject(p, ctxt, ctxt.getNodeFactory());
            return new EmbeddingResult(jsonNodes);
        }
        return null;
    }
}
