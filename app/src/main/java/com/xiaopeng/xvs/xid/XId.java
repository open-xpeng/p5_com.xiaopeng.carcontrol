package com.xiaopeng.xvs.xid;

import android.accounts.AccountManager;
import android.app.Application;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.xiaopeng.xvs.xid.account.AccountApi;
import com.xiaopeng.xvs.xid.account.api.IAccount;
import com.xiaopeng.xvs.xid.auth.OAuthApi;
import com.xiaopeng.xvs.xid.auth.api.IOAuth;
import com.xiaopeng.xvs.xid.config.api.IRemoteConfig;
import com.xiaopeng.xvs.xid.sync.SyncApi;
import com.xiaopeng.xvs.xid.sync.api.ISync;
import com.xiaopeng.xvs.xid.utils.L;
import com.xiaopeng.xvs.xid.utils.XIdSPUtils;

/* loaded from: classes2.dex */
public final class XId {
    public static final String APP_ID_AI_ASSISTANT = "xp_voice_assistant_car";
    public static final String APP_ID_CAR_ACCOUNT = "xp_car_cashier";
    public static final String APP_ID_CAR_CAMERA = "xp_car_camera_car";
    public static final String APP_ID_CAR_CONTROL = "xp_car_setting_car";
    public static final String APP_ID_CAR_DEMO = "xp_car_sync_demo";
    public static final String APP_ID_CAR_DVR = "xp_car_dvr_car";
    public static final String APP_ID_CAR_SETTINGS = "xp_car_setting_syn";
    public static final String APP_ID_CAR_SETTINGS_D21 = "xp_system_setting_car";
    private static final String TAG = "XId";
    private static AccountManager sAccountManager;
    private static String sAppId;
    private static String sAppSecret;
    private static Application sApplication;
    private static String sCarType;

    public static IRemoteConfig getRemoteConfigApi() {
        return null;
    }

    public static String getVIN() {
        String str = SystemProperties.get("persist.sys.xiaopeng.vin", "");
        return TextUtils.isEmpty(str) ? SystemProperties.get("sys.xiaopeng.vin", "") : str;
    }

    public static void init(Application application, String str, String str2, String str3) {
        L.d(TAG, "init: appId = [" + str + "], secret = [" + str2 + "], carType = [" + str3 + "]");
        sAppId = str;
        sAppSecret = str2;
        sApplication = application;
        sAccountManager = AccountManager.get(application);
        L.d(TAG, "appId=" + str);
        sCarType = str3;
        XIdSPUtils.getInstance().init(application);
    }

    public static Application getApplication() {
        Application application = sApplication;
        if (application != null) {
            return application;
        }
        throw new RuntimeException("XId must be call XId.init()!");
    }

    public static String getAppId() {
        if (TextUtils.isEmpty(sAppId)) {
            throw new RuntimeException("XId must be call XId.init()!");
        }
        return sAppId;
    }

    public static String getAppSecret() {
        if (TextUtils.isEmpty(sAppSecret)) {
            throw new RuntimeException("XId must be call XId.init()!");
        }
        return sAppSecret;
    }

    public static String getCarType() {
        if (TextUtils.isEmpty(sCarType)) {
            throw new RuntimeException("XId must be call XId.init()!");
        }
        return sCarType;
    }

    public static AccountManager getAccountManager() {
        AccountManager accountManager = sAccountManager;
        if (accountManager != null) {
            return accountManager;
        }
        throw new RuntimeException("XId must be call XId.init()!");
    }

    public static IAccount getAccountApi() {
        return AccountApi.getApi();
    }

    public static ISync getSyncApi() {
        return SyncApi.getApi();
    }

    public static IOAuth getOAuthApi() {
        return OAuthApi.getApi();
    }
}
