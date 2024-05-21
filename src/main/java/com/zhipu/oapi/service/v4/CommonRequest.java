package com.zhipu.oapi.service.v4;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * common extra json
 * other ZhiPuAI request class should extend_s this class
 */

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class CommonRequest {


    /**
     * 由用户端传参，需保证唯一性；用于区分每次请求的唯一标识，用户端不传时平台会默认生成
     */
    private String requestId;

    /**
     * A unique identifier representing your end-user, which will help ZhiPuAI to monitor and detect abuse.
     */
    private String userId;

    private Map<String,Object> extraJson;
}
