package com.xiaopeng.speech.protocol.query.phone;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.QueryPhoneEvent;

/* loaded from: classes2.dex */
public class PhoneQuery_Processor implements IQueryProcessor {
    private PhoneQuery mTarget;

    public PhoneQuery_Processor(PhoneQuery phoneQuery) {
        this.mTarget = phoneQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -991919164:
                if (str.equals(QueryPhoneEvent.GET_PAGE_OPEN_STATUS)) {
                    c = 0;
                    break;
                }
                break;
            case 579161930:
                if (str.equals(QueryPhoneEvent.GET_BLUETOOTH_STATUS)) {
                    c = 1;
                    break;
                }
                break;
            case 1113652927:
                if (str.equals(QueryPhoneEvent.GET_CONTACT_SYNC_STATUS)) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Integer.valueOf(this.mTarget.getGuiPageOpenState(str, str2));
            case 1:
                return Boolean.valueOf(this.mTarget.onQueryBluetooth(str, str2));
            case 2:
                return Integer.valueOf(this.mTarget.onQueryContactSyncStatus(str, str2));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{QueryPhoneEvent.GET_PAGE_OPEN_STATUS, QueryPhoneEvent.GET_BLUETOOTH_STATUS, QueryPhoneEvent.GET_CONTACT_SYNC_STATUS};
    }
}
