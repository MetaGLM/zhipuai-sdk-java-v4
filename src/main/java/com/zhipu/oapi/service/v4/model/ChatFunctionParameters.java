package com.zhipu.oapi.service.v4.model;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


@Getter
public class ChatFunctionParameters extends ObjectNode {


    private String type;

    private JsonNode properties;

    private List<String> required;


    public ChatFunctionParameters(){
        super(JsonNodeFactory.instance);
    }
    public ChatFunctionParameters(JsonNodeFactory nc, Map<String, JsonNode> kids) {
        super(nc, kids);
    }

    public void setType(String type) {
        this.type = type;
        this.put("type",type);
    }

    public void setProperties(Object properties) {
        if(properties instanceof JsonNode){
            this.properties = (JsonNode) properties;
        }else if(properties instanceof Map){
            Map<String, Object> map = (Map<String, Object>) properties;
            JsonNode jsonNode = JsonNodeFactory.instance.objectNode();
            map.forEach((k,v)->{
                if(v instanceof JsonNode){
                    ((ObjectNode) jsonNode).set(k,(JsonNode) v);
                }else{
                    ((ObjectNode) jsonNode).putPOJO(k,v);
                }
            });
            this.properties = jsonNode ;
        } else {
            throw new IllegalArgumentException("properties must be a Map or JsonNode");
        }
        this.putPOJO("properties",properties);
    }

    public void setRequired(List<String> required) {
        this.required = required;
        this.putPOJO("required",required);
    }
}
