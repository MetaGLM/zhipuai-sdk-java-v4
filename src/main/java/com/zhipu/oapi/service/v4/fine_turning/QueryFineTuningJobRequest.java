package com.zhipu.oapi.service.v4.fine_turning;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Request to create a fine tuning job
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QueryFineTuningJobRequest {


    private String jobId;

    private Integer limit;

    private String after;



}
