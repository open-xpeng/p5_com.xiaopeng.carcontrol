package com.xiaopeng.xui.vui.utils;

import android.view.View;
import android.view.ViewGroup;
import com.xiaopeng.carcontrol.view.speech.VuiActions;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.xui.vui.VuiView;
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
import com.xiaopeng.xui.widget.XSwitch;
import com.xiaopeng.xui.widget.XTabLayout;
import com.xiaopeng.xui.widget.XTextView;
import com.xiaopeng.xui.widget.XViewPager;
import com.xiaopeng.xui.widget.slider.XSlider;
import com.xiaopeng.xui.widget.timepicker.XTimePicker;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Deprecated
/* loaded from: classes2.dex */
public class VuiUtils {
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

    @Deprecated
    public static boolean isPerformVuiAction(View view) {
        return isPerformVuiActionAndReset(view);
    }

    public static boolean isPerformVuiActionNonReset(View view) {
        if (view instanceof VuiView) {
            return ((VuiView) view).isPerformVuiAction();
        }
        return false;
    }

    public static boolean isPerformVuiActionAndReset(View view) {
        if (view instanceof VuiView) {
            VuiView vuiView = (VuiView) view;
            boolean isPerformVuiAction = vuiView.isPerformVuiAction();
            vuiView.setPerformVuiAction(false);
            return isPerformVuiAction;
        }
        return false;
    }

    public static void setStatefulButtonAttr(VuiView vuiView, int i, String[] strArr) {
        JSONObject createStatefulButtonData;
        if (vuiView == null || (createStatefulButtonData = createStatefulButtonData(i, strArr)) == null) {
            return;
        }
        vuiView.setVuiElementType(VuiElementType.STATEFULBUTTON);
        vuiView.setVuiAction(VuiActions.SETVALUE);
        vuiView.setVuiProps(createStatefulButtonData);
    }

    public static JSONObject createStatefulButtonData(int i, String[] strArr) {
        if (strArr == null || strArr.length == 0 || i < 0 || i > strArr.length - 1) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        try {
            Object[] objArr = new String[strArr.length];
            int i2 = 0;
            while (i2 < strArr.length) {
                JSONObject jSONObject2 = new JSONObject();
                int i3 = i2 + 1;
                String str = "state_" + i3;
                objArr[i2] = str;
                jSONObject2.put(str, strArr[i2]);
                jSONArray.put(jSONObject2);
                i2 = i3;
            }
            jSONObject.put("states", jSONArray);
            jSONObject.put("curState", objArr[i]);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
