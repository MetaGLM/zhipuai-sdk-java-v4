package com.zhipu.oapi.service.v4.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Object containing a response from the chat completions api.
 */
@Getter
public class ChatCompletionResult extends ObjectNode {

    /**
     * Unique id assigned to this chat completion.
     */
    String id;

    /**
     * The type of object returned, should be "chat.completion"
     */
    String object;

    /**
     * The creation time in epoch seconds.
     */
    long created;
    
    /**
     * The GLM model used.
     */
    String model;

    /**
     * A list of all generated completions.
     */
    List<ChatCompletionChoice> choices;

    /**
     * The API usage for this request.
     */
    Usage usage;

    private String task_status;

    /**
     * 用户在客户端请求时提交的任务编号或者平台生成的任务编号
     */
    String request_id;

    public ChatCompletionResult() {
        super(JsonNodeFactory.instance);
    }

    public ChatCompletionResult(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = new ObjectMapper();

        List<ChatCompletionChoice> chatCompletionChoices = objectNode.get("choices") != null ? objectMapper.convertValue(objectNode.get("choices"), new TypeReference<List<ChatCompletionChoice>>() {
        }) : null;
        this.setChoices(chatCompletionChoices);

        String id = objectNode.get("id") != null ? objectNode.get("id").asText() : null;
        this.setId(id);
        long created = objectNode.get("created") != null ? objectNode.get("created").asLong() : 0;
        this.setCreated(created);
        String request_id = objectNode.get("request_id") != null ? objectNode.get("request_id").asText() : null;
        this.setRequest_id(request_id);
        String model = objectNode.get("model") != null ? objectNode.get("model").asText() : null;
        this.setModel(model);
        Usage usage = objectNode.get("usage") != null ? objectMapper.convertValue(objectNode.get("usage"), Usage.class) : null;
        this.setUsage(usage);
        String task_status = objectNode.get("task_status") != null ? objectNode.get("task_status").asText() : null;
        this.setTask_status(task_status);
        String object = objectNode.get("object") != null ? objectNode.get("object").asText() : null;
        this.setObject(object);
        Iterator<String> fieldNames = objectNode.fieldNames();

        while(fieldNames.hasNext()) {
            String fieldName = fieldNames.next();

            JsonNode field = objectNode.get(fieldName);
            this.put(fieldName, field);
        }

    }

    public ChatCompletionResult(JsonNodeFactory nc, Map<String, JsonNode> kids) {
        super(nc, kids);
    }

    public void setId(String id) {
        this.id = id;
        this.put("id", id);
    }

    public void setObject(String object) {
        this.object = object;
        this.put("object", object);
    }

    public void setCreated(long created) {
        this.created = created;
        this.put("created", created);
    }

    public void setModel(String model) {
        this.model = model;
        this.put("model", model);
    }

    public void setChoices(List<ChatCompletionChoice> choices) {
        this.choices = choices;
        this.putPOJO("choices", choices);
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
        this.putPOJO("usage", usage);
    }

    public void setTask_status(String task_status) {
        this.task_status = task_status;
        this.put("task_status", task_status);
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
        this.put("request_id", request_id);
    }
}
