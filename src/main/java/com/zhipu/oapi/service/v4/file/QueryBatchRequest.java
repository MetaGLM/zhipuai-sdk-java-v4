package com.zhipu.oapi.service.v4.file;

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
public class QueryBatchRequest implements ClientRequest<Map<String,Object>> {

    private Integer limit;

    private String after;


    @Override
    public Map<String, Object> getOptions() {
        Map<String, Object> objectObjectMap = new HashMap<>();

        objectObjectMap.put("limit", limit);
        objectObjectMap.put("after", after);
        return objectObjectMap;
    }
}
