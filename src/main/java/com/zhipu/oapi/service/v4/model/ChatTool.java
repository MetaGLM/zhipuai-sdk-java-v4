package com.zhipu.oapi.service.v4.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatTool {

    private String type;

    private ChatFunction function;


    private Retrieval retrieval;


    private WebSearch web_search;

}
