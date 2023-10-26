package com.xiaopeng.speech.protocol.node.internal;

import com.xiaopeng.speech.INodeListener;

/* loaded from: classes2.dex */
public interface InternalListener extends INodeListener {
    void onDmOutput(String str);

    void onInputDmData(String str, String str2);

    void onLocalWakeupResult(String str, String str2);

    void onLocalWakeupResultWithChannel(String str, String str2, String str3);

    void onRebootComplete(String str, String str2);

    void onResourceUpdateFinish(String str, String str2);
}
