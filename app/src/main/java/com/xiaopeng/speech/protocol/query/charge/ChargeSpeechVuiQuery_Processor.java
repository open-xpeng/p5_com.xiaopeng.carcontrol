package com.xiaopeng.speech.protocol.query.charge;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.speech.ChargeSpeechVuiEvent;

/* loaded from: classes2.dex */
public class ChargeSpeechVuiQuery_Processor implements IQueryProcessor {
    private ChargeSpeechVuiQuery mTarget;

    public ChargeSpeechVuiQuery_Processor(ChargeSpeechVuiQuery chargeSpeechVuiQuery) {
        this.mTarget = chargeSpeechVuiQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2140516078:
                if (str.equals(ChargeSpeechVuiEvent.CHARGE_VUI_XSWITCH_CHECKED)) {
                    c = 0;
                    break;
                }
                break;
            case -730105805:
                if (str.equals(ChargeSpeechVuiEvent.CHARGE_VUI_ELEMENT_RADIOBUTTON_CHECKED)) {
                    c = 1;
                    break;
                }
                break;
            case -405850515:
                if (str.equals(ChargeSpeechVuiEvent.CHARGE_VUI_XTABLAYOUT_SELECTED)) {
                    c = 2;
                    break;
                }
                break;
            case -343060995:
                if (str.equals(ChargeSpeechVuiEvent.CHARGE_VUI_ELEMENT_SCROLL_STATE)) {
                    c = 3;
                    break;
                }
                break;
            case -72881480:
                if (str.equals(ChargeSpeechVuiEvent.CHARGE_VUI_ELEMENT_ENABLED)) {
                    c = 4;
                    break;
                }
                break;
            case 33969499:
                if (str.equals(ChargeSpeechVuiEvent.CHARGE_VUI_XSLIDER_SETVALUE)) {
                    c = 5;
                    break;
                }
                break;
            case 96497224:
                if (str.equals(ChargeSpeechVuiEvent.CHARGE_VUI_STATEFULBUTTON_SETVALUE)) {
                    c = 6;
                    break;
                }
                break;
            case 703355697:
                if (str.equals(ChargeSpeechVuiEvent.CHARGE_VUI_ELEMENT_CHECKBOX_CHECKED)) {
                    c = 7;
                    break;
                }
                break;
            case 1386874504:
                if (str.equals(ChargeSpeechVuiEvent.CHARGE_VUI_STATEFULBUTTON_CHECKED)) {
                    c = '\b';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Boolean.valueOf(this.mTarget.isSwitchChecked(str, str2));
            case 1:
                return Boolean.valueOf(this.mTarget.isRaduoButtonChecked(str, str2));
            case 2:
                return Boolean.valueOf(this.mTarget.isTableLayoutSelected(str, str2));
            case 3:
                return Boolean.valueOf(this.mTarget.isElementCanScrolled(str, str2));
            case 4:
                return Boolean.valueOf(this.mTarget.isElementEnabled(str, str2));
            case 5:
                return Integer.valueOf(this.mTarget.getXSliderIndex(str, str2));
            case 6:
                return this.mTarget.getStatefulButtonValue(str, str2);
            case 7:
                return Boolean.valueOf(this.mTarget.isCheckBoxChecked(str, str2));
            case '\b':
                return Boolean.valueOf(this.mTarget.isStatefulButtonChecked(str, str2));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{ChargeSpeechVuiEvent.CHARGE_VUI_XSWITCH_CHECKED, ChargeSpeechVuiEvent.CHARGE_VUI_XTABLAYOUT_SELECTED, ChargeSpeechVuiEvent.CHARGE_VUI_XSLIDER_SETVALUE, ChargeSpeechVuiEvent.CHARGE_VUI_STATEFULBUTTON_CHECKED, ChargeSpeechVuiEvent.CHARGE_VUI_STATEFULBUTTON_SETVALUE, ChargeSpeechVuiEvent.CHARGE_VUI_ELEMENT_ENABLED, ChargeSpeechVuiEvent.CHARGE_VUI_ELEMENT_SCROLL_STATE, ChargeSpeechVuiEvent.CHARGE_VUI_ELEMENT_CHECKBOX_CHECKED, ChargeSpeechVuiEvent.CHARGE_VUI_ELEMENT_RADIOBUTTON_CHECKED};
    }
}
