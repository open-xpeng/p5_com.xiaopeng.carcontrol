package com.xiaopeng.carcontrol.view;

import android.view.View;

/* loaded from: classes2.dex */
public interface XPilotOperateInterface {
    void onCardItemChanged(int position, boolean isChecked, View view);

    default void onExtBtnClicked(int position, View view) {
    }

    void onInfoClicked(int position);

    void onTabItemChanged(int position, int selectedIndex, View view);

    void onVuiFeedbackViewCreate(View view);

    default void onVuiItemUpdate(View view) {
    }
}
