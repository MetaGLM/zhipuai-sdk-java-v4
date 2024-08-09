package com.zhipu.oapi.service.v4.knowledge;

import com.zhipu.oapi.core.model.ClientRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QueryKnowledgeRequest implements ClientRequest<Map<String,Object>> {

    private Integer page;

    private Integer size;


    @Override
    public Map<String, Object> getOptions() {
        Map<String, Object> objectObjectMap = new HashMap<>();

        objectObjectMap.put("page", page);
        objectObjectMap.put("size", size);
        return objectObjectMap;
    }
}
