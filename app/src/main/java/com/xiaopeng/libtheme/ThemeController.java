package com.xiaopeng.libtheme;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.provider.Settings;
import com.xiaopeng.libtheme.ThemeManager;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class ThemeController {
    private static final int MSG_STATE_NOTIFY = 1001;
    private static final int MSG_STATE_TIMEOUT = 1002;
    public static final int STATE_THEME_CHANGED = 2;
    public static final int STATE_THEME_PREPARE = 1;
    public static final int STATE_THEME_UNKNOWN = 0;
    private static final String TAG = "ThemeController";
    private static final int VIEW_DISABLE = 0;
    private static final int VIEW_ENABLED = 1;
    private static final int VIEW_UNKNOWN = -1;
    private Context mContext;
    private final Handler mHandler;
    private ArrayList<OnThemeListener> mThemeListeners;
    private final ThemeObserver mThemeObserver;
    private static final String KEY_THEME_MODE = "key_theme_mode";
    public static final Uri URI_THEME_MODE = Settings.Secure.getUriFor(KEY_THEME_MODE);
    private static final String KEY_THEME_STATE = "key_theme_type";
    public static final Uri URI_THEME_STATE = Settings.Secure.getUriFor(KEY_THEME_STATE);
    private static final String KEY_DAYNIGHT_MODE = "ui_night_mode";
    public static final Uri URI_DAYNIGHT_MODE = Settings.Secure.getUriFor(KEY_DAYNIGHT_MODE);
    public static final long DEFAULT_STATE_DELAY = SystemProperties.getLong("persist.sys.theme.settings.delay", 3000);
    public static final long DEFAULT_STATE_TIMEOUT = SystemProperties.getLong("persist.sys.theme.settings.timeout", 3000);
    private static ThemeController sThemeController = null;
    private int mThemeMode = 0;
    private int mThemeState = 0;
    private int mDaynightMode = 0;
    private int mViewState = -1;

    /* loaded from: classes2.dex */
    public interface OnThemeListener {
        void onStateChanged(boolean z);

        void onThemeChanged(boolean z, Uri uri);
    }

    public static ThemeController getInstance(Context context) {
        if (sThemeController == null) {
            synchronized (ThemeController.class) {
                if (sThemeController == null) {
                    sThemeController = new ThemeController(context);
                }
            }
        }
        return sThemeController;
    }

    private ThemeController(Context context) {
        Handler handler = new Handler(Looper.getMainLooper()) { // from class: com.xiaopeng.libtheme.ThemeController.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                int i = message.what;
                if (i == 1001) {
                    ThemeController.this.onStateChanged(message.arg1 == 1);
                } else if (i != 1002) {
                } else {
                    ThemeController.this.handleStateEvent();
                }
            }
        };
        this.mHandler = handler;
        this.mThemeListeners = new ArrayList<>();
        this.mContext = context;
        ThemeObserver themeObserver = new ThemeObserver(context, handler);
        this.mThemeObserver = themeObserver;
        themeObserver.registerThemeObserver();
        readThemeSettings();
    }

    public void register(OnThemeListener onThemeListener) {
        ThemeManager.Logger.log(TAG, "register listener=" + onThemeListener);
        this.mThemeListeners.add(onThemeListener);
    }

    public void unregister(OnThemeListener onThemeListener) {
        ThemeManager.Logger.log(TAG, "unregister listener=" + onThemeListener);
        if (this.mThemeListeners.contains(onThemeListener)) {
            this.mThemeListeners.remove(onThemeListener);
        }
    }

    public boolean isThemeWorking() {
        return ThemeManager.isThemeWorking(this.mContext) || this.mThemeState != 0;
    }

    private void setViewState(boolean z) {
        this.mViewState = !z ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleStateEvent() {
        long j;
        boolean z = this.mDaynightMode == 0;
        boolean isThemeWorking = isThemeWorking();
        if (isThemeWorking) {
            j = 0;
        } else {
            j = z ? DEFAULT_STATE_DELAY : 1000L;
        }
        this.mHandler.removeMessages(1001);
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(1001, isThemeWorking ? 1 : 0, 0), j);
        this.mHandler.removeMessages(1002);
        if (isThemeWorking) {
            this.mHandler.sendEmptyMessageDelayed(1002, DEFAULT_STATE_TIMEOUT);
        }
        StringBuffer stringBuffer = new StringBuffer("");
        stringBuffer.append("handleStateChanged");
        stringBuffer.append(" auto=" + z);
        stringBuffer.append(" isThemeWorking=" + isThemeWorking);
        stringBuffer.append(" disable=" + isThemeWorking);
        stringBuffer.append(" delay=" + j);
        stringBuffer.append(" themeState=" + this.mThemeState);
        stringBuffer.append(" daynightMode=" + this.mDaynightMode);
        ThemeManager.Logger.log(TAG, stringBuffer.toString());
    }

    private void resetViewStateIfNeed(boolean z, Uri uri) {
        ThemeManager.Logger.log(TAG, "resetViewStateIfNeed uri=" + uri);
        if (Settings.Secure.getUriFor(KEY_DAYNIGHT_MODE).equals(uri)) {
            this.mViewState = -1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onStateChanged(boolean z) {
        ThemeManager.Logger.log(TAG, "onStateChanged disableView=" + z);
        if ((!z) == this.mViewState) {
            ThemeManager.Logger.log(TAG, "onStateChanged same state not to listeners");
            return;
        }
        setViewState(z);
        ArrayList<OnThemeListener> arrayList = this.mThemeListeners;
        if (arrayList != null) {
            Iterator<OnThemeListener> it = arrayList.iterator();
            while (it.hasNext()) {
                OnThemeListener next = it.next();
                if (next != null) {
                    next.onStateChanged(z);
                }
            }
        }
    }

    private void onThemeChanged(boolean z, Uri uri) {
        ArrayList<OnThemeListener> arrayList = this.mThemeListeners;
        if (arrayList != null) {
            Iterator<OnThemeListener> it = arrayList.iterator();
            while (it.hasNext()) {
                OnThemeListener next = it.next();
                if (next != null) {
                    next.onThemeChanged(z, uri);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void performStateChanged(boolean z, Uri uri) {
        resetViewStateIfNeed(z, uri);
        handleStateEvent();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void performThemeChanged(boolean z, Uri uri) {
        onThemeChanged(z, uri);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void readThemeSettings() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mThemeMode = Settings.Secure.getInt(contentResolver, KEY_THEME_MODE, 0);
        this.mThemeState = Settings.Secure.getInt(contentResolver, KEY_THEME_STATE, 0);
        this.mDaynightMode = Settings.Secure.getInt(contentResolver, KEY_DAYNIGHT_MODE, 0);
        ThemeManager.Logger.log(TAG, "readThemeSettings themeState=" + this.mThemeState + " daynightMode=" + this.mDaynightMode);
    }

    /* loaded from: classes2.dex */
    private class ThemeObserver {
        private Context mThemeContext;
        private final ContentObserver mThemeObserver;

        public ThemeObserver(Context context, Handler handler) {
            this.mThemeContext = context;
            this.mThemeObserver = new ContentObserver(handler) { // from class: com.xiaopeng.libtheme.ThemeController.ThemeObserver.1
                @Override // android.database.ContentObserver
                public void onChange(boolean z, Uri uri) {
                    super.onChange(z, uri);
                    if (z) {
                        return;
                    }
                    ThemeController.this.readThemeSettings();
                    ThemeController.this.performStateChanged(z, uri);
                    ThemeController.this.performThemeChanged(z, uri);
                }
            };
        }

        public void registerThemeObserver() {
            this.mThemeContext.getContentResolver().registerContentObserver(ThemeController.URI_THEME_MODE, true, this.mThemeObserver);
            this.mThemeContext.getContentResolver().registerContentObserver(ThemeController.URI_THEME_STATE, true, this.mThemeObserver);
            this.mThemeContext.getContentResolver().registerContentObserver(ThemeController.URI_DAYNIGHT_MODE, true, this.mThemeObserver);
        }

        public void unregisterThemeObserver() {
            this.mThemeContext.getContentResolver().unregisterContentObserver(this.mThemeObserver);
        }
    }
}
