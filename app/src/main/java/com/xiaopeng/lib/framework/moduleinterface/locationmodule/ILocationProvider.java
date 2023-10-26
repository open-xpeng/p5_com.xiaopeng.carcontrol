package com.xiaopeng.lib.framework.moduleinterface.locationmodule;

import android.content.Context;

/* loaded from: classes2.dex */
public interface ILocationProvider {
    ILocation buildLocation();

    void publishLocation(ILocation iLocation) throws ILocationServiceException;

    boolean serviceStarted();

    void start(Context context) throws ILocationServiceException;
}
