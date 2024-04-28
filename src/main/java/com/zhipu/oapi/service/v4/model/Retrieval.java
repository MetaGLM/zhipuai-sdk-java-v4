package com.zhipu.oapi.service.v4.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import lombok.Getter;

import java.util.Map;

@Getter
public class Retrieval extends ObjectNode {


    /**
     * 当涉及到知识库ID时，请前往开放平台的知识库模块进行创建或获取。
     */
    private String knowledge_id;


    /**
     *请求模型时的知识库模板，默认模板：
     * 从文档
     * """
     * {{ knowledge}}
     * """
     * 中找问题
     * """
     * {{question}}
     * """
     * 的答案，找到答案就仅使用文档语句回答问题，找不到答案就用自身知识回答并且告诉用户该信息不是来自文档。
     * 不要复述问题，直接开始回答
     *
     * 注意：用户自定义模板时，知识库内容占位符 和用户侧问题占位符必是{{ knowledge}} 和{{question}}，其他模板内容用户可根据实际场景定义
     */
    private String prompt_template;

    public Retrieval(){
        super(JsonNodeFactory.instance);
    }
    public Retrieval(JsonNodeFactory nc, Map<String, JsonNode> kids) {
        super(nc, kids);
    }

    public void setKnowledge_id(String knowledge_id) {
        this.knowledge_id = knowledge_id;
        this.put("knowledge_id",knowledge_id);
    }

    public void setPrompt_template(String prompt_template) {
        this.prompt_template = prompt_template;
        this.put("prompt_template",prompt_template);
    }
}
