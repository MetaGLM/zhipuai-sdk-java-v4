package com.zhipu.oapi.service.v4.file;

import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

import java.util.List;

@Data
public class QueryFileResult {

    private String object;

    private List<File> data;

    private ChatError error;
}
