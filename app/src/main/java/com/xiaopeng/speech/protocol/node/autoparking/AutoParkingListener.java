package com.xiaopeng.speech.protocol.node.autoparking;

import com.xiaopeng.speech.INodeListener;
import com.xiaopeng.speech.protocol.node.autoparking.bean.ParkingPositionBean;

/* loaded from: classes2.dex */
public interface AutoParkingListener extends INodeListener {
    void onActivate();

    void onExit();

    default void onMemoryParkingStart() {
    }

    void onParkCarportCount(ParkingPositionBean parkingPositionBean);

    void onParkStart();

    default void onParkStart(String str) {
    }
}
