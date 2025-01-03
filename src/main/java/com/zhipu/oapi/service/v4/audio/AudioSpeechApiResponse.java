package com.zhipu.oapi.service.v4.audio;

import com.zhipu.oapi.core.model.ClientResponse;
import com.zhipu.oapi.service.v4.model.ChatError;
import lombok.Data;

import java.io.File;

@Data
public class AudioSpeechApiResponse implements ClientResponse<File> {

    private int code;

    private String msg;

    private boolean success;

    private File data;

    private ChatError error;

}
