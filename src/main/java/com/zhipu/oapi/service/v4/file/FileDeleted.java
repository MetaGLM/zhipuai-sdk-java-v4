package com.zhipu.oapi.service.v4.file;

import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

@Data
public class FileDeleted {
    private String id;
    private boolean deleted;
    private final String object = "file";
    private ChatError error;

}
