package com.xiaopeng.lludancemanager.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.lludancemanager.bean.LluAiLampBean;
import com.xiaopeng.xui.widget.XImageView;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class LampBeamShowView extends XImageView {
    public static final String TAG = "LampBeamShowView";
    private Context mContext;
    private List<Bitmap> mLampBeamList;
    private LluAiLampBean mLluAiLampBean;
    private List<LluAiLampBean> mLluAiLampBeans;

    private void init() {
    }

    public LampBeamShowView(Context context) {
        super(context);
        this.mLampBeamList = new ArrayList();
        this.mLluAiLampBeans = new ArrayList();
        this.mContext = context;
        init();
    }

    public LampBeamShowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mLampBeamList = new ArrayList();
        this.mLluAiLampBeans = new ArrayList();
        this.mContext = context;
        init();
    }

    public LampBeamShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mLampBeamList = new ArrayList();
        this.mLluAiLampBeans = new ArrayList();
        this.mContext = context;
        init();
    }

    public void setLluAiLampBeans(List<LluAiLampBean> lluAiLampBeans) {
        this.mLluAiLampBeans.clear();
        this.mLluAiLampBeans.addAll(lluAiLampBeans);
        initLampBeamList();
    }

    @Override // com.xiaopeng.xui.widget.XImageView, android.view.View
    protected void onConfigurationChanged(Configuration newConfig) {
        if (ThemeManager.isThemeChanged(newConfig)) {
            releaseLampBeamList();
            initLampBeamList();
        }
    }

    private Bitmap decodeBitmap(int resId) {
        return BitmapFactory.decodeResource(getContext().getResources(), resId);
    }

    private void initLampBeamList() {
        List<LluAiLampBean> list = this.mLluAiLampBeans;
        if (list == null || list.size() == 0) {
            return;
        }
        this.mLampBeamList.clear();
        for (LluAiLampBean lluAiLampBean : this.mLluAiLampBeans) {
            if (lluAiLampBean != null) {
                this.mLampBeamList.add(decodeBitmap(lluAiLampBean.getLampBgResId()));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XImageView, android.widget.ImageView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        releaseLampBeamList();
    }

    private void releaseLampBeamList() {
        for (Bitmap bitmap : this.mLampBeamList) {
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }
        this.mLampBeamList.clear();
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        LluAiLampBean lluAiLampBean = this.mLluAiLampBean;
        if (lluAiLampBean != null) {
            canvas.drawBitmap(this.mLampBeamList.get(lluAiLampBean.getIndex()), this.mLluAiLampBean.getPos(), 0.0f, (Paint) null);
        }
    }

    public void updateLampBeam(LluAiLampBean lluAiLampBean) {
        Log.d(TAG, "updateLampBeam:" + lluAiLampBean.toString());
        this.mLluAiLampBean = lluAiLampBean;
        postInvalidate();
    }
}
