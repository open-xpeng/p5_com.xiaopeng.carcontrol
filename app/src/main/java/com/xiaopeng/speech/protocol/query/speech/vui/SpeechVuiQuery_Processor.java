package com.xiaopeng.speech.protocol.query.speech.vui;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.speech.SpeechVuiEvent;

/* loaded from: classes2.dex */
public class SpeechVuiQuery_Processor implements IQueryProcessor {
    private SpeechVuiQuery mTarget;

    public SpeechVuiQuery_Processor(SpeechVuiQuery speechVuiQuery) {
        this.mTarget = speechVuiQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2145092778:
                if (str.equals(SpeechVuiEvent.VUI_XTABLAYOUT_SELECTED)) {
                    c = 0;
                    break;
                }
                break;
            case -1914744341:
                if (str.equals(SpeechVuiEvent.VUI_ELEMENT_ENABLED)) {
                    c = 1;
                    break;
                }
                break;
            case -1689433816:
                if (str.equals(SpeechVuiEvent.VUI_XSLIDER_SETVALUE)) {
                    c = 2;
                    break;
                }
                break;
            case -1559106607:
                if (str.equals(SpeechVuiEvent.VUI_XSWITCH_CHECKED)) {
                    c = 3;
                    break;
                }
                break;
            case -1526015973:
                if (str.equals(SpeechVuiEvent.VUI_STATEFULBUTTON_CHECKED)) {
                    c = 4;
                    break;
                }
                break;
            case -1065770734:
                if (str.equals(SpeechVuiEvent.VUI_ELEMENT_CHECKBOX_CHECKED)) {
                    c = 5;
                    break;
                }
                break;
            case -235119162:
                if (str.equals(SpeechVuiEvent.VUI_ELEMENT_SCROLL_STATE)) {
                    c = 6;
                    break;
                }
                break;
            case -162510757:
                if (str.equals(SpeechVuiEvent.VUI_STATEFULBUTTON_SETVALUE)) {
                    c = 7;
                    break;
                }
                break;
            case 561567344:
                if (str.equals(SpeechVuiEvent.VUI_SWITCH_OPENED)) {
                    c = '\b';
                    break;
                }
                break;
            case 1029533589:
                if (str.equals(SpeechVuiEvent.VUI_REBUILD_SCENE)) {
                    c = '\t';
                    break;
                }
                break;
            case 1554959696:
                if (str.equals(SpeechVuiEvent.VUI_ELEMENT_RADIOBUTTON_CHECKED)) {
                    c = '\n';
                    break;
                }
                break;
            case 1920063277:
                if (str.equals(SpeechVuiEvent.VUI_SCROLLVIEW_CHILD_VIEW_VISIBLE)) {
                    c = 11;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Boolean.valueOf(this.mTarget.isTableLayoutSelected(str, str2));
            case 1:
                return Boolean.valueOf(this.mTarget.isElementEnabled(str, str2));
            case 2:
                return Integer.valueOf(this.mTarget.getXSliderIndex(str, str2));
            case 3:
                return Boolean.valueOf(this.mTarget.isSwitchChecked(str, str2));
            case 4:
                return Boolean.valueOf(this.mTarget.isStatefulButtonChecked(str, str2));
            case 5:
                return Boolean.valueOf(this.mTarget.isCheckBoxChecked(str, str2));
            case 6:
                return Boolean.valueOf(this.mTarget.isElementCanScrolled(str, str2));
            case 7:
                return this.mTarget.getStatefulButtonValue(str, str2);
            case '\b':
                return Boolean.valueOf(this.mTarget.isVuiSwitchOpened());
            case '\t':
                return Boolean.valueOf(this.mTarget.rebuildScene(str, str2));
            case '\n':
                return Boolean.valueOf(this.mTarget.isRadiobuttonChecked(str, str2));
            case 11:
                return this.mTarget.isViewVisibleByScrollView(str, str2);
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{SpeechVuiEvent.VUI_XSWITCH_CHECKED, SpeechVuiEvent.VUI_XTABLAYOUT_SELECTED, SpeechVuiEvent.VUI_XSLIDER_SETVALUE, SpeechVuiEvent.VUI_STATEFULBUTTON_CHECKED, SpeechVuiEvent.VUI_STATEFULBUTTON_SETVALUE, SpeechVuiEvent.VUI_ELEMENT_ENABLED, SpeechVuiEvent.VUI_ELEMENT_SCROLL_STATE, SpeechVuiEvent.VUI_SWITCH_OPENED, SpeechVuiEvent.VUI_ELEMENT_CHECKBOX_CHECKED, SpeechVuiEvent.VUI_ELEMENT_RADIOBUTTON_CHECKED, SpeechVuiEvent.VUI_SCROLLVIEW_CHILD_VIEW_VISIBLE, SpeechVuiEvent.VUI_REBUILD_SCENE};
    }
}
