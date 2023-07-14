package com.zhipu.oapi.service.v3;

import okhttp3.sse.EventSourceListener;

import java.util.concurrent.CountDownLatch;

public abstract class ModelEventSourceListener extends EventSourceListener {

    public abstract String getOutputText();

    public abstract CountDownLatch getCountDownLatch();

    public abstract SseMeta getMeta();
}
