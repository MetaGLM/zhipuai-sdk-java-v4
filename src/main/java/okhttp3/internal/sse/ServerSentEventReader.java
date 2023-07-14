//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package okhttp3.internal.sse;

import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;

import java.io.IOException;

public final class ServerSentEventReader {
    private static final ByteString CRLF = ByteString.encodeUtf8("\r\n");
    private static final ByteString DATA = ByteString.encodeUtf8("data");
    private static final ByteString META = ByteString.encodeUtf8("meta");
    private static final ByteString ID = ByteString.encodeUtf8("id");
    private static final ByteString EVENT = ByteString.encodeUtf8("event");
    private static final ByteString RETRY = ByteString.encodeUtf8("retry");
    private final BufferedSource source;
    private final Callback callback;
    private String lastId = null;

    public ServerSentEventReader(BufferedSource source, Callback callback) {
        if (source == null) {
            throw new NullPointerException("source == null");
        } else if (callback == null) {
            throw new NullPointerException("callback == null");
        } else {
            this.source = source;
            this.callback = callback;
        }
    }

    boolean processNextEvent() throws IOException {
        String id = this.lastId;
        String type = null;
        Buffer data = new Buffer();
        Buffer meta = new Buffer();

        while (true) {
            long lineEnd = this.source.indexOfElement(CRLF);
            if (lineEnd == -1L) {
                return false;
            }
            switch (source.getBuffer().getByte(0)) {
                case '\r':
                case '\n':
                    completeEvent(id, type, data,meta);
                    return true;
                case 'm':
                    if (this.isKey(META)) {
                        this.parseData(meta, lineEnd);
                        continue;
                    }
                case 'd':
                    if (isKey(DATA)) {
                        parseData(data, lineEnd);
                        continue;
                    }
                    break;

                case 'e':
                    if (isKey(EVENT)) {
                        type = parseEvent(lineEnd);
                        continue;
                    }
                    break;

                case 'i':
                    if (isKey(ID)) {
                        id = parseId(lineEnd);
                        continue;
                    }
                    break;

                case 'r':
                    if (isKey(RETRY)) {
                        parseRetry(lineEnd);
                        continue;
                    }
                    break;
            }

            source.skip(lineEnd);
            skipCrAndOrLf();
        }
    }

    private void completeEvent(String id, String type, Buffer data, Buffer meta) throws IOException {
        this.skipCrAndOrLf();
        if (data.size() != 0L) {
            this.lastId = id;
            data.skip(1L);
            if (meta.size() != 0L) {
                this.callback.onEvent(id, type, data.readUtf8(), meta.readUtf8());
            } else {
                this.callback.onEvent(id, type, data.readUtf8(), null);
            }
        }

    }

    private void parseData(Buffer data, long end) throws IOException {
        data.writeByte(10);
        end -= this.skipNameAndDivider(4L);
        this.source.readFully(data, end);
        this.skipCrAndOrLf();
    }

    private String parseEvent(long end) throws IOException {
        String type = null;
        end -= this.skipNameAndDivider(5L);
        if (end != 0L) {
            type = this.source.readUtf8(end);
        }

        this.skipCrAndOrLf();
        return type;
    }

    private String parseId(long end) throws IOException {
        end -= this.skipNameAndDivider(2L);
        String id;
        if (end != 0L) {
            id = this.source.readUtf8(end);
        } else {
            id = null;
        }

        this.skipCrAndOrLf();
        return id;
    }

    private void parseRetry(long end) throws IOException {
        end -= this.skipNameAndDivider(5L);
        String retryString = this.source.readUtf8(end);
        long retryMs = -1L;

        try {
            retryMs = Long.parseLong(retryString);
        } catch (NumberFormatException var7) {
        }

        if (retryMs != -1L) {
            this.callback.onRetryChange(retryMs);
        }

        this.skipCrAndOrLf();
    }

    private boolean isKey(ByteString key) throws IOException {
        if (!this.source.rangeEquals(0L, key)) {
            return false;
        } else {
            byte nextByte = this.source.getBuffer().getByte((long) key.size());
            return nextByte == 58 || nextByte == 13 || nextByte == 10;
        }
    }

    private void skipCrAndOrLf() throws IOException {
        if ((this.source.readByte() & 255) == 13 && this.source.request(1L) && this.source.getBuffer().getByte(0L) == 10) {
            this.source.skip(1L);
        }

    }

    private long skipNameAndDivider(long length) throws IOException {
        this.source.skip(length);
        if (this.source.getBuffer().getByte(0L) == 58) {
            this.source.skip(1L);
            ++length;
            if (this.source.getBuffer().getByte(0L) == 32) {
                this.source.skip(1L);
                ++length;
            }
        }

        return length;
    }

    public interface Callback {
        void onEvent(String var1,String var2, String var3, String var4);

        void onRetryChange(long var1);
    }
}
