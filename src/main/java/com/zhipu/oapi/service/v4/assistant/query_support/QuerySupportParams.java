package com.zhipu.oapi.service.v4.assistant.query_support;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.core.model.ClientRequest;
import com.zhipu.oapi.service.v4.CommonRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuerySupportParams  extends CommonRequest implements ClientRequest<QuerySupportParams> {

    @JsonProperty("assistant_id_list")
    private List<String> assistantIdList;

    @Override
    public QuerySupportParams getOptions() {
        return this;
    }
}
