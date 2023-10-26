package com.unity3d.player;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;
import com.xiaopeng.lib.bughunter.anr.UILooperObserver;
import java.io.FileInputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public final class p extends FrameLayout implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback, MediaController.MediaPlayerControl {
    private static boolean a = false;
    private final Context b;
    private final SurfaceView c;
    private final SurfaceHolder d;
    private final String e;
    private final int f;
    private final int g;
    private final boolean h;
    private final long i;
    private final long j;
    private final FrameLayout k;
    private final Display l;
    private int m;
    private int n;
    private int o;
    private int p;
    private MediaPlayer q;
    private MediaController r;
    private boolean s;
    private boolean t;
    private int u;
    private boolean v;
    private boolean w;
    private a x;
    private b y;
    private volatile int z;

    /* loaded from: classes.dex */
    public interface a {
        void a(int i);
    }

    /* loaded from: classes.dex */
    public class b implements Runnable {
        private p b;
        private boolean c = false;

        public b(p pVar) {
            this.b = pVar;
        }

        public final void a() {
            this.c = true;
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                Thread.sleep(UILooperObserver.ANR_TRIGGER_TIME);
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
            if (this.c) {
                return;
            }
            if (p.a) {
                p.b("Stopping the video player due to timeout.");
            }
            this.b.CancelOnPrepare();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public p(Context context, String str, int i, int i2, int i3, boolean z, long j, long j2, a aVar) {
        super(context);
        this.s = false;
        this.t = false;
        this.u = 0;
        this.v = false;
        this.w = false;
        this.z = 0;
        this.x = aVar;
        this.b = context;
        this.k = this;
        SurfaceView surfaceView = new SurfaceView(context);
        this.c = surfaceView;
        SurfaceHolder holder = surfaceView.getHolder();
        this.d = holder;
        holder.addCallback(this);
        setBackgroundColor(i);
        addView(surfaceView);
        this.l = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        this.e = str;
        this.f = i2;
        this.g = i3;
        this.h = z;
        this.i = j;
        this.j = j2;
        if (a) {
            b("fileName: " + str);
        }
        if (a) {
            b("backgroundColor: " + i);
        }
        if (a) {
            b("controlMode: " + i2);
        }
        if (a) {
            b("scalingMode: " + i3);
        }
        if (a) {
            b("isURL: " + z);
        }
        if (a) {
            b("videoOffset: " + j);
        }
        if (a) {
            b("videoLength: " + j2);
        }
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    private void a(int i) {
        this.z = i;
        a aVar = this.x;
        if (aVar != null) {
            aVar.a(this.z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(String str) {
        Log.i("Video", "VideoPlayer: " + str);
    }

    private void c() {
        FileInputStream fileInputStream;
        MediaPlayer mediaPlayer = this.q;
        if (mediaPlayer != null) {
            mediaPlayer.setDisplay(this.d);
            if (this.v) {
                return;
            }
            if (a) {
                b("Resuming playback");
            }
            this.q.start();
            return;
        }
        a(0);
        doCleanUp();
        try {
            MediaPlayer mediaPlayer2 = new MediaPlayer();
            this.q = mediaPlayer2;
            if (this.h) {
                mediaPlayer2.setDataSource(this.b, Uri.parse(this.e));
            } else {
                if (this.j != 0) {
                    fileInputStream = new FileInputStream(this.e);
                    this.q.setDataSource(fileInputStream.getFD(), this.i, this.j);
                } else {
                    try {
                        AssetFileDescriptor openFd = getResources().getAssets().openFd(this.e);
                        this.q.setDataSource(openFd.getFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
                        openFd.close();
                    } catch (IOException unused) {
                        fileInputStream = new FileInputStream(this.e);
                        this.q.setDataSource(fileInputStream.getFD());
                    }
                }
                fileInputStream.close();
            }
            this.q.setDisplay(this.d);
            this.q.setScreenOnWhilePlaying(true);
            this.q.setOnBufferingUpdateListener(this);
            this.q.setOnCompletionListener(this);
            this.q.setOnPreparedListener(this);
            this.q.setOnVideoSizeChangedListener(this);
            this.q.setAudioStreamType(3);
            this.q.prepareAsync();
            this.y = new b(this);
            new Thread(this.y).start();
        } catch (Exception e) {
            if (a) {
                b("error: " + e.getMessage() + e);
            }
            a(2);
        }
    }

    private void d() {
        if (isPlaying()) {
            return;
        }
        a(1);
        if (a) {
            b("startVideoPlayback");
        }
        updateVideoLayout();
        if (this.v) {
            return;
        }
        start();
    }

    public final void CancelOnPrepare() {
        a(2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean a() {
        return this.v;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final boolean canPause() {
        return true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final boolean canSeekBackward() {
        return true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final boolean canSeekForward() {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final void destroyPlayer() {
        if (a) {
            b("destroyPlayer");
        }
        if (!this.v) {
            pause();
        }
        doCleanUp();
    }

    protected final void doCleanUp() {
        b bVar = this.y;
        if (bVar != null) {
            bVar.a();
            this.y = null;
        }
        MediaPlayer mediaPlayer = this.q;
        if (mediaPlayer != null) {
            mediaPlayer.release();
            this.q = null;
        }
        this.o = 0;
        this.p = 0;
        this.t = false;
        this.s = false;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final int getBufferPercentage() {
        if (this.h) {
            return this.u;
        }
        return 100;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final int getCurrentPosition() {
        MediaPlayer mediaPlayer = this.q;
        if (mediaPlayer == null) {
            return 0;
        }
        return mediaPlayer.getCurrentPosition();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final int getDuration() {
        MediaPlayer mediaPlayer = this.q;
        if (mediaPlayer == null) {
            return 0;
        }
        return mediaPlayer.getDuration();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final boolean isPlaying() {
        boolean z = this.t && this.s;
        MediaPlayer mediaPlayer = this.q;
        return mediaPlayer == null ? !z : mediaPlayer.isPlaying() || !z;
    }

    @Override // android.media.MediaPlayer.OnBufferingUpdateListener
    public final void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        if (a) {
            b("onBufferingUpdate percent:" + i);
        }
        this.u = i;
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public final void onCompletion(MediaPlayer mediaPlayer) {
        if (a) {
            b("onCompletion called");
        }
        destroyPlayer();
        a(3);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 && (this.f != 2 || i == 0 || keyEvent.isSystem())) {
            MediaController mediaController = this.r;
            return mediaController != null ? mediaController.onKeyDown(i, keyEvent) : super.onKeyDown(i, keyEvent);
        }
        destroyPlayer();
        a(3);
        return true;
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public final void onPrepared(MediaPlayer mediaPlayer) {
        if (a) {
            b("onPrepared called");
        }
        b bVar = this.y;
        if (bVar != null) {
            bVar.a();
            this.y = null;
        }
        int i = this.f;
        if (i == 0 || i == 1) {
            MediaController mediaController = new MediaController(this.b);
            this.r = mediaController;
            mediaController.setMediaPlayer(this);
            this.r.setAnchorView(this);
            this.r.setEnabled(true);
            this.r.show();
        }
        this.t = true;
        if (this.s) {
            d();
        }
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (this.f != 2 || action != 0) {
            MediaController mediaController = this.r;
            return mediaController != null ? mediaController.onTouchEvent(motionEvent) : super.onTouchEvent(motionEvent);
        }
        destroyPlayer();
        a(3);
        return true;
    }

    @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
    public final void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
        if (a) {
            b("onVideoSizeChanged called " + i + "x" + i2);
        }
        if (i == 0 || i2 == 0) {
            if (a) {
                b("invalid video width(" + i + ") or height(" + i2 + ")");
                return;
            }
            return;
        }
        this.s = true;
        this.o = i;
        this.p = i2;
        if (!this.t || 1 == 0) {
            return;
        }
        d();
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final void pause() {
        MediaPlayer mediaPlayer = this.q;
        if (mediaPlayer == null) {
            return;
        }
        if (this.w) {
            mediaPlayer.pause();
        }
        this.v = true;
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final void seekTo(int i) {
        MediaPlayer mediaPlayer = this.q;
        if (mediaPlayer == null) {
            return;
        }
        mediaPlayer.seekTo(i);
    }

    @Override // android.widget.MediaController.MediaPlayerControl
    public final void start() {
        if (a) {
            b("Start");
        }
        MediaPlayer mediaPlayer = this.q;
        if (mediaPlayer == null) {
            return;
        }
        if (this.w) {
            mediaPlayer.start();
        }
        this.v = false;
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (a) {
            b("surfaceChanged called " + i + " " + i2 + "x" + i3);
        }
        if (this.m == i2 && this.n == i3) {
            return;
        }
        this.m = i2;
        this.n = i3;
        if (this.w) {
            updateVideoLayout();
        }
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (a) {
            b("surfaceCreated called");
        }
        this.w = true;
        c();
    }

    @Override // android.view.SurfaceHolder.Callback
    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (a) {
            b("surfaceDestroyed called");
        }
        this.w = false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x004f, code lost:
        if (r5 <= r3) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0051, code lost:
        r1 = (int) (r0 / r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0055, code lost:
        r0 = (int) (r1 * r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x005e, code lost:
        if (r5 >= r3) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateVideoLayout() {
        /*
            r8 = this;
            boolean r0 = com.unity3d.player.p.a
            if (r0 == 0) goto La
            java.lang.String r0 = "updateVideoLayout"
            b(r0)
        La:
            android.media.MediaPlayer r0 = r8.q
            if (r0 != 0) goto Lf
            return
        Lf:
            int r0 = r8.m
            if (r0 == 0) goto L17
            int r0 = r8.n
            if (r0 != 0) goto L36
        L17:
            android.content.Context r0 = r8.b
            java.lang.String r1 = "window"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.view.WindowManager r0 = (android.view.WindowManager) r0
            android.util.DisplayMetrics r1 = new android.util.DisplayMetrics
            r1.<init>()
            android.view.Display r0 = r0.getDefaultDisplay()
            r0.getMetrics(r1)
            int r0 = r1.widthPixels
            r8.m = r0
            int r0 = r1.heightPixels
            r8.n = r0
        L36:
            int r0 = r8.m
            int r1 = r8.n
            boolean r2 = r8.s
            if (r2 == 0) goto L66
            int r2 = r8.o
            float r3 = (float) r2
            int r4 = r8.p
            float r5 = (float) r4
            float r3 = r3 / r5
            float r5 = (float) r0
            float r6 = (float) r1
            float r5 = r5 / r6
            int r6 = r8.g
            r7 = 1
            if (r6 != r7) goto L59
            int r2 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r2 > 0) goto L55
        L51:
            float r1 = (float) r0
            float r1 = r1 / r3
            int r1 = (int) r1
            goto L70
        L55:
            float r0 = (float) r1
            float r0 = r0 * r3
            int r0 = (int) r0
            goto L70
        L59:
            r7 = 2
            if (r6 != r7) goto L61
            int r2 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r2 < 0) goto L55
            goto L51
        L61:
            if (r6 != 0) goto L70
            r0 = r2
            r1 = r4
            goto L70
        L66:
            boolean r2 = com.unity3d.player.p.a
            if (r2 == 0) goto L70
            java.lang.String r2 = "updateVideoLayout: Video size is not known yet"
            b(r2)
        L70:
            int r2 = r8.m
            if (r2 != r0) goto L78
            int r2 = r8.n
            if (r2 == r1) goto La6
        L78:
            boolean r2 = com.unity3d.player.p.a
            if (r2 == 0) goto L98
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "frameWidth = "
            r2.<init>(r3)
            java.lang.StringBuilder r2 = r2.append(r0)
            java.lang.String r3 = "; frameHeight = "
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.StringBuilder r2 = r2.append(r1)
            java.lang.String r2 = r2.toString()
            b(r2)
        L98:
            android.widget.FrameLayout$LayoutParams r2 = new android.widget.FrameLayout$LayoutParams
            r3 = 17
            r2.<init>(r0, r1, r3)
            android.widget.FrameLayout r0 = r8.k
            android.view.SurfaceView r1 = r8.c
            r0.updateViewLayout(r1, r2)
        La6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.unity3d.player.p.updateVideoLayout():void");
    }
}
