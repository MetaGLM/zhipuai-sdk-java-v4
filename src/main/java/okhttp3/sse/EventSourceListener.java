package okhttp3.sse;

import okhttp3.Response;

import javax.annotation.Nullable;

public abstract class EventSourceListener {

    public EventSourceListener() {
    }

    public void onOpen(EventSource eventSource, Response response) {

    }

    public void onEvent(EventSource eventSource, @Nullable String id, @Nullable String type, String data, String meta ) {

    }

    public void onClosed(EventSource eventSource) {

    }

    public void onFailure(EventSource eventSource, @Nullable Throwable t, @Nullable Response response){

    }
}

