package com.xiaopeng.carcontrol.view.fragment.impl;

import androidx.lifecycle.Observer;
import com.xiaopeng.xui.widget.XSwitch;

/* compiled from: lambda */
/* renamed from: com.xiaopeng.carcontrol.view.fragment.impl.-$$Lambda$SettingsFragment$aeIiqtRaGva9_dxY-D21QFFdegA  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA implements Observer {
    public final /* synthetic */ XSwitch f$0;

    public /* synthetic */ $$Lambda$SettingsFragment$aeIiqtRaGva9_dxYD21QFFdegA(XSwitch xSwitch) {
        this.f$0 = xSwitch;
    }

    @Override // androidx.lifecycle.Observer
    public final void onChanged(Object obj) {
        SettingsFragment.m101lambda$aeIiqtRaGva9_dxYD21QFFdegA(this.f$0, ((Boolean) obj).booleanValue());
    }
}
