package com.zhipu.oapi.service.v4.fine_turning;

import com.zhipu.oapi.core.model.ClientRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


/**
 * ClientRequest to create a fine tuning job
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QueryFineTuningJobRequest implements ClientRequest<Map<String, Object>> {


    private String jobId;

    private Integer limit;

    private String after;

    @Override
    public Map<String, Object> getOptions() {
        Map<String,Object> map = new HashMap<>();
        map.put("job_id", jobId);
        map.put("limit", limit);
        map.put("after", after);
        return map;
    }


}
