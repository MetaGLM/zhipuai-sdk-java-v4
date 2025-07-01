package com.zhipu.oapi.service.v4.realtime.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ToolObj {
    @JsonProperty("type")
    private String type;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("parameters")
    private ParameterObj parameters;

    public ToolObj() {
        this.type = "function";
        this.name = "search_engine";
        this.description = "基于给定的查询执行通用搜索";
        this.parameters = new ParameterObj();
    }
}
