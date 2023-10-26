package com.xiaopeng.xvs.xid.account;

import android.accounts.Account;
import android.accounts.OnAccountsUpdateListener;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.os.Handler;
import com.xiaopeng.xvs.xid.XId;
import com.xiaopeng.xvs.xid.account.api.AccountInfo;
import com.xiaopeng.xvs.xid.account.api.IAccount;
import com.xiaopeng.xvs.xid.account.api.LoginType;
import com.xiaopeng.xvs.xid.base.IClient;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class AccountClientImpl extends IClient implements IAccount {
    private static final String TAG = "AccountClientImpl";
    private static volatile IAccount mInstance;
    private AccountContextWrapper mContextWrapper = new AccountContextWrapper(XId.getApplication());

    private AccountClientImpl() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static IAccount getInstance() {
        if (mInstance == null) {
            synchronized (AccountClientImpl.class) {
                if (mInstance == null) {
                    mInstance = new AccountClientImpl();
                }
            }
        }
        return mInstance;
    }

    @Override // com.xiaopeng.xvs.xid.account.api.IAccount
    public void login(Activity activity) {
        login(LoginType.TYPE_LOGIN_QR_SCAN, activity);
    }

    @Override // com.xiaopeng.xvs.xid.account.api.IAccount
    public void login(LoginType loginType, Activity activity) {
        this.mContextWrapper.login(activity);
    }

    @Override // com.xiaopeng.xvs.xid.account.api.IAccount
    public boolean isLogin() {
        return getAccount() != null;
    }

    @Override // com.xiaopeng.xvs.xid.account.api.IAccount
    public void logout() {
        this.mContextWrapper.logout();
    }

    @Override // com.xiaopeng.xvs.xid.account.api.IAccount
    public void addOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener) {
        this.mContextWrapper.addOnAccountsUpdatedListener(onAccountsUpdateListener, true);
    }

    @Override // com.xiaopeng.xvs.xid.account.api.IAccount
    public void addOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener, boolean z) {
        this.mContextWrapper.addOnAccountsUpdatedListener(onAccountsUpdateListener, z);
    }

    @Override // com.xiaopeng.xvs.xid.account.api.IAccount
    public void addOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener, Handler handler, boolean z, String[] strArr) {
        this.mContextWrapper.addOnAccountsUpdatedListener(onAccountsUpdateListener, handler, z, strArr);
    }

    @Override // com.xiaopeng.xvs.xid.account.api.IAccount
    public void registerAccountChangedReceiver(BroadcastReceiver broadcastReceiver) {
        this.mContextWrapper.registerAccountChangedReceiver(broadcastReceiver);
    }

    @Override // com.xiaopeng.xvs.xid.account.api.IAccount
    public void removeOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener) {
        this.mContextWrapper.removeOnAccountsUpdatedListener(onAccountsUpdateListener);
    }

    @Override // com.xiaopeng.xvs.xid.account.api.IAccount
    public void unregisterAccountChangedReceiver(BroadcastReceiver broadcastReceiver) {
        this.mContextWrapper.unregisterAccountChangedReceiver(broadcastReceiver);
    }

    @Override // com.xiaopeng.xvs.xid.account.api.IAccount
    public AccountInfo getAccountInfo() {
        return this.mContextWrapper.getAccountInfo();
    }

    @Override // com.xiaopeng.xvs.xid.account.api.IAccount
    public Account getAccount() {
        return this.mContextWrapper.getAccount();
    }

    @Override // com.xiaopeng.xvs.xid.account.api.IAccount
    public long getUid() {
        AccountInfo accountInfo = getAccountInfo();
        if (accountInfo != null) {
            return accountInfo.getUid();
        }
        return -1L;
    }

    @Override // com.xiaopeng.xvs.xid.account.api.IAccount
    public String getUserExtraData(String str, String str2) {
        return this.mContextWrapper.getUserData(str, str2);
    }

    @Override // com.xiaopeng.xvs.xid.account.api.IAccount
    public boolean setUserExtraData(String str, String str2) {
        return this.mContextWrapper.setUserData(str, str2);
    }
}
