package com.zhipu.oapi.service.v4.agents;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zhipu.oapi.core.model.ClientRequest;
import com.zhipu.oapi.service.v4.CommonRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.SensitiveWordCheckRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AgentsCompletionRequest extends CommonRequest implements ClientRequest<Map<String, Object>> {

    /**
     * 智能体id
     */
    private String agent_id;


    /**
     * 消息体
     */
     private List<ChatMessage> messages;


    /**
     * 同步调用：false，sse调用：true
     */
    private Boolean stream;


    /**
     * 敏感词检测控制
     */
    private SensitiveWordCheckRequest sensitiveWordCheck;

    /**
     * 智能体业务字段
     * @return
     */
    private ObjectNode custom_variables;

    @Override
    public Map<String, Object> getOptions() {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("agent_id", this.getAgent_id());
        paramsMap.put("request_id", this.getRequestId());
        paramsMap.put("user_id", this.getUserId());
        paramsMap.put("messages", this.getMessages());
        paramsMap.put("stream", this.getStream());
        paramsMap.put("sensitive_word_check", this.getSensitiveWordCheck());
        if(this.getExtraJson() !=null){
            paramsMap.putAll(this.getExtraJson());
        }
        return paramsMap;
    }
}
