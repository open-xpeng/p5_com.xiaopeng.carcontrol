package com.xiaopeng.carcontrol.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.xiaopeng.xui.widget.XSwitch;

/* loaded from: classes2.dex */
public class CustomXSwitch extends XSwitch {
    private OnInterceptClickListener mListener;

    /* loaded from: classes2.dex */
    public interface OnInterceptClickListener {
        boolean onInterceptClick(XSwitch xSwitch);
    }

    public CustomXSwitch(Context context) {
        super(context);
    }

    public CustomXSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomXSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomXSwitch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setOnInterceptClickListener(OnInterceptClickListener listener) {
        this.mListener = listener;
    }

    @Override // com.xiaopeng.xui.widget.XSwitch, com.xiaopeng.xui.widget.XCompoundButton, android.widget.CompoundButton, android.view.View
    public boolean performClick() {
        OnInterceptClickListener onInterceptClickListener = this.mListener;
        if (onInterceptClickListener == null || !onInterceptClickListener.onInterceptClick(this)) {
            return super.performClick();
        }
        return false;
    }
}
