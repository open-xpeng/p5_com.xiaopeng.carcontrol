package com.xiaopeng.carcontrol.view.control.hvac.vent;

import android.opengl.GLES20;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    public static int compileVertexShader(String shaderCode) {
        return compileShader(35633, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return compileShader(35632, shaderCode);
    }

    public static int compileShader(int type, String shaderCode) {
        int glCreateShader = GLES20.glCreateShader(type);
        if (glCreateShader == 0) {
            LogUtils.d(TAG, "create shader failed " + shaderCode);
            return 0;
        }
        GLES20.glShaderSource(glCreateShader, shaderCode);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] == 0) {
            LogUtils.d(TAG, "compile shader failed " + shaderCode);
            return 0;
        }
        return glCreateShader;
    }

    public static int linkProgram(int vertexShader, int fragmentShader) {
        int glCreateProgram = GLES20.glCreateProgram();
        if (glCreateProgram == 0) {
            LogUtils.d(TAG, "create program failed");
            return 0;
        }
        GLES20.glAttachShader(glCreateProgram, vertexShader);
        GLES20.glAttachShader(glCreateProgram, fragmentShader);
        GLES20.glLinkProgram(glCreateProgram);
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(glCreateProgram, 35714, iArr, 0);
        if (iArr[0] == 0) {
            LogUtils.d(TAG, "link program failed");
            return 0;
        }
        return glCreateProgram;
    }

    public static boolean validateProgram(int programID) {
        GLES20.glValidateProgram(programID);
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(programID, 35715, iArr, 0);
        LogUtils.d(TAG, "validate program result: " + iArr[0] + "\n Info:" + GLES20.glGetProgramInfoLog(programID));
        return iArr[0] != 0;
    }
}
