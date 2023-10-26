package com.xiaopeng.xui.vui.floatinglayer;

import android.view.View;
import com.xiaopeng.xui.Xui;
import com.xiaopeng.xui.utils.XLogUtils;
import com.xiaopeng.xui.utils.XViewLocationUtils;
import com.xiaopeng.xui.vui.VuiView;

/* loaded from: classes2.dex */
public class VuiFloatingLayerManager {
    private static IVuiFloatingLayer mVuiFloatingLayer;

    private VuiFloatingLayerManager() {
    }

    public static void show(View view) {
        show(view, 0);
    }

    public static void show(View view, boolean z) {
        show(view, 0, 0, 0, z);
    }

    public static void show(View view, int i) {
        show(view, i, 0, 0);
    }

    public static void show(View view, int i, int i2) {
        show(view, 0, i, i2);
    }

    public static void show(View view, int i, int i2, int i3) {
        show(view, i, i2, i3, true);
    }

    public static void show(final View view, final int i, final int i2, final int i3, boolean z) {
        if (i == 0 && (view instanceof VuiView) && ((VuiView) view).getVuiDisableHitEffect()) {
            XLogUtils.d("VuiFloatingManager", "DisableHitEffect type " + i + " view " + view);
            return;
        }
        if (mVuiFloatingLayer == null) {
            mVuiFloatingLayer = new VuiFloatingLayer(Xui.getContext());
        }
        XLogUtils.d("VuiFloatingManager", "show==   isCheckScroll : " + z + " view : " + view + " x : " + i2 + " y : " + i3 + " type : " + i);
        if (z) {
            XViewLocationUtils.scrollByLocation(view, new XViewLocationUtils.OnCorrectionLocationListener() { // from class: com.xiaopeng.xui.vui.floatinglayer.-$$Lambda$VuiFloatingLayerManager$jaSCYJvaWK_2nzXlEqcFDLuqq3s
                @Override // com.xiaopeng.xui.utils.XViewLocationUtils.OnCorrectionLocationListener
                public final void onCorrectionLocationEnd(View view2) {
                    VuiFloatingLayerManager.mVuiFloatingLayer.showFloatingLayer(view, i, i2, i3);
                }
            });
        } else {
            mVuiFloatingLayer.showFloatingLayer(view, i, i2, i3);
        }
    }

    public static void hide() {
        hide(0);
    }

    public static void hide(int i) {
        IVuiFloatingLayer iVuiFloatingLayer = mVuiFloatingLayer;
        if (iVuiFloatingLayer != null) {
            iVuiFloatingLayer.hideFloatingLayer(i);
        }
    }
}
