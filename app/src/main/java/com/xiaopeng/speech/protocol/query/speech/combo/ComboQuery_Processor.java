package com.xiaopeng.speech.protocol.query.speech.combo;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.speech.ComboQueryEvent;

/* loaded from: classes2.dex */
public class ComboQuery_Processor implements IQueryProcessor {
    private ComboQuery mTarget;

    public ComboQuery_Processor(ComboQuery comboQuery) {
        this.mTarget = comboQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2089284112:
                if (str.equals(ComboQueryEvent.COMBO_ENTER_USERMODE)) {
                    c = 0;
                    break;
                }
                break;
            case -1068777799:
                if (str.equals(ComboQueryEvent.COMBO_GET_CURRENT_USERMODE)) {
                    c = 1;
                    break;
                }
                break;
            case -949369482:
                if (str.equals(ComboQueryEvent.COMBO_EXIT_USERMODE)) {
                    c = 2;
                    break;
                }
                break;
            case 1765601312:
                if (str.equals(ComboQueryEvent.COMBO_CHECK_ENTER_USERMODE)) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return this.mTarget.enterMode(str, str2);
            case 1:
                return this.mTarget.getCurrentUserMode(str, str2);
            case 2:
                return this.mTarget.exitMode(str, str2);
            case 3:
                return this.mTarget.checkEnterUserMode(str, str2);
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{ComboQueryEvent.COMBO_ENTER_USERMODE, ComboQueryEvent.COMBO_EXIT_USERMODE, ComboQueryEvent.COMBO_CHECK_ENTER_USERMODE, ComboQueryEvent.COMBO_GET_CURRENT_USERMODE};
    }
}
