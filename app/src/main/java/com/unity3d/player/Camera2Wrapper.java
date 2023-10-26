package com.unity3d.player;

import android.content.Context;
import android.graphics.Rect;

/* loaded from: classes.dex */
public class Camera2Wrapper implements d {
    private Context a;
    private a b = null;

    public Camera2Wrapper(Context context) {
        this.a = context;
        initCamera2Jni();
    }

    private final native void initCamera2Jni();

    private final native void nativeFrameReady(Object obj, Object obj2, Object obj3, int i, int i2, int i3);

    public final void a() {
        closeCamera2();
    }

    @Override // com.unity3d.player.d
    public final void a(Object obj, Object obj2, Object obj3, int i, int i2, int i3) {
        nativeFrameReady(obj, obj2, obj3, i, i2, i3);
    }

    protected void closeCamera2() {
        a aVar = this.b;
        if (aVar != null) {
            aVar.b();
        }
        this.b = null;
    }

    protected int getCamera2Count() {
        if (j.b) {
            return a.a(this.a);
        }
        return 0;
    }

    protected int getCamera2SensorOrientation(int i) {
        if (j.b) {
            return a.a(this.a, i);
        }
        return 0;
    }

    protected Rect getFrameSizeCamera2() {
        a aVar = this.b;
        return aVar != null ? aVar.a() : new Rect();
    }

    protected boolean initializeCamera2(int i, int i2, int i3, int i4) {
        if (j.b && this.b == null && UnityPlayer.currentActivity != null) {
            a aVar = new a(this);
            this.b = aVar;
            return aVar.a(this.a, i, i2, i3, i4);
        }
        return false;
    }

    protected boolean isCamera2FrontFacing(int i) {
        if (j.b) {
            return a.b(this.a, i);
        }
        return false;
    }

    protected void startCamera2() {
        a aVar = this.b;
        if (aVar != null) {
            aVar.c();
        }
    }

    protected void stopCamera2() {
        a aVar = this.b;
        if (aVar != null) {
            aVar.d();
        }
    }
}
