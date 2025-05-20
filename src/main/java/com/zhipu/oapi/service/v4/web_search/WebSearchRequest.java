package com.zhipu.oapi.service.v4.web_search;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.core.model.ClientRequest;
import com.zhipu.oapi.service.v4.model.SensitiveWordCheckRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WebSearchRequest implements ClientRequest<WebSearchRequest> {

    /**
     * 搜索引擎
     */
    @JsonProperty("search_engine")
    private String searchEngine;

    /**
     * 搜索query文本
     */
    @JsonProperty("search_query")
    private String searchQuery;

    /**
     * 由用户端传参，需保证唯一性；用于区分每次请求的唯一标识，用户端不传时平台会默认生成。
     */
    @JsonProperty("request_id")
    private String requestId;
    /**
     * 用户端
     */
    @JsonProperty("user_id")
    private String userId;

    /**
     * 敏感词检测控制
     */
    @JsonProperty("sensitive_word_check")
    private SensitiveWordCheckRequest sensitiveWordCheck;

    /**
     * 返回结果的条数
     */
    @JsonProperty("count")
    private Integer count;

    /**
     * 限定搜索结果的范围
     */
    @JsonProperty("search_domain_filter")
    private String searchDomainFilter;

    /**
     * 限定搜索结果的范围
     */
    @JsonProperty("search_recency_filter")
    private String searchRecencyFilter;

    /**
     * 控制网页摘要的字数
     */
    @JsonProperty("content_size")
    private String contentSize;

    @Override
    public WebSearchRequest getOptions() {
        return this;
    }
}
