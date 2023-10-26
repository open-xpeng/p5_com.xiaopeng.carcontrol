package com.xiaopeng.xui.app;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xuimanager.systemui.masklayer.IMaskLayerListener;
import com.xiaopeng.xuimanager.systemui.masklayer.MaskLayerManager;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

/* loaded from: classes2.dex */
public class XMaskLayer {
    private static final String TAG = "XMaskLayer";
    private Context mContext;
    private TN mTN;

    /* loaded from: classes2.dex */
    public interface MaskLayerInterface {
        void onHide();

        void onShow();
    }

    public static void init(Context context) {
    }

    private XMaskLayer(Context context, TN tn) {
        this.mContext = context;
        this.mTN = tn;
    }

    public void show() {
        Log.i(TAG, "requestShow: mTN = " + this.mTN + "mScreenId" + this.mTN.mScreenId);
        MaskLayerManager maskLayerManager = MaskLayerManager.get();
        TN tn = this.mTN;
        maskLayerManager.requestShow(tn, tn.mIsStackWindow, this.mTN.mScreenId);
    }

    public void cancel() throws RemoteException {
        if (this.mTN.getWindowState()) {
            MaskLayerManager maskLayerManager = MaskLayerManager.get();
            TN tn = this.mTN;
            maskLayerManager.notifyDismiss(tn, tn.mScreenId);
            this.mTN.dismissMaskLayer();
        }
    }

    /* loaded from: classes2.dex */
    public static class XMaskLayerBuilder {
        public static final int ID_SHARED_PRIMARY = 0;
        public static final int ID_SHARED_SECONDARY = 1;
        private int mBg;
        private Context mContext;
        private View mItemView;
        private boolean mClickable = false;
        private int mScreenId = 0;
        private boolean mIsStackWindow = true;
        private int mWidth = 2400;
        private int mHeight = 1200;

        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes2.dex */
        public @interface ScreenId {
        }

        public XMaskLayerBuilder setContext(Context context) {
            this.mContext = context;
            return this;
        }

        public XMaskLayerBuilder setWidth(int i) {
            this.mWidth = i;
            return this;
        }

        public XMaskLayerBuilder setHeight(int i) {
            this.mHeight = i;
            return this;
        }

        public XMaskLayerBuilder setClickable(boolean z) {
            this.mClickable = z;
            return this;
        }

        public XMaskLayerBuilder setStackWindow(boolean z) {
            this.mIsStackWindow = z;
            return this;
        }

        public XMaskLayerBuilder setScreenId(int i) {
            this.mScreenId = i;
            return this;
        }

        public XMaskLayerBuilder setItemView(View view) {
            this.mItemView = view;
            return this;
        }

        public XMaskLayerBuilder setBackground(int i) {
            this.mBg = i;
            return this;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public XMaskLayer create() {
            TN tn = new TN(this.mContext, this.mWidth, this.mHeight, this.mScreenId, this.mIsStackWindow, this.mBg, this.mClickable, this.mItemView);
            Log.i(XMaskLayer.TAG, "create: TN : 构造TN");
            return new XMaskLayer(this.mContext, tn);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class TN extends IMaskLayerListener.Stub {
        private static final int HIDE = 1;
        private MaskLayerInterface listener;
        private BackgroundLayout mBackgroundLayout;
        private final int mBg;
        private boolean mClickable;
        private final Context mContext;
        private final Handler mHandler;
        private boolean mIsStackWindow;
        private final WindowManager.LayoutParams mParams;
        private final int mScreenId;
        private final WindowManager mWM;
        private boolean mWindowState = false;

        public TN(Context context, int i, int i2, int i3, boolean z, int i4, boolean z2, View view) {
            this.mContext = context;
            this.mClickable = z2;
            this.mIsStackWindow = z;
            this.mScreenId = i3;
            this.mBg = i4;
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            this.mParams = layoutParams;
            this.mWM = (WindowManager) context.getSystemService("window");
            layoutParams.format = -2;
            layoutParams.width = i;
            layoutParams.height = i2;
            layoutParams.x = 0;
            layoutParams.y = 0;
            if (this.mClickable) {
                layoutParams.flags = 16777256;
            } else {
                layoutParams.flags = 25165880;
            }
            setXpFlags(layoutParams, i3);
            setBackgroundLayout(view);
            this.mHandler = new Handler(Looper.myLooper(), null) { // from class: com.xiaopeng.xui.app.XMaskLayer.TN.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    super.handleMessage(message);
                    if (message.what != 1) {
                        return;
                    }
                    TN.this.handleHide();
                }
            };
        }

        private void setBackgroundLayout(View view) {
            BackgroundLayout backgroundLayout = new BackgroundLayout(this.mContext, this.mBg);
            this.mBackgroundLayout = backgroundLayout;
            backgroundLayout.setTN(this);
            if (view != null) {
                this.mBackgroundLayout.addView(view);
            }
        }

        private static void setXpFlags(WindowManager.LayoutParams layoutParams, int i) {
            try {
                Field field = WindowManager.LayoutParams.class.getField("xpFlags");
                int i2 = field.getInt(layoutParams);
                Log.d(XMaskLayer.TAG, "currentXpFlags : " + i2);
                Field field2 = WindowManager.LayoutParams.class.getField("intentFlags");
                field2.setInt(layoutParams, field2.getInt(layoutParams) | 32);
                if (i == 0) {
                    i2 |= 16;
                    layoutParams.gravity = 51;
                } else if (i == 1) {
                    i2 |= 32;
                    layoutParams.gravity = 53;
                }
                field.setInt(layoutParams, i2);
            } catch (Exception unused) {
                Log.w(XMaskLayer.TAG, "xpFlags is null");
            }
            try {
                Field field3 = WindowManager.LayoutParams.class.getField("displayId");
                Log.d(XMaskLayer.TAG, "currentDisplayId : " + field3);
                field3.setInt(layoutParams, i);
                layoutParams.gravity = 51;
            } catch (IllegalAccessException | NoSuchFieldException unused2) {
                Log.w(XMaskLayer.TAG, "displayId is null");
            }
        }

        private void handleShow() {
            if (this.mWM != null) {
                Log.i(XMaskLayer.TAG, "showMaskLayer: 弹出水印");
                this.mWM.addView(this.mBackgroundLayout, this.mParams);
            }
            this.mWindowState = true;
            MaskLayerInterface maskLayerInterface = this.listener;
            if (maskLayerInterface != null) {
                maskLayerInterface.onShow();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleHide() {
            this.mHandler.removeMessages(1);
            if (this.mWM != null) {
                Log.i(XMaskLayer.TAG, "dismissMaskLayer: 隐藏水印");
                this.mWM.removeView(this.mBackgroundLayout);
            }
            this.mWindowState = false;
            MaskLayerInterface maskLayerInterface = this.listener;
            if (maskLayerInterface != null) {
                maskLayerInterface.onHide();
            }
            MaskLayerManager.get().notifyDismiss(this, this.mScreenId);
        }

        public boolean getWindowState() {
            return this.mWindowState;
        }

        public void showMaskLayer(int i) throws RemoteException {
            this.mParams.type = i;
            handleShow();
        }

        public void dismissMaskLayer() {
            Log.i(XMaskLayer.TAG, "dismissMaskLayer: 收到dismiss回调");
            handleHide();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class BackgroundLayout extends RelativeLayout {
        private final int mBg;
        private final Context mContext;
        private TN mTN;

        public BackgroundLayout(Context context, int i) {
            super(context);
            this.mContext = context;
            this.mBg = i;
        }

        public void setTN(TN tn) {
            this.mTN = tn;
        }

        @Override // android.view.View
        protected void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            if (XThemeManager.isThemeChanged(configuration)) {
                int i = this.mBg;
                if (i != 0) {
                    setBackground(this.mContext.getDrawable(i));
                }
                invalidate();
            }
        }
    }
}
