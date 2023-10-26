package com.xiaopeng.speech.protocol.node.personalCenter;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.PersonalCenterEvent;

/* loaded from: classes2.dex */
public class PersonalCenterNode_Processor implements ICommandProcessor {
    private PersonalCenterNode mTarget;

    public PersonalCenterNode_Processor(PersonalCenterNode personalCenterNode) {
        this.mTarget = personalCenterNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        if (str.equals(PersonalCenterEvent.CONTROL_PROFILE_HABIT_SELECT_NEXT)) {
            this.mTarget.onControlProfileHabitSelectNext(str, str2);
        } else if (str.equals(PersonalCenterEvent.CONTROL_PROFILE_HABIT_SELECT)) {
            this.mTarget.onControlProfileHabitSelect(str, str2);
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{PersonalCenterEvent.CONTROL_PROFILE_HABIT_SELECT, PersonalCenterEvent.CONTROL_PROFILE_HABIT_SELECT_NEXT};
    }
}
