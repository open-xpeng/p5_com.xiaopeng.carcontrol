package com.xiaopeng.speech.vui.event;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewRootImpl;
import android.widget.ListView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.model.VuiElement;

/* loaded from: classes2.dex */
public class ListItemClickEvent extends BaseEvent {
    private VuiElement params;

    @Override // com.xiaopeng.speech.vui.event.IVuiEvent
    public <T extends View> T run(T t, VuiElement vuiElement) {
        this.params = this.params;
        if (!performClick(t)) {
            boolean z = getListView(t) instanceof ListView;
        }
        return t;
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
        LogUtils.i("ClickEvent run :" + performClick);
        if (z) {
            ((IVuiElement) view).setPerformVuiAction(false);
        }
        if (performClick) {
            return true;
        }
        if (z) {
            ((IVuiElement) view).setPerformVuiAction(false);
        }
        if (view.getParent() instanceof ViewRootImpl) {
            return false;
        }
        return performClick((View) view.getParent());
    }

    private View getListView(View view) {
        return ((view instanceof ListView) || (view instanceof RecyclerView) || (view.getParent() instanceof ViewRootImpl)) ? view : getListView((View) view.getParent());
    }

    public String getPositionByViewId(int i, VuiElement vuiElement) {
        if (vuiElement == null || TextUtils.isEmpty(vuiElement.getId())) {
            return "0";
        }
        String[] split = vuiElement.getId().split("_");
        return (split.length != 2 || split[1] == null) ? "0" : split[1];
    }
}
