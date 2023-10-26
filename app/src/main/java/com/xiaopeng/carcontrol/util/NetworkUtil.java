package com.xiaopeng.carcontrol.util;

import android.app.Application;
import com.xiaopeng.carcontrol.CarConfig;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.lib.framework.module.IModuleEntry;
import com.xiaopeng.lib.framework.module.Module;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IHttp;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IResponse;
import com.xiaopeng.lib.framework.netchannelmodule.NetworkChannelsEntry;
import com.xiaopeng.lib.framework.netchannelmodule.common.TrafficeStaFlagInterceptor;
import com.xiaopeng.lib.http.HttpsUtils;
import java.util.Optional;
import java.util.function.Function;

/* loaded from: classes2.dex */
public class NetworkUtil {
    private static final String TAG = "NetworkUtil";

    public static void init(Application application) {
        try {
            HttpsUtils.init(application, true);
            Module.register(NetworkChannelsEntry.class, new NetworkChannelsEntry());
            configHttp(application);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, " NetworkUtil init exception");
        }
    }

    private static IHttp configHttp(Application context) {
        IHttp iHttp = (IHttp) Module.get(NetworkChannelsEntry.class).get(IHttp.class);
        iHttp.config().addInterceptor(new TrafficeStaFlagInterceptor()).retryCount(2).applicationContext(context).enableTrafficStats().apply();
        return iHttp;
    }

    private static IHttp getHttp() {
        return (IHttp) Optional.ofNullable(Module.get(NetworkChannelsEntry.class)).map(new Function() { // from class: com.xiaopeng.carcontrol.util.-$$Lambda$NetworkUtil$YMvrd1BJbhyzh6GSrnYXiyuCiEM
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return NetworkUtil.lambda$getHttp$0((IModuleEntry) obj);
            }
        }).orElse(null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ IHttp lambda$getHttp$0(IModuleEntry o) {
        return (IHttp) o.get(IHttp.class);
    }

    public static void doPost(int service, String jsonStr, final Callback callback) {
        String url = getUrl(service);
        IHttp http = getHttp();
        if (http == null) {
            LogUtils.e(TAG, "doPost getHttp is null");
            return;
        }
        LogUtils.d(TAG, " doPost URL=" + url);
        http.bizHelper().post(url, jsonStr).appId("xp_car_setting_car").enableSecurityEncoding().needAuthorizationInfo().buildWithSecretKey(CarConfig.SECRET_PUB).execute(new Callback() { // from class: com.xiaopeng.carcontrol.util.NetworkUtil.1
            @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
            public void onSuccess(IResponse iResponse) {
                LogUtils.d(NetworkUtil.TAG, "onSuccess response=" + iResponse.body());
                Callback callback2 = Callback.this;
                if (callback2 != null) {
                    callback2.onSuccess(iResponse);
                }
            }

            @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.Callback
            public void onFailure(IResponse iResponse) {
                LogUtils.d(NetworkUtil.TAG, "onFailure response=" + iResponse.body());
                Callback callback2 = Callback.this;
                if (callback2 != null) {
                    callback2.onFailure(iResponse);
                }
            }
        });
    }

    private static String getUrl(int service) {
        return service == 16 ? getSelfCheckHost() : "";
    }

    private static String getSelfCheckHost() {
        int env = FunctionModel.getInstance().getEnv();
        return env != 1 ? env != 3 ? HttpConst.XP_SELFCHECK_PRE : HttpConst.XP_SELFCHECK_PRODUCT : HttpConst.XP_SELFCHECK_TEST;
    }
}
