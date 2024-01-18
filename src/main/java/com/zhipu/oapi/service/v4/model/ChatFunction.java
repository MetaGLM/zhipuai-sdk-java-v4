package com.zhipu.oapi.service.v4.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.function.Function;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatFunction {


    private String name;

    private String description;

    @JsonProperty("parameters")
    private Class<?> parametersClass;

    private ChatFunctionParameters parameters;

    private Retrieval retrieval;

    private Ref ref;


    @JsonIgnore
    private Function<Object, Object> executor;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String description;
        private Class<?> parametersClass;

        private Retrieval retrieval;

        private Ref ref;

        private ChatFunctionParameters parameters;

        private Function<Object, Object> executor;

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

        public Builder parameters(Class<?>  parametersClass) {
            this.parametersClass = parametersClass;
            return this;
        }

        public Builder ref(Ref ref) {
            this.ref = ref;
            return this;
        }

        public Builder retrieval(Retrieval retrieval) {
            this.retrieval = retrieval;
            return this;
        }

        public <T> Builder executor(Class<T> requestClass, Function<T, Object> executor) {
            this.parametersClass = requestClass;
            this.executor = (Function<Object, Object>) executor;
            return this;
        }

        public ChatFunction build() {
            ChatFunction chatFunction = new ChatFunction();
            chatFunction.setName(name);
            chatFunction.setDescription(description);
            chatFunction.setParametersClass(parametersClass);
            chatFunction.setParameters(parameters);
            chatFunction.setExecutor(executor);
            chatFunction.setRef(ref);
            chatFunction.setRetrieval(retrieval);
            return chatFunction;
        }
    }
}
