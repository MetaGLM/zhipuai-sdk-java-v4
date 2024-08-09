package com.zhipu.oapi.service.v4.deserialize.knowledge.document;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.BaseNodeDeserializer;
import com.zhipu.oapi.service.v4.deserialize.ObjectDeserializer;
import com.zhipu.oapi.service.v4.knowledge.document.DocumentData;

import java.io.IOException;

/**
 * Deserializer that can build instances of {@link DocumentData} from any
 * JSON content, using appropriate {@link DocumentData} type.
 */
public class DocumentDataDeserializer extends BaseNodeDeserializer<DocumentData> {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    private final static DocumentDataDeserializer instance = new DocumentDataDeserializer();

    public DocumentDataDeserializer() {
        super(DocumentData.class, null);
    }

    public static JsonDeserializer<? extends JsonNode> getDeserializer(Class<?> nodeClass) {
        if (nodeClass == ObjectNode.class) {
            return ObjectDeserializer.getInstance();
        }
        return instance;
    }

    @Override
    public DocumentData getNullValue(DeserializationContext ctxt) {
        return null;
    }

    @Override
    public DocumentData deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.currentTokenId() == JsonTokenId.ID_START_OBJECT) {
            ObjectNode jsonNodes = deserializeObject(p, ctxt, ctxt.getNodeFactory());
            return new DocumentData(jsonNodes);
        }
        return null;
    }
}
