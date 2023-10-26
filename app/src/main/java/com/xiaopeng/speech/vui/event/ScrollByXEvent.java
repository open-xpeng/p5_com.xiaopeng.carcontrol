package com.xiaopeng.speech.vui.event;

import android.view.View;
import androidx.viewpager.widget.ViewPager;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.speech.vui.utils.LogUtils;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.VuiAction;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.model.VuiElement;

/* loaded from: classes2.dex */
public class ScrollByXEvent extends BaseEvent {
    @Override // com.xiaopeng.speech.vui.event.IVuiEvent
    public <T extends View> T run(T t, VuiElement vuiElement) {
        String str;
        ViewPager viewPager;
        int extraPage;
        int extraPage2;
        if (t == null || vuiElement == null || vuiElement.getResultActions() == null || vuiElement.getResultActions().isEmpty() || !VuiAction.SCROLLBYX.getName().equals(vuiElement.getResultActions().get(0)) || (str = (String) VuiUtils.getValueByName(vuiElement, VuiConstants.EVENT_VALUE_DIRECTION)) == null) {
            return t;
        }
        boolean z = t instanceof IVuiElement;
        if (z) {
            ((IVuiElement) t).setPerformVuiAction(true);
        }
        LogUtils.i("ScrollByXEvent run direction:" + str);
        if (vuiElement.type.equals(VuiElementType.VIEWPAGER.getType())) {
            if (!(t instanceof ViewPager)) {
                viewPager = VuiUtils.findViewPager(t);
            } else {
                viewPager = (ViewPager) t;
            }
            if (viewPager != null) {
                boolean canScrollHorizontally = viewPager.canScrollHorizontally(-1);
                boolean canScrollHorizontally2 = viewPager.canScrollHorizontally(1);
                int currentItem = viewPager.getCurrentItem();
                if ("left".equals(str)) {
                    int i = currentItem - 1;
                    if (canScrollHorizontally) {
                        viewPager.setCurrentItem(i, false);
                    } else if (canScrollHorizontally2 && (extraPage2 = VuiUtils.getExtraPage(vuiElement)) != -1) {
                        viewPager.setCurrentItem((viewPager.getAdapter().getCount() - 1) - extraPage2, false);
                    }
                } else {
                    int i2 = currentItem + 1;
                    if (canScrollHorizontally2) {
                        viewPager.setCurrentItem(i2, false);
                    } else if (canScrollHorizontally && (extraPage = VuiUtils.getExtraPage(vuiElement)) != -1) {
                        viewPager.setCurrentItem(extraPage, false);
                    }
                }
            }
        } else {
            vuiElement.type.equals(VuiElementType.SCROLLVIEW.getType());
        }
        if (z) {
            ((IVuiElement) t).setPerformVuiAction(false);
        }
        return t;
    }
}
