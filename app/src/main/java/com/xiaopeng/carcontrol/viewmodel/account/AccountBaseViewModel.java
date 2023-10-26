package com.xiaopeng.carcontrol.viewmodel.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes2.dex */
public abstract class AccountBaseViewModel {
    public static final String ACCOUNT_TYPE_XP_VEHICLE = "com.xiaopeng.accountservice.ACCOUNT_TYPE_XP_VEHICLE";
    private static final String ACTION_REQUEST_QRCODE = "com.xiaopeng.xvs.account.ACTION_ACCOUNT_DIALOG_QR_REQUEST";
    public static final String AUTH_INFO_EXTRA_APP_ID = "app_id";
    public static final String AUTH_TYPE_AUTH_CODE = "com.xiaopeng.accountservice.AUTH_TYPE_AUTH_CODE";
    public static final String AUTH_TYPE_AUTH_OTP = "com.xiaopeng.accountservice.AUTH_TYPE_AUTH_OTP";
    private static final String PACKAGE_NAME = "com.xiaopeng.caraccount";
    protected static final String TAG = "X_EXAM_AccountViewModel";
    public static final String USER_DATA_EXTRA_UID = "uid";
    private AccountManager mAccountManager = AccountManager.get(App.getInstance());

    /* JADX INFO: Access modifiers changed from: protected */
    public AccountManager getAccountManager() {
        return this.mAccountManager;
    }

    private void login() {
        Intent intent = new Intent();
        intent.setPackage("com.xiaopeng.caraccount");
        intent.setAction(ACTION_REQUEST_QRCODE);
        App.getInstance().startService(intent);
    }

    public Account getCurrentAccountInfo() {
        AccountManager accountManager = this.mAccountManager;
        if (accountManager == null) {
            LogUtils.d(TAG, "mAccountManager = null, you should init first! ");
            return null;
        }
        Account[] accountsByType = accountManager.getAccountsByType("com.xiaopeng.accountservice.ACCOUNT_TYPE_XP_VEHICLE");
        if (accountsByType.length > 0) {
            Account account = accountsByType[0];
            LogUtils.d(TAG, "getCurrentAccountInfo accounts.length=" + accountsByType.length + ";account[0].name=" + account.name, false);
            return account;
        }
        LogUtils.d(TAG, "getCurrentAccountInfo account is empty", false);
        return null;
    }

    public boolean isLogin() {
        if (getCurrentAccountInfo() == null) {
            login();
            return false;
        }
        return true;
    }
}
