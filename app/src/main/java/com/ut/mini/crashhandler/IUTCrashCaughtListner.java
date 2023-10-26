package com.ut.mini.crashhandler;

import java.util.Map;

/* loaded from: classes.dex */
public interface IUTCrashCaughtListner {
    Map<String, String> onCrashCaught(Thread thread, Throwable th);
}
