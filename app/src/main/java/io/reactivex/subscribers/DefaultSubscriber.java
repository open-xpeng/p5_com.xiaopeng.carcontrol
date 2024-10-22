package io.reactivex.subscribers;

import io.reactivex.FlowableSubscriber;
import io.reactivex.internal.subscriptions.SubscriptionHelper;
import io.reactivex.internal.util.EndConsumerHelper;
import org.reactivestreams.Subscription;
import org.tukaani.xz.common.Util;

/* loaded from: classes3.dex */
public abstract class DefaultSubscriber<T> implements FlowableSubscriber<T> {
    private Subscription s;

    @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
    public final void onSubscribe(Subscription subscription) {
        if (EndConsumerHelper.validate(this.s, subscription, getClass())) {
            this.s = subscription;
            onStart();
        }
    }

    protected final void request(long j) {
        Subscription subscription = this.s;
        if (subscription != null) {
            subscription.request(j);
        }
    }

    protected final void cancel() {
        Subscription subscription = this.s;
        this.s = SubscriptionHelper.CANCELLED;
        subscription.cancel();
    }

    protected void onStart() {
        request(Util.VLI_MAX);
    }
}
