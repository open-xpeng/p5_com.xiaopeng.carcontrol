package com.xiaopeng.carcontrol.view.fragment.controlscene;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.xiaopeng.xui.widget.XScrollView;

/* loaded from: classes2.dex */
public class MainScrollView extends XScrollView {
    private boolean mScrollable;

    public MainScrollView(Context context) {
        super(context);
        this.mScrollable = true;
    }

    public MainScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mScrollable = true;
    }

    public MainScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mScrollable = true;
    }

    @Override // android.widget.ScrollView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!this.mScrollable) {
            if (ev.getAction() == 1 || ev.getAction() == 3) {
                this.mScrollable = true;
                return false;
            }
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setScrollable(boolean enable) {
        this.mScrollable = enable;
    }

    @Override // com.xiaopeng.xui.widget.XScrollView, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mScrollable = true;
    }

    @Override // com.xiaopeng.xui.widget.XScrollView, android.view.View
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == 0) {
            this.mScrollable = true;
        }
    }
}
