package com.xiaopeng.speech.protocol.query.personalCenter;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.QueryPersonalCenterEvent;

/* loaded from: classes2.dex */
public class PersonalCenterQuery_Processor implements IQueryProcessor {
    private PersonalCenterQuery mTarget;

    public PersonalCenterQuery_Processor(PersonalCenterQuery personalCenterQuery) {
        this.mTarget = personalCenterQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1082449569:
                if (str.equals(QueryPersonalCenterEvent.GET_PAGE_OPEN_STATUS_PERSONAL_CENTER)) {
                    c = 0;
                    break;
                }
                break;
            case 982269335:
                if (str.equals(QueryPersonalCenterEvent.CONTROL_PROFILE_HABIT_NUM_SUPPORT)) {
                    c = 1;
                    break;
                }
                break;
            case 1404633087:
                if (str.equals(QueryPersonalCenterEvent.CONTROL_PROFILE_HABIT_SUPPORT)) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Integer.valueOf(this.mTarget.getGuiPageOpenState(str, str2));
            case 1:
                return Integer.valueOf(this.mTarget.getControlProfileHabitNumSupport(str, str2));
            case 2:
                return Integer.valueOf(this.mTarget.getControlProfileHabitSupport(str, str2));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{QueryPersonalCenterEvent.CONTROL_PROFILE_HABIT_SUPPORT, QueryPersonalCenterEvent.CONTROL_PROFILE_HABIT_NUM_SUPPORT, QueryPersonalCenterEvent.GET_PAGE_OPEN_STATUS_PERSONAL_CENTER};
    }
}
