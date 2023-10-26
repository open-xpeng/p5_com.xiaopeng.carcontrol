package com.xiaopeng.xui.view.font;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;
import com.xiaopeng.xui.Xui;

/* loaded from: classes2.dex */
public class XFontScaleHelper {
    private float mComplexToFloat;

    public static XFontScaleHelper create(Resources resources, int i) {
        if (Xui.isFontScaleDynamicChangeEnable()) {
            float complexToFloatForSp = complexToFloatForSp(resources, i);
            if (complexToFloatForSp != -1.0f) {
                return new XFontScaleHelper(complexToFloatForSp);
            }
            return null;
        }
        return null;
    }

    public static XFontScaleHelper create(TypedArray typedArray, int i) {
        return create(typedArray, i, 0);
    }

    public static XFontScaleHelper create(TypedArray typedArray, int i, int i2) {
        if (Xui.isFontScaleDynamicChangeEnable()) {
            float complexToFloatForSp = complexToFloatForSp(typedArray, i, i2);
            if (complexToFloatForSp != -1.0f) {
                return new XFontScaleHelper(complexToFloatForSp);
            }
            return null;
        }
        return null;
    }

    private XFontScaleHelper(float f) {
        this.mComplexToFloat = f;
    }

    public float getCurrentFontSize(DisplayMetrics displayMetrics) {
        return TypedValue.applyDimension(2, this.mComplexToFloat, displayMetrics);
    }

    public void refreshTextSize(TextView textView) {
        textView.setTextSize(this.mComplexToFloat);
    }

    private static float complexToFloatForSp(TypedArray typedArray, int i, int i2) {
        if (typedArray.hasValue(i)) {
            TypedValue typedValue = new TypedValue();
            typedArray.getValue(i, typedValue);
            if (typedValue.getComplexUnit() == 2) {
                return TypedValue.complexToFloat(typedValue.data);
            }
            return -1.0f;
        }
        return complexToFloatForSp(typedArray.getResources(), i2);
    }

    private static float complexToFloatForSp(Resources resources, int i) {
        if (i != 0) {
            TypedValue typedValue = new TypedValue();
            resources.getValue(i, typedValue, true);
            if (typedValue.getComplexUnit() == 2) {
                return TypedValue.complexToFloat(typedValue.data);
            }
            return -1.0f;
        }
        return -1.0f;
    }
}
