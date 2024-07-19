package com.zhipu.oapi.service.v4.deserialize.videos;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.BaseNodeDeserializer;
import com.zhipu.oapi.service.v4.deserialize.ObjectDeserializer;
import com.zhipu.oapi.service.v4.videos.VideoResult;

import java.io.IOException;

/**
 * Deserializer that can build instances of {@link VideoResult} from any
 * JSON content, using appropriate {@link VideoResult} type.
 */
public class VideoResultDeserializer extends BaseNodeDeserializer<VideoResult> {

    private final static ObjectMapper MAPPER = new ObjectMapper();

    private final static VideoResultDeserializer instance = new VideoResultDeserializer();

    public VideoResultDeserializer() {
        super(VideoResult.class, null);
    }

    public static JsonDeserializer<? extends JsonNode> getDeserializer(Class<?> nodeClass) {
        if (nodeClass == ObjectNode.class) {
            return ObjectDeserializer.getInstance();
        }
        return instance;
    }

    @Override
    public VideoResult getNullValue(DeserializationContext ctxt) {
        return null;
    }

    @Override
    public VideoResult deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.currentTokenId() == JsonTokenId.ID_START_OBJECT) {
            ObjectNode jsonNodes = deserializeObject(p, ctxt, ctxt.getNodeFactory());
            return new VideoResult(jsonNodes);
        }
        return null;
    }
}
