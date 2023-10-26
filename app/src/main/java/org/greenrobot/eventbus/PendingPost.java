package org.greenrobot.eventbus;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
final class PendingPost {
    private static final List<PendingPost> pendingPostPool = new ArrayList();
    Object event;
    PendingPost next;
    Subscription subscription;

    private PendingPost(Object obj, Subscription subscription) {
        this.event = obj;
        this.subscription = subscription;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static PendingPost obtainPendingPost(Subscription subscription, Object obj) {
        List<PendingPost> list = pendingPostPool;
        synchronized (list) {
            int size = list.size();
            if (size > 0) {
                PendingPost remove = list.remove(size - 1);
                remove.event = obj;
                remove.subscription = subscription;
                remove.next = null;
                return remove;
            }
            return new PendingPost(obj, subscription);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void releasePendingPost(PendingPost pendingPost) {
        pendingPost.event = null;
        pendingPost.subscription = null;
        pendingPost.next = null;
        List<PendingPost> list = pendingPostPool;
        synchronized (list) {
            if (list.size() < 10000) {
                list.add(pendingPost);
            }
        }
    }
}
