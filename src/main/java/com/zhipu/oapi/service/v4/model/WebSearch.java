package com.zhipu.oapi.service.v4.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WebSearch {


    /**
     * 是否启用搜索，默认启用搜索
     * 启用：true
     * 禁用：false
     */
    private Boolean enable;


    /**
     * 强制搜索自定义关键内容，此时模型会根据自定义搜索关键内容返回的结果作为背景知识来回答用户发起的对话。
     */
    private String search_query;
}
