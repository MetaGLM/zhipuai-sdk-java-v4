package com.zhipu.oapi.service.v4.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatFunction {


    private String name;

    private String description;

    private ChatFunctionParameters parameters;

    private Retrieval retrieval;

    private WebSearch web_search;

    private List<String> required;

    @JsonIgnore
    private Function<Object, Object> executor;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String description;

        private Retrieval retrieval;

        private WebSearch web_search;

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


        public Builder webSearch(WebSearch webSearch) {
            this.web_search = webSearch;
            return this;
        }

        public Builder retrieval(Retrieval retrieval) {
            this.retrieval = retrieval;
            return this;
        }


        public ChatFunction build() {
            ChatFunction chatFunction = new ChatFunction();
            chatFunction.setName(name);
            chatFunction.setDescription(description);
            chatFunction.setParameters(parameters);
            chatFunction.setWeb_search(web_search);
            chatFunction.setRetrieval(retrieval);
            chatFunction.setRequired(required);
            return chatFunction;
        }
    }
}
