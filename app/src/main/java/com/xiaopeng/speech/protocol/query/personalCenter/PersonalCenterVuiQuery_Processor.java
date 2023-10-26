package com.xiaopeng.speech.protocol.query.personalCenter;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.speech.PersonalCenterVuiEvent;

/* loaded from: classes2.dex */
public class PersonalCenterVuiQuery_Processor implements IQueryProcessor {
    private PersonalCenterVuiQuery mTarget;

    public PersonalCenterVuiQuery_Processor(PersonalCenterVuiQuery personalCenterVuiQuery) {
        this.mTarget = personalCenterVuiQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1676441979:
                if (str.equals(PersonalCenterVuiEvent.VUI_ELEMENT_SCROLL_STATE)) {
                    c = 0;
                    break;
                }
                break;
            case -1377418726:
                if (str.equals(PersonalCenterVuiEvent.VUI_STATEFULBUTTON_SETVALUE)) {
                    c = 1;
                    break;
                }
                break;
            case -1003325425:
                if (str.equals(PersonalCenterVuiEvent.VUI_ELEMENT_RADIOBUTTON_CHECKED)) {
                    c = 2;
                    break;
                }
                break;
            case -435247663:
                if (str.equals(PersonalCenterVuiEvent.VUI_ELEMENT_CHECKBOX_CHECKED)) {
                    c = 3;
                    break;
                }
                break;
            case 102757397:
                if (str.equals(PersonalCenterVuiEvent.VUI_XTABLAYOUT_SELECTED)) {
                    c = 4;
                    break;
                }
                break;
            case 384983952:
                if (str.equals(PersonalCenterVuiEvent.VUI_XSWITCH_CHECKED)) {
                    c = 5;
                    break;
                }
                break;
            case 1183902695:
                if (str.equals(PersonalCenterVuiEvent.VUI_XSLIDER_SETVALUE)) {
                    c = 6;
                    break;
                }
                break;
            case 1448450986:
                if (str.equals(PersonalCenterVuiEvent.VUI_ELEMENT_ENABLED)) {
                    c = 7;
                    break;
                }
                break;
            case 1843237338:
                if (str.equals(PersonalCenterVuiEvent.VUI_STATEFULBUTTON_CHECKED)) {
                    c = '\b';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Boolean.valueOf(this.mTarget.isElementCanScrolled(str, str2));
            case 1:
                return this.mTarget.getStatefulButtonValue(str, str2);
            case 2:
                return Boolean.valueOf(this.mTarget.isRadioButtonChecked(str, str2));
            case 3:
                return Boolean.valueOf(this.mTarget.isCheckBoxChecked(str, str2));
            case 4:
                return Boolean.valueOf(this.mTarget.isTableLayoutSelected(str, str2));
            case 5:
                return Boolean.valueOf(this.mTarget.isSwitchChecked(str, str2));
            case 6:
                return Integer.valueOf(this.mTarget.getXSliderIndex(str, str2));
            case 7:
                return Boolean.valueOf(this.mTarget.isElementEnabled(str, str2));
            case '\b':
                return Boolean.valueOf(this.mTarget.isStatefulButtonChecked(str, str2));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{PersonalCenterVuiEvent.VUI_XSWITCH_CHECKED, PersonalCenterVuiEvent.VUI_XTABLAYOUT_SELECTED, PersonalCenterVuiEvent.VUI_XSLIDER_SETVALUE, PersonalCenterVuiEvent.VUI_STATEFULBUTTON_CHECKED, PersonalCenterVuiEvent.VUI_STATEFULBUTTON_SETVALUE, PersonalCenterVuiEvent.VUI_ELEMENT_ENABLED, PersonalCenterVuiEvent.VUI_ELEMENT_SCROLL_STATE, PersonalCenterVuiEvent.VUI_ELEMENT_CHECKBOX_CHECKED, PersonalCenterVuiEvent.VUI_ELEMENT_RADIOBUTTON_CHECKED};
    }
}
