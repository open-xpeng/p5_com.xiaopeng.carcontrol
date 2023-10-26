package com.xiaopeng.speech.protocol.node.meter;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.MeterEvent;

/* loaded from: classes2.dex */
public class MeterNode_Processor implements ICommandProcessor {
    private MeterNode mTarget;

    public MeterNode_Processor(MeterNode meterNode) {
        this.mTarget = meterNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1943195853:
                if (str.equals(MeterEvent.SET_RIGHTT_CARD)) {
                    c = 0;
                    break;
                }
                break;
            case -1464668575:
                if (str.equals(MeterEvent.DASHBOARD_LIGHTS_STATUS)) {
                    c = 1;
                    break;
                }
                break;
            case 432832230:
                if (str.equals(MeterEvent.SET_LEFT_CARD)) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.setRightCard(str, str2);
                return;
            case 1:
                this.mTarget.onDashboardLightsStatus(str, str2);
                return;
            case 2:
                this.mTarget.setLeftCard(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{MeterEvent.SET_LEFT_CARD, MeterEvent.SET_RIGHTT_CARD, MeterEvent.DASHBOARD_LIGHTS_STATUS};
    }
}
