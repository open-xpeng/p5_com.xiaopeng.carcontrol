package com.xiaopeng.carcontrol.util;

import com.xiaopeng.carcontrol.CarConfig;
import com.xiaopeng.lib.utils.config.CommonConfig;

/* loaded from: classes2.dex */
public class EnvironmentUtils {
    public static String getAppSecret() {
        if (CommonConfig.HTTP_HOST.startsWith("https://xmart.xiaopeng.com")) {
            return CarConfig.SECRET_PUB;
        }
        if (CommonConfig.HTTP_HOST.startsWith("https://xmart.deploy-test.xiaopeng.com")) {
        }
        return "uJ91amJeLtZeKs4C";
    }
}
