package com.zhipu.oapi.service.v4.deserialize.assistant;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.assistant.ErrorInfo;
import com.zhipu.oapi.service.v4.deserialize.BaseNodeDeserializer;
import com.zhipu.oapi.service.v4.deserialize.ObjectDeserializer;

import java.io.IOException;

/**
 * Deserializer that can build instances of {@link ErrorInfo} from any
 * JSON content, using appropriate {@link ErrorInfo} type.
 */
public class ErrorInfoDeserializer extends BaseNodeDeserializer<ErrorInfo> {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    private final static ErrorInfoDeserializer instance = new ErrorInfoDeserializer();

    public ErrorInfoDeserializer() {
        super(ErrorInfo.class, null);
    }

    public static JsonDeserializer<? extends JsonNode> getDeserializer(Class<?> nodeClass) {
        if (nodeClass == ObjectNode.class) {
            return ObjectDeserializer.getInstance();
        }
        return instance;
    }

    @Override
    public ErrorInfo getNullValue(DeserializationContext ctxt) {
        return null;
    }

    @Override
    public ErrorInfo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.currentTokenId() == JsonTokenId.ID_START_OBJECT) {
            ObjectNode jsonNodes = deserializeObject(p, ctxt, ctxt.getNodeFactory());
            return new ErrorInfo(jsonNodes);
        }
        return null;
    }
}
