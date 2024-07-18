package com.zhipu.oapi.service.v4.file;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.embedding.EmbeddingResult;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

@Data
public class FileApiResponse   implements ClientResponse<File> {
    private int code;
    private String msg;
    private boolean success;

    private File data;

    private ChatError error;
}
