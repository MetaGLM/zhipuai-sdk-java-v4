package com.zhipu.oapi.service.v3;

import okhttp3.sse.EventSourceListener;

public abstract class ModelEventSourceListener extends EventSourceListener {

    public abstract String getOutputText();
}
