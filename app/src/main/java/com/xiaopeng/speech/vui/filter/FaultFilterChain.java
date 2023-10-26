package com.xiaopeng.speech.vui.filter;

import com.xiaopeng.vui.commons.model.VuiElement;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class FaultFilterChain implements IFilter {
    public List<IFilter> mFilters = new ArrayList();

    public FaultFilterChain addFilter(IFilter iFilter) {
        this.mFilters.add(iFilter);
        return this;
    }

    @Override // com.xiaopeng.speech.vui.filter.IFilter
    public VuiElement doFilter(VuiElement vuiElement) {
        for (IFilter iFilter : this.mFilters) {
            vuiElement = iFilter.doFilter(vuiElement);
        }
        return vuiElement;
    }
}
