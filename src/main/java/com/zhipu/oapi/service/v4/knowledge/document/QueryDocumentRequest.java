package com.zhipu.oapi.service.v4.knowledge.document;

import com.zhipu.oapi.core.model.ClientRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import retrofit2.http.Query;

import java.util.HashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QueryDocumentRequest implements ClientRequest<Map<String,Object>> {

    private String knowledgeId;
    private String purpose;
    private Integer page;
    private Integer limit;
    private String order;


    @Override
    public Map<String, Object> getOptions() {
        Map<String, Object> objectObjectMap = new HashMap<>();

        if (knowledgeId != null){

            objectObjectMap.put("knowledge_id", knowledgeId);
        }

        if (purpose != null){
            objectObjectMap.put("purpose", purpose);
        }

        if (page != null){
            objectObjectMap.put("page", page);
        }
        if (limit != null){
            objectObjectMap.put("limit", limit);
        }
        if (order != null){
            objectObjectMap.put("order", order);
        }
        return objectObjectMap;
    }
}
