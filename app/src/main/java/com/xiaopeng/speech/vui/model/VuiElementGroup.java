package com.xiaopeng.speech.vui.model;

import com.xiaopeng.vui.commons.model.VuiElement;
import java.util.List;

/* loaded from: classes2.dex */
public class VuiElementGroup extends VuiElement {
    private boolean dynamic = false;
    private int position = -1;
    private List<VuiElement> elements = null;

    @Override // com.xiaopeng.vui.commons.model.VuiElement
    public int getPosition() {
        return this.position;
    }

    public void setDynamic(boolean z) {
        this.dynamic = z;
    }

    public boolean isDynamic() {
        return this.dynamic;
    }

    @Override // com.xiaopeng.vui.commons.model.VuiElement
    public void setPosition(int i) {
        this.position = i;
    }

    @Override // com.xiaopeng.vui.commons.model.VuiElement
    public List<VuiElement> getElements() {
        return this.elements;
    }

    @Override // com.xiaopeng.vui.commons.model.VuiElement
    public void setElements(List<VuiElement> list) {
        this.elements = list;
    }
}
