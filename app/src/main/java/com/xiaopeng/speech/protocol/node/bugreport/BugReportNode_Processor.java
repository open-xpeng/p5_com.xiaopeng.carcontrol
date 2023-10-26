package com.xiaopeng.speech.protocol.node.bugreport;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.BugReportEvent;

/* loaded from: classes2.dex */
public class BugReportNode_Processor implements ICommandProcessor {
    private BugReportNode mTarget;

    public BugReportNode_Processor(BugReportNode bugReportNode) {
        this.mTarget = bugReportNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1272286452:
                if (str.equals(BugReportEvent.BUG_REPORT_END)) {
                    c = 0;
                    break;
                }
                break;
            case -1272269173:
                if (str.equals(BugReportEvent.BUG_REPORT_ENDTTS)) {
                    c = 1;
                    break;
                }
                break;
            case -1265421229:
                if (str.equals(BugReportEvent.BUG_REPORT_BEGIN)) {
                    c = 2;
                    break;
                }
                break;
            case -508144235:
                if (str.equals(BugReportEvent.BUG_REPORT_VOLUME)) {
                    c = 3;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onBugReportEnd(str, str2);
                return;
            case 1:
                this.mTarget.onBugReportEndtts(str, str2);
                return;
            case 2:
                this.mTarget.onBugReportBegin(str, str2);
                return;
            case 3:
                this.mTarget.onBugReportVolume(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{BugReportEvent.BUG_REPORT_BEGIN, BugReportEvent.BUG_REPORT_END, BugReportEvent.BUG_REPORT_VOLUME, BugReportEvent.BUG_REPORT_ENDTTS};
    }
}
