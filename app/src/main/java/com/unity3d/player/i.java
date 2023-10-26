package com.unity3d.player;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

/* loaded from: classes.dex */
public final class i extends Fragment {
    private final Runnable a;

    public i() {
        this.a = null;
    }

    public i(Runnable runnable) {
        this.a = runnable;
    }

    @Override // android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.a == null) {
            getFragmentManager().beginTransaction().remove(this).commit();
        } else {
            requestPermissions(getArguments().getStringArray("PermissionNames"), 15881);
        }
    }

    @Override // android.app.Fragment
    public final void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i != 15881) {
            return;
        }
        if (strArr.length == 0) {
            requestPermissions(getArguments().getStringArray("PermissionNames"), 15881);
            return;
        }
        for (int i2 = 0; i2 < strArr.length && i2 < iArr.length; i2++) {
            g.Log(4, strArr[i2] + (iArr[i2] == 0 ? " granted" : " denied"));
        }
        FragmentTransaction beginTransaction = getActivity().getFragmentManager().beginTransaction();
        beginTransaction.remove(this);
        beginTransaction.commit();
        this.a.run();
    }
}
