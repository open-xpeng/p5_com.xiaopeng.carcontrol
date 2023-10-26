package com.xiaopeng.xui.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaopeng.xui.view.touch.XTouchTargetUtils;
import java.util.List;

/* loaded from: classes2.dex */
public class XTouchAreaUtils {
    public static final Class[] CLASSES = {TextView.class, ImageView.class};
    public static final int MIN_PADDING = 20;

    public static void extendTouchArea(ViewGroup viewGroup) {
        extendTouchArea(CLASSES, viewGroup);
    }

    public static void extendTouchArea(Class<?>[] clsArr, ViewGroup viewGroup) {
        extendTouchArea(clsArr, viewGroup, (int[]) null);
    }

    public static void extendTouchArea(Class<?>[] clsArr, ViewGroup viewGroup, int[] iArr) {
        for (Class<?> cls : clsArr) {
            List<View> findViewsByType = XuiUtils.findViewsByType(viewGroup, cls);
            if (findViewsByType.size() > 0) {
                View[] viewArr = new View[findViewsByType.size()];
                findViewsByType.toArray(viewArr);
                extendTouchArea(viewArr, viewGroup, iArr);
            }
        }
    }

    public static void extendTouchArea(View[] viewArr, ViewGroup viewGroup) {
        extendTouchArea(viewArr, viewGroup, (int[]) null);
    }

    public static void extendTouchArea(View[] viewArr, ViewGroup viewGroup, int[] iArr) {
        for (View view : viewArr) {
            extendTouchArea(view, viewGroup, iArr);
        }
    }

    public static void extendTouchArea(View view, ViewGroup viewGroup) {
        extendTouchArea(view, viewGroup, (int[]) null);
    }

    public static void extendTouchArea(View view, ViewGroup viewGroup, int[] iArr) {
        if (view == null) {
            return;
        }
        if (iArr == null) {
            iArr = new int[]{20, 20, 20, 20};
        }
        XTouchTargetUtils.extendViewTouchTarget(view, viewGroup, iArr[0], iArr[1], iArr[2], iArr[3]);
    }

    public static void extendTouchAreaAsParentSameSize(ViewGroup viewGroup) {
        extendTouchAreaAsParentSameSize(CLASSES, viewGroup);
    }

    public static void extendTouchAreaAsParentSameSize(Class<?>[] clsArr, ViewGroup viewGroup) {
        for (Class<?> cls : clsArr) {
            List<View> findViewsByType = XuiUtils.findViewsByType(viewGroup, cls);
            if (findViewsByType.size() > 0) {
                View[] viewArr = new View[findViewsByType.size()];
                findViewsByType.toArray(viewArr);
                extendTouchAreaAsParentSameSize(viewArr, viewGroup);
            }
        }
    }

    public static void extendTouchAreaAsParentSameSize(View[] viewArr, ViewGroup viewGroup) {
        for (View view : viewArr) {
            extendTouchAreaAsParentSameSize(view, viewGroup);
        }
    }

    public static void extendTouchAreaAsParentSameSize(View view, ViewGroup viewGroup) {
        if (view == null || viewGroup == null) {
            return;
        }
        XTouchTargetUtils.extendTouchAreaAsParentSameSize(view, viewGroup);
    }
}
