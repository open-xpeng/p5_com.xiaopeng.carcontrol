package com.xiaopeng.lib.framework.moduleinterface.carcontroller.v2extend;

import com.xiaopeng.lib.framework.moduleinterface.carcontroller.AbstractEventMsg;

/* loaded from: classes2.dex */
public interface IInputController {
    public static final int HAS_REQUEST = 1;
    public static final int NO_REQUEST = 0;

    /* loaded from: classes2.dex */
    public static class InputAudioSwitchEventMsgV2 extends AbstractEventMsg<InputKeyEvent> {
    }

    /* loaded from: classes2.dex */
    public static class InputKeyEvent {
        int code;
        Object value;

        public InputKeyEvent(int i, Object obj) {
            this.code = i;
            this.value = obj;
        }

        public int getCode() {
            return this.code;
        }

        public Object getValue() {
            return this.value;
        }
    }
}
