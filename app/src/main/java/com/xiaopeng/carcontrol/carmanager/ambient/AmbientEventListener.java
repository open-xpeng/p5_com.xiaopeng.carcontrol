package com.xiaopeng.carcontrol.carmanager.ambient;

import android.util.Pair;

/* loaded from: classes.dex */
public interface AmbientEventListener {
    void onChangeBright(String partition, int bright);

    void onChangeColorType(String partition, String type);

    void onChangeDoubleColor(String partition, Pair<AmbientColor, AmbientColor> color);

    void onChangeMode(String partition, String mode);

    void onChangeMonoColor(String partition, AmbientColor color);

    void onErrorPlay(String partition, String effect);

    void onErrorSet(String partition, String mode);

    void onErrorStop(String partition, String effect);

    void onErrorSub(String partition, String mode);
}
