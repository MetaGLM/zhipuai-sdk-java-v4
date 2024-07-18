package com.zhipu.oapi.service.v4.fine_turning;


import com.zhipu.oapi.core.model.ClientRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FineTuningJobIdRequest implements ClientRequest<Map<String, Object>> {


    private String jobId;

    @Override
    public Map<String, Object> getOptions() {
        Map<String,Object> map = new HashMap<>();
        map.put("job_id", jobId);
        return map;
    }
}
