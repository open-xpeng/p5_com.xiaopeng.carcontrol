package com.xiaopeng.speech.protocol.node.userbook;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.CarcontrolEvent;

/* loaded from: classes2.dex */
public class UserBookNode_Processor implements ICommandProcessor {
    private UserBookNode mTarget;

    public UserBookNode_Processor(UserBookNode userBookNode) {
        this.mTarget = userBookNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1001296170:
                if (str.equals(CarcontrolEvent.CLOSE_USER_BOOK)) {
                    c = 0;
                    break;
                }
                break;
            case 1414376552:
                if (str.equals(CarcontrolEvent.OPEN_USER_BOOK)) {
                    c = 1;
                    break;
                }
                break;
            case 1829348592:
                if (str.equals(CarcontrolEvent.CHECK_USER_BOOK)) {
                    c = 2;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onCloseUserBook(str, str2);
                return;
            case 1:
                this.mTarget.onOpenUserBook(str, str2);
                return;
            case 2:
                this.mTarget.onCheckUserBook(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{CarcontrolEvent.CHECK_USER_BOOK, CarcontrolEvent.OPEN_USER_BOOK, CarcontrolEvent.CLOSE_USER_BOOK};
    }
}
