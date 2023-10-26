package com.xiaopeng.xui.app;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.core.content.ContextCompat;
import com.xiaopeng.appstore.storeprovider.AssembleRequest;
import com.xiaopeng.vui.commons.IVuiEngine;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.vui.IVuiViewScene;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.popPanelView.XPopPanelView;
import com.xiaopeng.xuimanager.systemui.osd.IOsdListener;
import com.xiaopeng.xuimanager.systemui.osd.OsdManager;
import com.xiaopeng.xuimanager.systemui.osd.OsdRegionRecord;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

/* loaded from: classes2.dex */
public class XPopPanel implements IVuiViewScene {
    private static final String TAG = "XPopPanel";
    private static final String VUI_SCENE_TAG = "PopPanel";
    private Context mContext;
    private XPopPanelView mPopPanelView;
    private TN mTN;

    public static void init(Context context) {
    }

    private XPopPanel(Context context, TN tn, View view) {
        this.mContext = context;
        this.mTN = tn;
        if (view != null) {
            XPopPanelView xPopPanelView = new XPopPanelView(view);
            this.mPopPanelView = xPopPanelView;
            xPopPanelView.setPopPanelViewInterface(new XPopPanelView.IPopPanelViewInterface() { // from class: com.xiaopeng.xui.app.XPopPanel.1
                @Override // com.xiaopeng.xui.widget.popPanelView.XPopPanelView.IPopPanelViewInterface
                public int getDisplayScreenId() {
                    return XPopPanel.this.getScreenId();
                }

                @Override // com.xiaopeng.xui.widget.popPanelView.XPopPanelView.IPopPanelViewInterface
                public void onVuiEventExecuted() {
                    Log.d(XPopPanel.TAG, "onVuiEventExecuted");
                    XPopPanel.this.handleUserEvent();
                }
            });
        }
    }

    public void show() {
        OsdManager osdManager = OsdManager.get();
        TN tn = this.mTN;
        osdManager.requestShow(tn, tn.mType, this.mTN.mRegionId);
    }

    public void cancel() {
        TN tn = this.mTN;
        if (tn == null || !tn.getWindowState()) {
            return;
        }
        this.mTN.handleHide(102);
        OsdManager osdManager = OsdManager.get();
        TN tn2 = this.mTN;
        osdManager.notifyHide(tn2, tn2.mRegionId);
    }

    public boolean isShowing() {
        TN tn = this.mTN;
        if (tn != null) {
            return tn.getWindowState();
        }
        return false;
    }

    public int getScreenId() {
        TN tn = this.mTN;
        if (tn != null) {
            return tn.getScreenId();
        }
        return 1;
    }

    public void setOnStateListener(PopPanelInterface popPanelInterface) {
        TN tn = this.mTN;
        if (tn != null) {
            tn.setListener(popPanelInterface);
        }
    }

    public void handleUserEvent() {
        TN tn = this.mTN;
        if (tn != null) {
            tn.holdPanel();
            this.mTN.reHidePanel();
        }
    }

    public void setItemView(View view) {
        TN tn = this.mTN;
        if (tn == null || view == null) {
            return;
        }
        tn.resetItemView(view);
    }

    public void removeItemView() {
        TN tn = this.mTN;
        if (tn != null) {
            tn.removeItemView();
        }
    }

    /* loaded from: classes2.dex */
    public static class XPopPanelBuilder {
        public static final int ID_SHARED_PRIMARY = 1;
        public static final int ID_SHARED_SECONDARY = 2;
        public static final int LENGTH_LONG = 5000;
        public static final int LENGTH_LONGER = 10000;
        public static final int LENGTH_SHORT = 2000;
        public static final int TYPE_NORMAL_WINDOW = 1;
        public static final int TYPE_STACK_WINDOW = 2;
        private boolean mClickable;
        private Context mContext;
        private View mItemView;
        private String mRegionId;
        private int mDuration = 2000;
        private int mWidth = AssembleRequest.ASSEMBLE_ACTION_CANCEL;
        private int mHeight = AssembleRequest.ASSEMBLE_ACTION_CANCEL;
        private int mType = 1;
        private int mBg = R.drawable.x_osd_background;
        private boolean mAutoHeight = true;
        private boolean mAutoHide = true;

        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes2.dex */
        public @interface Duration {
        }

        @Retention(RetentionPolicy.SOURCE)
        /* loaded from: classes2.dex */
        public @interface Type {
        }

        public XPopPanelBuilder setContext(Context context) {
            this.mContext = context;
            return this;
        }

        public XPopPanelBuilder setAutoHide(boolean z) {
            this.mAutoHide = z;
            return this;
        }

        public XPopPanelBuilder setDurationLength(int i) {
            this.mDuration = i;
            return this;
        }

        public XPopPanelBuilder setWidth(int i) {
            this.mWidth = i;
            return this;
        }

        public XPopPanelBuilder setHeight(int i) {
            this.mHeight = i;
            return this;
        }

        public XPopPanelBuilder setItemView(View view) {
            this.mItemView = view;
            return this;
        }

        public XPopPanelBuilder setRegionId(String str) {
            this.mRegionId = str;
            return this;
        }

        public XPopPanelBuilder setType(int i) {
            this.mType = i;
            return this;
        }

        public XPopPanelBuilder setBackground(int i) {
            this.mBg = i;
            return this;
        }

        public XPopPanelBuilder setClickable(boolean z) {
            this.mClickable = z;
            return this;
        }

        public XPopPanelBuilder setSelfAdapterHeight(boolean z) {
            this.mAutoHeight = z;
            return this;
        }

        public int getWidth() {
            return this.mWidth;
        }

        public int getHeight() {
            return this.mHeight;
        }

        public XPopPanel create() {
            Log.i(XPopPanel.TAG, "create: mContext = " + this.mContext + "  mWidth = " + this.mWidth + "  mHeight = " + this.mHeight + "  mDuration = " + this.mDuration + "  mRegionId = " + this.mRegionId + "  mType = " + this.mType + "  mBg = " + this.mBg + "  mClickable = " + this.mClickable + "  mItemView = " + this.mItemView);
            return new XPopPanel(this.mContext, new TN(this.mContext, this.mWidth, this.mHeight, this.mDuration, this.mRegionId, this.mType, this.mBg, this.mClickable, this.mItemView, this.mAutoHeight, this.mAutoHide), this.mItemView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class TN extends IOsdListener.Stub {
        private static final int HIDE = 1;
        private PopPanelInterface listener;
        private boolean mAutoHeight;
        private boolean mAutoHide;
        private BackgroundLayout mBackgroundLayout;
        private final int mBg;
        private final boolean mClickable;
        private final Context mContext;
        private final int mDuration;
        private final Handler mHandler;
        private final int mHeight;
        private View mItemView;
        private final WindowManager.LayoutParams mParams;
        private final String mRegionId;
        private int mScreenId;
        private final int mType;
        private final WindowManager mWM;
        private final int mWidth;

        public TN(Context context, int i, int i2, int i3, String str, int i4, int i5, boolean z, View view, boolean z2, boolean z3) {
            this.mType = i4;
            this.mDuration = i3;
            this.mContext = context;
            this.mWidth = i;
            this.mHeight = i2;
            this.mBg = i5;
            this.mRegionId = str;
            this.mClickable = z;
            this.mItemView = view;
            this.mAutoHeight = z2;
            this.mAutoHide = z3;
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            this.mParams = layoutParams;
            this.mWM = (WindowManager) context.getSystemService("window");
            layoutParams.gravity = 51;
            layoutParams.format = -3;
            layoutParams.flags = 168;
            layoutParams.packageName = context.getPackageName();
            layoutParams.windowAnimations = R.style.XAnimation_XOsd;
            this.mHandler = new Handler(Looper.myLooper(), null) { // from class: com.xiaopeng.xui.app.XPopPanel.TN.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    super.handleMessage(message);
                    if (message.what != 1) {
                        return;
                    }
                    TN.this.handleHide(200);
                }
            };
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setListener(PopPanelInterface popPanelInterface) {
            this.listener = popPanelInterface;
        }

        private void setBackgroundLayout(View view, int i, int i2, int i3, int i4) {
            if (this.mAutoHeight) {
                i3 = 0;
            }
            int i5 = i3;
            if (this.mBackgroundLayout == null) {
                BackgroundLayout backgroundLayout = new BackgroundLayout(this.mContext, this.mBg, this.mClickable, i2, i5, i4, this.listener);
                this.mBackgroundLayout = backgroundLayout;
                backgroundLayout.setClipToOutline(true);
                this.mBackgroundLayout.setTN(this);
                this.mBackgroundLayout.setBackground(ContextCompat.getDrawable(this.mContext, this.mBg));
                this.mBackgroundLayout.setMinimumHeight(i);
            }
            if (view != null) {
                if (this.mItemView.getParent() != null) {
                    ((ViewGroup) this.mItemView.getParent()).removeAllViews();
                    Log.d(XPopPanel.TAG, "remove child");
                }
                this.mBackgroundLayout.addView(view);
            }
        }

        private static void setXpFlags(WindowManager.LayoutParams layoutParams, int i) {
            try {
                Field field = WindowManager.LayoutParams.class.getField("xpFlags");
                Field field2 = WindowManager.LayoutParams.class.getField("displayId");
                int i2 = field.getInt(layoutParams);
                if (i == 1) {
                    field.setInt(layoutParams, i2 | 16);
                } else if (i == 2) {
                    field.setInt(layoutParams, i2 | 32);
                } else if (i == 3) {
                    field2.setInt(layoutParams, i);
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        private void handleShow() {
            if (this.mBackgroundLayout.getParent() != null) {
                this.mWM.removeView(this.mBackgroundLayout);
            }
            if (this.mWM != null) {
                Log.d(XPopPanel.TAG, "BackgroundLayout : " + this.mBackgroundLayout);
                this.mWM.addView(this.mBackgroundLayout, this.mParams);
            }
            PopPanelInterface popPanelInterface = this.listener;
            if (popPanelInterface != null) {
                popPanelInterface.onShow();
            }
            if (this.mAutoHide) {
                this.mHandler.sendEmptyMessageDelayed(1, this.mDuration);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleHide(int i) {
            this.mHandler.removeMessages(1);
            if (this.mBackgroundLayout.getParent() != null) {
                this.mWM.removeView(this.mBackgroundLayout);
            }
            PopPanelInterface popPanelInterface = this.listener;
            if (popPanelInterface != null) {
                popPanelInterface.onHide();
                this.listener.onHide(i);
            }
            OsdManager.get().notifyHide(this, this.mRegionId);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void holdPanel() {
            this.mHandler.removeMessages(1);
        }

        public void reHidePanel() {
            if (this.mAutoHide) {
                this.mHandler.sendEmptyMessageDelayed(1, this.mDuration);
            }
        }

        public boolean getWindowState() {
            BackgroundLayout backgroundLayout = this.mBackgroundLayout;
            return (backgroundLayout == null || backgroundLayout.getParent() == null) ? false : true;
        }

        public int getScreenId() {
            return this.mScreenId;
        }

        public void setScreenId(int i) {
            this.mScreenId = i;
        }

        public void resetItemView(View view) {
            BackgroundLayout backgroundLayout = this.mBackgroundLayout;
            if (backgroundLayout != null) {
                this.mItemView = view;
                backgroundLayout.addView(view);
            }
        }

        public void removeItemView() {
            View view = this.mItemView;
            if (view != null) {
                if (view.getParent() != null) {
                    ((ViewGroup) this.mItemView.getParent()).removeAllViews();
                }
                this.mItemView = null;
            }
        }

        public void hideOsd() {
            handleHide(101);
        }

        public void showOsd(OsdRegionRecord osdRegionRecord) {
            Log.i(XPopPanel.TAG, "showPanel: recordID = " + osdRegionRecord.getScreenId() + " recordMinWidth = " + osdRegionRecord.getMinWidth() + " MaxWidth = " + osdRegionRecord.getMaxWidth() + " WindowType = " + osdRegionRecord.getWindowType());
            if (osdRegionRecord != null) {
                this.mParams.x = osdRegionRecord.getX();
                this.mParams.y = osdRegionRecord.getY();
                if (this.mWidth >= osdRegionRecord.getMinWidth() && this.mWidth <= osdRegionRecord.getMaxWidth()) {
                    this.mParams.width = this.mWidth;
                } else if (this.mWidth < osdRegionRecord.getMinWidth()) {
                    this.mParams.width = osdRegionRecord.getMinWidth();
                } else if (this.mWidth > osdRegionRecord.getMaxWidth()) {
                    this.mParams.width = osdRegionRecord.getMaxWidth();
                }
                if (this.mHeight >= osdRegionRecord.getMinHeight() && this.mHeight <= osdRegionRecord.getMaxHeight()) {
                    this.mParams.height = this.mHeight;
                } else if (this.mHeight < osdRegionRecord.getMinHeight()) {
                    this.mParams.height = osdRegionRecord.getMinHeight();
                } else if (this.mHeight > osdRegionRecord.getMaxHeight()) {
                    this.mParams.height = osdRegionRecord.getMaxHeight();
                }
                this.mParams.type = osdRegionRecord.getWindowType();
                setScreenId(osdRegionRecord.getScreenId());
                setXpFlags(this.mParams, osdRegionRecord.getScreenId());
                setBackgroundLayout(this.mItemView, osdRegionRecord.getMinHeight(), this.mParams.width, this.mParams.height, osdRegionRecord.getMaxWidth());
                Log.d(XPopPanel.TAG, "setBackground Success");
                handleShow();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class BackgroundLayout extends XLinearLayout {
        private final int mBg;
        private final boolean mClickable;
        private final Context mContext;
        private final PopPanelInterface mListener;
        private final int mMaxHeight;
        private final int mSetHeight;
        private final int mSetWidth;
        private TN mTN;

        public BackgroundLayout(Context context, int i, boolean z, int i2, int i3, int i4, PopPanelInterface popPanelInterface) {
            super(context);
            this.mContext = context;
            this.mBg = i;
            this.mSetWidth = i2;
            this.mSetHeight = i3;
            this.mMaxHeight = i4;
            this.mClickable = z;
            this.mListener = popPanelInterface;
            this.mXViewDelegate.getThemeViewModel().setBackgroundResource(i);
        }

        public void setTN(TN tn) {
            this.mTN = tn;
        }

        @Override // android.widget.LinearLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mSetWidth, BasicMeasure.EXACTLY);
            int i3 = this.mSetHeight;
            if (i3 <= 0) {
                int i4 = this.mMaxHeight;
                if (i4 >= 0) {
                    super.onMeasure(makeMeasureSpec, View.MeasureSpec.makeMeasureSpec(i4, Integer.MIN_VALUE));
                    return;
                } else {
                    super.onMeasure(makeMeasureSpec, i2);
                    return;
                }
            }
            super.onMeasure(makeMeasureSpec, View.MeasureSpec.makeMeasureSpec(i3, BasicMeasure.EXACTLY));
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.View
        public void onConfigurationChanged(Configuration configuration) {
            Log.i(XPopPanel.TAG, "onConfigurationChanged: X = " + this.mTN.mParams.x + "  Y = " + this.mTN.mParams.y);
            super.onConfigurationChanged(configuration);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0) {
                if (actionMasked == 1) {
                    this.mTN.reHidePanel();
                } else if (actionMasked == 3) {
                    this.mTN.handleHide(100);
                }
            } else if (!this.mClickable) {
                return false;
            } else {
                this.mTN.holdPanel();
            }
            return super.dispatchTouchEvent(motionEvent);
        }
    }

    /* loaded from: classes2.dex */
    public interface PopPanelInterface {
        default void onHide() {
        }

        default void onShow() {
        }

        default void onHide(int i) {
            if (i == 200) {
                Log.d(XPopPanel.TAG, "code : " + i + "pop panel hides normally");
                return;
            }
            switch (i) {
                case 100:
                    Log.d(XPopPanel.TAG, "code : " + i + "pop panel is hidden by MotionEvent.ACTION_CANCEL");
                    return;
                case 101:
                    Log.d(XPopPanel.TAG, "code : " + i + "pop panel is hidden by server");
                    return;
                case 102:
                    Log.d(XPopPanel.TAG, "code : " + i + "pop panel is hidden by user event");
                    return;
                default:
                    return;
            }
        }
    }

    @Override // com.xiaopeng.xui.vui.IVuiViewScene
    public void initVuiScene(String str, IVuiEngine iVuiEngine) {
        initVuiScene(str, iVuiEngine, null);
    }

    @Override // com.xiaopeng.xui.vui.IVuiViewScene
    public void initVuiScene(String str, IVuiEngine iVuiEngine, IVuiSceneListener iVuiSceneListener) {
        if (this.mPopPanelView != null) {
            if (!str.endsWith(VUI_SCENE_TAG)) {
                str = str + VUI_SCENE_TAG;
            }
            this.mPopPanelView.initVuiScene(str, iVuiEngine, iVuiSceneListener);
        }
    }
}
