package com.xiaopeng.carcontrol.quicksetting;

import android.provider.Settings;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import java.util.List;
import java.util.function.Supplier;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public abstract class AbstractHandler implements IQuickSettingHandler {
    protected final String TAG = getClass().getSimpleName();
    protected final IQuickSettingHandler.IQuickSettingCallback mCallback;
    private final IQuickSettingHandler mCommonHandler;

    protected abstract <T> int onHandleCallback(String key, T value);

    protected abstract <T> String onHandleCallbackForString(String key, T value);

    protected abstract void onHandleCommand(String key, int command);

    public AbstractHandler(IQuickSettingHandler.IQuickSettingCallback callback) {
        this.mCallback = callback;
        this.mCommonHandler = new CommonHandler(callback);
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public void initViewModel() {
        this.mCommonHandler.initViewModel();
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public List<String> getKeyList() {
        return this.mCommonHandler.getKeyList();
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public void initData() {
        this.mCommonHandler.initData();
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public boolean handleCommand(String key, int command) {
        if (this.mCommonHandler.handleCommand(key, command)) {
            return true;
        }
        onHandleCommand(key, command);
        return true;
    }

    public <T> void onSignalCallbackOnSingleThread(final String key, final Supplier<T> supplier) {
        ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.carcontrol.quicksetting.-$$Lambda$AbstractHandler$sn7DFNSCJ1yDL9x6KvwmTvm5hAI
            @Override // java.lang.Runnable
            public final void run() {
                AbstractHandler.this.lambda$onSignalCallbackOnSingleThread$0$AbstractHandler(supplier, key);
            }
        });
    }

    public /* synthetic */ void lambda$onSignalCallbackOnSingleThread$0$AbstractHandler(final Supplier supplier, final String key) {
        Object obj = supplier.get();
        LogUtils.d(this.TAG, "onSignalCallbackOnSingleThread: key = " + key + ", value = " + obj);
        onSignalCallback(key, obj);
    }

    @Override // com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler
    public <T> boolean onSignalCallback(String key, T value) {
        if (this.mCommonHandler.onSignalCallback(key, value)) {
            return true;
        }
        int onHandleCallback = onHandleCallback(key, value);
        if (!TextUtils.isEmpty(key) && onHandleCallback != -1) {
            this.mCallback.onHandleCallback(key, onHandleCallback);
            return true;
        } else if (onHandleCallback == -1) {
            String onHandleCallbackForString = onHandleCallbackForString(key, value);
            if (TextUtils.isEmpty(key) || TextUtils.isEmpty(onHandleCallbackForString)) {
                return true;
            }
            this.mCallback.onHandleCallbackForString(key, onHandleCallbackForString);
            return true;
        } else {
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004d A[Catch: all -> 0x007b, TRY_LEAVE, TryCatch #2 {, blocks: (B:3:0x0001, B:5:0x0015, B:7:0x001f, B:10:0x002b, B:11:0x0031, B:20:0x004d, B:17:0x003f), top: B:30:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized int getTargetCommand(java.lang.String r8) {
        /*
            r7 = this;
            monitor-enter(r7)
            com.xiaopeng.carcontrol.App r0 = com.xiaopeng.carcontrol.App.getInstance()     // Catch: java.lang.Throwable -> L7b
            android.content.ContentResolver r0 = r0.getContentResolver()     // Catch: java.lang.Throwable -> L7b
            java.lang.String r0 = android.provider.Settings.System.getString(r0, r8)     // Catch: java.lang.Throwable -> L7b
            boolean r1 = android.text.TextUtils.isEmpty(r0)     // Catch: java.lang.Throwable -> L7b
            r2 = -1
            r3 = 0
            if (r1 != 0) goto L4a
            java.lang.String r1 = ":"
            java.lang.String[] r1 = r0.split(r1)     // Catch: java.lang.Throwable -> L7b
            int r4 = r1.length     // Catch: java.lang.Throwable -> L7b
            r5 = 3
            if (r4 != r5) goto L4a
            java.lang.String r4 = "c"
            r5 = 2
            r5 = r1[r5]     // Catch: java.lang.Throwable -> L7b
            boolean r4 = r4.equals(r5)     // Catch: java.lang.Throwable -> L7b
            if (r4 != 0) goto L4a
            r4 = 1
            r4 = r1[r4]     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L7b
            int r4 = java.lang.Integer.parseInt(r4)     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L7b
            r1 = r1[r3]     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L7b
            int r1 = java.lang.Integer.parseInt(r1)     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L7b
            r7.putValue(r8, r1, r4, r3)     // Catch: java.lang.Exception -> L3b java.lang.Throwable -> L7b
            goto L4b
        L3b:
            r1 = move-exception
            goto L3f
        L3d:
            r1 = move-exception
            r4 = r2
        L3f:
            java.lang.String r5 = r7.TAG     // Catch: java.lang.Throwable -> L7b
            java.lang.String r1 = r1.getMessage()     // Catch: java.lang.Throwable -> L7b
            r6 = 0
            com.xiaopeng.carcontrol.util.LogUtils.e(r5, r1, r6, r3)     // Catch: java.lang.Throwable -> L7b
            goto L4b
        L4a:
            r4 = r2
        L4b:
            if (r4 == r2) goto L79
            java.lang.String r1 = r7.TAG     // Catch: java.lang.Throwable -> L7b
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L7b
            r2.<init>()     // Catch: java.lang.Throwable -> L7b
            java.lang.String r5 = "getTargetCommand: key="
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch: java.lang.Throwable -> L7b
            java.lang.StringBuilder r8 = r2.append(r8)     // Catch: java.lang.Throwable -> L7b
            java.lang.String r2 = ", value="
            java.lang.StringBuilder r8 = r8.append(r2)     // Catch: java.lang.Throwable -> L7b
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch: java.lang.Throwable -> L7b
            java.lang.String r0 = ", targetCommand="
            java.lang.StringBuilder r8 = r8.append(r0)     // Catch: java.lang.Throwable -> L7b
            java.lang.StringBuilder r8 = r8.append(r4)     // Catch: java.lang.Throwable -> L7b
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L7b
            com.xiaopeng.carcontrol.util.LogUtils.d(r1, r8, r3)     // Catch: java.lang.Throwable -> L7b
        L79:
            monitor-exit(r7)
            return r4
        L7b:
            r8 = move-exception
            monitor-exit(r7)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.quicksetting.AbstractHandler.getTargetCommand(java.lang.String):int");
    }

    private synchronized void putValue(String key, int state, int command, boolean print) {
        if (print) {
            LogUtils.d(this.TAG, "putValue: key=" + key + ", state=" + state + ", command=" + command, false);
        }
        Settings.System.putString(App.getInstance().getContentResolver(), key, state + QuickSettingConstants.JOINER + command + QuickSettingConstants.JOINER + QuickSettingConstants.SUFFIX);
    }
}
