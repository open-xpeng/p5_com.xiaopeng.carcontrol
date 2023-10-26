package com.xiaopeng.speech.protocol.query.appstore;

import android.text.TextUtils;
import com.xiaopeng.speech.SpeechQuery;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class AppAndAppletQuery extends SpeechQuery<IAppAndAppletCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public int onOpenAppshopPage(String str, String str2) {
        return ((IAppAndAppletCaller) this.mQueryCaller).onOpenAppshopPage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int onCloseAppshopPage(String str, String str2) {
        return ((IAppAndAppletCaller) this.mQueryCaller).onCloseAppshopPage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int onOpenAppStoreMainPage(String str, String str2) {
        return ((IAppAndAppletCaller) this.mQueryCaller).onOpenAppStoreMainPage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int onCloseAppStoreMainPage(String str, String str2) {
        return ((IAppAndAppletCaller) this.mQueryCaller).onCloseAppStoreMainPage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int onOpenAppletMainPage(String str, String str2) {
        return ((IAppAndAppletCaller) this.mQueryCaller).onOpenAppletMainPage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int onCloseAppletMainPage(String str, String str2) {
        return ((IAppAndAppletCaller) this.mQueryCaller).onCloseAppletMainPage();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAppOpenStatus(String str, String str2) {
        return ((IAppAndAppletCaller) this.mQueryCaller).getAppOpenStatus(str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAppCloseStatus(String str, String str2) {
        return ((IAppAndAppletCaller) this.mQueryCaller).getAppCloseStatus(str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAppletOpenStatus(String str, String str2) {
        return ((IAppAndAppletCaller) this.mQueryCaller).getAppletOpenStatus(str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getAppletCloseStatus(String str, String str2) {
        return ((IAppAndAppletCaller) this.mQueryCaller).getAppletCloseStatus(str2);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCurrentCloseStatus(String str, String str2) {
        String str3 = null;
        try {
            if (!TextUtils.isEmpty(str2)) {
                str3 = new JSONObject(str2).optString(VuiConstants.ELEMENT_TYPE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ((IAppAndAppletCaller) this.mQueryCaller).getCurrentCloseStatus(str3);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean hasDialogCloseByHand(String str, String str2) {
        return ((IAppAndAppletCaller) this.mQueryCaller).hasDialogCloseByHand();
    }
}
