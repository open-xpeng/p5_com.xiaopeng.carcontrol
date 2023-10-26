package com.xiaopeng.speech.protocol.node.app;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.AppEvent;

/* loaded from: classes2.dex */
public class AppNode_Processor implements ICommandProcessor {
    private AppNode mTarget;

    public AppNode_Processor(AppNode appNode) {
        this.mTarget = appNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2098758070:
                if (str.equals(AppEvent.START_PAGE)) {
                    c = 0;
                    break;
                }
                break;
            case -1740381936:
                if (str.equals(AppEvent.APP_LAUNCHER_EXIT)) {
                    c = 1;
                    break;
                }
                break;
            case -1284156689:
                if (str.equals(AppEvent.APP_OPEN_YOUKU_SEARCH)) {
                    c = 2;
                    break;
                }
                break;
            case -1266760065:
                if (str.equals(AppEvent.GUI_CARSPEECH_DEBUG_OPEN)) {
                    c = 3;
                    break;
                }
                break;
            case -210825502:
                if (str.equals(AppEvent.AI_HOMEPAGE_CLOSE)) {
                    c = 4;
                    break;
                }
                break;
            case -210679123:
                if (str.equals(AppEvent.App_PAGE_OPEN)) {
                    c = 5;
                    break;
                }
                break;
            case -135994987:
                if (str.equals(AppEvent.DEBUG_OPEN)) {
                    c = 6;
                    break;
                }
                break;
            case 277054473:
                if (str.equals(AppEvent.APPSTORE_OPEN)) {
                    c = 7;
                    break;
                }
                break;
            case 636922058:
                if (str.equals(AppEvent.KEYEVENT_BACK)) {
                    c = '\b';
                    break;
                }
                break;
            case 963391520:
                if (str.equals(AppEvent.AI_HOMEPAGE_OPEN)) {
                    c = '\t';
                    break;
                }
                break;
            case 1150874049:
                if (str.equals(AppEvent.TRIGGER_INTENT)) {
                    c = '\n';
                    break;
                }
                break;
            case 1242343382:
                if (str.equals(AppEvent.APP_ACTIVE)) {
                    c = 11;
                    break;
                }
                break;
            case 1440847352:
                if (str.equals(AppEvent.QUERY)) {
                    c = '\f';
                    break;
                }
                break;
            case 1863838566:
                if (str.equals(AppEvent.APP_OPEN)) {
                    c = '\r';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onStartPage(str, str2);
                return;
            case 1:
                this.mTarget.onAppLauncherExit(str, str2);
                return;
            case 2:
                this.mTarget.onOpenYoukuSearch(str, str2);
                return;
            case 3:
                this.mTarget.onGuiSpeechDebugPage(str, str2);
                return;
            case 4:
                this.mTarget.onAiHomepageClose(str, str2);
                return;
            case 5:
                this.mTarget.onAppPageOpen(str, str2);
                return;
            case 6:
                this.mTarget.onDebugOpen(str, str2);
                return;
            case 7:
                this.mTarget.onAppStoreOpen(str, str2);
                return;
            case '\b':
                this.mTarget.onKeyeventBack(str, str2);
                return;
            case '\t':
                this.mTarget.onAiHomepageOpen(str, str2);
                return;
            case '\n':
                this.mTarget.onTriggerIntent(str, str2);
                return;
            case 11:
                this.mTarget.onAppActive(str, str2);
                return;
            case '\f':
                this.mTarget.onQuery(str, str2);
                return;
            case '\r':
                this.mTarget.onAppOpen(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{AppEvent.QUERY, AppEvent.APP_OPEN, AppEvent.App_PAGE_OPEN, AppEvent.KEYEVENT_BACK, AppEvent.APPSTORE_OPEN, AppEvent.TRIGGER_INTENT, AppEvent.DEBUG_OPEN, AppEvent.APP_ACTIVE, AppEvent.AI_HOMEPAGE_OPEN, AppEvent.AI_HOMEPAGE_CLOSE, AppEvent.APP_LAUNCHER_EXIT, AppEvent.START_PAGE, AppEvent.GUI_CARSPEECH_DEBUG_OPEN, AppEvent.APP_OPEN_YOUKU_SEARCH};
    }
}
