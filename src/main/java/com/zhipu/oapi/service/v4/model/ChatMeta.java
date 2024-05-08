package com.zhipu.oapi.service.v4.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.service.v4.deserialize.MessageDeserializeFactory;
import lombok.Getter;

import java.util.Map;

/**
 * 超拟人对话
 * 角色及用户信息数据，该信息中 user_info：用户信息，bot_info：角色信息，bot_name：角色名，user_name：用户名
 *
 */
@Getter
public class ChatMeta extends ObjectNode {

    /**
     * 用户信息
     */
    private String user_info;
    /**
     * 角色信息
     */
    private String bot_info;
    /**
     * 角色名称
     */
    private String bot_name;
    /**
     *
     * 用户名称
     */
    private String user_name;

    public ChatMeta(){
        super(JsonNodeFactory.instance);
    }
    public ChatMeta(ObjectNode objectNode) {
        super(JsonNodeFactory.instance);
        if(objectNode.get("user_info") != null) {
            this.setUser_info(objectNode.get("user_info").asText());
        } else {
            this.setUser_info(null);
        }
        if(objectNode.get("bot_info") != null) {
            this.setBot_info(objectNode.get("bot_info").asText());
        } else {
            this.setBot_info(null);
        }
        if(objectNode.get("bot_name") != null) {
            this.setBot_name(objectNode.get("bot_name").asText());
        } else {
            this.setBot_name(null);
        }
        if(objectNode.get("user_name") != null) {
            this.setUser_name(objectNode.get("user_name").asText());
        } else {
            this.setUser_name(null);
        }
    }


    public ChatMeta(JsonNodeFactory nc) {
        super(nc);
    }

    public ChatMeta(JsonNodeFactory nc, Map<String, JsonNode> kids) {
        super(nc, kids);
    }

    public void setUser_info(String user_info) {
        this.user_info = user_info;
        this.put("user_info",user_info);
    }

    public void setBot_info(String bot_info) {
        this.bot_info = bot_info;
        this.put("bot_info",bot_info);
    }

    public void setBot_name(String bot_name) {
        this.bot_name = bot_name;
        this.put("bot_name",bot_name);
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
        this.put("user_name",user_name);
    }
}
