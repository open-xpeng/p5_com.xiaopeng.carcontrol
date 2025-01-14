package io.reactivex.internal.operators.observable;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.LambdaObserver;
import io.reactivex.internal.util.BlockingHelper;
import io.reactivex.internal.util.BlockingIgnoringReceiver;
import io.reactivex.internal.util.ExceptionHelper;

/* loaded from: classes3.dex */
public final class ObservableBlockingSubscribe {
    private ObservableBlockingSubscribe() {
        throw new IllegalStateException("No instances!");
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static <T> void subscribe(io.reactivex.ObservableSource<? extends T> r4, io.reactivex.Observer<? super T> r5) {
        /*
            java.util.concurrent.LinkedBlockingQueue r0 = new java.util.concurrent.LinkedBlockingQueue
            r0.<init>()
            io.reactivex.internal.observers.BlockingObserver r1 = new io.reactivex.internal.observers.BlockingObserver
            r1.<init>(r0)
            r5.onSubscribe(r1)
            r4.subscribe(r1)
        L10:
            boolean r2 = r1.isDisposed()
            if (r2 == 0) goto L17
            goto L3a
        L17:
            java.lang.Object r2 = r0.poll()
            if (r2 != 0) goto L2a
            java.lang.Object r2 = r0.take()     // Catch: java.lang.InterruptedException -> L22
            goto L2a
        L22:
            r4 = move-exception
            r1.dispose()
            r5.onError(r4)
            return
        L2a:
            boolean r3 = r1.isDisposed()
            if (r3 != 0) goto L3a
            java.lang.Object r3 = io.reactivex.internal.observers.BlockingObserver.TERMINATED
            if (r4 == r3) goto L3a
            boolean r2 = io.reactivex.internal.util.NotificationLite.acceptFull(r2, r5)
            if (r2 == 0) goto L10
        L3a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.operators.observable.ObservableBlockingSubscribe.subscribe(io.reactivex.ObservableSource, io.reactivex.Observer):void");
    }

    public static <T> void subscribe(ObservableSource<? extends T> observableSource) {
        BlockingIgnoringReceiver blockingIgnoringReceiver = new BlockingIgnoringReceiver();
        LambdaObserver lambdaObserver = new LambdaObserver(Functions.emptyConsumer(), blockingIgnoringReceiver, blockingIgnoringReceiver, Functions.emptyConsumer());
        observableSource.subscribe(lambdaObserver);
        BlockingHelper.awaitForComplete(blockingIgnoringReceiver, lambdaObserver);
        Throwable th = blockingIgnoringReceiver.error;
        if (th != null) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }

    public static <T> void subscribe(ObservableSource<? extends T> observableSource, Consumer<? super T> consumer, Consumer<? super Throwable> consumer2, Action action) {
        ObjectHelper.requireNonNull(consumer, "onNext is null");
        ObjectHelper.requireNonNull(consumer2, "onError is null");
        ObjectHelper.requireNonNull(action, "onComplete is null");
        subscribe(observableSource, new LambdaObserver(consumer, consumer2, action, Functions.emptyConsumer()));
    }
}
