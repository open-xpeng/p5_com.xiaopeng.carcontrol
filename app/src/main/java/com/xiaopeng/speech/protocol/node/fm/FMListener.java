package com.xiaopeng.speech.protocol.node.fm;

import com.xiaopeng.speech.INodeListener;

/* loaded from: classes2.dex */
public interface FMListener extends INodeListener {
    void onFmLocalOff();

    void onFmLocalOn();

    void onFmNetworkOff();

    void onFmNetworkOn();

    void onFmPlayChannel(String str);

    void onFmPlayChannelName(String str, String str2);

    void onPlayCollectFM();
}
