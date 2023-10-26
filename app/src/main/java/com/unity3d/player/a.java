package com.unity3d.player;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Range;
import android.util.Size;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class a {
    private static CameraManager b;
    private static String[] c;
    private static Semaphore e = new Semaphore(1);
    private d a;
    private CameraDevice d;
    private HandlerThread f;
    private Handler g;
    private Rect h;
    private Range i;
    private ImageReader j;
    private CaptureRequest.Builder k;
    private CameraCaptureSession l;
    private final CameraDevice.StateCallback m = new CameraDevice.StateCallback() { // from class: com.unity3d.player.a.2
        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onClosed(CameraDevice cameraDevice) {
            g.Log(4, "Camera2: CameraDevice closed.");
            a.e.release();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onDisconnected(CameraDevice cameraDevice) {
            cameraDevice.close();
            a.this.d = null;
            g.Log(5, "Camera2: CameraDevice disconnected.");
            a.e.release();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onError(CameraDevice cameraDevice, int i) {
            cameraDevice.close();
            a.this.d = null;
            g.Log(6, "Camera2: Error opeining CameraDevice " + i);
            a.e.release();
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public final void onOpened(CameraDevice cameraDevice) {
            a.this.d = cameraDevice;
            g.Log(4, "Camera2: CameraDevice opened.");
            a.e.release();
        }
    };
    private final ImageReader.OnImageAvailableListener n = new ImageReader.OnImageAvailableListener() { // from class: com.unity3d.player.a.3
        @Override // android.media.ImageReader.OnImageAvailableListener
        public final void onImageAvailable(ImageReader imageReader) {
            if (a.e.tryAcquire()) {
                Image acquireLatestImage = imageReader.acquireLatestImage();
                if (acquireLatestImage != null) {
                    Image.Plane[] planes = acquireLatestImage.getPlanes();
                    if (acquireLatestImage.getFormat() == 35 && planes != null && planes.length == 3) {
                        a.this.a.a(planes[0].getBuffer(), planes[1].getBuffer(), planes[2].getBuffer(), planes[0].getRowStride(), planes[1].getRowStride(), planes[1].getPixelStride());
                    } else {
                        g.Log(6, "Camera2: Wrong image format.");
                    }
                    acquireLatestImage.close();
                }
                a.e.release();
            }
        }
    };
    private CameraCaptureSession.CaptureCallback o = new CameraCaptureSession.CaptureCallback() { // from class: com.unity3d.player.a.4
        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureFailed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureFailure captureFailure) {
            g.Log(5, "Camera2: Capture session failed " + captureFailure.getReason());
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureSequenceAborted(CameraCaptureSession cameraCaptureSession, int i) {
            g.Log(4, "Camera2: Capture sequence aborted.");
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public final void onCaptureSequenceCompleted(CameraCaptureSession cameraCaptureSession, int i, long j) {
            g.Log(4, "Camera2: Capture sequence completed.");
        }
    };

    /* JADX INFO: Access modifiers changed from: protected */
    public a(d dVar) {
        this.a = null;
        this.a = dVar;
        f();
    }

    public static int a(Context context) {
        return c(context).length;
    }

    public static int a(Context context, int i) {
        try {
            return ((Integer) b(context).getCameraCharacteristics(c(context)[i]).get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
        } catch (CameraAccessException e2) {
            g.Log(6, "Camera2: CameraAccessException " + e2);
            return 0;
        }
    }

    private static Rect a(Size[] sizeArr, double d, double d2) {
        double d3 = Double.MAX_VALUE;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < sizeArr.length; i3++) {
            int width = sizeArr[i3].getWidth();
            int height = sizeArr[i3].getHeight();
            double abs = Math.abs(Math.log(d / width)) + Math.abs(Math.log(d2 / height));
            if (abs < d3) {
                i = width;
                i2 = height;
                d3 = abs;
            }
            g.Log(4, "Camera2: FrameSize " + width + " x " + height + " [" + abs + "]");
        }
        return new Rect(0, 0, i, i2);
    }

    private static Range a(Range[] rangeArr, double d) {
        int i = -1;
        double d2 = Double.MAX_VALUE;
        for (int i2 = 0; i2 < rangeArr.length; i2++) {
            int intValue = ((Integer) rangeArr[i2].getLower()).intValue();
            int intValue2 = ((Integer) rangeArr[i2].getUpper()).intValue();
            double abs = Math.abs(Math.log(d / intValue)) + Math.abs(Math.log(d / intValue2));
            if (abs < d2) {
                i = i2;
                d2 = abs;
            }
            g.Log(4, "Camera2: Frame rate[" + i2 + "] = " + intValue + "-" + intValue2 + " [" + abs + "]");
        }
        return rangeArr[i];
    }

    private static CameraManager b(Context context) {
        if (b == null) {
            b = (CameraManager) context.getSystemService("camera");
        }
        return b;
    }

    public static boolean b(Context context, int i) {
        try {
            return ((Integer) b(context).getCameraCharacteristics(c(context)[i]).get(CameraCharacteristics.LENS_FACING)).intValue() == 0;
        } catch (CameraAccessException e2) {
            g.Log(6, "Camera2: CameraAccessException " + e2);
            return false;
        }
    }

    private static String[] c(Context context) {
        if (c == null) {
            try {
                c = b(context).getCameraIdList();
            } catch (CameraAccessException e2) {
                g.Log(6, "Camera2: CameraAccessException " + e2);
                c = new String[0];
            }
        }
        return c;
    }

    private void f() {
        HandlerThread handlerThread = new HandlerThread("CameraBackground");
        this.f = handlerThread;
        handlerThread.start();
        this.g = new Handler(this.f.getLooper());
    }

    private void g() {
        this.f.quit();
        try {
            this.f.join(4000L);
            this.f = null;
            this.g = null;
        } catch (InterruptedException e2) {
            this.f.interrupt();
            g.Log(6, "Camera2: Interrupted while waiting for the background thread to finish " + e2);
        }
    }

    private void h() {
        try {
            if (!e.tryAcquire(4L, TimeUnit.SECONDS)) {
                g.Log(5, "Camera2: Timeout waiting to lock camera for closing.");
                return;
            }
            this.d.close();
            try {
                if (!e.tryAcquire(4L, TimeUnit.SECONDS)) {
                    g.Log(5, "Camera2: Timeout waiting to close camera.");
                }
            } catch (InterruptedException e2) {
                g.Log(6, "Camera2: Interrupted while waiting to close camera " + e2);
            }
            e.release();
        } catch (InterruptedException e3) {
            g.Log(6, "Camera2: Interrupted while trying to lock camera for closing " + e3);
        }
    }

    public final Rect a() {
        return this.h;
    }

    public final boolean a(Context context, int i, int i2, int i3, int i4) {
        try {
            CameraCharacteristics cameraCharacteristics = b.getCameraCharacteristics(c(context)[i]);
            g.Log(4, "Camera2: Hardware level: " + cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL));
            if (((Integer) cameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL)).intValue() == 2) {
                g.Log(5, "Camera2: only LEGACY hardware level is supported.");
                return false;
            }
            StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            if (streamConfigurationMap == null) {
                g.Log(6, "Camera2: configuration map is not available.");
                return false;
            }
            Size[] outputSizes = streamConfigurationMap.getOutputSizes(35);
            if (outputSizes == null || outputSizes.length == 0) {
                g.Log(6, "Camera2: output sizes for YUV_420_888 format are not avialable.");
                return false;
            }
            this.h = a(outputSizes, i2, i3);
            Range[] rangeArr = (Range[]) cameraCharacteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
            if (rangeArr == null || rangeArr.length == 0) {
                g.Log(6, "Camera2: target FPS ranges are not avialable.");
                return false;
            }
            this.i = a(rangeArr, i4);
            try {
                if (!e.tryAcquire(4L, TimeUnit.SECONDS)) {
                    g.Log(5, "Camera2: Timeout waiting to lock camera for opening.");
                    return false;
                }
                try {
                    b.openCamera(c(context)[i], this.m, this.g);
                    try {
                    } catch (InterruptedException e2) {
                        g.Log(6, "Camera2: Interrupted while waiting to open camera " + e2);
                    }
                    if (e.tryAcquire(4L, TimeUnit.SECONDS)) {
                        e.release();
                        return this.d != null;
                    }
                    g.Log(5, "Camera2: Timeout waiting to open camera.");
                    return false;
                } catch (CameraAccessException e3) {
                    g.Log(6, "Camera2: CameraAccessException " + e3);
                    e.release();
                    return false;
                }
            } catch (InterruptedException e4) {
                g.Log(6, "Camera2: Interrupted while trying to lock camera for opening " + e4);
                return false;
            }
        } catch (CameraAccessException e5) {
            g.Log(6, "Camera2: CameraAccessException " + e5);
            return false;
        }
    }

    public final void b() {
        g.Log(4, "Camera2: Close.");
        if (this.d != null) {
            d();
            h();
            this.d = null;
            this.j.close();
            this.j = null;
        }
        g();
    }

    public final void c() {
        g.Log(4, "Camera2: Start preview.");
        if (this.j == null) {
            ImageReader newInstance = ImageReader.newInstance(this.h.width(), this.h.height(), 35, 2);
            this.j = newInstance;
            newInstance.setOnImageAvailableListener(this.n, this.g);
        }
        try {
            this.d.createCaptureSession(Arrays.asList(this.j.getSurface()), new CameraCaptureSession.StateCallback() { // from class: com.unity3d.player.a.1
                @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                public final void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
                    g.Log(6, "Camera2: CaptureSession configuration failed.");
                }

                @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                public final void onConfigured(CameraCaptureSession cameraCaptureSession) {
                    g.Log(4, "Camera2: CaptureSession is configured.");
                    if (a.this.d == null) {
                        return;
                    }
                    a.this.l = cameraCaptureSession;
                    try {
                        a aVar = a.this;
                        aVar.k = aVar.d.createCaptureRequest(1);
                        a.this.k.addTarget(a.this.j.getSurface());
                        a.this.k.set(CaptureRequest.CONTROL_AF_MODE, 4);
                        a.this.k.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, a.this.i);
                        a.this.l.setRepeatingRequest(a.this.k.build(), a.this.o, a.this.g);
                    } catch (CameraAccessException e2) {
                        g.Log(6, "Camera2: CameraAccessException " + e2);
                    }
                }
            }, this.g);
        } catch (CameraAccessException e2) {
            g.Log(6, "Camera2: CameraAccessException " + e2);
        }
    }

    public final void d() {
        g.Log(4, "Camera2: Stop preview.");
        CameraCaptureSession cameraCaptureSession = this.l;
        if (cameraCaptureSession != null) {
            try {
                cameraCaptureSession.abortCaptures();
            } catch (CameraAccessException e2) {
                g.Log(6, "Camera2: CameraAccessException " + e2);
            }
            this.l.close();
            this.l = null;
        }
    }
}
