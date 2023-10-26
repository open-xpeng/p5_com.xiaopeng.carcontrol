package com.xiaopeng.xui.app;

import android.app.Activity;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaopeng.xpui.R;

/* loaded from: classes2.dex */
public class XPanelActivity extends AppCompatActivity {
    public static final int FEATURE_XUI_FULLSCREEN = 14;
    private static final long PANEL_DISMISS_DELAY = 30000;
    public static final int TYPE_DISMISS_BACK = 1;
    public static final int TYPE_DISMISS_STOP = 0;
    private boolean mAutoVisibility;
    private boolean mCloseOnPauseOneshot;
    private boolean mCloseOnTouchOutside;
    private float mDimAlpha;
    private long mDismissDelay;
    private int mDismissType;
    private Builder mLayoutBuilder;
    private final Handler mPanelHandler;
    private ContentObserver mPanelObserver;
    private static final String KEY_PANEL_SPEED = "key_panel_car_speed";
    private static final Uri URI_PANEL_SPEED = Settings.System.getUriFor(KEY_PANEL_SPEED);
    private float mLastSpeed = 0.0f;
    private final Runnable mAutoDismissRunnable = new Runnable() { // from class: com.xiaopeng.xui.app.XPanelActivity.1
        @Override // java.lang.Runnable
        public void run() {
            XPanelActivity.this.handleAutoDismissEvent();
        }
    };

    public XPanelActivity() {
        Handler handler = new Handler();
        this.mPanelHandler = handler;
        this.mCloseOnTouchOutside = false;
        this.mCloseOnPauseOneshot = true;
        this.mDimAlpha = 0.6f;
        this.mDismissDelay = 0L;
        this.mAutoVisibility = false;
        this.mDismissType = 0;
        this.mPanelObserver = new ContentObserver(handler) { // from class: com.xiaopeng.xui.app.XPanelActivity.3
            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                XPanelActivity.this.onPanelSpeedChanged();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        requestWindowFeature(14);
        super.onCreate(bundle);
        this.mLayoutBuilder = new Builder(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        int i = getResources().getConfiguration().orientation;
        int dp2px = dp2px(this, R.dimen.x_compat_app_panel_x);
        int dp2px2 = dp2px(this, R.dimen.x_compat_app_panel_y);
        int dp2px3 = dp2px(this, R.dimen.x_compat_app_panel_width);
        int dp2px4 = dp2px(this, R.dimen.x_compat_app_panel_height);
        if (i == 1) {
            getLayoutBuilder().setX(dp2px).setY(dp2px2).setWidth(dp2px3).setHeight(dp2px4).setGravity(80).apply();
        } else if (i != 2) {
        } else {
            getLayoutBuilder().setX(dp2px).setY(dp2px2).setWidth(dp2px3).setHeight(dp2px4).setGravity(49).apply();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        postAutoDismissRunnable(false);
        handleWindowVisibility(true);
        registerPanelObserver(getApplicationContext());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        handleWindowVisibility(false);
        unregisterPanelObserver(getApplicationContext());
        if (this.mCloseOnPauseOneshot) {
            handleDismissEvent();
        }
        this.mCloseOnPauseOneshot = true;
    }

    @Override // android.app.Activity
    public void recreate() {
        super.recreate();
        this.mCloseOnPauseOneshot = false;
    }

    @Override // android.app.Activity
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (shouldCloseOnTouch(this, motionEvent)) {
            handleDismissEvent();
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        postAutoDismissRunnable(false);
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.app.Activity
    public void finish() {
        super.finish();
    }

    @Override // android.app.Activity
    public boolean moveTaskToBack(boolean z) {
        return super.moveTaskToBack(z);
    }

    public Builder getLayoutBuilder() {
        return this.mLayoutBuilder;
    }

    public void setDismissType(int i) {
        this.mDismissType = i;
    }

    public void setCloseOnPauseOneshot(boolean z) {
        this.mCloseOnPauseOneshot = z;
    }

    public void setCloseOnTouchOutside(boolean z) {
        this.mCloseOnTouchOutside = z;
    }

    public void setDimAlpha(float f) {
        this.mDimAlpha = f;
    }

    public void setDismissDelay(long j) {
        this.mDismissDelay = j;
    }

    public void setAutoVisibilityEnable(boolean z) {
        this.mAutoVisibility = z;
    }

    public void handleDismissEvent() {
        final int i = this.mDismissType;
        Runnable runnable = new Runnable() { // from class: com.xiaopeng.xui.app.XPanelActivity.2
            @Override // java.lang.Runnable
            public void run() {
                int i2 = i;
                if (i2 == 0) {
                    ActivityUtils.finish(XPanelActivity.this);
                } else if (i2 != 1) {
                } else {
                    ActivityUtils.moveTaskToBack(XPanelActivity.this, true);
                }
            }
        };
        handleWindowVisibility(false);
        this.mPanelHandler.postDelayed(runnable, this.mDismissDelay);
    }

    public void dispatchUserEvent() {
        postAutoDismissRunnable(false);
    }

    public void handleWindowVisibility(boolean z) {
        if (this.mAutoVisibility) {
            setWindowVisible(z);
        }
    }

    private void setWindowVisible(boolean z) {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            if (attributes != null) {
                attributes.alpha = z ? 1.0f : 0.0f;
                attributes.dimAmount = z ? this.mDimAlpha : 0.0f;
            }
            window.setAttributes(attributes);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleAutoDismissEvent() {
        if (getSpeed() > 0.0f) {
            handleDismissEvent();
        }
    }

    private void postAutoDismissRunnable(boolean z) {
        float speed = getSpeed();
        boolean z2 = true;
        if (z && this.mLastSpeed * speed > 0.0f) {
            z2 = false;
        }
        if (speed <= 0.0f) {
            this.mPanelHandler.removeCallbacks(this.mAutoDismissRunnable);
        } else if (z2) {
            this.mPanelHandler.removeCallbacks(this.mAutoDismissRunnable);
            this.mPanelHandler.postDelayed(this.mAutoDismissRunnable, PANEL_DISMISS_DELAY);
        }
        this.mLastSpeed = speed;
    }

    private float getSpeed() {
        try {
            return Settings.System.getFloat(getContentResolver(), KEY_PANEL_SPEED, 0.0f);
        } catch (Exception unused) {
            return 0.0f;
        }
    }

    private boolean isOutOfBounds(Context context, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int scaledWindowTouchSlop = ViewConfiguration.get(context).getScaledWindowTouchSlop();
        View decorView = getWindow().getDecorView();
        int i = -scaledWindowTouchSlop;
        return x < i || y < i || x > decorView.getWidth() + scaledWindowTouchSlop || y > decorView.getHeight() + scaledWindowTouchSlop;
    }

    private boolean shouldCloseOnTouch(Context context, MotionEvent motionEvent) {
        return this.mCloseOnTouchOutside && getWindow().getDecorView() != null && ((motionEvent.getAction() == 0 && isOutOfBounds(context, motionEvent)) || motionEvent.getAction() == 4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPanelSpeedChanged() {
        postAutoDismissRunnable(true);
    }

    private void registerPanelObserver(Context context) {
        try {
            context.getContentResolver().registerContentObserver(URI_PANEL_SPEED, true, this.mPanelObserver);
        } catch (Exception unused) {
        }
    }

    private void unregisterPanelObserver(Context context) {
        try {
            context.getContentResolver().unregisterContentObserver(this.mPanelObserver);
        } catch (Exception unused) {
        }
    }

    /* loaded from: classes2.dex */
    public static final class Builder {
        private int flags;
        private int gravity;
        private int height;
        private final Activity mActivity;
        private int systemUiVisibility;
        private int width;
        private int x;
        private int y;

        public Builder(Activity activity) {
            this.mActivity = activity;
            init();
        }

        private void init() {
            WindowManager.LayoutParams attributes;
            Activity activity = this.mActivity;
            if (activity == null || activity.getWindow() == null || (attributes = this.mActivity.getWindow().getAttributes()) == null) {
                return;
            }
            this.x = attributes.x;
            this.y = attributes.y;
            this.flags = attributes.flags;
            this.width = attributes.width;
            this.height = attributes.height;
            this.gravity = attributes.gravity;
            this.systemUiVisibility = attributes.systemUiVisibility;
        }

        public void apply() {
            WindowManager.LayoutParams attributes;
            Activity activity = this.mActivity;
            if (activity == null || activity.getWindow() == null || (attributes = this.mActivity.getWindow().getAttributes()) == null) {
                return;
            }
            attributes.x = this.x;
            attributes.y = this.y;
            attributes.flags |= this.flags;
            attributes.width = this.width;
            attributes.height = this.height;
            attributes.gravity = this.gravity;
            attributes.systemUiVisibility |= this.systemUiVisibility;
            this.mActivity.getWindow().setAttributes(attributes);
        }

        public Builder setX(int i) {
            this.x = i;
            return this;
        }

        public Builder setY(int i) {
            this.y = i;
            return this;
        }

        public Builder setFlags(int i) {
            this.flags = i;
            return this;
        }

        public Builder setWidth(int i) {
            this.width = i;
            return this;
        }

        public Builder setHeight(int i) {
            this.height = i;
            return this;
        }

        public Builder setGravity(int i) {
            this.gravity = i;
            return this;
        }

        public Builder setSystemUiVisibility(int i) {
            this.systemUiVisibility = i;
            return this;
        }
    }

    public static int dp2px(Context context, int i) {
        return (int) ((context.getResources().getDimension(i) * context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}
