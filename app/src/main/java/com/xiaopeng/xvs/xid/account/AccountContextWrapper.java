package com.xiaopeng.xvs.xid.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OnAccountsUpdateListener;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.xiaopeng.xvs.xid.account.api.AccountInfo;
import com.xiaopeng.xvs.xid.base.IWrapper;
import com.xiaopeng.xvs.xid.utils.L;
import java.io.IOException;
import java.util.Arrays;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;

/* loaded from: classes2.dex */
class AccountContextWrapper implements IWrapper {
    private static final String TAG = "AccountContextWrapper";
    private AccountManager mAccountManager;
    private Application mApplication;
    private AccountInfo mCurrentAccountInfo;

    private void loginStartActivity(Activity activity) {
    }

    private void loginStartService(Context context) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AccountContextWrapper(Application application) {
        this.mApplication = application;
        this.mAccountManager = AccountManager.get(application);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void login(final Activity activity) {
        this.mAccountManager.addAccount("com.xiaopeng.accountservice.ACCOUNT_TYPE_XP_VEHICLE", null, null, null, null, new AccountManagerCallback() { // from class: com.xiaopeng.xvs.xid.account.-$$Lambda$AccountContextWrapper$oquVEb-MMgNFCh4YglzUwnoOwKs
            @Override // android.accounts.AccountManagerCallback
            public final void run(AccountManagerFuture accountManagerFuture) {
                AccountContextWrapper.lambda$login$0(activity, accountManagerFuture);
            }
        }, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$login$0(Activity activity, AccountManagerFuture accountManagerFuture) {
        if (activity == null) {
            Log.e(TAG, "login: context is null . cannot launch login intent.");
            return;
        }
        try {
            Intent intent = (Intent) ((Bundle) accountManagerFuture.getResult()).getParcelable("intent");
            intent.addFlags(ClientDefaults.MAX_MSG_SIZE);
            activity.startActivity(intent);
        } catch (AuthenticatorException | OperationCanceledException | IOException e) {
            L.e(TAG, "login callback fail. exception = " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void logout() {
        removeAccounts();
    }

    private void removeAccounts() {
        Account[] accountsByType = this.mAccountManager.getAccountsByType("com.xiaopeng.accountservice.ACCOUNT_TYPE_XP_VEHICLE");
        Log.d(TAG, "removeAccounts length=" + accountsByType.length);
        for (Account account : accountsByType) {
            try {
                this.mAccountManager.removeAccountExplicitly(account);
            } catch (Exception e) {
                Log.e(TAG, "removeAccounts err=" + e.getMessage());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void addOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener, boolean z) {
        this.mAccountManager.addOnAccountsUpdatedListener(onAccountsUpdateListener, null, z, new String[]{"com.xiaopeng.accountservice.ACCOUNT_TYPE_XP_VEHICLE"});
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void addOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener, Handler handler, boolean z, String[] strArr) {
        this.mAccountManager.addOnAccountsUpdatedListener(onAccountsUpdateListener, handler, z, strArr);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void registerAccountChangedReceiver(BroadcastReceiver broadcastReceiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.accounts.action.VISIBLE_ACCOUNTS_CHANGED");
        intentFilter.addAction("android.intent.action.DEVICE_STORAGE_OK");
        this.mApplication.registerReceiver(broadcastReceiver, intentFilter);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void removeOnAccountsUpdatedListener(OnAccountsUpdateListener onAccountsUpdateListener) {
        this.mAccountManager.removeOnAccountsUpdatedListener(onAccountsUpdateListener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void unregisterAccountChangedReceiver(BroadcastReceiver broadcastReceiver) {
        this.mApplication.unregisterReceiver(broadcastReceiver);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AccountInfo getAccountInfo() {
        return getCurrentAccountInfo(getAccount());
    }

    public synchronized String getUserData(String str, String str2) {
        Account account = getAccount();
        if (account == null) {
            return str2;
        }
        String userData = this.mAccountManager.getUserData(account, str);
        return userData == null ? str2 : userData;
    }

    public synchronized boolean setUserData(String str, String str2) {
        Account account = getAccount();
        if (account == null) {
            return false;
        }
        this.mAccountManager.setUserData(account, str, str2);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Account getAccount() {
        Account[] accountsByType = this.mAccountManager.getAccountsByType("com.xiaopeng.accountservice.ACCOUNT_TYPE_XP_VEHICLE");
        if (accountsByType.length > 0) {
            L.d(TAG, "getAccount accounts.length=" + accountsByType.length + "; accounts=" + Arrays.toString(accountsByType));
            return accountsByType[0];
        }
        L.d(TAG, "getAccount account is null, means not login");
        return null;
    }

    private AccountInfo getCurrentAccountInfo(Account account) {
        if (account == null) {
            return null;
        }
        AccountInfo accountInfo = this.mCurrentAccountInfo;
        if (accountInfo == null) {
            this.mCurrentAccountInfo = new AccountInfo(this.mAccountManager, account);
        } else {
            accountInfo.update(account);
        }
        return this.mCurrentAccountInfo;
    }
}
