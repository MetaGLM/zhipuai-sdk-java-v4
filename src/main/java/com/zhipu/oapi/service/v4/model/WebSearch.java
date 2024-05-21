package com.zhipu.oapi.service.v4.model;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;


@Getter
public class WebSearch extends ObjectNode {


    /**
     * 是否启用搜索，默认启用搜索
     * 启用：true
     * 禁用：false
     */
    private Boolean enable;
    /**
     * 是否返回搜索结果，默认返回搜索结果
     * 返回：true
     * 不返回：false
     */
    private Boolean search_result;


    /**
     * 强制搜索自定义关键内容，此时模型会根据自定义搜索关键内容返回的结果作为背景知识来回答用户发起的对话。
     */
    private String search_query;


    public WebSearch(){
        super(JsonNodeFactory.instance);
    }
    public WebSearch(JsonNodeFactory nc, Map<String, JsonNode> kids) {
        super(nc, kids);
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
        this.put("enable",enable);
    }

    public void setSearch_result(Boolean search_result) {
        this.search_result = search_result;
        this.put("search_result",search_result);
    }

    public void setSearch_query(String search_query) {
        this.search_query = search_query;
        this.put("search_query",search_query);
    }
}
