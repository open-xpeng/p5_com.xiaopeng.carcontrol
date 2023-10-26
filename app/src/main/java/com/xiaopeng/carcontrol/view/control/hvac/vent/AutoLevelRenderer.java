package com.xiaopeng.carcontrol.view.control.hvac.vent;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import com.xiaopeng.carcontrolmodule.R;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes2.dex */
public class AutoLevelRenderer implements GLSurfaceView.Renderer {
    private static final float[] M_PROJECT_MATRIX = new float[16];
    private static final float[] M_VIEW_MATRIX = new float[16];
    private static final float[] M_VIEW_PROJECT_MATRIX = new float[16];
    private static final String TAG = "AutoLevelRenderer";
    private Context context;
    private FanAutoLevelSystem mAutoLevelSystem;
    private BackgroundShaderProgram mBackgroundProgram;
    private long mGlobalStartTime;
    private boolean mIsRunning;
    private boolean mIsShowing = true;
    private int[] mResources = {R.drawable.hvac_wind_strip_auto0, R.drawable.hvac_wind_strip_auto1, R.drawable.hvac_wind_strip_auto2};
    private int[] mTextures;

    public AutoLevelRenderer(Context context) {
        this.context = context;
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.mGlobalStartTime = System.nanoTime();
        this.mBackgroundProgram = new BackgroundShaderProgram(this.context);
        this.mAutoLevelSystem = new FanAutoLevelSystem();
        GLES20.glEnable(3042);
        GLES20.glBlendFunc(1, 771);
        this.mTextures = TextureHelper.loadTexture(this.context, this.mResources);
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float[] fArr = M_PROJECT_MATRIX;
        MatrixHelper.perspectiveM(fArr, 45.0f, width / height, 0.0f, 2.0f);
        float[] fArr2 = M_VIEW_MATRIX;
        Matrix.setIdentityM(fArr2, 0);
        Matrix.translateM(fArr2, 0, 0.0f, 0.0f, -1.0f);
        Matrix.multiplyMM(M_VIEW_PROJECT_MATRIX, 0, fArr, 0, fArr2, 0);
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(16640);
        if (this.mIsRunning) {
            this.mAutoLevelSystem.updateWaveLine(((float) (System.nanoTime() - this.mGlobalStartTime)) / 1.0E9f);
            if (this.mIsShowing) {
                this.mBackgroundProgram.useProgram();
                this.mAutoLevelSystem.bindData(this.mBackgroundProgram);
                this.mBackgroundProgram.setUniformMatrix(M_VIEW_PROJECT_MATRIX);
                BackgroundShaderProgram backgroundShaderProgram = this.mBackgroundProgram;
                int[] iArr = this.mTextures;
                backgroundShaderProgram.setUniformTexture(iArr[0], iArr[1], iArr[2]);
                this.mAutoLevelSystem.draw();
                return;
            }
            return;
        }
        this.mGlobalStartTime = System.nanoTime();
        this.mAutoLevelSystem.resetAndClearWave();
    }

    public void startAnim() {
        this.mIsRunning = true;
    }

    public void stopAnim() {
        this.mIsRunning = false;
    }

    public void setIsShowing(boolean showing) {
        this.mIsShowing = showing;
    }

    public void onThemeChanged() {
        int[] iArr = this.mTextures;
        if (iArr != null) {
            TextureHelper.changeTexture(this.context, iArr[0], R.drawable.hvac_wind_strip_auto0);
            TextureHelper.changeTexture(this.context, this.mTextures[1], R.drawable.hvac_wind_strip_auto1);
            TextureHelper.changeTexture(this.context, this.mTextures[2], R.drawable.hvac_wind_strip_auto2);
        }
    }
}
