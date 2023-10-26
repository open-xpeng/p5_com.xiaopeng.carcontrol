package com.xiaopeng.carcontrol.view.dialog;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.speechwidget.ListWidget;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.widget.XTextView;
import java.io.IOException;

/* loaded from: classes2.dex */
public abstract class BaseVideoDialog extends XDialog {
    protected static final String LCC_VIDEO_PATH = "/system/etc/lcc.mp4";
    protected static final String MEM_PARK_VIDEO_PATH = "/system/etc/lcc.mp4";
    private static final String TAG = "XPilotVideoDialog";
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;
    protected boolean isInit;
    private AudioManager mAudioManager;
    private Context mContext;
    private boolean mForceWatch;
    protected ImageView mIvCover;
    protected ImageView mIvPlayer;
    protected MediaPlayer mMediaPlayer;
    protected SurfaceView mTextureView;
    protected XTextView mTvTips;
    private String mVideoPath;

    protected abstract void bindView(View view);

    protected abstract boolean enableVui();

    protected abstract int getLayoutId();

    public /* synthetic */ void lambda$new$0$BaseVideoDialog(int focusChange) {
        LogUtils.d(TAG, "audio focus change...  focus change type: " + focusChange, false);
        if (focusChange == -2 || focusChange == -1) {
            pausePlayback();
        }
    }

    public BaseVideoDialog(Context context) {
        this(context, R.style.XDialogView_Large_Video);
        this.mContext = context;
        this.mAudioManager = (AudioManager) context.getApplicationContext().getSystemService(ListWidget.EXTRA_TYPE_AUDIO);
    }

    public BaseVideoDialog(Context context, int style) {
        super(context, style);
        this.audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$BaseVideoDialog$6vgux-cXXPs283ZIZZ0-ygCV0Mk
            @Override // android.media.AudioManager.OnAudioFocusChangeListener
            public final void onAudioFocusChange(int i) {
                BaseVideoDialog.this.lambda$new$0$BaseVideoDialog(i);
            }
        };
        setSystemDialog(2048);
        View inflate = LayoutInflater.from(context).inflate(getLayoutId(), getContentView(), false);
        bindView(inflate);
        initViews();
        setCustomView(inflate, false);
        getDialog().setCanceledOnTouchOutside(false);
    }

    private void pausePlayback() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            return;
        }
        this.mMediaPlayer.pause();
        setPlayIconState(false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initViews() {
        this.mTextureView.getHolder().addCallback(new SurfaceHolder.Callback() { // from class: com.xiaopeng.carcontrol.view.dialog.BaseVideoDialog.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder holder) {
                BaseVideoDialog.this.mMediaPlayer.setDisplay(holder);
            }
        });
        this.mTextureView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$BaseVideoDialog$m2oMx6QIaLggweN70LaGbuH8eOc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BaseVideoDialog.this.lambda$initViews$1$BaseVideoDialog(view);
            }
        });
    }

    public /* synthetic */ void lambda$initViews$1$BaseVideoDialog(View v) {
        if (this.isInit) {
            this.isInit = false;
            this.mMediaPlayer.start();
            setPlayIconState(true);
            requestAudioFocus();
            this.mIvCover.setVisibility(8);
        } else if (this.mMediaPlayer.isPlaying()) {
            this.mMediaPlayer.pause();
            setPlayIconState(false);
            abandonAudioFocus();
        } else {
            this.mMediaPlayer.start();
            setPlayIconState(true);
            requestAudioFocus();
        }
    }

    public void release() {
        LogUtils.d(TAG, "release ...", false);
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            this.mMediaPlayer.stop();
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
        abandonAudioFocus();
    }

    private void initMedia(String videoPath) {
        LogUtils.d(TAG, "init media...", false);
        this.mMediaPlayer = new MediaPlayer();
        MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        try {
            this.mMediaPlayer.setDataSource(videoPath);
            mediaMetadataRetriever.setDataSource(videoPath);
            this.mIvCover.setImageBitmap(mediaMetadataRetriever.getFrameAtTime(0L));
            this.mIvCover.setVisibility(0);
            this.mMediaPlayer.setAudioStreamType(3);
            this.mMediaPlayer.prepare();
            this.mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$BaseVideoDialog$RU2EJYhYXc9wVajU3oRYJjghumw
                @Override // android.media.MediaPlayer.OnPreparedListener
                public final void onPrepared(MediaPlayer mediaPlayer) {
                    BaseVideoDialog.this.lambda$initMedia$2$BaseVideoDialog(mediaPlayer);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.d(TAG, " perhaps has not video. init media occur an exception...", false);
            setPositiveButtonEnable(true);
            if (enableVui()) {
                VuiManager.instance().updateVuiScene(VuiManager.SCENE_XPILOT_DIALOG_VIDEO, this.mContext, getContentView());
            }
        }
        this.mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$BaseVideoDialog$MXa7iRVJfiZOVcn2VyJw7d1MpyM
            @Override // android.media.MediaPlayer.OnCompletionListener
            public final void onCompletion(MediaPlayer mediaPlayer) {
                BaseVideoDialog.this.lambda$initMedia$3$BaseVideoDialog(mediaPlayer);
            }
        });
    }

    public /* synthetic */ void lambda$initMedia$2$BaseVideoDialog(MediaPlayer mp) {
        mp.start();
        mp.seekTo(0);
        mp.pause();
        this.isInit = true;
    }

    public /* synthetic */ void lambda$initMedia$3$BaseVideoDialog(MediaPlayer mp) {
        setPlayIconState(false);
        setPositiveButtonEnable(true);
        if (enableVui()) {
            VuiManager.instance().updateVuiScene(VuiManager.SCENE_XPILOT_DIALOG_VIDEO, this.mContext, getContentView());
        }
    }

    private void setPlayIconState(boolean play) {
        if (play) {
            this.mIvPlayer.setVisibility(8);
            return;
        }
        this.mIvPlayer.setImageResource(R.drawable.ic_icon_play);
        this.mIvPlayer.setVisibility(0);
    }

    @Override // com.xiaopeng.xui.app.XDialog
    public void show() {
        setPositiveButtonEnable(!this.mForceWatch);
        initMedia(this.mVideoPath);
        setPlayIconState(false);
        requestAudioFocus();
        super.show();
        if (enableVui()) {
            VuiManager.instance().initVuiDialog(this, this.mContext, VuiManager.SCENE_XPILOT_DIALOG_VIDEO);
        }
    }

    private void requestAudioFocus() {
        AudioManager audioManager = this.mAudioManager;
        if (audioManager != null) {
            audioManager.requestAudioFocus(this.audioFocusChangeListener, 3, 2);
        }
    }

    private void abandonAudioFocus() {
        AudioManager audioManager = this.mAudioManager;
        if (audioManager != null) {
            audioManager.abandonAudioFocus(this.audioFocusChangeListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setVideoTips(String tips) {
        XTextView xTextView = this.mTvTips;
        if (xTextView != null) {
            xTextView.setText(tips);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setForceWatch(boolean forceWatch) {
        this.mForceWatch = forceWatch;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setVideoPath(String videoPath) {
        this.mVideoPath = videoPath;
    }
}
