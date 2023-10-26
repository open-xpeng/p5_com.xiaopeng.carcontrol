package com.xiaopeng.carcontrol.view.control.hvac.vent;

/* loaded from: classes2.dex */
public class MatrixHelper {
    public static void perspectiveM(float[] projectMatrix, float yFovDegrees, float aspect, float n, float f) {
        float tan = (float) (1.0d / Math.tan(((float) ((yFovDegrees * 3.141592653589793d) / 180.0d)) / 2.0f));
        projectMatrix[0] = tan / aspect;
        projectMatrix[1] = 0.0f;
        projectMatrix[2] = 0.0f;
        projectMatrix[3] = 0.0f;
        projectMatrix[4] = 0.0f;
        projectMatrix[5] = tan;
        projectMatrix[6] = 0.0f;
        projectMatrix[7] = 0.0f;
        projectMatrix[8] = 0.0f;
        projectMatrix[9] = 0.0f;
        float f2 = f - n;
        projectMatrix[10] = -((f + n) / f2);
        projectMatrix[11] = -1.0f;
        projectMatrix[12] = 0.0f;
        projectMatrix[13] = 0.0f;
        projectMatrix[14] = -(((f * 2.0f) * n) / f2);
        projectMatrix[15] = 0.0f;
    }
}
