package com.xiaopeng.speech.vui.filter;

import com.xiaopeng.vui.commons.model.VuiElement;

/* loaded from: classes2.dex */
public class FaultManager {
    private static volatile FaultManager faultManager;
    private FaultFilterChain mFaultChain = new FaultFilterChain();

    private FaultManager() {
        init();
    }

    public static FaultManager getInstance() {
        if (faultManager == null) {
            synchronized (FaultManager.class) {
                if (faultManager == null) {
                    faultManager = new FaultManager();
                }
            }
        }
        return faultManager;
    }

    private void init() {
        this.mFaultChain.addFilter(new ListClickEventFaultFilter());
    }

    public VuiElement startFault(VuiElement vuiElement) {
        return this.mFaultChain.doFilter(vuiElement);
    }

    public void addFilter(IFilter iFilter) {
        if (iFilter != null) {
            this.mFaultChain.addFilter(iFilter);
        }
    }

    public void removeAllFilter() {
        this.mFaultChain.mFilters.clear();
    }

    public void removeFilter(IFilter iFilter) {
        this.mFaultChain.mFilters.remove(iFilter);
    }
}
