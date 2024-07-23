package com.zhipu.oapi.service.v4.model.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CodeGeexExtra {
    @JsonProperty("target")
    private CodeGeexTarget target;

    @JsonProperty("contexts")
    private List<CodeGeexContext> contexts;

}