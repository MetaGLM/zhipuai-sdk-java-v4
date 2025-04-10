package com.zhipu.oapi.service.v4.realtime.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.service.v4.realtime.RealtimeServerEvent;
import com.zhipu.oapi.service.v4.realtime.object.RateLimitObj;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class RateLimitsUpdated extends RealtimeServerEvent {
    @JsonProperty("rate_limits")
    private List<RateLimitObj> rateLimits;

    public RateLimitsUpdated() {
        super.setType("rate_limits.updated");
        this.rateLimits = new ArrayList<>();
    }
}
