package com.xiaopeng.speech.protocol.node.ota;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.OtaEvent;

/* loaded from: classes2.dex */
public class OtaNode_Processor implements ICommandProcessor {
    private OtaNode mTarget;

    public OtaNode_Processor(OtaNode otaNode) {
        this.mTarget = otaNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        if (str.equals(OtaEvent.OTA_PAGE_OPEN)) {
            this.mTarget.onOpenOtaPage(str, str2);
        } else if (str.equals(OtaEvent.OTARESERVATION_PAGE_OPEN)) {
            this.mTarget.onOpenReservationPage(str, str2);
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{OtaEvent.OTA_PAGE_OPEN, OtaEvent.OTARESERVATION_PAGE_OPEN};
    }
}
