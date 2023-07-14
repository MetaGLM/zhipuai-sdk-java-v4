//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package okhttp3.internal.sse;

import okhttp3.*;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.sse.ServerSentEventReader.Callback;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

import java.io.IOException;
import java.net.Proxy;

public final class RealEventSource implements EventSource, Callback, okhttp3.Callback {
    private final Request request;
    private final EventSourceListener listener;
    private Call call;

    public RealEventSource(Request request, EventSourceListener listener) {
        this.request = request;
        this.listener = listener;
    }

    public void connect(OkHttpClient client) {
        client = client.newBuilder().eventListener(EventListener.NONE).build();
        this.call = client.newCall(this.request);
        this.call.enqueue(this);
    }

    public void onResponse(Call call, Response response) {
        this.processResponse(response);
    }

    public void processResponse(Response response) {
        try {
            if (!response.isSuccessful()) {
                this.listener.onFailure(this, (Throwable) null, response);
            } else {
                ResponseBody body = response.body();
                MediaType contentType = body.contentType();
                if (!isEventStream(contentType)) {
                    this.listener.onFailure(this, new IllegalStateException("Invalid content-type: " + contentType), response);
                } else {
                    Exchange exchange = Internal.instance.exchange(response);
                    if (exchange != null) {
                        exchange.timeoutEarlyExit();
                    }

                    response = response.newBuilder().body(Util.EMPTY_RESPONSE).build();
                    ServerSentEventReader reader = new ServerSentEventReader(body.source(), this);

                    try {
                        this.listener.onOpen(this, response);

                        while (reader.processNextEvent()) {
                        }
                    } catch (Exception var10) {
                        this.listener.onFailure(this, var10, response);
                        return;
                    }

                    this.listener.onClosed(this);
                }
            }
        } finally {
            response.close();
        }
    }

    private static boolean isEventStream(MediaType contentType) {
        return contentType != null && contentType.type().equals("text") && contentType.subtype().equals("event-stream");
    }

    public void onFailure(Call call, IOException e) {
        this.listener.onFailure(this, e, (Response) null);
    }

    public Request request() {
        return this.request;
    }

    public void cancel() {
        this.call.cancel();
    }

    @Override
    public void onEvent(String id,String type, String data, String meta) {
        this.listener.onEvent(this, id, type, data, meta);
    }

    public void onRetryChange(long timeMs) {
    }
}
