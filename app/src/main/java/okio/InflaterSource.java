package okio;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/* loaded from: classes3.dex */
public final class InflaterSource implements Source {
    private int bufferBytesHeldByInflater;
    private boolean closed;
    private final Inflater inflater;
    private final BufferedSource source;

    public InflaterSource(Source source, Inflater inflater) {
        this(Okio.buffer(source), inflater);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public InflaterSource(BufferedSource bufferedSource, Inflater inflater) {
        if (bufferedSource == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (inflater == null) {
            throw new IllegalArgumentException("inflater == null");
        }
        this.source = bufferedSource;
        this.inflater = inflater;
    }

    @Override // okio.Source
    public long read(Buffer buffer, long j) throws IOException {
        Segment writableSegment;
        int i = (j > 0L ? 1 : (j == 0L ? 0 : -1));
        if (i < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        }
        if (this.closed) {
            throw new IllegalStateException("closed");
        }
        if (i == 0) {
            return 0L;
        }
        while (true) {
            boolean refill = refill();
            try {
                writableSegment = buffer.writableSegment(1);
                int inflate = this.inflater.inflate(writableSegment.data, writableSegment.limit, (int) Math.min(j, 8192 - writableSegment.limit));
                if (inflate > 0) {
                    writableSegment.limit += inflate;
                    long j2 = inflate;
                    buffer.size += j2;
                    return j2;
                } else if (this.inflater.finished() || this.inflater.needsDictionary()) {
                    break;
                } else if (refill) {
                    throw new EOFException("source exhausted prematurely");
                }
            } catch (DataFormatException e) {
                throw new IOException(e);
            }
        }
        releaseInflatedBytes();
        if (writableSegment.pos == writableSegment.limit) {
            buffer.head = writableSegment.pop();
            SegmentPool.recycle(writableSegment);
            return -1L;
        }
        return -1L;
    }

    public final boolean refill() throws IOException {
        if (this.inflater.needsInput()) {
            releaseInflatedBytes();
            if (this.inflater.getRemaining() != 0) {
                throw new IllegalStateException("?");
            }
            if (this.source.exhausted()) {
                return true;
            }
            Segment segment = this.source.buffer().head;
            this.bufferBytesHeldByInflater = segment.limit - segment.pos;
            this.inflater.setInput(segment.data, segment.pos, this.bufferBytesHeldByInflater);
            return false;
        }
        return false;
    }

    private void releaseInflatedBytes() throws IOException {
        int i = this.bufferBytesHeldByInflater;
        if (i == 0) {
            return;
        }
        int remaining = i - this.inflater.getRemaining();
        this.bufferBytesHeldByInflater -= remaining;
        this.source.skip(remaining);
    }

    @Override // okio.Source
    public Timeout timeout() {
        return this.source.timeout();
    }

    @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (this.closed) {
            return;
        }
        this.inflater.end();
        this.closed = true;
        this.source.close();
    }
}
