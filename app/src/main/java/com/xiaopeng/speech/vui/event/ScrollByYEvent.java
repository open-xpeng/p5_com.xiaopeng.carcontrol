package com.xiaopeng.speech.vui.event;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.speech.vui.constants.Foo;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.VuiAction;
import com.xiaopeng.vui.commons.model.VuiElement;

/* loaded from: classes2.dex */
public class ScrollByYEvent extends BaseEvent {
    private EndSmoothScroller mScroller = null;

    @Override // com.xiaopeng.speech.vui.event.IVuiEvent
    public <T extends View> T run(T t, VuiElement vuiElement) {
        if (t != null && vuiElement != null && vuiElement.getResultActions() != null && !vuiElement.getResultActions().isEmpty() && VuiAction.SCROLLBYY.getName().equals(vuiElement.getResultActions().get(0))) {
            String str = (String) VuiUtils.getValueByName(vuiElement, VuiConstants.EVENT_VALUE_DIRECTION);
            Double d = (Double) VuiUtils.getValueByName(vuiElement, "offset");
            if (str != null && d != null) {
                boolean z = t instanceof IVuiElement;
                if (z) {
                    ((IVuiElement) t).setPerformVuiAction(true);
                }
                boolean equals = "up".equals(str);
                int intValue = d.intValue();
                if (equals) {
                    intValue = -intValue;
                }
                LogUtils.i("ScrollByYEvent", "ScrollByYEvent run value:" + intValue + ",view:" + t);
                if (t instanceof RecyclerView) {
                    int height = intValue != 100 ? (int) (intValue * 0.01d * t.getHeight()) : 100;
                    if (height == 0) {
                        ((RecyclerView) t).smoothScrollToPosition(0);
                    } else if (height == 100) {
                        RecyclerView recyclerView = (RecyclerView) t;
                        if (recyclerView.getAdapter() == null) {
                            return t;
                        }
                        smoothMoveToPosition(recyclerView, recyclerView.getAdapter().getItemCount() - 1);
                    } else {
                        ((RecyclerView) t).smoothScrollBy(0, height);
                    }
                } else if (t instanceof ScrollView) {
                    int height2 = intValue != 100 ? (int) (intValue * 0.01d * t.getHeight()) : 100;
                    if (height2 == 0) {
                        ((ScrollView) t).fullScroll(33);
                    } else if (height2 == 100) {
                        ((ScrollView) t).fullScroll(130);
                    } else {
                        ((ScrollView) t).smoothScrollBy(0, height2);
                    }
                } else if (t instanceof ListView) {
                    int height3 = intValue != 100 ? (int) (intValue * 0.01d * t.getHeight()) : 100;
                    ListView listView = (ListView) t;
                    if (height3 == 0) {
                        listView.setSelection(listView.getTop());
                    } else if (height3 == 100) {
                        listView.setSelection(listView.getBottom());
                    } else {
                        listView.smoothScrollBy(0, height3);
                    }
                } else {
                    Rect rect = new Rect();
                    t.getGlobalVisibleRect(rect);
                    if (intValue == 0) {
                        t.scrollTo(0, 0);
                    } else if (intValue == 100) {
                        t.scrollTo(0, t.getHeight() - rect.height());
                    } else {
                        int height4 = (int) (intValue * 0.01d * rect.height());
                        if (height4 > 0) {
                            if (t.getScrollY() + height4 + rect.height() >= t.getHeight()) {
                                t.scrollTo(0, t.getHeight() - rect.height());
                                return t;
                            }
                        } else if ((t.getScrollY() + height4) - rect.height() < 0) {
                            t.scrollTo(0, 0);
                            return t;
                        }
                        t.scrollBy(0, height4);
                    }
                }
                if (z) {
                    ((IVuiElement) t).setPerformVuiAction(false);
                }
            }
        }
        return t;
    }

    public void smoothMoveToPosition(RecyclerView recyclerView, int i) {
        if (recyclerView.getLayoutManager() != null) {
            Log.d("ScrollByYEvent", "smoothMoveToPosition: ===== " + i);
            if (this.mScroller == null) {
                this.mScroller = new EndSmoothScroller(Foo.getContext());
            }
            this.mScroller.setTargetPosition(i);
            recyclerView.getLayoutManager().startSmoothScroll(this.mScroller);
        }
    }
}
