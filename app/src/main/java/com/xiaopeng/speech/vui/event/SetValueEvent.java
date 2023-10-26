package com.xiaopeng.speech.vui.event;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.speech.vui.vuiengine.R;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.model.VuiElement;

/* loaded from: classes2.dex */
public class SetValueEvent extends BaseEvent {
    private int value = -1;
    private String elementId = null;
    private VuiElement mVuiElement = null;
    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() { // from class: com.xiaopeng.speech.vui.event.SetValueEvent.1
        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            View findViewByPosition;
            super.onScrollStateChanged(recyclerView, i);
            if (i == 0) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null) {
                    int findFirstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    int findLastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    if (SetValueEvent.this.value >= findFirstVisibleItemPosition && SetValueEvent.this.value <= findLastVisibleItemPosition && (findViewByPosition = linearLayoutManager.findViewByPosition(SetValueEvent.this.value)) != null) {
                        SetValueEvent setValueEvent = SetValueEvent.this;
                        View findActionView = setValueEvent.findActionView(findViewByPosition, setValueEvent.elementId);
                        if (findActionView != null) {
                            if (!TextUtils.isEmpty(SetValueEvent.this.mSceneId)) {
                                VuiElement unused = SetValueEvent.this.mVuiElement;
                            }
                            SetValueEvent.this.mSceneId = null;
                            SetValueEvent.this.mVuiElement = null;
                            SetValueEvent.this.performClick(findActionView);
                        }
                    }
                }
                recyclerView.removeOnScrollListener(SetValueEvent.this.mScrollListener);
            }
        }
    };
    private String mSceneId = null;

    @Override // com.xiaopeng.speech.vui.event.IVuiEvent
    public <T extends View> T run(T t, VuiElement vuiElement) {
        if (t == null) {
            return null;
        }
        if (vuiElement != null) {
            try {
                if (vuiElement.getResultActions() != null && !vuiElement.getResultActions().isEmpty() && VuiElementType.VIRTUALLIST.getType().equals(vuiElement.getType())) {
                    this.elementId = vuiElement.getId();
                    Double d = (Double) VuiUtils.getValueByName(vuiElement, "value");
                    if (t instanceof RecyclerView) {
                        if (((IVuiElement) t).getVuiProps() != null) {
                            if (((IVuiElement) t).getVuiProps().has("isReverse") ? ((IVuiElement) t).getVuiProps().getBoolean("isReverse") : false) {
                                this.value = d.intValue();
                                if (((IVuiElement) t).getVuiProps().has("maxValue")) {
                                    this.value = ((IVuiElement) t).getVuiProps().getInt("maxValue") - this.value;
                                }
                                if (((IVuiElement) t).getVuiProps().has("hasHeader")) {
                                    this.value = d.intValue() + 1;
                                }
                                LogUtils.d("SetValueEvent", "reverse value:" + this.value);
                            } else {
                                if (((IVuiElement) t).getVuiProps().has("hasHeader")) {
                                    this.value = d.intValue();
                                } else {
                                    this.value = d.intValue() - 1;
                                }
                                if (((IVuiElement) t).getVuiProps().has("minValue")) {
                                    this.value = (this.value - ((IVuiElement) t).getVuiProps().getInt("minValue")) + 1;
                                }
                                LogUtils.d("SetValueEvent", "value:" + this.value);
                            }
                        }
                        RecyclerView recyclerView = (RecyclerView) t;
                        recyclerView.addOnScrollListener(this.mScrollListener);
                        recyclerView.smoothScrollToPosition(this.value);
                    }
                    this.mVuiElement = vuiElement;
                }
            } catch (Exception e) {
                LogUtils.e("SetValueEvent", e.fillInStackTrace());
            }
        }
        return t;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View findActionView(View view, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        Object tag = view.getTag(R.id.executeVirtualId);
        if (tag == null || !str.equals(tag.toString())) {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    View childAt = viewGroup.getChildAt(i);
                    if (findActionView(childAt, str) != null) {
                        return childAt;
                    }
                }
            }
            return null;
        }
        return view;
    }

    public boolean performClick(View view) {
        if (view == null) {
            return false;
        }
        boolean z = view instanceof IVuiElement;
        if (z) {
            ((IVuiElement) view).setPerformVuiAction(true);
        }
        boolean performClick = view.performClick();
        if (z) {
            ((IVuiElement) view).setPerformVuiAction(false);
        }
        LogUtils.i("ClickEvent run :" + performClick);
        if (performClick) {
            return true;
        }
        if (view.getParent() instanceof ViewRootImpl) {
            return false;
        }
        return performClick((View) view.getParent());
    }

    public void setSceneId(String str) {
        this.mSceneId = str;
    }
}
