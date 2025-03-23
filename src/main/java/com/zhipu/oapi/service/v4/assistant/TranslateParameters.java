package com.zhipu.oapi.service.v4.assistant;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TranslateParameters {

    /**
     * 翻译源语言
     */
    private String from;

    /**
     * 翻译目标语言
     */
    private String to;
}
