package com.zhipu.oapi.service.v4.model;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;

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


    /**
     * 指定搜索引擎输出结果的Prompt
     */
    private String search_prompt;

    /**
     * 指定搜索引擎
     */
    private String search_engine;

    /**
     * 是否必须要获得搜索结果后进行回答
     */
    private Boolean require_search;

    /**
     * 搜索结果输出位置
     */
    private String result_sequence;

    /**
     * 返回结果的条数
     */
    private Integer count;

    /**
     * 限定搜索结果的范围
     */
    private String search_domain_filter;

    /**
     * 限定搜索结果的范围
     */
    private String search_recency_filter;

    /**
     * 控制网页摘要的字数
     */
    private String content_size;


    /**
     * 是否开启意图识别
     */
    private Boolean search_intent;


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

    public void setSearch_prompt(String search_prompt) {
        this.search_prompt = search_prompt;
        this.put("search_prompt",search_prompt);
    }
    public void setSearch_engine(String search_engine) {
        this.search_engine = search_engine;
        this.put("search_engine",search_engine);
    }

    public void setRequire_search(Boolean require_search) {
        this.require_search = require_search;
        this.put("require_search",require_search);
    }

    public void setResult_sequence(String result_sequence) {
        this.result_sequence = result_sequence;
        this.put("result_sequence",result_sequence);
    }

    public void setCount(Integer count) {
        this.count = count;
        this.put("count",count);
    }

    public void setSearch_domain_filter(String search_domain_filter) {
        this.search_domain_filter = search_domain_filter;
        this.put("search_domain_filter",search_domain_filter);
    }

    public void setSearch_recency_filter(String search_recency_filter) {
        this.search_recency_filter = search_recency_filter;
        this.put("search_recency_filter",search_recency_filter);
    }

    public void setContent_size(String content_size) {
        this.content_size = content_size;
        this.put("content_size",content_size);
    }

    public void setSearch_intent(Boolean search_intent) {
        this.search_intent = search_intent;
        this.put("search_intent",search_intent);
    }
}
