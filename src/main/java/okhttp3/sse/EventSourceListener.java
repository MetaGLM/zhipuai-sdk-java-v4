//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package okhttp3.sse;

import okhttp3.Response;

public abstract class EventSourceListener {
    public EventSourceListener() {
    }

    public void onOpen(EventSource eventSource, Response response) {
    }

    public void onEvent(EventSource eventSource,String id,String type, String data,String meta) {
    }

    public void onClosed(EventSource eventSource) {
    }

    public void onFailure(EventSource eventSource,Throwable t,Response response) {
    }
}
