package org.greenrobot.eventbus;

import java.lang.reflect.Method;

/* loaded from: classes3.dex */
public class SubscriberMethod {
    final Class<?> eventType;
    final Method method;
    String methodString;
    final int priority;
    final boolean sticky;
    final ThreadMode threadMode;

    public SubscriberMethod(Method method, Class<?> cls, ThreadMode threadMode, int i, boolean z) {
        this.method = method;
        this.threadMode = threadMode;
        this.eventType = cls;
        this.priority = i;
        this.sticky = z;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SubscriberMethod) {
            checkMethodString();
            SubscriberMethod subscriberMethod = (SubscriberMethod) obj;
            subscriberMethod.checkMethodString();
            return this.methodString.equals(subscriberMethod.methodString);
        }
        return false;
    }

    private synchronized void checkMethodString() {
        if (this.methodString == null) {
            StringBuilder sb = new StringBuilder(64);
            sb.append(this.method.getDeclaringClass().getName());
            sb.append('#').append(this.method.getName());
            sb.append('(').append(this.eventType.getName());
            this.methodString = sb.toString();
        }
    }

    public int hashCode() {
        return this.method.hashCode();
    }
}
