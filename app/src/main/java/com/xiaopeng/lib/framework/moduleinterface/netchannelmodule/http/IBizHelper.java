package com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http;

import java.util.Map;

/* loaded from: classes2.dex */
public interface IBizHelper {
    IBizHelper appId(String str);

    IRequest build();

    IRequest buildWithSecretKey(String str);

    IBizHelper customTokensForAuth(String[] strArr);

    @Deprecated
    IBizHelper enableIrdetoEncoding();

    IBizHelper enableSecurityEncoding();

    IBizHelper extendBizHeader(String str, String str2);

    IBizHelper get(String str);

    IBizHelper needAuthorizationInfo();

    IBizHelper needAuthorizationInfo(Map<String, String> map);

    IBizHelper post(String str, String str2);

    IBizHelper uid(String str);
}
