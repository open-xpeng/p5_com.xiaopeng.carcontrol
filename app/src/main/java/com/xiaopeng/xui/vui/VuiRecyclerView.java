package com.xiaopeng.xui.vui;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.vui.commons.IVuiElementBuilder;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.IVuiEngine;
import com.xiaopeng.vui.commons.VuiAction;
import com.xiaopeng.vui.commons.model.VuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.vui.listenner.VuiRecyclerViewGlobalLayoutListener;
import com.xiaopeng.xui.vui.listenner.VuiRecyclerViewScrollListener;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class VuiRecyclerView extends RecyclerView implements VuiView, IVuiElementListener {
    private static final String TAG = "VuiRecyclerView";
    private int UPDATE_DELAY_TIME;
    public boolean isVuiCanScrollDown;
    public boolean isVuiCanScrollRight;
    private final RecyclerView.AdapterDataObserver mAdapterDataObserver;
    private VuiRecyclerViewGlobalLayoutListener mGlobalLayoutListener;
    private Handler mHandler;
    private boolean mIsEnableInvisible;
    private boolean mIsItemChanged;
    private boolean mIsNeedLayout;
    private boolean mIsNeedScroll;
    private boolean mIsReverse;
    private boolean mIsVuiCanControlScroll;
    private int mItemCount;
    private Runnable mRun;
    private String mSceneId;
    private VuiRecyclerViewScrollListener mScrollListener;
    private int mStartPosition;
    private List<String[]> mSupportVuiLabels;
    private IVuiEngine mVuiEngine;

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public boolean onVuiElementEvent(View view, VuiEvent vuiEvent) {
        return false;
    }

    public VuiRecyclerView(Context context) {
        super(context);
        this.mIsVuiCanControlScroll = true;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.UPDATE_DELAY_TIME = 1000;
        this.mRun = new Runnable() { // from class: com.xiaopeng.xui.vui.VuiRecyclerView.1
            @Override // java.lang.Runnable
            public void run() {
                if (VuiRecyclerView.this.mVuiEngine == null || TextUtils.isEmpty(VuiRecyclerView.this.mSceneId)) {
                    return;
                }
                VuiRecyclerView.this.mVuiEngine.updateScene(VuiRecyclerView.this.mSceneId, VuiRecyclerView.this);
            }
        };
        this.mIsNeedScroll = true;
        this.mIsNeedLayout = true;
        this.mIsItemChanged = false;
        this.mAdapterDataObserver = new RecyclerView.AdapterDataObserver() { // from class: com.xiaopeng.xui.vui.VuiRecyclerView.2
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                if (!VuiRecyclerView.this.mIsNeedLayout || VuiRecyclerView.this.mGlobalLayoutListener == null) {
                    return;
                }
                VuiRecyclerView.this.mGlobalLayoutListener.setUpdateNum();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int i, int i2) {
                if (VuiRecyclerView.this.mIsItemChanged) {
                    VuiRecyclerView.this.updateVuiScene();
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int i, int i2, Object obj) {
                if (VuiRecyclerView.this.mIsItemChanged) {
                    VuiRecyclerView.this.updateVuiScene();
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeInserted(int i, int i2) {
                if (!VuiRecyclerView.this.mIsNeedLayout || VuiRecyclerView.this.mGlobalLayoutListener == null) {
                    return;
                }
                VuiRecyclerView.this.mGlobalLayoutListener.setUpdateNum();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeRemoved(int i, int i2) {
                if (!VuiRecyclerView.this.mIsNeedLayout || VuiRecyclerView.this.mGlobalLayoutListener == null) {
                    return;
                }
                VuiRecyclerView.this.mGlobalLayoutListener.setUpdateNum();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeMoved(int i, int i2, int i3) {
                if (!VuiRecyclerView.this.mIsNeedLayout || VuiRecyclerView.this.mGlobalLayoutListener == null) {
                    return;
                }
                VuiRecyclerView.this.mGlobalLayoutListener.setUpdateNum();
            }
        };
        this.isVuiCanScrollDown = false;
        this.isVuiCanScrollRight = false;
        this.mIsEnableInvisible = false;
        this.mStartPosition = 0;
        this.mItemCount = -1;
        this.mIsReverse = false;
        this.mSupportVuiLabels = null;
        initListener();
    }

    public VuiRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsVuiCanControlScroll = true;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.UPDATE_DELAY_TIME = 1000;
        this.mRun = new Runnable() { // from class: com.xiaopeng.xui.vui.VuiRecyclerView.1
            @Override // java.lang.Runnable
            public void run() {
                if (VuiRecyclerView.this.mVuiEngine == null || TextUtils.isEmpty(VuiRecyclerView.this.mSceneId)) {
                    return;
                }
                VuiRecyclerView.this.mVuiEngine.updateScene(VuiRecyclerView.this.mSceneId, VuiRecyclerView.this);
            }
        };
        this.mIsNeedScroll = true;
        this.mIsNeedLayout = true;
        this.mIsItemChanged = false;
        this.mAdapterDataObserver = new RecyclerView.AdapterDataObserver() { // from class: com.xiaopeng.xui.vui.VuiRecyclerView.2
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                if (!VuiRecyclerView.this.mIsNeedLayout || VuiRecyclerView.this.mGlobalLayoutListener == null) {
                    return;
                }
                VuiRecyclerView.this.mGlobalLayoutListener.setUpdateNum();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int i, int i2) {
                if (VuiRecyclerView.this.mIsItemChanged) {
                    VuiRecyclerView.this.updateVuiScene();
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int i, int i2, Object obj) {
                if (VuiRecyclerView.this.mIsItemChanged) {
                    VuiRecyclerView.this.updateVuiScene();
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeInserted(int i, int i2) {
                if (!VuiRecyclerView.this.mIsNeedLayout || VuiRecyclerView.this.mGlobalLayoutListener == null) {
                    return;
                }
                VuiRecyclerView.this.mGlobalLayoutListener.setUpdateNum();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeRemoved(int i, int i2) {
                if (!VuiRecyclerView.this.mIsNeedLayout || VuiRecyclerView.this.mGlobalLayoutListener == null) {
                    return;
                }
                VuiRecyclerView.this.mGlobalLayoutListener.setUpdateNum();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeMoved(int i, int i2, int i3) {
                if (!VuiRecyclerView.this.mIsNeedLayout || VuiRecyclerView.this.mGlobalLayoutListener == null) {
                    return;
                }
                VuiRecyclerView.this.mGlobalLayoutListener.setUpdateNum();
            }
        };
        this.isVuiCanScrollDown = false;
        this.isVuiCanScrollRight = false;
        this.mIsEnableInvisible = false;
        this.mStartPosition = 0;
        this.mItemCount = -1;
        this.mIsReverse = false;
        this.mSupportVuiLabels = null;
        initVui(this, attributeSet);
        initListener();
    }

    public VuiRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsVuiCanControlScroll = true;
        this.mHandler = new Handler(Looper.getMainLooper());
        this.UPDATE_DELAY_TIME = 1000;
        this.mRun = new Runnable() { // from class: com.xiaopeng.xui.vui.VuiRecyclerView.1
            @Override // java.lang.Runnable
            public void run() {
                if (VuiRecyclerView.this.mVuiEngine == null || TextUtils.isEmpty(VuiRecyclerView.this.mSceneId)) {
                    return;
                }
                VuiRecyclerView.this.mVuiEngine.updateScene(VuiRecyclerView.this.mSceneId, VuiRecyclerView.this);
            }
        };
        this.mIsNeedScroll = true;
        this.mIsNeedLayout = true;
        this.mIsItemChanged = false;
        this.mAdapterDataObserver = new RecyclerView.AdapterDataObserver() { // from class: com.xiaopeng.xui.vui.VuiRecyclerView.2
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                if (!VuiRecyclerView.this.mIsNeedLayout || VuiRecyclerView.this.mGlobalLayoutListener == null) {
                    return;
                }
                VuiRecyclerView.this.mGlobalLayoutListener.setUpdateNum();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int i2, int i22) {
                if (VuiRecyclerView.this.mIsItemChanged) {
                    VuiRecyclerView.this.updateVuiScene();
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeChanged(int i2, int i22, Object obj) {
                if (VuiRecyclerView.this.mIsItemChanged) {
                    VuiRecyclerView.this.updateVuiScene();
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeInserted(int i2, int i22) {
                if (!VuiRecyclerView.this.mIsNeedLayout || VuiRecyclerView.this.mGlobalLayoutListener == null) {
                    return;
                }
                VuiRecyclerView.this.mGlobalLayoutListener.setUpdateNum();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeRemoved(int i2, int i22) {
                if (!VuiRecyclerView.this.mIsNeedLayout || VuiRecyclerView.this.mGlobalLayoutListener == null) {
                    return;
                }
                VuiRecyclerView.this.mGlobalLayoutListener.setUpdateNum();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onItemRangeMoved(int i2, int i22, int i3) {
                if (!VuiRecyclerView.this.mIsNeedLayout || VuiRecyclerView.this.mGlobalLayoutListener == null) {
                    return;
                }
                VuiRecyclerView.this.mGlobalLayoutListener.setUpdateNum();
            }
        };
        this.isVuiCanScrollDown = false;
        this.isVuiCanScrollRight = false;
        this.mIsEnableInvisible = false;
        this.mStartPosition = 0;
        this.mItemCount = -1;
        this.mIsReverse = false;
        this.mSupportVuiLabels = null;
        initVui(this, attributeSet);
        initListener();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        VuiRecyclerViewScrollListener vuiRecyclerViewScrollListener;
        super.onAttachedToWindow();
        logD("onAttachedToWindow:" + this.mSceneId);
        if (this.mIsNeedLayout && this.mGlobalLayoutListener != null) {
            getViewTreeObserver().addOnGlobalLayoutListener(this.mGlobalLayoutListener);
        }
        if (!this.mIsNeedScroll || (vuiRecyclerViewScrollListener = this.mScrollListener) == null) {
            return;
        }
        addOnScrollListener(vuiRecyclerViewScrollListener);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        VuiRecyclerViewScrollListener vuiRecyclerViewScrollListener;
        super.onDetachedFromWindow();
        logD("onDetachedFromWindow:" + this.mSceneId);
        if (this.mIsNeedLayout && this.mGlobalLayoutListener != null) {
            getViewTreeObserver().removeOnGlobalLayoutListener(this.mGlobalLayoutListener);
        }
        if (this.mIsNeedScroll && (vuiRecyclerViewScrollListener = this.mScrollListener) != null) {
            removeOnScrollListener(vuiRecyclerViewScrollListener);
        }
        this.mHandler.removeCallbacks(this.mRun);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        releaseVui();
    }

    private void initListener() {
        this.mGlobalLayoutListener = new VuiRecyclerViewGlobalLayoutListener(this);
        this.mScrollListener = new VuiRecyclerViewScrollListener(this);
        setVuiLayoutLoadable(true);
    }

    public void updateVuiScene() {
        if (!TextUtils.isEmpty(this.mSceneId) && this.mVuiEngine != null) {
            this.mHandler.removeCallbacks(this.mRun);
            this.mHandler.postDelayed(this.mRun, this.UPDATE_DELAY_TIME);
            return;
        }
        logD("updateVuiScene sceneid is empty");
    }

    public void updateVuiScene(int i) {
        if (!TextUtils.isEmpty(this.mSceneId) && this.mVuiEngine != null) {
            this.mHandler.removeCallbacks(this.mRun);
            this.mHandler.postDelayed(this.mRun, i);
            return;
        }
        logD("updateVuiScene sceneid is empty");
    }

    public void initVuiAttr(String str, IVuiEngine iVuiEngine) {
        this.mSceneId = str;
        this.mVuiEngine = iVuiEngine;
    }

    public void initVuiAttr(String str, IVuiEngine iVuiEngine, boolean z) {
        this.mSceneId = str;
        this.mVuiEngine = iVuiEngine;
        this.mIsItemChanged = z;
    }

    public void initVuiAttr(String str, IVuiEngine iVuiEngine, boolean z, boolean z2) {
        this.mSceneId = str;
        this.mIsNeedScroll = z;
        this.mIsNeedLayout = z2;
        this.mVuiEngine = iVuiEngine;
    }

    public void initVuiAttr(String str, IVuiEngine iVuiEngine, boolean z, boolean z2, boolean z3) {
        this.mSceneId = str;
        this.mIsNeedScroll = z;
        this.mIsNeedLayout = z2;
        this.mVuiEngine = iVuiEngine;
        this.mIsItemChanged = z3;
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void setAdapter(RecyclerView.Adapter adapter) {
        VuiRecyclerViewGlobalLayoutListener vuiRecyclerViewGlobalLayoutListener;
        RecyclerView.Adapter adapter2 = getAdapter();
        if (adapter2 != null) {
            adapter2.unregisterAdapterDataObserver(this.mAdapterDataObserver);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.mAdapterDataObserver);
        }
        if (!this.mIsNeedLayout || (vuiRecyclerViewGlobalLayoutListener = this.mGlobalLayoutListener) == null) {
            return;
        }
        vuiRecyclerViewGlobalLayoutListener.setUpdateNum();
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public VuiElement onBuildVuiElement(String str, IVuiElementBuilder iVuiElementBuilder) {
        JSONObject vuiProps;
        RecyclerView.LayoutManager layoutManager;
        if (this.mIsVuiCanControlScroll) {
            boolean canScrollVertically = canScrollVertically(-1);
            boolean z = true;
            boolean canScrollVertically2 = canScrollVertically(1);
            boolean canScrollHorizontally = canScrollHorizontally(-1);
            boolean canScrollHorizontally2 = canScrollHorizontally(1);
            if (canScrollVertically || canScrollVertically2 || this.isVuiCanScrollDown) {
                setVuiAction(VuiAction.SCROLLBYY.getName());
            } else if (canScrollHorizontally || canScrollHorizontally2 || this.isVuiCanScrollRight) {
                setVuiAction(VuiAction.SCROLLBYX.getName());
            }
            try {
                vuiProps = getVuiProps();
                if (vuiProps == null) {
                    vuiProps = new JSONObject();
                }
                if (this.mIsEnableInvisible && (layoutManager = getLayoutManager()) != null && (layoutManager instanceof LinearLayoutManager)) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    vuiProps.put("minValue", this.mStartPosition);
                    int i = this.mItemCount;
                    int itemCount = i != -1 ? this.mStartPosition + i : this.mStartPosition + layoutManager.getItemCount();
                    vuiProps.put("maxValue", itemCount);
                    vuiProps.put("displayStart", this.mIsReverse ? itemCount - linearLayoutManager.findLastCompletelyVisibleItemPosition() : this.mStartPosition + linearLayoutManager.findFirstCompletelyVisibleItemPosition());
                    vuiProps.put("displayEnd", this.mIsReverse ? itemCount - linearLayoutManager.findFirstCompletelyVisibleItemPosition() : this.mStartPosition + linearLayoutManager.findLastCompletelyVisibleItemPosition());
                    List<String[]> list = this.mSupportVuiLabels;
                    if (list != null && list.size() > 0) {
                        vuiProps.put("supportVuiLabels", this.mSupportVuiLabels);
                    }
                    vuiProps.put("isReverse", this.mIsReverse);
                }
            } catch (JSONException unused) {
            }
            if (getVuiAction() == null) {
                return null;
            }
            if (getVuiAction().equals(VuiAction.SCROLLBYY.getName())) {
                vuiProps.put(VuiConstants.PROPS_SCROLLUP, canScrollVertically);
                if (!canScrollVertically2 && !this.isVuiCanScrollDown) {
                    z = false;
                }
                vuiProps.put(VuiConstants.PROPS_SCROLLDOWN, z);
            } else {
                vuiProps.put(VuiConstants.PROPS_SCROLLLEFT, canScrollHorizontally);
                if (!canScrollHorizontally2 && !this.isVuiCanScrollRight) {
                    z = false;
                }
                vuiProps.put(VuiConstants.PROPS_SCROLLRIGHT, z);
            }
            setVuiProps(vuiProps);
            return null;
        }
        return null;
    }

    public void setCanVuiScrollDown(boolean z) {
        this.isVuiCanScrollDown = z;
    }

    public void setCanVuiScrollRight(boolean z) {
        this.isVuiCanScrollRight = z;
    }

    public void setVuiCanControlScroll(boolean z) {
        this.mIsVuiCanControlScroll = z;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        if (this.mIsNeedLayout) {
            return;
        }
        updateVuiScene();
    }

    public void enableVuiInvisibleFeature(int i) {
        logI("enableVuiInvisibleFeature:" + i);
        this.mIsEnableInvisible = true;
        this.mStartPosition = i;
    }

    public void enableVuiInvisibleFeature(int i, List<String[]> list) {
        this.mIsEnableInvisible = true;
        this.mStartPosition = i;
        this.mSupportVuiLabels = list;
    }

    public void enableVuiInvisibleFeature(int i, int i2) {
        logD("enableVuiInvisibleFeature:" + i);
        this.mIsEnableInvisible = true;
        this.mStartPosition = i;
        this.mItemCount = i2;
    }

    public void setOrderState(boolean z) {
        this.mIsReverse = z;
    }
}
