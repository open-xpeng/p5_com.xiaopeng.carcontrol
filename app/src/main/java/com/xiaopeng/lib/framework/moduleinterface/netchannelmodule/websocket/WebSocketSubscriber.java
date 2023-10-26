package com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okio.ByteString;

/* loaded from: classes2.dex */
public abstract class WebSocketSubscriber implements Observer<IWebSocketInfo> {
    private Disposable disposable;
    private boolean hasOpened;

    protected void onClose() {
    }

    protected void onClosed(int i, String str) {
    }

    protected void onMessage(String str) {
    }

    protected void onMessage(ByteString byteString) {
    }

    protected void onOpen() {
    }

    protected void onReconnect() {
    }

    @Override // io.reactivex.Observer
    public final void onNext(IWebSocketInfo iWebSocketInfo) {
        if (iWebSocketInfo.isOnOpen()) {
            this.hasOpened = true;
            onOpen();
        } else if (iWebSocketInfo.isClosed()) {
            this.hasOpened = false;
            onClosed(iWebSocketInfo.closedReasonCode(), iWebSocketInfo.closedReason());
        } else if (iWebSocketInfo.stringMessage() != null) {
            onMessage(iWebSocketInfo.stringMessage());
        } else if (iWebSocketInfo.byteStringMessage() != null) {
            onMessage(iWebSocketInfo.byteStringMessage());
        } else if (iWebSocketInfo.isOnReconnect()) {
            onReconnect();
        }
    }

    @Override // io.reactivex.Observer
    public final void onSubscribe(Disposable disposable) {
        this.disposable = disposable;
    }

    public final void dispose() {
        Disposable disposable = this.disposable;
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override // io.reactivex.Observer
    public final void onComplete() {
        if (this.hasOpened) {
            onClose();
        }
    }

    @Override // io.reactivex.Observer
    public void onError(Throwable th) {
        th.printStackTrace();
    }
}
