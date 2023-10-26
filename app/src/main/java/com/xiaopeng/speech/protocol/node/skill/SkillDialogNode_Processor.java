package com.xiaopeng.speech.protocol.node.skill;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.SkillDialogEvent;

/* loaded from: classes2.dex */
public class SkillDialogNode_Processor implements ICommandProcessor {
    private SkillDialogNode mTarget;

    public SkillDialogNode_Processor(SkillDialogNode skillDialogNode) {
        this.mTarget = skillDialogNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1594338748:
                if (str.equals(SkillDialogEvent.SKILL_SHOW_KEY_GUIDE)) {
                    c = 0;
                    break;
                }
                break;
            case -1248956104:
                if (str.equals(SkillDialogEvent.ROUTE_OPEN_WINDOW)) {
                    c = 1;
                    break;
                }
                break;
            case -764162793:
                if (str.equals(SkillDialogEvent.ROUTE_CLOSE_WINDOW)) {
                    c = 2;
                    break;
                }
                break;
            case -91367129:
                if (str.equals(SkillDialogEvent.AI_FORWARD_SCREEN_EVENT)) {
                    c = 3;
                    break;
                }
                break;
            case 2043108326:
                if (str.equals(SkillDialogEvent.SKILL_REFRESH_DATA)) {
                    c = 4;
                    break;
                }
                break;
            case 2110322600:
                if (str.equals(SkillDialogEvent.SKILL_SHOW_DOUBLE_GUIDE)) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onShowKeyGuide(str, str2);
                return;
            case 1:
                this.mTarget.onOpenWindow(str, str2);
                return;
            case 2:
                this.mTarget.onCloseWindow(str, str2);
                return;
            case 3:
                this.mTarget.onForwardScreenEvent(str, str2);
                return;
            case 4:
                this.mTarget.onRefreshSkillData(str, str2);
                return;
            case 5:
                this.mTarget.onShowDoubleGuide(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{SkillDialogEvent.ROUTE_CLOSE_WINDOW, SkillDialogEvent.ROUTE_OPEN_WINDOW, SkillDialogEvent.AI_FORWARD_SCREEN_EVENT, SkillDialogEvent.SKILL_REFRESH_DATA, SkillDialogEvent.SKILL_SHOW_DOUBLE_GUIDE, SkillDialogEvent.SKILL_SHOW_KEY_GUIDE};
    }
}
