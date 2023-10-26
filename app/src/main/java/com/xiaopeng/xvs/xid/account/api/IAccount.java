package com.xiaopeng.xvs.xid.account.api;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.os.Handler;
import com.xiaopeng.xvs.xid.base.IInterface;

/* loaded from: classes2.dex */
public interface IAccount extends IInterface {
    public static final String ACCOUNT_TYPE_XP_VEHICLE = "com.xiaopeng.accountservice.ACCOUNT_TYPE_XP_VEHICLE";

    void addOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener);

    void addOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener, Handler handler, boolean z, String[] strArr);

    void addOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener, boolean z);

    Account getAccount();

    AccountInfo getAccountInfo();

    long getUid();

    String getUserExtraData(String str, String str2);

    boolean isLogin();

    void login(Activity activity);

    void login(LoginType loginType, Activity activity);

    void logout();

    void registerAccountChangedReceiver(BroadcastReceiver broadcastReceiver);

    void removeOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener);

    boolean setUserExtraData(String str, String str2);

    void unregisterAccountChangedReceiver(BroadcastReceiver broadcastReceiver);
}
