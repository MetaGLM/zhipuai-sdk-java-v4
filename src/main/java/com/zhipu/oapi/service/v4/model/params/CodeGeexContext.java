package com.zhipu.oapi.service.v4.model.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.CodeGeexContextDeserializer;
import lombok.Getter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Getter
@JsonDeserialize(using = CodeGeexContextDeserializer.class)
public class CodeGeexContext extends ObjectNode {
    @JsonProperty("path")
    private String path;

    @JsonProperty("code")
    private String code;

    public CodeGeexContext() {
        super(JsonNodeFactory.instance);
    }

    public CodeGeexContext(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        this.setPath(objectNode.has("path") ? objectNode.get("path").asText() : null);
        this.setCode(objectNode.has("code") ? objectNode.get("code").asText() : null);

        Iterator<String> fieldNames = objectNode.fieldNames();

        while(fieldNames.hasNext()) {
            String fieldName = fieldNames.next();

            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }

    }
    public CodeGeexContext(JsonNodeFactory nc, Map<String, JsonNode> kids) {
        super(nc, kids);
    }

    public void setPath(String path) {
        this.path = path;
        this.put("path", path);
    }

    public void setCode(String code) {
        this.code = code;
        this.put("code", code);
    }
}
