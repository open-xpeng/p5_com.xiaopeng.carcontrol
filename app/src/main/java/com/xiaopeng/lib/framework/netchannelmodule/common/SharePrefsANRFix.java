package com.xiaopeng.lib.framework.netchannelmodule.common;

import android.text.TextUtils;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/* loaded from: classes2.dex */
public class SharePrefsANRFix {
    private static volatile boolean hooked = false;

    public static void fix() {
        if (hooked) {
            return;
        }
        hooked = true;
        try {
            Field declaredField = Class.forName("android.app.QueuedWork").getDeclaredField("sPendingWorkFinishers");
            if (declaredField != null) {
                declaredField.setAccessible(true);
                Object obj = declaredField.get(null);
                if (obj instanceof ConcurrentLinkedQueue) {
                    ConcurrentLinkedQueue<Runnable> concurrentLinkedQueue = new ConcurrentLinkedQueue<Runnable>() { // from class: com.xiaopeng.lib.framework.netchannelmodule.common.SharePrefsANRFix.1
                        @Override // java.util.concurrent.ConcurrentLinkedQueue, java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection, java.util.Queue
                        public boolean add(Runnable runnable) {
                            String name = runnable.getClass().getName();
                            if (TextUtils.isEmpty(name) || !name.contains("android.app.SharedPreferencesImpl$EditorImpl")) {
                                return super.add((AnonymousClass1) runnable);
                            }
                            return false;
                        }
                    };
                    concurrentLinkedQueue.addAll((ConcurrentLinkedQueue) obj);
                    declaredField.set(null, concurrentLinkedQueue);
                }
            }
        } catch (Exception unused) {
        }
        try {
            Field declaredField2 = Class.forName("android.app.QueuedWork").getDeclaredField("sFinishers");
            if (declaredField2 != null) {
                declaredField2.setAccessible(true);
                Object obj2 = declaredField2.get(null);
                if (obj2 instanceof LinkedList) {
                    LinkedList<Runnable> linkedList = new LinkedList<Runnable>() { // from class: com.xiaopeng.lib.framework.netchannelmodule.common.SharePrefsANRFix.2
                        @Override // java.util.LinkedList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque, java.util.Queue
                        public boolean add(Runnable runnable) {
                            String name = runnable.getClass().getName();
                            if (TextUtils.isEmpty(name) || !name.contains("android.app.SharedPreferencesImpl$EditorImpl")) {
                                return super.add((AnonymousClass2) runnable);
                            }
                            return false;
                        }
                    };
                    linkedList.addAll((LinkedList) obj2);
                    declaredField2.set(null, linkedList);
                }
            }
        } catch (Exception unused2) {
        }
    }
}
