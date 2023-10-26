package zipkin2.reporter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import zipkin2.Callback;

/* loaded from: classes3.dex */
public final class AwaitableCallback implements Callback<Void> {
    final CountDownLatch countDown = new CountDownLatch(1);
    final AtomicReference<Throwable> throwable = new AtomicReference<>();

    public void await() {
        Throwable th;
        boolean z = false;
        while (true) {
            try {
                this.countDown.await();
                th = this.throwable.get();
            } catch (InterruptedException unused) {
                z = true;
            } catch (Throwable th2) {
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th2;
            }
            if (th == null) {
                if (z) {
                    Thread.currentThread().interrupt();
                    return;
                }
                return;
            } else if (th instanceof Throwable) {
                if (th instanceof Error) {
                    throw ((Error) th);
                }
                if (!(th instanceof RuntimeException)) {
                    throw new RuntimeException(th);
                }
                throw ((RuntimeException) th);
            }
        }
    }

    @Override // zipkin2.Callback
    public void onSuccess(Void r1) {
        this.countDown.countDown();
    }

    @Override // zipkin2.Callback
    public void onError(Throwable th) {
        this.throwable.set(th);
        this.countDown.countDown();
    }
}
