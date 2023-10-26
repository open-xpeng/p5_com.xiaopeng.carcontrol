package com.xiaopeng.xui.vui.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.view.ViewGroup;
import com.xiaopeng.vui.commons.IVuiElementChangedListener;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.VuiUpdateType;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XCheckBox;
import com.xiaopeng.xui.widget.XEditText;
import com.xiaopeng.xui.widget.XGroupHeader;
import com.xiaopeng.xui.widget.XImageButton;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XProgressBar;
import com.xiaopeng.xui.widget.XRadioButton;
import com.xiaopeng.xui.widget.XRadioGroup;
import com.xiaopeng.xui.widget.XRecyclerView;
import com.xiaopeng.xui.widget.XScrollView;
import com.xiaopeng.xui.widget.XSeekBar;
import com.xiaopeng.xui.widget.XSegmented;
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTabLayout;
import com.xiaopeng.xui.widget.XTextView;
import com.xiaopeng.xui.widget.XViewPager;
import com.xiaopeng.xui.widget.slider.XSlider;
import com.xiaopeng.xui.widget.timepicker.XTimePicker;

/* loaded from: classes2.dex */
public class VuiViewUtils {
    static Handler mHandler;
    static HandlerThread mThread = new HandlerThread("VuiUpdate");

    public static VuiElementType getElementType(Object obj) {
        if (obj instanceof XImageView) {
            return VuiElementType.IMAGEVIEW;
        }
        if (obj instanceof XImageButton) {
            return VuiElementType.IMAGEBUTTON;
        }
        if (obj instanceof XButton) {
            return VuiElementType.BUTTON;
        }
        if (obj instanceof XTextView) {
            return VuiElementType.TEXTVIEW;
        }
        if (obj instanceof XRadioButton) {
            return VuiElementType.RADIOBUTTON;
        }
        if (obj instanceof XCheckBox) {
            return VuiElementType.CHECKBOX;
        }
        if (obj instanceof XSwitch) {
            return VuiElementType.SWITCH;
        }
        if (obj instanceof XRecyclerView) {
            return VuiElementType.RECYCLEVIEW;
        }
        if (obj instanceof XProgressBar) {
            return VuiElementType.PROGRESSBAR;
        }
        if (obj instanceof XScrollView) {
            return VuiElementType.SCROLLVIEW;
        }
        if (obj instanceof XSlider) {
            return VuiElementType.XSLIDER;
        }
        if (obj instanceof XTabLayout) {
            return VuiElementType.XTABLAYOUT;
        }
        if (obj instanceof XSegmented) {
            return VuiElementType.XTABLAYOUT;
        }
        if (obj instanceof XRadioGroup) {
            return VuiElementType.RADIOGROUP;
        }
        if (obj instanceof XEditText) {
            return VuiElementType.EDITTEXT;
        }
        if (obj instanceof XGroupHeader) {
            return VuiElementType.XGROUPHEADER;
        }
        if (obj instanceof XSeekBar) {
            return VuiElementType.SEEKBAR;
        }
        if (obj instanceof XTimePicker) {
            return VuiElementType.TIMEPICKER;
        }
        if (obj instanceof XViewPager) {
            return VuiElementType.VIEWPAGER;
        }
        if (obj instanceof ViewGroup) {
            return VuiElementType.GROUP;
        }
        return VuiElementType.UNKNOWN;
    }

    public static void updateVui(final IVuiElementChangedListener iVuiElementChangedListener, final View view, final VuiUpdateType vuiUpdateType) {
        try {
            if (mHandler == null) {
                mThread.start();
                mHandler = new Handler(mThread.getLooper());
            }
            Handler handler = mHandler;
            if (handler != null) {
                handler.post(new Runnable() { // from class: com.xiaopeng.xui.vui.utils.VuiViewUtils.1
                    @Override // java.lang.Runnable
                    public void run() {
                        View view2;
                        IVuiElementChangedListener iVuiElementChangedListener2 = IVuiElementChangedListener.this;
                        if (iVuiElementChangedListener2 == null || (view2 = view) == null) {
                            return;
                        }
                        iVuiElementChangedListener2.onVuiElementChaned(view2, vuiUpdateType);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
