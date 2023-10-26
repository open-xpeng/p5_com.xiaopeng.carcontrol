package com.xiaopeng.carcontrol.view.speech;

import com.xiaopeng.vui.commons.model.VuiElement;

/* loaded from: classes2.dex */
public interface IFictitiousElementConstructor<T> {
    VuiElement generateElement(String id, T currState, String... vuiLable);

    VuiElement generateElement(String id, T currState, String[] values, String... vuiLable);
}
