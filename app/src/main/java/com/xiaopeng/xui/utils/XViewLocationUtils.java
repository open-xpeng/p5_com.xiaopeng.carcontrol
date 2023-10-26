package com.xiaopeng.xui.utils;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/* loaded from: classes2.dex */
public class XViewLocationUtils {
    private static final int FADING_EDGE_LENGTH = 60;

    /* loaded from: classes2.dex */
    public interface OnCorrectionLocationListener {
        void onCorrectionLocationEnd(View view);
    }

    public static void scrollByLocation(View view, OnCorrectionLocationListener onCorrectionLocationListener) {
        ViewGroup isInScrollContainer = isInScrollContainer(view);
        if (isInScrollContainer != null) {
            scrollByLocation(isInScrollContainer, view, onCorrectionLocationListener);
        } else {
            onCorrectionLocationListener.onCorrectionLocationEnd(view);
        }
    }

    public static void scrollByLocation(final ViewGroup viewGroup, final View view, final OnCorrectionLocationListener onCorrectionLocationListener) {
        log("scrollByLocation start ");
        view.post(new Runnable() { // from class: com.xiaopeng.xui.utils.-$$Lambda$XViewLocationUtils$3Lg15fzgusw7yN3dfwsIDJ4mm8w
            @Override // java.lang.Runnable
            public final void run() {
                XViewLocationUtils.lambda$scrollByLocation$0(view, viewGroup, onCorrectionLocationListener);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$scrollByLocation$0(View view, ViewGroup viewGroup, OnCorrectionLocationListener onCorrectionLocationListener) {
        String str;
        Rect rect = new Rect();
        boolean localVisibleRect = view.getLocalVisibleRect(rect);
        int height = view.getHeight();
        if (rect.top < 0) {
            viewGroup.scrollBy(0, rect.top - 60);
            str = "top all ";
        } else if (rect.top > 0) {
            if (rect.height() < height) {
                viewGroup.scrollBy(0, (-rect.top) - 60);
                str = "top a part ";
            } else {
                viewGroup.scrollBy(0, (rect.bottom - viewGroup.getHeight()) + 60);
                str = "bottom all ";
            }
        } else if (rect.height() < height) {
            viewGroup.scrollBy(0, (height - rect.bottom) + 60);
            str = "bottom a part ";
        } else {
            str = "";
        }
        log("scrollByLocation end localVisible : " + localVisibleRect + ", top " + rect.top + ", bottom " + rect.bottom + ", rect h:" + rect.height() + ",h:" + height + ", type: " + str);
        onCorrectionLocationListener.onCorrectionLocationEnd(view);
    }

    private static ViewGroup isInScrollContainer(View view) {
        for (ViewParent parent = view.getParent(); parent instanceof ViewGroup; parent = parent.getParent()) {
            ViewGroup viewGroup = (ViewGroup) parent;
            if (viewGroup.shouldDelayChildPressedState()) {
                return viewGroup;
            }
        }
        return null;
    }

    private static void log(String str) {
        XLogUtils.d("xui-ViewLocation", str);
    }
}
