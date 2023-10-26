package com.xiaopeng.speech.protocol.node.controlcard;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.ControlCardEvent;

/* loaded from: classes2.dex */
public class ControlCardNode_Processor implements ICommandProcessor {
    private ControlCardNode mTarget;

    public ControlCardNode_Processor(ControlCardNode controlCardNode) {
        this.mTarget = controlCardNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1874400088:
                if (str.equals(ControlCardEvent.SHOW_CARD_AC_SEAT_VENTILATE_DRIVER)) {
                    c = 0;
                    break;
                }
                break;
            case -778931165:
                if (str.equals(ControlCardEvent.SHOW_CARD_SYSTEM_SCREEN_BRIGHTNESS)) {
                    c = 1;
                    break;
                }
                break;
            case -205168772:
                if (str.equals(ControlCardEvent.SHOW_CARD_SYSTEM_ICM_BRIGHTNESS)) {
                    c = 2;
                    break;
                }
                break;
            case 90422065:
                if (str.equals(ControlCardEvent.SHOW_CARD_AC_TEMP)) {
                    c = 3;
                    break;
                }
                break;
            case 90515301:
                if (str.equals(ControlCardEvent.SHOW_CARD_AC_WIND)) {
                    c = 4;
                    break;
                }
                break;
            case 463841200:
                if (str.equals(ControlCardEvent.SHOW_CARD_AC_SEAT_HEATING_DRIVER)) {
                    c = 5;
                    break;
                }
                break;
            case 551410205:
                if (str.equals(ControlCardEvent.SHOW_CARD_AC_PASSENGER_TEMP)) {
                    c = 6;
                    break;
                }
                break;
            case 1167636869:
                if (str.equals(ControlCardEvent.SHOW_CARD_AC_DRIVER_TEMP)) {
                    c = 7;
                    break;
                }
                break;
            case 1804974098:
                if (str.equals(ControlCardEvent.SHOW_CARD_AC_SEAT_HEATING_PASSENGER)) {
                    c = '\b';
                    break;
                }
                break;
            case 1905039599:
                if (str.equals(ControlCardEvent.SHOW_CARD_ATMOSPWHERE_COLOR)) {
                    c = '\t';
                    break;
                }
                break;
            case 2107431493:
                if (str.equals(ControlCardEvent.SHOW_CARD_ATMOSPWHERE_BRIGHTNESS)) {
                    c = '\n';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.showAcSeatVentilateDriverCard(str, str2);
                return;
            case 1:
                this.mTarget.showSystemScreenBrightnessCard(str, str2);
                return;
            case 2:
                this.mTarget.showSystemIcmBrightnessCard(str, str2);
                return;
            case 3:
                this.mTarget.showAcTempCard(str, str2);
                return;
            case 4:
                this.mTarget.showAcWindCard(str, str2);
                return;
            case 5:
                this.mTarget.showAcSeatHeatingDriverCard(str, str2);
                return;
            case 6:
                this.mTarget.showAcPassengerTempCard(str, str2);
                return;
            case 7:
                this.mTarget.showAcDriverTempCard(str, str2);
                return;
            case '\b':
                this.mTarget.showAcSeatHeatingPassengerCard(str, str2);
                return;
            case '\t':
                this.mTarget.showAtmosphereBrightnessColorCard(str, str2);
                return;
            case '\n':
                this.mTarget.showAtmosphereBrightnessCard(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{ControlCardEvent.SHOW_CARD_AC_TEMP, ControlCardEvent.SHOW_CARD_AC_DRIVER_TEMP, ControlCardEvent.SHOW_CARD_AC_PASSENGER_TEMP, ControlCardEvent.SHOW_CARD_AC_WIND, ControlCardEvent.SHOW_CARD_ATMOSPWHERE_BRIGHTNESS, ControlCardEvent.SHOW_CARD_ATMOSPWHERE_COLOR, ControlCardEvent.SHOW_CARD_AC_SEAT_HEATING_DRIVER, ControlCardEvent.SHOW_CARD_AC_SEAT_HEATING_PASSENGER, ControlCardEvent.SHOW_CARD_AC_SEAT_VENTILATE_DRIVER, ControlCardEvent.SHOW_CARD_SYSTEM_SCREEN_BRIGHTNESS, ControlCardEvent.SHOW_CARD_SYSTEM_ICM_BRIGHTNESS};
    }
}
