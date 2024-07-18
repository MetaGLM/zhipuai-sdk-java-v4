package com.zhipu.oapi.service.v4.model;

import com.zhipu.oapi.core.model.ClientRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QueryModelResultRequest implements ClientRequest<String> {

    private String taskId;


    @Override
    public String getOptions() {
        return taskId;
    }
}
