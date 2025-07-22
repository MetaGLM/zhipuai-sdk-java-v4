package com.zhipu.oapi.service.v4.deserialize.audio;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.audio.AudioSpeechChoice;
import com.zhipu.oapi.service.v4.deserialize.BaseNodeDeserializer;
import com.zhipu.oapi.service.v4.deserialize.ObjectDeserializer;

import java.io.IOException;

/**
 * Deserializer that can build instances of {@link AudioSpeechChoice} from any JSON content, using
 * appropriate {@link AudioSpeechChoice} type.
 */
public class AudioSpeechChoiceDeserializer extends BaseNodeDeserializer<AudioSpeechChoice> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final AudioSpeechChoiceDeserializer instance =
            new AudioSpeechChoiceDeserializer();

    public AudioSpeechChoiceDeserializer() {
        super(AudioSpeechChoice.class, null);
    }

    public static JsonDeserializer<? extends JsonNode> getDeserializer(Class<?> nodeClass) {
        if (nodeClass == ObjectNode.class) {
            return ObjectDeserializer.getInstance();
        }
        return instance;
    }

    @Override
    public AudioSpeechChoice getNullValue(DeserializationContext ctxt) {
        return null;
    }

    @Override
    public AudioSpeechChoice deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        if (p.currentTokenId() == JsonTokenId.ID_START_OBJECT) {
            ObjectNode jsonNodes = deserializeObject(p, ctxt, ctxt.getNodeFactory());
            return new AudioSpeechChoice(jsonNodes);
        }
        return null;
    }
}
