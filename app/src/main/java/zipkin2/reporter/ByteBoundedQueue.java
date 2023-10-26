package zipkin2.reporter;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes3.dex */
final class ByteBoundedQueue<S> implements SpanWithSizeConsumer<S> {
    final Condition available;
    int count;
    final S[] elements;
    final ReentrantLock lock;
    final int maxBytes;
    final int maxSize;
    int readPos;
    int sizeInBytes;
    final int[] sizesInBytes;
    int writePos;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ByteBoundedQueue(int i, int i2) {
        ReentrantLock reentrantLock = new ReentrantLock(false);
        this.lock = reentrantLock;
        this.available = reentrantLock.newCondition();
        this.elements = (S[]) new Object[i];
        this.sizesInBytes = new int[i];
        this.maxSize = i;
        this.maxBytes = i2;
    }

    @Override // zipkin2.reporter.SpanWithSizeConsumer
    public boolean offer(S s, int i) {
        this.lock.lock();
        try {
            int i2 = this.count;
            int i3 = this.maxSize;
            if (i2 != i3) {
                int i4 = this.sizeInBytes;
                if (i4 + i <= this.maxBytes) {
                    S[] sArr = this.elements;
                    int i5 = this.writePos;
                    sArr[i5] = s;
                    int[] iArr = this.sizesInBytes;
                    int i6 = i5 + 1;
                    this.writePos = i6;
                    iArr[i5] = i;
                    if (i6 == i3) {
                        this.writePos = 0;
                    }
                    this.count = i2 + 1;
                    this.sizeInBytes = i4 + i;
                    this.available.signal();
                    return true;
                }
            }
            return false;
        } finally {
            this.lock.unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int drainTo(SpanWithSizeConsumer<S> spanWithSizeConsumer, long j) {
        try {
            this.lock.lockInterruptibly();
            while (this.count == 0) {
                if (j > 0) {
                    j = this.available.awaitNanos(j);
                } else {
                    this.lock.unlock();
                    return 0;
                }
            }
            int doDrain = doDrain(spanWithSizeConsumer);
            this.lock.unlock();
            return doDrain;
        } catch (InterruptedException unused) {
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int clear() {
        this.lock.lock();
        try {
            int i = this.count;
            this.writePos = 0;
            this.readPos = 0;
            this.sizeInBytes = 0;
            this.count = 0;
            Arrays.fill(this.elements, (Object) null);
            return i;
        } finally {
            this.lock.unlock();
        }
    }

    int doDrain(SpanWithSizeConsumer<S> spanWithSizeConsumer) {
        int i = 0;
        int i2 = 0;
        while (i < this.count) {
            S[] sArr = this.elements;
            int i3 = this.readPos;
            S s = sArr[i3];
            int i4 = this.sizesInBytes[i3];
            if (s == null || !spanWithSizeConsumer.offer(s, i4)) {
                break;
            }
            i++;
            i2 += i4;
            S[] sArr2 = this.elements;
            int i5 = this.readPos;
            sArr2[i5] = null;
            int i6 = i5 + 1;
            this.readPos = i6;
            if (i6 == sArr2.length) {
                this.readPos = 0;
            }
        }
        this.count -= i;
        this.sizeInBytes -= i2;
        return i;
    }
}
