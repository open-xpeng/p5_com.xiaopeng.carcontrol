package com.xiaopeng.lib.framework.netchannelmodule.http.statistic;

/* loaded from: classes2.dex */
public class SocketCounter extends BaseHttpCounter {
    private static final String EVENT_NAME = "data_traffic_socket";
    private static final String MOLE_TRAFFIC_SOCKET_BUTTON_ID = "B002";

    public SocketCounter() {
        super(EVENT_NAME, MOLE_TRAFFIC_SOCKET_BUTTON_ID);
        sKeyFailed = "net_socket_failed";
        sKeySucceed = "net_socket_succeed";
        sKeyRequest = "net_socket_request";
        sKeyTrafficRx = "net_socket_rx";
        sKeyTrafficTx = "net_socket_tx";
        sKeyDetails = "net_socket_details";
        sKeyLastDate = "net_socket_date";
    }

    public static SocketCounter getInstance() {
        return Holder.INSTANCE;
    }

    /* loaded from: classes2.dex */
    private static final class Holder {
        private static final SocketCounter INSTANCE = new SocketCounter();

        private Holder() {
        }
    }
}
