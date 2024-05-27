package com.zhipu.oapi.service.v4.knowledge.document;

import com.zhipu.oapi.service.v4.knowledge.KnowledgeInfo;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

import java.util.List;

@Data
public class DocumentDataPage {

    private String total;

    private String object;

    private List<DocumentData> data;

    private ChatError error;
}