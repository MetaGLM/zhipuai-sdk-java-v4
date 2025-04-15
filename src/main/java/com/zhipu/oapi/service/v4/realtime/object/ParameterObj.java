package com.zhipu.oapi.service.v4.realtime.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ParameterObj {
    @JsonProperty("type")
    private String type;

    @JsonProperty("properties")
    private Map<String, PropertyItem> properties;

    @JsonProperty("required")
    private List<String> required;

    public ParameterObj() {
        this.type = "object";
        this.properties = new HashMap<>();
        this.required = new ArrayList<>();
    }

    @Getter
    @Setter
    @EqualsAndHashCode(callSuper = false)
    public static class PropertyItem {
        @JsonProperty("type")
        private String type;

        @JsonProperty("description")
        private String description;

        public PropertyItem() {
            this.type = "string";
            this.description = "";
        }
    }
}
