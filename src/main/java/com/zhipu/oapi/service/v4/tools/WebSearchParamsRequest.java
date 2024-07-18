package com.zhipu.oapi.service.v4.tools;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zhipu.oapi.core.model.ClientRequest;
import com.zhipu.oapi.service.v4.CommonRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WebSearchParamsRequest extends CommonRequest implements ClientRequest<Map<String,Object>> {
    /**
     * 工具名：web-search-pro参数类型定义
     * Attributes:
     * 模型名称
     */
    @JsonProperty("model")
    private String model;

    /**
     * 是否流式
     */
    @JsonProperty("stream")
    private Boolean stream;

    /**
     * 包含历史对话上下文的内容，按照 {"role": "user", "content": "你好"} 的json 数组形式进行传参
     * 当前版本仅支持 User Message 单轮对话，工具会理解User Message并进行搜索，
     * 请尽可能传入不带指令格式的用户原始提问，以提高搜索准确率。
     */
    @JsonProperty("messages")
    private List<SearchChatMessage> messages;

    /**
     * 指定搜索范围，全网、学术等，默认全网
     */
    @JsonProperty("scope")
    private String scope;

    /**
     * 指定搜索用户地区 location 提高相关性
     */
    @JsonProperty("location")
    private String location;

    /**
     * 支持指定返回 N 天（1-30）更新的搜索结果
     */
    @JsonProperty("recent_days")
    private Integer recentDays;

    @Override
    public Map<String, Object> getOptions() {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("request_id", this.getRequestId());
        paramsMap.put("user_id", this.getUserId());
        paramsMap.put("messages", this.getMessages());
        paramsMap.put("model", this.getModel());
        paramsMap.put("stream", this.getStream());
        paramsMap.put("scope", this.getScope());
        paramsMap.put("location", this.getLocation());
        paramsMap.put("recent_days", this.getRecentDays());
        if(this.getExtraJson() !=null){
            paramsMap.putAll(this.getExtraJson());
        }
        return paramsMap;

    }
}
