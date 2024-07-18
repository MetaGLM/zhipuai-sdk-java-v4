package com.zhipu.oapi.service.v4.fine_turning;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.core.model.ClientRequest;
import lombok.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * ClientRequest to create a fine tuning job
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QueryPersonalFineTuningJobRequest implements ClientRequest<Map<String, Object>> {

    private Integer limit;

    private String after;


    @Override
    public Map<String, Object> getOptions() {
        Map<String,Object> map = new HashMap<>();
        map.put("limit", limit);
        map.put("after", after);
        return map;
    }
}
