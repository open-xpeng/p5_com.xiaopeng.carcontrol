package com.xiaopeng.carcontrol.view.control.hvac.vent;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/* loaded from: classes2.dex */
public class MyGLSurfaceView extends GLSurfaceView {
    public MyGLSurfaceView(Context context) {
        this(context, null);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        getHolder().setFormat(-2);
        setEGLConfigChooser(new MSAAConfigChooser());
    }
}
