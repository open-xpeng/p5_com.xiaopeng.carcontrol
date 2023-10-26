package com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage;

import android.app.Application;
import java.util.Map;

/* loaded from: classes2.dex */
public interface IRemoteStorage {

    /* loaded from: classes2.dex */
    public enum CATEGORY {
        CDU,
        CAN
    }

    void appendWithPathAndCallback(String str, String str2, byte[] bArr, Callback callback) throws Exception;

    void downloadWithPathAndCallback(String str, String str2, String str3, Callback callback) throws Exception;

    @Deprecated
    void initWithCategoryAndContext(Application application) throws Exception;

    void initWithContext(Application application) throws Exception;

    void uploadWithCallback(CATEGORY category, String str, String str2, Callback callback) throws Exception;

    void uploadWithCallback(CATEGORY category, String str, String str2, Callback callback, Map<String, String> map) throws Exception;

    void uploadWithPathAndCallback(String str, String str2, String str3, Callback callback) throws Exception;

    void uploadWithPathAndCallback(String str, String str2, String str3, Callback callback, Map<String, String> map) throws Exception;
}
