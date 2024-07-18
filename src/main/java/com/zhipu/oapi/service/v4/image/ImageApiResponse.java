package com.zhipu.oapi.service.v4.image;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

@Data
public class ImageApiResponse implements ClientResponse<ImageResult> {
    private int code;
    private String msg;
    private boolean success;

    private ImageResult data;

    private ChatError error;
}
