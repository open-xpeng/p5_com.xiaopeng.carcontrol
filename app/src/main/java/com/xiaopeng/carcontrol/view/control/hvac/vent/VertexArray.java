package com.xiaopeng.carcontrol.view.control.hvac.vent;

import android.opengl.GLES20;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* loaded from: classes2.dex */
public class VertexArray {
    private final String TAG = VertexArray.class.getSimpleName();
    private FloatBuffer mFloatBuffer;

    public VertexArray(float[] vertexData) {
        this.mFloatBuffer = ByteBuffer.allocateDirect(vertexData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertexData);
    }

    public void setVertexAttrPointer(int offset, int attributeLocation, int componentCount, int stride) {
        this.mFloatBuffer.position(offset);
        GLES20.glVertexAttribPointer(attributeLocation, componentCount, 5126, false, stride, (Buffer) this.mFloatBuffer);
        GLES20.glEnableVertexAttribArray(attributeLocation);
        this.mFloatBuffer.position(0);
    }

    public void updateBuffer(float[] vertexData, int start, int count) {
        this.mFloatBuffer.position(start);
        this.mFloatBuffer.put(vertexData, start, count);
        this.mFloatBuffer.position(0);
    }
}
