package com.zhipu.oapi.service.v4.deserialize.audio;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.audio.AudioSpeechDelta;
import com.zhipu.oapi.service.v4.deserialize.BaseNodeDeserializer;
import com.zhipu.oapi.service.v4.deserialize.ObjectDeserializer;
import com.zhipu.oapi.service.v4.tools.ChoiceDelta;

import java.io.IOException;

/**
 * Deserializer that can build instances of {@link ChoiceDelta} from any JSON content, using
 * appropriate {@link ChoiceDelta} type.
 */
public class AudioSpeechDeltaDeserializer extends BaseNodeDeserializer<AudioSpeechDelta> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final AudioSpeechDeltaDeserializer instance = new AudioSpeechDeltaDeserializer();

    public AudioSpeechDeltaDeserializer() {
        super(AudioSpeechDelta.class, null);
    }

    public static JsonDeserializer<? extends JsonNode> getDeserializer(Class<?> nodeClass) {
        if (nodeClass == ObjectNode.class) {
            return ObjectDeserializer.getInstance();
        }
        return instance;
    }

    @Override
    public AudioSpeechDelta getNullValue(DeserializationContext ctxt) {
        return null;
    }

    @Override
    public AudioSpeechDelta deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        if (p.currentTokenId() == JsonTokenId.ID_START_OBJECT) {
            ObjectNode jsonNodes = deserializeObject(p, ctxt, ctxt.getNodeFactory());
            return new AudioSpeechDelta(jsonNodes);
        }
        return null;
    }
}
