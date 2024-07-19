package com.zhipu.oapi.service.v4.videos;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

@Data
public class VideosResponse implements ClientResponse<VideoObject> {
    private int code;
    private String msg;
    private boolean success;

    private VideoObject data;

    private ChatError error;
}
