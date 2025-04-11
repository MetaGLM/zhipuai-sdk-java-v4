package com.zhipu.oapi.service.v4.realtime.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ErrorObj {
    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    @JsonProperty("param")
    private String param;

    @JsonProperty("type")
    private String type;

    public ErrorObj() {
        this.code = "";
        this.message = "";
        this.param = "";
        this.type = "";
    }
}
