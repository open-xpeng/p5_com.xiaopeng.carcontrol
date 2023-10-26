package com.xiaopeng.vui.commons.model;

import java.util.List;

/* loaded from: classes2.dex */
public abstract class VuiEvent {
    public abstract <T> T getEventValue(VuiEvent vuiEvent);

    public abstract VuiElement getHitVuiElement();

    public abstract List<VuiElement> getHitVuiElements(List<VuiElement> list);
}
