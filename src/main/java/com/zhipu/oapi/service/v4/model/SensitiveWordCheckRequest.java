package com.zhipu.oapi.service.v4.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SensitiveWordCheckRequest {


    /**
     * 敏感词类型，当前仅支持ALL
     * 所有：ALL
     */
    private String type;


    /**
     * 敏感词启用禁用状态
     * 启用：ENABLE
     * 禁用：DISABLE
     * 备注：默认开启敏感词校验，如果要关闭敏感词校验，需联系商务获取对应权限，否则敏感词禁用不生效。
     */
    private String status;


}
