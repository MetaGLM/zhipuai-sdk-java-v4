package com.zhipu.oapi.service.v4.embedding;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.file.File;
import com.zhipu.oapi.service.v4.image.ImageResult;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

@Data
public class EmbeddingApiResponse implements ClientResponse<EmbeddingResult> {
    private int code;
    private String msg;
    private boolean success;

    private EmbeddingResult data;

    private ChatError error;
}
