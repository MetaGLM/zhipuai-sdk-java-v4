package com.zhipu.oapi.service.v4.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.DeltaDeserializer;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.ModelDataDeserializer;
import lombok.Getter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@JsonDeserialize(using = ModelDataDeserializer.class)
@Getter
public final class ModelData extends ObjectNode {
    @JsonProperty("choices")
    private List<Choice> choices;
    private Usage usage;
    @JsonProperty("request_id")
    private String requestId;
    @JsonProperty("task_status")
    private TaskStatus taskStatus;
    private Long created;
    private String model;
    private String id;

    private String type;
    private String text;
    private List<Segment> segments;


    public ModelData() {
        super(JsonNodeFactory.instance);
    }

    public ModelData(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        ObjectMapper objectMapper = MessageDeserializeFactory.defaultObjectMapper();
        if (objectNode.get("choices") != null) {
            List<Choice> choices1 = objectMapper.convertValue(objectNode.get("choices"), new TypeReference<List<Choice>>() {
            });

            this.setChoices(choices1);
        } else {
            this.setChoices(null);
        }
        if (objectNode.get("usage") != null) {
            this.setUsage(objectMapper.convertValue(objectNode.get("usage"), Usage.class));
        } else {
            this.setUsage(null);
        }
        if (objectNode.get("request_id") != null) {
            this.setRequestId(objectNode.get("request_id").asText());
        } else {
            this.setRequestId(null);
        }
        if(objectNode.get("task_status") != null) {
            this.setTaskStatus(objectMapper.convertValue(objectNode.get("task_status"), TaskStatus.class));
        } else {
            this.setTaskStatus(null);
        }
        if(objectNode.get("created") != null) {
            this.setCreated(objectNode.get("created").asLong());
        } else {
            this.setCreated(null);
        }
        if(objectNode.get("model") != null) {
            this.setModel(objectNode.get("model").asText());
        } else {
            this.setModel(null);
        }
        if(objectNode.get("id") != null) {
            this.setId(objectNode.get("id").asText());
        } else {
            this.setId(null);
        }
        if(objectNode.get("text") != null){
            this.setText(objectNode.get("text").asText());
        }else {
            this.setText(null);
        }
        if(objectNode.get("type") != null){
            this.setType(objectNode.get("type").asText());
        }else {
            this.setType(null);
        }
        if (objectNode.get("segments") != null) {
            List<Segment> segments =
                    objectMapper.convertValue(objectNode.get("segments"), new TypeReference<List<Segment>>() {});
            this.setSegments(segments);
        } else {
            this.setSegments(null);
        }


        Iterator<String> fieldNames = objectNode.fieldNames();

        while(fieldNames.hasNext()) {
            String fieldName = fieldNames.next();

            JsonNode field = objectNode.get(fieldName);
            this.set(fieldName, field);
        }

    }

    public ModelData(JsonNodeFactory nc, Map<String, JsonNode> kids) {
        super(nc, kids);
    }


    public void setChoices(List<Choice> choices) {
        this.choices = choices;
        ArrayNode jsonNodes = this.putArray("choices");
        if (choices == null) {
            jsonNodes.removeAll();
        }
        else {

            for (Choice choice : choices) {
                jsonNodes.add(choice);
            }
        }
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
        this.putPOJO("usage", usage);
    }


    public void setRequestId(String requestId) {
        this.requestId = requestId;
        this.put("request_id", requestId);
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
        this.putPOJO("task_status", taskStatus);
    }

    public void setCreated(Long created) {
        this.created = created;
        this.put("created", created);
    }

    public void setModel(String model) {
        this.model = model;
        this.put("model", model);
    }

    public void setId(String id) {
        this.id = id;
        this.put("id", id);
    }

    public void setText(String text) {
        this.text = text;
        this.put("text", text);
    }

    public void setType(String type) {
        this.type = type;
        this.put("type", type);
    }


    public void setSegments(List<Segment> segments) {
        this.segments = segments;
        this.putPOJO("segments", segments);
    }

}
