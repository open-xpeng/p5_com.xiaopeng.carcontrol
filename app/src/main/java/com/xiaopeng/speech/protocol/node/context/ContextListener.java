package com.xiaopeng.speech.protocol.node.context;

import com.xiaopeng.speech.INodeListener;

/* loaded from: classes2.dex */
public interface ContextListener extends INodeListener {
    void onExitRecommendCard();

    void onExpression(String str);

    void onInputText(String str);

    void onOutputText(String str);

    void onPageNext();

    void onPagePrev();

    void onPageSetLow();

    void onPageTopping();

    void onSayWelcome(String str);

    void onScript(String str, String str2);

    void onScriptStatus(String str, String str2);

    void onTipsText(String str);

    void onWidgetCancel(String str, String str2);

    void onWidgetCard(String str);

    void onWidgetCustom(String str);

    void onWidgetList(String str);

    void onWidgetListCancelFocus(String str, int i);

    default void onWidgetListExpend(String str, String str2) {
    }

    void onWidgetListFocus(String str, int i);

    default void onWidgetListFold(String str, String str2) {
    }

    void onWidgetListSelect(String str, int i);

    void onWidgetListSelect(String str, int i, String str2);

    default void onWidgetListStopCountdown() {
    }

    void onWidgetMedia(String str);

    void onWidgetRecommend(String str);

    void onWidgetScroll(String str, int i);

    void onWidgetSearch(String str);

    void onWidgetText(String str);
}
