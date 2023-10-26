package org.greenrobot.eventbus.util;

import android.content.res.Resources;
import android.util.Log;
import org.greenrobot.eventbus.EventBus;

/* loaded from: classes3.dex */
public class ErrorDialogConfig {
    int defaultDialogIconId;
    final int defaultErrorMsgId;
    Class<?> defaultEventTypeOnDialogClosed;
    final int defaultTitleId;
    EventBus eventBus;
    boolean logExceptions = true;
    final ExceptionToResourceMapping mapping = new ExceptionToResourceMapping();
    final Resources resources;
    String tagForLoggingExceptions;

    public ErrorDialogConfig(Resources resources, int i, int i2) {
        this.resources = resources;
        this.defaultTitleId = i;
        this.defaultErrorMsgId = i2;
    }

    public ErrorDialogConfig addMapping(Class<? extends Throwable> cls, int i) {
        this.mapping.addMapping(cls, i);
        return this;
    }

    public int getMessageIdForThrowable(Throwable th) {
        Integer mapThrowable = this.mapping.mapThrowable(th);
        if (mapThrowable != null) {
            return mapThrowable.intValue();
        }
        Log.d(EventBus.TAG, "No specific message ressource ID found for " + th);
        return this.defaultErrorMsgId;
    }

    public void setDefaultDialogIconId(int i) {
        this.defaultDialogIconId = i;
    }

    public void setDefaultEventTypeOnDialogClosed(Class<?> cls) {
        this.defaultEventTypeOnDialogClosed = cls;
    }

    public void disableExceptionLogging() {
        this.logExceptions = false;
    }

    public void setTagForLoggingExceptions(String str) {
        this.tagForLoggingExceptions = str;
    }

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public EventBus getEventBus() {
        EventBus eventBus = this.eventBus;
        return eventBus != null ? eventBus : EventBus.getDefault();
    }
}
