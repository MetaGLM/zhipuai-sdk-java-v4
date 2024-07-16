package com.zhipu.oapi.service.v4.deserialize.tools;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.BaseNodeDeserializer;
import com.zhipu.oapi.service.v4.deserialize.ObjectDeserializer;
import com.zhipu.oapi.service.v4.tools.WebSearchMessageToolCall;

import java.io.IOException;

/**
 * Deserializer that can build instances of {@link WebSearchMessageToolCall} from any
 * JSON content, using appropriate {@link WebSearchMessageToolCall} type.
 */
public class WebSearchMessageToolCallDeserializer extends BaseNodeDeserializer<WebSearchMessageToolCall> {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    private final static WebSearchMessageToolCallDeserializer instance = new WebSearchMessageToolCallDeserializer();

    public WebSearchMessageToolCallDeserializer() {
        super(WebSearchMessageToolCall.class, null);
    }

    public static JsonDeserializer<? extends JsonNode> getDeserializer(Class<?> nodeClass) {
        if (nodeClass == ObjectNode.class) {
            return ObjectDeserializer.getInstance();
        }
        return instance;
    }

    @Override
    public WebSearchMessageToolCall getNullValue(DeserializationContext ctxt) {
        return null;
    }

    @Override
    public WebSearchMessageToolCall deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.currentTokenId() == JsonTokenId.ID_START_OBJECT) {
            ObjectNode jsonNodes = deserializeObject(p, ctxt, ctxt.getNodeFactory());
            return new WebSearchMessageToolCall(jsonNodes);
        }
        return null;
    }
}
