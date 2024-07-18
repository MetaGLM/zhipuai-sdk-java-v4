package com.zhipu.oapi.service.v4.batchs;

import com.zhipu.oapi.core.model.ClientRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.Map;
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BatchRequest implements ClientRequest<Map<String,Object>> {

    private String batchId;

    @Override
    public Map<String, Object> getOptions() {
        return Collections.singletonMap("batchId", batchId);
    }
}
