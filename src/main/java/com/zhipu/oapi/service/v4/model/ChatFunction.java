package com.zhipu.oapi.service.v4.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Getter
public class ChatFunction extends ObjectNode {


    private String name;

    private String description;

    private ChatFunctionParameters parameters;


    private List<String> required;

    public ChatFunction(){
        super(JsonNodeFactory.instance);
    }
    public ChatFunction(JsonNodeFactory nc, Map<String, JsonNode> kids) {
        super(nc, kids);
    }

    public void setName(String name) {
        this.name = name;
        this.put("name",name);
    }

    public void setDescription(String description) {
        this.description = description;
        this.put("description",description);
    }

    public void setParameters(ChatFunctionParameters parameters) {
        this.parameters = parameters;
        this.set("parameters",parameters);
    }

    public void setRequired(List<String> required) {
        this.required = required;
        this.putPOJO("required",required);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String description;


        private ChatFunctionParameters parameters;


        private List<String> required;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder parameters(ChatFunctionParameters parameters) {
            this.parameters = parameters;
            return this;
        }

        public Builder required(List<String> required) {
            this.required = required;
            return this;
        }



        public ChatFunction build() {
            ChatFunction chatFunction = new ChatFunction();
            chatFunction.setName(name);
            chatFunction.setDescription(description);
            chatFunction.setParameters(parameters);
            chatFunction.setRequired(required);
            return chatFunction;
        }
    }
}
