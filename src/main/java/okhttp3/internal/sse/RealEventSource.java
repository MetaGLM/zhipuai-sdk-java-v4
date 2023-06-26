package okhttp3.internal.sse;

import okhttp3.*;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.sse.ServerSentEventReader.Callback;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

import javax.annotation.Nullable;
import java.io.IOException;


public class RealEventSource implements EventSource, Callback, okhttp3.Callback {

    private final Request request;
    private final EventSourceListener listener;
    @Nullable
    private Call call;

    public RealEventSource(Request request, EventSourceListener listener) {
        this.request = request;
        this.listener = listener;
    }

    public void connect(OkHttpClient client) {
        client = client.newBuilder().eventListener(EventListener.NONE).build();
        Response response = null;
        try{
            response = client.newCall(request).execute();
            processResponse(response);
        }catch (Exception e){
            this.listener.onFailure(this, e, response);
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {
        listener.onFailure(this, e, null);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        processResponse(response);
    }

    public void processResponse(Response response) {
        try {
            if (!response.isSuccessful()) {
                listener.onFailure(this, null, response);
                return;
            }
            ResponseBody body = response.body();
            MediaType contentType = body.contentType();
            if (!isEventStream(contentType)) {
                listener.onFailure(this, new IllegalStateException("Invalid content-type: " + contentType), response);
                return;
            }
            Exchange exchange = Internal.instance.exchange(response);
            if (exchange != null) {
                exchange.timeoutEarlyExit();
            }
            response = response.newBuilder().body(Util.EMPTY_RESPONSE).build();

            ServerSentEventReader reader = new ServerSentEventReader(body.source(), this);
            try {
                listener.onOpen(this, response);
                while (reader.processNextEvent()) {

                }
            } catch (Exception e) {
                listener.onFailure(this, e, response);
                return;
            }
            listener.onClosed(this);
        } finally {
            response.close();
        }
    }

    @Override
    public void onEvent(@Nullable String id, @Nullable String type, String data, String meta) {
        listener.onEvent(this, id, type, data, meta);
    }

    @Override
    public void onRetryChange(long var1) {
        // Ignored. We do not auto-retry
    }

    @Override
    public Request request() {
        return request;
    }

    @Override
    public void cancel() {
        call.cancel();
    }

    private static boolean isEventStream(@Nullable MediaType contentType) {
        return contentType != null && contentType.type().equals("text") && contentType.subtype()
                .equals("event-stream");
    }

}
