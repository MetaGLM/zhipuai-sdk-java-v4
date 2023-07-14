package com.zhipu.oapi.service.v3;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zhipu.oapi.utils.StringUtils;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 监听模型结果，等到所有事件完成后统一返回。效果类似同步调用，如您有其他Listener需求可以联系我们，或自己对Listener进行扩展
 */
public class StandardEventSourceListener extends ModelEventSourceListener {

    private static final Logger logger = LoggerFactory.getLogger(StandardEventSourceListener.class);

    private String outputText = "";

    private boolean incremental;

    /*    private EventStatus status = new EventStatus();*/

    protected CountDownLatch countDownLatch = new CountDownLatch(1);

    private Gson gson = new Gson();

    private SseMeta meta;

    @Override
    public void onOpen(EventSource eventSource, Response response) {
        logger.info("server start sending events");
    }

    @Override
    public void onEvent(EventSource eventSource, String id, String type, String data, String meta) {
        //String formatData = gson.fromJson(data, JsonObject.class).get(ModelConstants.sseFormat).getAsString();
        if ("finish".equals(type) && StringUtils.isNotEmpty(meta)) {
            this.meta = gson.fromJson(meta, SseMeta.class);
        }
        if (this.isIncremental()) {
            outputText += data;
        } else {
            outputText = data;
        }
    }

    @Override
    public void onClosed(EventSource eventSource) {
        logger.info("server stream closed");
        try {
/*            synchronized (status) {
                status.setStatus(EventStatus.StatusClosed);
                status.notify();
            }*/
            eventSource.cancel();
        } finally {
            countDownLatch.countDown();
        }
    }

    @Override
    public void onFailure(EventSource eventSource, Throwable t, Response response) {
        try {
            logger.error("sse connection fail");
/*            synchronized (status) {
                status.setStatus(EventStatus.StatusFailed);
                status.notify();
            }*/
            eventSource.cancel();
        } finally {
            countDownLatch.countDown();
        }
    }

    @Override
    public String getOutputText() {
        return outputText;
    }

    public SseMeta getMeta() {
        return this.meta;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public boolean isIncremental() {
        return incremental;
    }

    public void setIncremental(boolean incremental) {
        this.incremental = incremental;
    }
/*
    private static class EventStatus {

        public static final String StatusClosed = "closed";
        public static final String StatusFailed = "failed";

        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }*/
}
