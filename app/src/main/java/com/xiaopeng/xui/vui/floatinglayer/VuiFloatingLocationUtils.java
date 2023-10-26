package com.xiaopeng.xui.vui.floatinglayer;

import com.xiaopeng.xui.utils.XLogUtils;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayer;

/* loaded from: classes2.dex */
class VuiFloatingLocationUtils {
    private static final String TAG = "VuiFloatingLocation";

    VuiFloatingLocationUtils() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static int[] getLocation(int i, VuiFloatingLayer.LayerInfo layerInfo, int i2, int i3) {
        int[] iArr = new int[2];
        if (layerInfo == null) {
            return iArr;
        }
        int i4 = layerInfo.location[0];
        if (i == 0) {
            int i5 = ((layerInfo.targetWidth / 2) + i4) - (i2 / 2);
            int i6 = (layerInfo.location[1] + (layerInfo.targetHeight / 2)) - (i3 / 2);
            int i7 = i5 + layerInfo.mCenterOffsetX;
            int i8 = i6 + layerInfo.mCenterOffsetY;
            if (i7 < i4 || i7 > i4 + layerInfo.targetWidth) {
                log("offset more or less than current view width");
            }
            iArr[0] = i7;
            iArr[1] = i8;
        } else if (i == 1) {
            int i9 = (((layerInfo.targetWidth / 2) + i4) - (i2 / 2)) + layerInfo.mCenterOffsetX;
            int i10 = (layerInfo.location[1] - i3) + 35 + layerInfo.mCenterOffsetY;
            if (i9 < i4 || i9 > i4 + layerInfo.targetWidth) {
                log("offset more or less than current view width");
            }
            iArr[0] = i9;
            iArr[1] = i10;
        }
        return iArr;
    }

    private static void log(String str) {
        XLogUtils.v(TAG, str);
    }
}
