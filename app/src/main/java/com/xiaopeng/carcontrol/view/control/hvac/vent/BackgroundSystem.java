package com.xiaopeng.carcontrol.view.control.hvac.vent;

import android.opengl.GLES20;

/* loaded from: classes2.dex */
class BackgroundSystem {
    private static final float BACKGROUND_HEIGHT = 0.58f;
    private static final float BACKGROUND_WIDTH = 0.35959998f;
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int STRIDE = 24;
    private static final int TEXTURE_COMPONENT_COUNT = 2;
    private static final int TEXTURE_TYPE_COMPONENT_COUNT = 1;
    private static final int TOTAL_COMPONENT_COUNT = 6;
    private static final int TOTAL_POINT_COUNT = 8;
    private static final float VENT_BOTTOM = 0.203f;
    private static final float VENT_TOP = 0.26099998f;
    private static final float VENT_WIDTH = 0.35959998f;
    private int mCurrentPosition;
    private final VertexArray mVertexArray;
    private float[] mVertexData = new float[48];

    /* JADX INFO: Access modifiers changed from: package-private */
    public BackgroundSystem() {
        initVertexData();
        this.mVertexArray = new VertexArray(this.mVertexData);
    }

    private void initVertexData() {
        this.mCurrentPosition = 0;
        addPoint2Data(-0.35959998f, -0.58f, 0.0f, 0.0f, 1.0f, 1.0f);
        addPoint2Data(0.35959998f, -0.58f, 0.0f, 1.0f, 1.0f, 1.0f);
        addPoint2Data(-0.35959998f, BACKGROUND_HEIGHT, 0.0f, 0.0f, 0.0f, 1.0f);
        addPoint2Data(0.35959998f, BACKGROUND_HEIGHT, 0.0f, 1.0f, 0.0f, 1.0f);
        addPoint2Data(-0.35959998f, VENT_BOTTOM, 0.0f, 0.0f, 1.0f, 2.0f);
        addPoint2Data(0.35959998f, VENT_BOTTOM, 0.0f, 1.0f, 1.0f, 2.0f);
        addPoint2Data(-0.35959998f, VENT_TOP, 0.0f, 0.0f, 0.0f, 2.0f);
        addPoint2Data(0.35959998f, VENT_TOP, 0.0f, 1.0f, 0.0f, 2.0f);
    }

    private void addPoint2Data(float positionX, float positionY, float positionZ, float textureX, float textureY, float textureType) {
        float[] fArr = this.mVertexData;
        int i = this.mCurrentPosition;
        int i2 = i + 1;
        this.mCurrentPosition = i2;
        fArr[i] = positionX;
        int i3 = i2 + 1;
        this.mCurrentPosition = i3;
        fArr[i2] = positionY;
        int i4 = i3 + 1;
        this.mCurrentPosition = i4;
        fArr[i3] = positionZ;
        int i5 = i4 + 1;
        this.mCurrentPosition = i5;
        fArr[i4] = textureX;
        int i6 = i5 + 1;
        this.mCurrentPosition = i6;
        fArr[i5] = textureY;
        this.mCurrentPosition = i6 + 1;
        fArr[i6] = textureType;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void bindData(BackgroundShaderProgram backgroundShaderProgram) {
        this.mVertexArray.setVertexAttrPointer(0, backgroundShaderProgram.getPositionLocation(), 3, 24);
        this.mVertexArray.setVertexAttrPointer(3, backgroundShaderProgram.getTextureCooLocation(), 2, 24);
        this.mVertexArray.setVertexAttrPointer(5, backgroundShaderProgram.getTextureTypeLocation(), 1, 24);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void draw(boolean drawVent) {
        GLES20.glDrawArrays(5, 0, 4);
        if (drawVent) {
            GLES20.glDrawArrays(5, 4, 4);
        }
    }

    void drawXFreeBreath() {
        GLES20.glDrawArrays(4, 8, 6);
    }

    void drawBlowWindow() {
        GLES20.glDrawArrays(4, 14, 6);
    }
}
