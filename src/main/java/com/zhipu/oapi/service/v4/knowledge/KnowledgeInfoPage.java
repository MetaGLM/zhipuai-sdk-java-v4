package com.zhipu.oapi.service.v4.knowledge;

import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

import java.util.List;

@Data
public class KnowledgeInfoPage {

    private String total;

    private String object;

    private List<KnowledgeInfo> data;

    private ChatError error;
}