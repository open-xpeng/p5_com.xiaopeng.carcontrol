package com.xiaopeng.xui.view.font;

import android.content.Context;
import android.content.res.Configuration;
import com.xiaopeng.xui.view.XViewDelegate;

/* loaded from: classes2.dex */
public class XFontChangeMonitor {
    private Context mContext;
    private float mFontScale;
    private XViewDelegate.onFontScaleChangeCallback mOnFontChangeCallback;

    public XFontChangeMonitor(Context context) {
        this.mContext = context;
        this.mFontScale = context.getResources().getConfiguration().fontScale;
    }

    public void setOnFontScaleChangeCallback(XViewDelegate.onFontScaleChangeCallback onfontscalechangecallback) {
        this.mOnFontChangeCallback = onfontscalechangecallback;
    }

    public void onConfigurationChanged(Configuration configuration) {
        checkFont(configuration);
    }

    private void checkFont(Configuration configuration) {
        if (this.mFontScale != configuration.fontScale) {
            this.mFontScale = configuration.fontScale;
            XViewDelegate.onFontScaleChangeCallback onfontscalechangecallback = this.mOnFontChangeCallback;
            if (onfontscalechangecallback != null) {
                onfontscalechangecallback.onFontScaleChanged();
            }
        }
    }

    public void onAttachedToWindow() {
        checkFont(this.mContext.getResources().getConfiguration());
    }
}
