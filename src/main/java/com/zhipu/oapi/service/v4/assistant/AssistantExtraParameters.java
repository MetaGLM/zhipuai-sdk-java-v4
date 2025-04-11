package com.zhipu.oapi.service.v4.assistant;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssistantExtraParameters {

    /**
     * 翻译智能体参数
     */
    private TranslateParameters translate;

}
