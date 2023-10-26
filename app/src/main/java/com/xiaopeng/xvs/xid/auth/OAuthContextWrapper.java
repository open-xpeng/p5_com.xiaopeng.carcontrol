package com.xiaopeng.xvs.xid.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import com.xiaopeng.xvs.xid.XId;
import com.xiaopeng.xvs.xid.auth.api.OAuthResponse;
import com.xiaopeng.xvs.xid.base.AbsException;
import com.xiaopeng.xvs.xid.base.ICallback;
import com.xiaopeng.xvs.xid.base.IException;
import com.xiaopeng.xvs.xid.base.IResponse;
import com.xiaopeng.xvs.xid.base.IWrapper;
import com.xiaopeng.xvs.xid.utils.L;
import java.io.IOException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public final class OAuthContextWrapper implements IWrapper {
    private static final String TAG = "OAuthContextWrapper";
    private AccountManager mAccountManager;
    private String mAppId;

    /* JADX INFO: Access modifiers changed from: package-private */
    public OAuthContextWrapper(String str, AccountManager accountManager) {
        this.mAccountManager = accountManager;
        this.mAppId = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public IResponse getAuthToken(Account account, String str) {
        L.d(TAG, "getAuthToken account.name=" + account.name + ";authTokenType=" + str);
        Bundle bundle = new Bundle();
        bundle.putString("app_id", this.mAppId);
        return parseToken(this.mAccountManager.getAuthToken(account, str, bundle, false, (AccountManagerCallback<Bundle>) null, (Handler) null));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void getAuthToken(Account account, String str, final ICallback<String, IException> iCallback, Handler handler) {
        L.d(TAG, "getAuthToken account.name=" + account.name + ";authTokenType=" + str);
        Bundle bundle = new Bundle();
        bundle.putString("app_id", XId.getAppId());
        this.mAccountManager.getAuthToken(account, str, bundle, false, new AccountManagerCallback() { // from class: com.xiaopeng.xvs.xid.auth.-$$Lambda$OAuthContextWrapper$BoSKu2xCrD397tgMkQGnx7pl250
            @Override // android.accounts.AccountManagerCallback
            public final void run(AccountManagerFuture accountManagerFuture) {
                OAuthContextWrapper.this.lambda$getAuthToken$0$OAuthContextWrapper(iCallback, accountManagerFuture);
            }
        }, handler);
    }

    public /* synthetic */ void lambda$getAuthToken$0$OAuthContextWrapper(ICallback iCallback, AccountManagerFuture accountManagerFuture) {
        IResponse parseToken = parseToken(accountManagerFuture);
        L.d(TAG, "getAuthToken response=" + parseToken.toString());
        if (parseToken.getCode() == 0) {
            iCallback.onSuccess(parseToken.getContent());
        } else {
            iCallback.onFail(parseToken);
        }
    }

    private IResponse parseToken(AccountManagerFuture<Bundle> accountManagerFuture) {
        try {
            String string = accountManagerFuture.getResult().getString("authtoken");
            String string2 = accountManagerFuture.getResult().getString("errorMessage", "");
            int i = accountManagerFuture.getResult().getInt("errorCode", 0);
            if (!TextUtils.isEmpty(string)) {
                return new OAuthResponse(string);
            }
            L.d(TAG, "parseToken errCode=" + i + ";errMsg=" + string2);
            return new OAuthResponse(i);
        } catch (AuthenticatorException | OperationCanceledException | IOException | NumberFormatException e) {
            L.d(TAG, "parseToken exception e=" + e.getMessage());
            return new OAuthResponse(AbsException.ERROR_CODE_COMMON_SERVER_UNKNOWN, e.getMessage());
        }
    }
}
