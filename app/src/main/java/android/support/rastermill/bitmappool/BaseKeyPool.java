package android.support.rastermill.bitmappool;

import android.support.rastermill.bitmappool.Poolable;
import android.support.rastermill.util.Util;
import java.util.Queue;

/* loaded from: classes.dex */
abstract class BaseKeyPool<T extends Poolable> {
    private static final int MAX_SIZE = 20;
    private final Queue<T> keyPool = Util.createQueue(20);

    protected abstract T create();

    /* JADX INFO: Access modifiers changed from: protected */
    public T get() {
        T poll = this.keyPool.poll();
        return poll == null ? create() : poll;
    }

    public void offer(T t) {
        if (this.keyPool.size() < 20) {
            this.keyPool.offer(t);
        }
    }
}
