
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
import com.zhipu.oapi.service.v4.embedding.Embedding;

import java.io.IOException;

/**
 * Deserializer that can build instances of {@link Embedding} from any
 * JSON content, using appropriate {@link Embedding} type.
 */
public class EmbeddingDeserializer extends BaseNodeDeserializer<Embedding> {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    private final static EmbeddingDeserializer instance = new EmbeddingDeserializer();

    public EmbeddingDeserializer() {
        super(Embedding.class, null);
    }

    public static JsonDeserializer<? extends JsonNode> getDeserializer(Class<?> nodeClass) {
        if (nodeClass == ObjectNode.class) {
            return ObjectDeserializer.getInstance();
        }
        return instance;
    }

    @Override
    public Embedding getNullValue(DeserializationContext ctxt) {
        return null;
    }

    @Override
    public Embedding deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.currentTokenId() == JsonTokenId.ID_START_OBJECT) {
            ObjectNode jsonNodes = deserializeObject(p, ctxt, ctxt.getNodeFactory());
            return new Embedding(jsonNodes);
        }
        return null;
    }
}
