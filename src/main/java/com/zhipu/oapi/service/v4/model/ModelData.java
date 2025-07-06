package com.zhipu.oapi.service.v4.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import com.zhipu.oapi.service.v4.deserialize.ModelDataDeserializer;
import com.zhipu.oapi.service.v4.web_search.WebSearchResp;
import lombok.Getter;
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
    private String agent_id;
    private String conversation_id;
    private String async_id;
    private String status;
    @JsonProperty("web_search")
    private List<WebSearchResp> webSearch;

    private String type;
    private String text;
    private List<Segment> segments;
    private String delta;


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
        if(objectNode.get("agent_id") != null) {
            this.setAgent_id(objectNode.get("agent_id").asText());
        } else {
            this.setAgent_id(null);
        }
        if(objectNode.get("conversation_id") != null) {
            this.setConversation_id(objectNode.get("conversation_id").asText());
        } else {
            this.setConversation_id(null);
        }
        if(objectNode.get("async_id") != null) {
            this.setAsync_id(objectNode.get("async_id").asText());
        } else {
            this.setAsync_id(null);
        }
        if(objectNode.get("status") != null) {
            this.setStatus(objectNode.get("status").asText());
        } else {
            this.setStatus(null);
        }
        if(objectNode.get("web_search") != null) {
            this.setWebSearch(objectMapper.convertValue(objectNode.get("web_search"), List.class));
        } else {
            this.setWebSearch(null);
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
        if (objectNode.get("delta") != null) {
            this.setDelta(objectMapper.convertValue(objectNode.get("delta"), String.class));
        } else {
            this.setDelta(null);
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

    public void setWebSearch(List<WebSearchResp> webSearch) {
        this.webSearch = webSearch;
        this.putPOJO("web_search", webSearch);
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
    public void setDelta(String delta) {
        this.delta = delta;
        this.put("delta", delta);
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
        this.put("agent_id", agent_id);
    }

    public void setConversation_id(String conversation_id) {
        this.conversation_id = conversation_id;
        this.put("conversation_id", conversation_id);
    }

    public void setAsync_id(String async_id) {
        this.async_id = async_id;
        this.put("async_id", async_id);
    }

    public void setStatus(String status) {
        this.status = status;
        this.put("status", status);
    }

}
