package com.xiaopeng.lib;

/* loaded from: classes2.dex */
public interface HttpInitEventListener {
    public static final int CODE_FAILED_INIT_DEFAULT_KEYSTORE = 6002;
    public static final int CODE_FAILED_INIT_KEYSTORE_SSL_FACTORY = 6003;
    public static final int CODE_FAILED_INIT_KEYSTORE_TRUST_MGR = 6004;
    public static final int CODE_FAILED_INIT_LOCAL_SSL_FACTORY = 6006;
    public static final int CODE_FAILED_INIT_LOCAL_TRUST_MGR = 6005;
    public static final int CODE_FAILED_INIT_XMART_KEYSTORE = 6001;
    public static final int CODE_NOT_INDIV = 6000;

    void onInitException(int i, String str);
}
