package com.xiaopeng.carcontrol.quicksetting;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler;
import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.xvs.xid.BuildConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class QuickSettingManager {
    private static final long INIT_TIMEOUT = 5000;
    private static final String TAG = "QuickSettingManager";
    private volatile boolean isInit;
    private final IQuickSettingHandler.IQuickSettingCallback mCallback;
    private final CountDownLatch mCountDown;
    private AbstractHandler mHandler;
    private final ExecutorService mInitThreadPool;
    private final List<String> mKeyList;
    private Handler mTaskHandler;
    private final ExecutorService mThreadPool;

    private QuickSettingManager() {
        this.mKeyList = new ArrayList();
        this.isInit = false;
        this.mCallback = new IQuickSettingHandler.IQuickSettingCallback() { // from class: com.xiaopeng.carcontrol.quicksetting.QuickSettingManager.1
            @Override // com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler.IQuickSettingCallback
            public void onHandleCallback(String key, int value) {
                QuickSettingManager.this.putValue(key, value, value, true);
            }

            @Override // com.xiaopeng.carcontrol.quicksetting.IQuickSettingHandler.IQuickSettingCallback
            public void onHandleCallbackForString(String key, String result) {
                QuickSettingManager.this.putStringValue(key, result);
            }
        };
        this.mInitThreadPool = Executors.newSingleThreadExecutor();
        this.mThreadPool = Executors.newFixedThreadPool(8);
        this.mCountDown = new CountDownLatch(1);
    }

    private AbstractHandler createQuickSettingHandler() {
        String newHardwareCarType = CarStatusUtils.getNewHardwareCarType();
        newHardwareCarType.hashCode();
        char c = 65535;
        switch (newHardwareCarType.hashCode()) {
            case 66948:
                if (newHardwareCarType.equals("D22")) {
                    c = 0;
                    break;
                }
                break;
            case 67044:
                if (newHardwareCarType.equals("D55")) {
                    c = 1;
                    break;
                }
                break;
            case 67915:
                if (newHardwareCarType.equals(BuildConfig.LIB_PRODUCT)) {
                    c = 2;
                    break;
                }
                break;
            case 67946:
                if (newHardwareCarType.equals("E38")) {
                    c = 3;
                    break;
                }
                break;
            case 68899:
                if (newHardwareCarType.equals("F30")) {
                    c = 4;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return new D22Handler(this.mCallback);
            case 1:
                return new D55Handler(this.mCallback);
            case 2:
            case 3:
            case 4:
                return new E28Handler(this.mCallback);
            default:
                return new D21Handler(this.mCallback);
        }
    }

    public static QuickSettingManager getInstance() {
        return Holder.sInstance;
    }

    public void init() {
        if (this.isInit) {
            return;
        }
        AbstractHandler createQuickSettingHandler = createQuickSettingHandler();
        this.mHandler = createQuickSettingHandler;
        this.mKeyList.addAll(createQuickSettingHandler.getKeyList());
        HandlerThread handlerThread = new HandlerThread("QuickSettingThread");
        handlerThread.start();
        this.mTaskHandler = new Handler(handlerThread.getLooper());
        ContentResolver contentResolver = App.getInstance().getContentResolver();
        ContentObserver contentObserver = new ContentObserver(this.mTaskHandler) { // from class: com.xiaopeng.carcontrol.quicksetting.QuickSettingManager.2
            @Override // android.database.ContentObserver
            public void onChange(boolean selfChange, Uri uri) {
                int targetCommand;
                String lastPathSegment = uri.getLastPathSegment();
                if (TextUtils.isEmpty(lastPathSegment) || !QuickSettingManager.this.mKeyList.contains(lastPathSegment) || (targetCommand = QuickSettingManager.this.getTargetCommand(lastPathSegment)) == -1) {
                    return;
                }
                QuickSettingManager.this.postOnHandleCommand(lastPathSegment, targetCommand);
            }
        };
        for (String str : this.mKeyList) {
            contentResolver.registerContentObserver(Settings.System.getUriFor(str), false, contentObserver);
        }
        this.mTaskHandler.post(new Runnable() { // from class: com.xiaopeng.carcontrol.quicksetting.-$$Lambda$QuickSettingManager$pWZHIz9r31rwEnAuH7I5UGI8Us8
            @Override // java.lang.Runnable
            public final void run() {
                QuickSettingManager.this.lambda$init$0$QuickSettingManager();
            }
        });
    }

    public /* synthetic */ void lambda$init$0$QuickSettingManager() {
        LogUtils.d(TAG, "init start", false);
        ExecutorService executorService = this.mInitThreadPool;
        final AbstractHandler abstractHandler = this.mHandler;
        abstractHandler.getClass();
        try {
            executorService.submit(new Runnable() { // from class: com.xiaopeng.carcontrol.quicksetting.-$$Lambda$kKwi-uH-ebBDFyB9zGmIc7BaYhY
                @Override // java.lang.Runnable
                public final void run() {
                    AbstractHandler.this.initViewModel();
                }
            }).get(5000L, TimeUnit.MILLISECONDS);
            this.isInit = true;
            this.mCountDown.countDown();
            LogUtils.d(TAG, "init end", false);
        } catch (InterruptedException e) {
            e = e;
            LogUtils.e(TAG, "init failed: " + e.getMessage(), false);
        } catch (ExecutionException e2) {
            e = e2;
            LogUtils.e(TAG, "init failed: " + e.getMessage(), false);
        } catch (TimeoutException unused) {
            LogUtils.e(TAG, "init timeout, retry");
            init();
        }
    }

    private void checkInit() {
        if (this.isInit) {
            return;
        }
        LogUtils.d(TAG, "checkInit: waiting for init end", false);
        try {
            this.mCountDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void initData() {
        LogUtils.d(TAG, "initData start", false);
        this.mThreadPool.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.quicksetting.-$$Lambda$QuickSettingManager$kdtLq-ajoRHFSzbqeZra_h1IMWk
            @Override // java.lang.Runnable
            public final void run() {
                QuickSettingManager.this.lambda$initData$1$QuickSettingManager();
            }
        });
    }

    public /* synthetic */ void lambda$initData$1$QuickSettingManager() {
        checkInit();
        this.mHandler.initData();
        LogUtils.d(TAG, "initData end", false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postOnHandleCommand(final String key, final int command) {
        LogUtils.d(TAG, "onHandleCommand: key=" + key + ", command=" + command, false);
        this.mThreadPool.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.quicksetting.-$$Lambda$QuickSettingManager$0oPPQwKfnCtq7l_2S7XoF7jaGXY
            @Override // java.lang.Runnable
            public final void run() {
                QuickSettingManager.this.lambda$postOnHandleCommand$2$QuickSettingManager(key, command);
            }
        });
    }

    public /* synthetic */ void lambda$postOnHandleCommand$2$QuickSettingManager(final String key, final int command) {
        checkInit();
        this.mHandler.handleCommand(key, command);
    }

    public <T> void onSignalCallback(final String key, final T value) {
        if (App.isMainProcess()) {
            LogUtils.d(TAG, "onSignalCallback: key=" + key + ", value=" + value, false);
            this.mThreadPool.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.quicksetting.-$$Lambda$QuickSettingManager$ZdXfFitjuZa_29qgTV1eRiJ_RPI
                @Override // java.lang.Runnable
                public final void run() {
                    QuickSettingManager.this.lambda$onSignalCallback$3$QuickSettingManager(key, value);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onSignalCallback$3$QuickSettingManager(final String key, final Object value) {
        checkInit();
        this.mHandler.onSignalCallback(key, value);
    }

    public <T> void onSignalCallbackSingleThread(final String key, final Supplier<T> supplier) {
        if (App.isMainProcess()) {
            this.mThreadPool.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.quicksetting.-$$Lambda$QuickSettingManager$IHnKCCujQjpJnwk2Qb8lfHvSFgE
                @Override // java.lang.Runnable
                public final void run() {
                    QuickSettingManager.this.lambda$onSignalCallbackSingleThread$4$QuickSettingManager(key, supplier);
                }
            });
        }
    }

    public /* synthetic */ void lambda$onSignalCallbackSingleThread$4$QuickSettingManager(final String key, final Supplier supplier) {
        checkInit();
        this.mHandler.onSignalCallbackOnSingleThread(key, supplier);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004d A[Catch: all -> 0x007b, TRY_LEAVE, TryCatch #2 {, blocks: (B:3:0x0001, B:5:0x0015, B:7:0x001f, B:10:0x002b, B:11:0x0031, B:20:0x004d, B:17:0x003f), top: B:30:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized int getTargetCommand(java.lang.String r8) {
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
            java.lang.String r5 = "QuickSettingManager"
            java.lang.String r1 = r1.getMessage()     // Catch: java.lang.Throwable -> L7b
            r6 = 0
            com.xiaopeng.carcontrol.util.LogUtils.e(r5, r1, r6, r3)     // Catch: java.lang.Throwable -> L7b
            goto L4b
        L4a:
            r4 = r2
        L4b:
            if (r4 == r2) goto L79
            java.lang.String r1 = "QuickSettingManager"
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
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.quicksetting.QuickSettingManager.getTargetCommand(java.lang.String):int");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void putValue(String key, int state, int command, boolean print) {
        if (print) {
            LogUtils.d(TAG, "putValue: key=" + key + ", state=" + state + ", command=" + command, false);
        }
        Settings.System.putString(App.getInstance().getContentResolver(), key, state + QuickSettingConstants.JOINER + command + QuickSettingConstants.JOINER + QuickSettingConstants.SUFFIX);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void putStringValue(String key, String result) {
        Settings.System.putString(App.getInstance().getContentResolver(), key, result);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Holder {
        private static final QuickSettingManager sInstance = new QuickSettingManager();

        private Holder() {
        }
    }
}
