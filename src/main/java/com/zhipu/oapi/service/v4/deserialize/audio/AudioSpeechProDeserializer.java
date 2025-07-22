package com.zhipu.oapi.service.v4.deserialize.audio;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.audio.AudioSpeechPro;
import com.zhipu.oapi.service.v4.deserialize.BaseNodeDeserializer;
import com.zhipu.oapi.service.v4.deserialize.ObjectDeserializer;

import java.io.IOException;

/**
 * Deserializer that can build instances of {@link AudioSpeechPro} from any JSON content, using
 * appropriate {@link AudioSpeechPro} type.
 */
public class AudioSpeechProDeserializer extends BaseNodeDeserializer<AudioSpeechPro> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final AudioSpeechProDeserializer instance = new AudioSpeechProDeserializer();

    public AudioSpeechProDeserializer() {
        super(AudioSpeechPro.class, null);
    }

    public static JsonDeserializer<? extends JsonNode> getDeserializer(Class<?> nodeClass) {
        if (nodeClass == ObjectNode.class) {
            return ObjectDeserializer.getInstance();
        }
        return instance;
    }

    @Override
    public AudioSpeechPro getNullValue(DeserializationContext ctxt) {
        return null;
    }

    @Override
    public AudioSpeechPro deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        if (p.currentTokenId() == JsonTokenId.ID_START_OBJECT) {
            ObjectNode jsonNodes = deserializeObject(p, ctxt, ctxt.getNodeFactory());
            return new AudioSpeechPro(jsonNodes);
        }
        return null;
    }
}
