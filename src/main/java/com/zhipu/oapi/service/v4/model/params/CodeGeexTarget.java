package com.zhipu.oapi.service.v4.model.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class CodeGeexTarget {
    @JsonProperty("path")
    private String path;

    @JsonProperty("language")
    private String language;

    @JsonProperty("code_prefix")
    private String codePrefix;

    @JsonProperty("code_suffix")
    private String codeSuffix;

}
