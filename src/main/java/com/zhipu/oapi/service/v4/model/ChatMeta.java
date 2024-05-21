package com.zhipu.oapi.service.v4.model;

import lombok.Data;

/**
 * 超拟人对话
 * 角色及用户信息数据，该信息中 user_info：用户信息，bot_info：角色信息，bot_name：角色名，user_name：用户名
 *
 */
@Data
public class ChatMeta {

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

}
