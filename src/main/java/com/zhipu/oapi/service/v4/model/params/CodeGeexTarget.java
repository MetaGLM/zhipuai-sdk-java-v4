package com.zhipu.oapi.service.v4.model.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.CodeGeexTargetDeserializer;
import lombok.Getter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Getter
@JsonDeserialize(using = CodeGeexTargetDeserializer.class)
public class CodeGeexTarget extends ObjectNode {
    @JsonProperty("path")
    private String path;

    @JsonProperty("language")
    private String language;

    @JsonProperty("code_prefix")
    private String codePrefix;

    @JsonProperty("code_suffix")
    private String codeSuffix;

    public CodeGeexTarget() {
        super(JsonNodeFactory.instance);
    }

    public CodeGeexTarget(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);

        this.setPath(objectNode.has("path") ? objectNode.get("path").asText() : null);
        this.setLanguage(objectNode.has("language") ? objectNode.get("language").asText() : null);
        this.setCodePrefix(objectNode.has("code_prefix") ? objectNode.get("code_prefix").asText() : null);
        this.setCodeSuffix(objectNode.has("code_suffix") ? objectNode.get("code_suffix").asText() : null);


        Iterator<String> fieldNames = objectNode.fieldNames();

        while(fieldNames.hasNext()) {
            String fieldName = fieldNames.next();

            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }
    }
    public CodeGeexTarget(JsonNodeFactory nc, Map<String, JsonNode> kids) {
        super(nc, kids);
    }

    public void setPath(String path) {
        this.path = path;
        this.put("path", path);
    }

    public void setLanguage(String language) {
        this.language = language;
        this.put("language", language);
    }

    public void setCodePrefix(String codePrefix) {
        this.codePrefix = codePrefix;
        this.put("code_prefix", codePrefix);
    }

    public void setCodeSuffix(String codeSuffix) {
        this.codeSuffix = codeSuffix;
        this.put("code_suffix", codeSuffix);
    }
}
