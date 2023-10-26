package com.xiaopeng.speech.protocol.node.media;

import com.xiaopeng.speech.INodeListener;

/* loaded from: classes2.dex */
public interface MediaListener extends INodeListener {
    default void onBackward(int i) {
    }

    default void onCancelCollect() {
    }

    default void onCollect() {
    }

    default void onForward(int i) {
    }

    default void onNext() {
    }

    default void onPause() {
    }

    default void onPlay(String str) {
    }

    default void onPlayMode(String str) {
    }

    default void onPrev() {
    }

    default void onResume() {
    }

    default void onSetTime(int i) {
    }

    default void onStop() {
    }
}
