package com.zhipu.oapi.service.v4.deserialize.knowledge.document;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.BaseNodeDeserializer;
import com.zhipu.oapi.service.v4.deserialize.ObjectDeserializer;
import com.zhipu.oapi.service.v4.knowledge.document.DocumentFailedInfo;

import java.io.IOException;

/**
 * Deserializer that can build instances of {@link DocumentFailedInfo} from any
 * JSON content, using appropriate {@link DocumentFailedInfo} type.
 */
public class DocumentFailedInfoDeserializer extends BaseNodeDeserializer<DocumentFailedInfo> {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    private final static DocumentFailedInfoDeserializer instance = new DocumentFailedInfoDeserializer();

    public DocumentFailedInfoDeserializer() {
        super(DocumentFailedInfo.class, null);
    }

    public static JsonDeserializer<? extends JsonNode> getDeserializer(Class<?> nodeClass) {
        if (nodeClass == ObjectNode.class) {
            return ObjectDeserializer.getInstance();
        }
        return instance;
    }

    @Override
    public DocumentFailedInfo getNullValue(DeserializationContext ctxt) {
        return null;
    }

    @Override
    public DocumentFailedInfo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.currentTokenId() == JsonTokenId.ID_START_OBJECT) {
            ObjectNode jsonNodes = deserializeObject(p, ctxt, ctxt.getNodeFactory());
            return new DocumentFailedInfo(jsonNodes);
        }
        return null;
    }
}
