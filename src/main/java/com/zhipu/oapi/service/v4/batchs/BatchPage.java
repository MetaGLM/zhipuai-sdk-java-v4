package com.zhipu.oapi.service.v4.batchs;

import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

import java.util.List;

@Data
public class BatchPage {


    private String object;

    private List<Batch> data;

    private ChatError error;
}
