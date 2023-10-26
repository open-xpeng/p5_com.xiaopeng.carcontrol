package com.xiaopeng.carcontrol.view.control.hvac.vent;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacWindTempMode;
import com.xiaopeng.carcontrolmodule.R;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes2.dex */
public class WindRenderer implements GLSurfaceView.Renderer {
    private static final int BLOW_ANIMATION_LONG = 2000;
    private static final int BLOW_ANIMATION_SHORT = 500;
    private static final String TAG = "WindRenderer";
    private Context context;
    private ValueAnimator mAnimatorBlowFace;
    private ValueAnimator mAnimatorBlowFoot;
    private ValueAnimator mAnimatorBlowWindow;
    private BackgroundShaderProgram mBackgroundShaderProgram;
    private BackgroundSystem mBackgroundSystem;
    private float mBlowFaceAlpha;
    private float mBlowFootAlpha;
    private float mBlowWindowAlpha;
    private BlowWindowSystem mBlowWindowSystem;
    private float mDLAngleHor;
    private ParticleSystem mDLParticleSystem;
    private WaveSystem mDLWaveSystem;
    private float mDRAngleHor;
    private ParticleSystem mDRParticleSystem;
    private WaveSystem mDRWaveSystem;
    private BlowFootSystem mDrvBlowFootSystem;
    private long mGlobalStartTime;
    private boolean mIonizerEnable;
    private boolean mIsBlowFace;
    private boolean mIsBlowFoot;
    private boolean mIsBlowWindow;
    private boolean mIsOpen;
    private float mPLAngleHor;
    private ParticleSystem mPLParticleSystem;
    private WaveSystem mPLWaveSystem;
    private float mPRAngleHor;
    private ParticleSystem mPRParticleSystem;
    private WaveSystem mPRWaveSystem;
    private float mParticleBlue;
    private float mParticleGreen;
    private float mParticleRed;
    private ParticleShaderProgram mParticleShaderProgram;
    private BlowFootSystem mPsnBlowFootSystem;
    private float mShootTime;
    private int[] mTextures;
    private WaveShaderProgram mWaveShaderProgram;
    private static final float[] M_PROJECT_MATRIX = new float[16];
    private static final float[] M_VIEW_MATRIX = new float[16];
    private static final float[] M_BACK_PROJECT_MATRIX = new float[16];
    private static final float[] M_DRV_LEFT_PROJECT_MATRIX = new float[16];
    private static final float[] M_DRV_RIGHT_PROJECT_MATRIX = new float[16];
    private static final float[] M_PSN_LEFT_PROJECT_MATRIX = new float[16];
    private static final float[] M_PSN_RIGHT_PROJECT_MATRIX = new float[16];
    private static final float[] M_DRV_FOOT_PROJECT_MATRIX = new float[16];
    private static final float[] M_PSN_FOOT_PROJECT_MATRIX = new float[16];
    private int mWindSpeed = 0;
    private RotateHelper mDLRotateHelper = new RotateHelper(-7.0f, 7.0f, -7.0f, 7.0f);
    private RotateHelper mDRRotateHelper = new RotateHelper(-7.0f, 7.0f, -7.0f, 7.0f);
    private RotateHelper mPLRotateHelper = new RotateHelper(-7.0f, 7.0f, -7.0f, 7.0f);
    private RotateHelper mPRRotateHelper = new RotateHelper(-7.0f, 7.0f, -7.0f, 7.0f);
    private int[] mResources = {R.drawable.hvac_wind_strip, R.drawable.bg_pressed, R.drawable.bg_air_outlet, R.drawable.hvac_blow_window0, R.drawable.hvac_blow_feet};

    public WindRenderer(Context context) {
        this.context = context;
        setParticleColor();
    }

    private void setParticleColor() {
        Color valueOf = Color.valueOf(this.context.getColor(R.color.hvac_particel_color));
        this.mParticleRed = valueOf.red();
        this.mParticleGreen = valueOf.green();
        this.mParticleBlue = valueOf.blue();
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        this.mGlobalStartTime = System.nanoTime();
        initWaveSystem();
        this.mBackgroundShaderProgram = new BackgroundShaderProgram(this.context);
        this.mBackgroundSystem = new BackgroundSystem();
        GLES20.glEnable(3042);
        GLES20.glBlendFunc(1, 771);
        this.mTextures = TextureHelper.loadTexture(this.context, this.mResources);
    }

    private void initWaveSystem() {
        this.mWaveShaderProgram = new WaveShaderProgram(this.context);
        WaveSystem waveSystem = new WaveSystem();
        this.mDLWaveSystem = waveSystem;
        waveSystem.setSpeed(this.mWindSpeed);
        WaveSystem waveSystem2 = new WaveSystem();
        this.mDRWaveSystem = waveSystem2;
        waveSystem2.setSpeed(this.mWindSpeed);
        WaveSystem waveSystem3 = new WaveSystem();
        this.mPLWaveSystem = waveSystem3;
        waveSystem3.setSpeed(this.mWindSpeed);
        WaveSystem waveSystem4 = new WaveSystem();
        this.mPRWaveSystem = waveSystem4;
        waveSystem4.setSpeed(this.mWindSpeed);
        BlowWindowSystem blowWindowSystem = new BlowWindowSystem();
        this.mBlowWindowSystem = blowWindowSystem;
        blowWindowSystem.setSpeed(this.mWindSpeed);
        BlowFootSystem blowFootSystem = new BlowFootSystem();
        this.mDrvBlowFootSystem = blowFootSystem;
        blowFootSystem.setSpeed(this.mWindSpeed);
        BlowFootSystem blowFootSystem2 = new BlowFootSystem();
        this.mPsnBlowFootSystem = blowFootSystem2;
        blowFootSystem2.setSpeed(this.mWindSpeed);
        this.mParticleShaderProgram = new ParticleShaderProgram(this.context);
        this.mDLParticleSystem = new ParticleSystem(500);
        this.mDRParticleSystem = new ParticleSystem(500);
        this.mPLParticleSystem = new ParticleSystem(500);
        this.mPRParticleSystem = new ParticleSystem(500);
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float[] fArr = M_PROJECT_MATRIX;
        MatrixHelper.perspectiveM(fArr, 45.0f, width / height, 0.0f, 2.0f);
        float[] fArr2 = M_VIEW_MATRIX;
        Matrix.setIdentityM(fArr2, 0);
        Matrix.translateM(fArr2, 0, 0.0f, 0.0f, -1.4f);
        Matrix.multiplyMM(M_BACK_PROJECT_MATRIX, 0, fArr, 0, fArr2, 0);
        boolean isDrvRightVentOnTop = BaseFeatureOption.getInstance().isDrvRightVentOnTop();
        float[] fArr3 = M_DRV_LEFT_PROJECT_MATRIX;
        Matrix.multiplyMM(fArr3, 0, fArr, 0, fArr2, 0);
        Matrix.translateM(fArr3, 0, -0.26f, 0.2f, 0.2f);
        Matrix.rotateM(fArr3, 0, 10.8f, 1.0f, 0.0f, 0.0f);
        Matrix.rotateM(fArr3, 0, 11.0f, 0.0f, 1.0f, 0.0f);
        float[] fArr4 = M_DRV_RIGHT_PROJECT_MATRIX;
        Matrix.multiplyMM(fArr4, 0, fArr, 0, fArr2, 0);
        Matrix.translateM(fArr4, 0, isDrvRightVentOnTop ? -0.02f : -0.07f, isDrvRightVentOnTop ? 0.245f : 0.2f, 0.2f);
        Matrix.rotateM(fArr4, 0, isDrvRightVentOnTop ? 12.5f : 10.8f, 1.0f, 0.0f, 0.0f);
        Matrix.rotateM(fArr4, 0, isDrvRightVentOnTop ? 0.0f : 2.0f, 0.0f, 1.0f, 0.0f);
        float[] fArr5 = M_PSN_LEFT_PROJECT_MATRIX;
        Matrix.multiplyMM(fArr5, 0, fArr, 0, fArr2, 0);
        Matrix.translateM(fArr5, 0, isDrvRightVentOnTop ? 0.055f : 0.065f, 0.2f, 0.2f);
        Matrix.rotateM(fArr5, 0, 10.8f, 1.0f, 0.0f, 0.0f);
        Matrix.rotateM(fArr5, 0, isDrvRightVentOnTop ? -3.5f : -4.0f, 0.0f, 1.0f, 0.0f);
        float[] fArr6 = M_PSN_RIGHT_PROJECT_MATRIX;
        Matrix.multiplyMM(fArr6, 0, fArr, 0, fArr2, 0);
        Matrix.translateM(fArr6, 0, isDrvRightVentOnTop ? 0.2f : 0.18f, 0.2f, 0.2f);
        Matrix.rotateM(fArr6, 0, 10.8f, 1.0f, 0.0f, 0.0f);
        Matrix.rotateM(fArr6, 0, -9.0f, 0.0f, 1.0f, 0.0f);
        float[] fArr7 = M_DRV_FOOT_PROJECT_MATRIX;
        Matrix.multiplyMM(fArr7, 0, fArr, 0, fArr2, 0);
        Matrix.translateM(fArr7, 0, -0.2f, isDrvRightVentOnTop ? 0.18f : 0.15f, -0.5f);
        Matrix.rotateM(fArr7, 0, 0.0f, 1.0f, 0.0f, 0.0f);
        float[] fArr8 = M_PSN_FOOT_PROJECT_MATRIX;
        Matrix.multiplyMM(fArr8, 0, fArr, 0, fArr2, 0);
        Matrix.translateM(fArr8, 0, 0.2f, isDrvRightVentOnTop ? 0.18f : 0.15f, -0.5f);
        Matrix.rotateM(fArr8, 0, 0.0f, 1.0f, 0.0f, 0.0f);
        initAngle();
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(16640);
        this.mBackgroundShaderProgram.useProgram();
        this.mBackgroundSystem.bindData(this.mBackgroundShaderProgram);
        BackgroundShaderProgram backgroundShaderProgram = this.mBackgroundShaderProgram;
        int[] iArr = this.mTextures;
        backgroundShaderProgram.setUniformTexture(iArr[1], iArr[2], iArr[3]);
        BackgroundShaderProgram backgroundShaderProgram2 = this.mBackgroundShaderProgram;
        float[] fArr = M_BACK_PROJECT_MATRIX;
        backgroundShaderProgram2.setUniformMatrix(fArr);
        this.mBackgroundSystem.draw(this.mIsOpen && this.mIsBlowFace);
        float nanoTime = ((float) (System.nanoTime() - this.mGlobalStartTime)) / 1.0E9f;
        this.mWaveShaderProgram.useProgram();
        if ((this.mIsBlowWindow && this.mIsOpen) || this.mBlowWindowAlpha > 0.0f) {
            this.mWaveShaderProgram.setUniformTextureAlpha(this.mBlowWindowAlpha);
            this.mBlowWindowSystem.updateWaveLine(nanoTime);
            this.mBlowWindowSystem.bindData(this.mWaveShaderProgram);
            this.mWaveShaderProgram.setUniformAngle(fArr, 0.0f);
            this.mWaveShaderProgram.setUniformTexture(this.mTextures[3]);
            this.mBlowWindowSystem.draw();
        } else {
            this.mBlowWindowSystem.resetAndClearWave();
        }
        if ((this.mIsBlowFoot && this.mIsOpen) || this.mBlowFootAlpha > 0.0f) {
            this.mWaveShaderProgram.setUniformTextureAlpha(this.mBlowFootAlpha);
            this.mDrvBlowFootSystem.updateWaveLine(nanoTime);
            this.mPsnBlowFootSystem.updateWaveLine(nanoTime);
            this.mDrvBlowFootSystem.bindData(this.mWaveShaderProgram);
            this.mWaveShaderProgram.setUniformAngle(M_DRV_FOOT_PROJECT_MATRIX, 0.0f);
            this.mWaveShaderProgram.setUniformTexture(this.mTextures[4]);
            this.mDrvBlowFootSystem.draw();
            this.mPsnBlowFootSystem.bindData(this.mWaveShaderProgram);
            this.mWaveShaderProgram.setUniformAngle(M_PSN_FOOT_PROJECT_MATRIX, 0.0f);
            this.mWaveShaderProgram.setUniformTexture(this.mTextures[4]);
            this.mPsnBlowFootSystem.draw();
        } else {
            this.mDrvBlowFootSystem.resetAndClearWave();
            this.mPsnBlowFootSystem.resetAndClearWave();
        }
        if ((this.mIsBlowFace && this.mIsOpen) || this.mBlowFaceAlpha > 0.0f) {
            this.mDLWaveSystem.updateWaveLine(nanoTime);
            this.mDRWaveSystem.updateWaveLine(nanoTime);
            this.mPLWaveSystem.updateWaveLine(nanoTime);
            this.mPRWaveSystem.updateWaveLine(nanoTime);
            this.mWaveShaderProgram.setUniformTextureAlpha(this.mBlowFaceAlpha);
            this.mDLWaveSystem.bindData(this.mWaveShaderProgram);
            WaveShaderProgram waveShaderProgram = this.mWaveShaderProgram;
            float[] fArr2 = M_DRV_LEFT_PROJECT_MATRIX;
            waveShaderProgram.setUniformAngle(fArr2, this.mDLAngleHor);
            this.mWaveShaderProgram.setUniformTexture(this.mTextures[0]);
            this.mDLWaveSystem.draw();
            this.mDRWaveSystem.bindData(this.mWaveShaderProgram);
            WaveShaderProgram waveShaderProgram2 = this.mWaveShaderProgram;
            float[] fArr3 = M_DRV_RIGHT_PROJECT_MATRIX;
            waveShaderProgram2.setUniformAngle(fArr3, this.mDRAngleHor);
            this.mWaveShaderProgram.setUniformTexture(this.mTextures[0]);
            this.mDRWaveSystem.draw();
            this.mPLWaveSystem.bindData(this.mWaveShaderProgram);
            WaveShaderProgram waveShaderProgram3 = this.mWaveShaderProgram;
            float[] fArr4 = M_PSN_LEFT_PROJECT_MATRIX;
            waveShaderProgram3.setUniformAngle(fArr4, this.mPLAngleHor);
            this.mWaveShaderProgram.setUniformTexture(this.mTextures[0]);
            this.mPLWaveSystem.draw();
            this.mPRWaveSystem.bindData(this.mWaveShaderProgram);
            WaveShaderProgram waveShaderProgram4 = this.mWaveShaderProgram;
            float[] fArr5 = M_PSN_RIGHT_PROJECT_MATRIX;
            waveShaderProgram4.setUniformAngle(fArr5, this.mPRAngleHor);
            this.mWaveShaderProgram.setUniformTexture(this.mTextures[0]);
            this.mPRWaveSystem.draw();
            if (this.mIonizerEnable && this.mIsBlowFace && this.mIsOpen && this.mShootTime + 0.1f < nanoTime) {
                this.mDLParticleSystem.shootParticle(1, nanoTime);
                this.mDRParticleSystem.shootParticle(1, nanoTime);
                this.mPLParticleSystem.shootParticle(1, nanoTime);
                this.mPRParticleSystem.shootParticle(1, nanoTime);
                this.mShootTime = nanoTime;
            }
            if (this.mShootTime + 2.3d > nanoTime) {
                this.mParticleShaderProgram.useProgram();
                this.mParticleShaderProgram.setUniformSpeed(1.0f);
                this.mParticleShaderProgram.setUniformTime(nanoTime);
                this.mParticleShaderProgram.setUniformColor(this.mParticleRed, this.mParticleGreen, this.mParticleBlue);
                this.mParticleShaderProgram.setUniformMatrix(fArr2);
                this.mDLParticleSystem.bindData(this.mParticleShaderProgram);
                this.mDLParticleSystem.draw();
                this.mParticleShaderProgram.setUniformMatrix(fArr3);
                this.mDRParticleSystem.bindData(this.mParticleShaderProgram);
                this.mDRParticleSystem.draw();
                this.mParticleShaderProgram.setUniformMatrix(fArr4);
                this.mPLParticleSystem.bindData(this.mParticleShaderProgram);
                this.mPLParticleSystem.draw();
                this.mParticleShaderProgram.setUniformMatrix(fArr5);
                this.mPRParticleSystem.bindData(this.mParticleShaderProgram);
                this.mPRParticleSystem.draw();
                return;
            }
            return;
        }
        this.mDLWaveSystem.resetAndClearWave();
        this.mDRWaveSystem.resetAndClearWave();
        this.mPLWaveSystem.resetAndClearWave();
        this.mPRWaveSystem.resetAndClearWave();
    }

    public void setIonizerEnable(boolean enable) {
        this.mIonizerEnable = enable;
    }

    public void setSpeed(int speed) {
        LogUtils.d(TAG, "speed " + speed);
        this.mWindSpeed = speed;
        WaveSystem waveSystem = this.mDLWaveSystem;
        if (waveSystem != null) {
            waveSystem.setSpeed(speed);
        }
        WaveSystem waveSystem2 = this.mDRWaveSystem;
        if (waveSystem2 != null) {
            waveSystem2.setSpeed(speed);
        }
        WaveSystem waveSystem3 = this.mPLWaveSystem;
        if (waveSystem3 != null) {
            waveSystem3.setSpeed(speed);
        }
        WaveSystem waveSystem4 = this.mPRWaveSystem;
        if (waveSystem4 != null) {
            waveSystem4.setSpeed(speed);
        }
        BlowWindowSystem blowWindowSystem = this.mBlowWindowSystem;
        if (blowWindowSystem != null) {
            blowWindowSystem.setSpeed(speed);
        }
        BlowFootSystem blowFootSystem = this.mDrvBlowFootSystem;
        if (blowFootSystem != null) {
            blowFootSystem.setSpeed(speed);
        }
        BlowFootSystem blowFootSystem2 = this.mPsnBlowFootSystem;
        if (blowFootSystem2 != null) {
            blowFootSystem2.setSpeed(speed);
        }
    }

    public void onThemeChanged(HvacWindTempMode windTempMode) {
        int[] iArr = this.mTextures;
        if (iArr != null) {
            TextureHelper.changeTexture(this.context, iArr[0], R.drawable.hvac_wind_strip);
            TextureHelper.changeTexture(this.context, this.mTextures[1], R.drawable.bg_pressed);
            TextureHelper.changeTexture(this.context, this.mTextures[2], R.drawable.bg_air_outlet);
            TextureHelper.changeTexture(this.context, this.mTextures[3], R.drawable.hvac_blow_window0);
            TextureHelper.changeTexture(this.context, this.mTextures[4], R.drawable.hvac_blow_feet);
        }
        setParticleColor();
    }

    private void initAngle() {
        Matrix.rotateM(M_DRV_LEFT_PROJECT_MATRIX, 0, this.mDLRotateHelper.getAngleVertical(), 1.0f, 0.0f, 0.0f);
        this.mDLAngleHor = (this.mDLRotateHelper.getAngleHorizontal() * 3.1415927f) / 180.0f;
        Matrix.rotateM(M_DRV_RIGHT_PROJECT_MATRIX, 0, this.mDRRotateHelper.getAngleVertical(), 1.0f, 0.0f, 0.0f);
        this.mDRAngleHor = (this.mDRRotateHelper.getAngleHorizontal() * 3.1415927f) / 180.0f;
        Matrix.rotateM(M_PSN_LEFT_PROJECT_MATRIX, 0, this.mPLRotateHelper.getAngleVertical(), 1.0f, 0.0f, 0.0f);
        this.mPLAngleHor = (this.mPLRotateHelper.getAngleHorizontal() * 3.1415927f) / 180.0f;
        Matrix.rotateM(M_PSN_RIGHT_PROJECT_MATRIX, 0, this.mPRRotateHelper.getAngleVertical(), 1.0f, 0.0f, 0.0f);
        this.mPRAngleHor = (this.mPRRotateHelper.getAngleHorizontal() * 3.1415927f) / 180.0f;
    }

    public void setIsOpen(boolean isOpen) {
        this.mIsOpen = isOpen;
        startFaceBlowAnim();
        startFootBlowAnim();
        startWindowBlowAnim();
    }

    public boolean isOpen() {
        return this.mIsOpen;
    }

    public void setIsBlowWindow(boolean isBlowWindow) {
        this.mIsBlowWindow = isBlowWindow;
        startWindowBlowAnim();
    }

    public void setIsBlowFoot(boolean isBlowFoot) {
        this.mIsBlowFoot = isBlowFoot;
        startFootBlowAnim();
    }

    public void setIsBlowFace(boolean isBlowFace) {
        this.mIsBlowFace = isBlowFace;
        startFaceBlowAnim();
    }

    private void startFaceBlowAnim() {
        ValueAnimator valueAnimator = this.mAnimatorBlowFace;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        float[] fArr = new float[2];
        fArr[0] = this.mBlowFaceAlpha;
        fArr[1] = (this.mIsBlowFace && this.mIsOpen) ? 1.0f : 0.0f;
        ValueAnimator duration = ValueAnimator.ofFloat(fArr).setDuration((this.mIsBlowFace && this.mIsOpen) ? 2000L : 500L);
        this.mAnimatorBlowFace = duration;
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.carcontrol.view.control.hvac.vent.-$$Lambda$WindRenderer$DuhaHy0J3HjzhK6boZdGvKE8Rjo
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                WindRenderer.this.lambda$startFaceBlowAnim$0$WindRenderer(valueAnimator2);
            }
        });
        this.mAnimatorBlowFace.start();
    }

    public /* synthetic */ void lambda$startFaceBlowAnim$0$WindRenderer(ValueAnimator animation) {
        this.mBlowFaceAlpha = ((Float) animation.getAnimatedValue()).floatValue();
    }

    private void startFootBlowAnim() {
        ValueAnimator valueAnimator = this.mAnimatorBlowFoot;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        float[] fArr = new float[2];
        fArr[0] = this.mBlowFootAlpha;
        fArr[1] = (this.mIsBlowFoot && this.mIsOpen) ? 1.0f : 0.0f;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
        if (this.mIsBlowFoot) {
            boolean z = this.mIsOpen;
        }
        ValueAnimator duration = ofFloat.setDuration(500L);
        this.mAnimatorBlowFoot = duration;
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.carcontrol.view.control.hvac.vent.-$$Lambda$WindRenderer$-rwoiDUhhBhLcmIh57NTXpiRlDo
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                WindRenderer.this.lambda$startFootBlowAnim$1$WindRenderer(valueAnimator2);
            }
        });
        this.mAnimatorBlowFoot.start();
    }

    public /* synthetic */ void lambda$startFootBlowAnim$1$WindRenderer(ValueAnimator animation) {
        this.mBlowFootAlpha = ((Float) animation.getAnimatedValue()).floatValue();
    }

    private void startWindowBlowAnim() {
        ValueAnimator valueAnimator = this.mAnimatorBlowWindow;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        float[] fArr = new float[2];
        fArr[0] = this.mBlowWindowAlpha;
        fArr[1] = (this.mIsBlowWindow && this.mIsOpen) ? 1.0f : 0.0f;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
        if (this.mIsBlowWindow) {
            boolean z = this.mIsOpen;
        }
        ValueAnimator duration = ofFloat.setDuration(500L);
        this.mAnimatorBlowWindow = duration;
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaopeng.carcontrol.view.control.hvac.vent.-$$Lambda$WindRenderer$ZEznP2QW7Jf1jKXKHLVG0I76_pM
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                WindRenderer.this.lambda$startWindowBlowAnim$2$WindRenderer(valueAnimator2);
            }
        });
        this.mAnimatorBlowWindow.start();
    }

    public /* synthetic */ void lambda$startWindowBlowAnim$2$WindRenderer(ValueAnimator animation) {
        this.mBlowWindowAlpha = ((Float) animation.getAnimatedValue()).floatValue();
    }
}
