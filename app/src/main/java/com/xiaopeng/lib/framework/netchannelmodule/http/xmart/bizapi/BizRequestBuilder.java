package com.xiaopeng.lib.framework.netchannelmodule.http.xmart.bizapi;

import android.net.Uri;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpHeaders;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest;
import com.xiaopeng.lib.framework.netchannelmodule.common.GlobalConfig;
import com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter.GetRequestAdapter;
import com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter.PostRequestAdapter;
import com.xiaopeng.lib.framework.netchannelmodule.http.xmart.bizapi.BizConstants;
import com.xiaopeng.lib.http.IrdetoUtils;
import com.xiaopeng.lib.http.Security;
import com.xiaopeng.lib.utils.LogUtils;
import com.xiaopeng.lib.utils.MD5Utils;
import com.xiaopeng.lib.utils.NetUtils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.lib.utils.info.DeviceInfoUtils;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public class BizRequestBuilder {
    private static final String EMPTY_BODY = "";
    private static final String NETWORK_TYPE_2G = "2G";
    private static final String NETWORK_TYPE_3G = "3G";
    private static final String NETWORK_TYPE_4G = "4G";
    private static final String NETWORK_TYPE_UNKNOWN = "UNKNOWN";
    private static final String NETWORK_TYPE_WIFI = "WIFI";
    private static final String TAG = "BizRequestBuilder";
    private static final List<String> sBasicBizHeaders = Arrays.asList("XP-Appid", BizConstants.HEADER_CLIENT, BizConstants.HEADER_CLIENT_ENCODING, BizConstants.HEADER_CLIENT_TYPE, BizConstants.HEADER_ENCRYPTION_TYPE, BizConstants.HEADER_NONCE, BizConstants.HEADER_UID, BizConstants.HEADER_AUTHORIZATION);
    private static Gson sGson = new Gson();
    private String mAppId;
    private String mBody;
    private int mEncryptionType;
    private Map<String, String> mExtAuthorizationInfo;
    private BizConstants.METHOD mMethod;
    private boolean mNeedAuthorizationInfo;
    private IRequest mRequest;
    private String mSecretKey;
    private String[] mTokensForAuthorization;
    private String mUid;

    private BizRequestBuilder() {
        this.mUid = null;
        this.mAppId = BizConstants.APPID_CDU;
        this.mBody = "";
        this.mSecretKey = null;
        this.mNeedAuthorizationInfo = false;
        this.mTokensForAuthorization = Security.TOKEN_ALL;
        this.mExtAuthorizationInfo = null;
    }

    public BizRequestBuilder(IRequest iRequest, BizConstants.METHOD method) {
        this.mUid = null;
        this.mAppId = BizConstants.APPID_CDU;
        this.mBody = "";
        this.mSecretKey = null;
        this.mNeedAuthorizationInfo = false;
        this.mTokensForAuthorization = Security.TOKEN_ALL;
        this.mExtAuthorizationInfo = null;
        this.mRequest = iRequest;
        this.mMethod = method;
        this.mEncryptionType = 0;
    }

    public BizRequestBuilder body(String str) {
        this.mBody = str;
        return this;
    }

    public BizRequestBuilder enableIrdetoEncoding() {
        this.mEncryptionType = 1;
        return this;
    }

    public BizRequestBuilder enableSecurityEncoding() {
        this.mEncryptionType = Security.getActiveEncryptionType().getValue();
        return this;
    }

    public BizRequestBuilder needAuthorizationInfo(Map<String, String> map) {
        this.mNeedAuthorizationInfo = true;
        this.mExtAuthorizationInfo = map;
        return this;
    }

    public BizRequestBuilder appId(String str) {
        this.mAppId = str;
        return this;
    }

    public BizRequestBuilder uid(String str) {
        this.mUid = str;
        return this;
    }

    public BizRequestBuilder setExtHeader(String str, String str2) {
        if (str.startsWith(BizConstants.HEADER_PREFIX)) {
            this.mRequest.headers(str, str2);
        }
        return this;
    }

    public BizRequestBuilder customTokensForAuth(String[] strArr) {
        this.mTokensForAuthorization = strArr;
        return this;
    }

    public IRequest build(String str) {
        if (str != null) {
            this.mSecretKey = str;
        } else {
            this.mSecretKey = BizConstants.CAR_SECRET;
        }
        for (String str2 : sBasicBizHeaders) {
            String bizHeaderValue = bizHeaderValue(str2);
            if (bizHeaderValue != null) {
                this.mRequest.headers(str2, bizHeaderValue);
            }
        }
        this.mRequest.headers(BizConstants.HEADER_SIGNATURE, generateSignature());
        if (this.mMethod == BizConstants.METHOD.POST) {
            if (TextUtils.isEmpty(this.mBody)) {
                this.mBody = "{}";
            }
            this.mRequest.headers("Content-Type", "application/json");
            this.mRequest.uploadJson(this.mBody);
        }
        return this.mRequest;
    }

    private String getAuthorization() {
        if (this.mNeedAuthorizationInfo) {
            String generateSignature = generateSignature();
            HashMap hashMap = new HashMap(1);
            hashMap.put(BizConstants.AUTHORIZATION_XPSIGN, generateSignature);
            Map<String, String> map = this.mExtAuthorizationInfo;
            if (map != null) {
                for (String str : map.keySet()) {
                    hashMap.put(str, this.mExtAuthorizationInfo.get(str));
                }
            }
            try {
                if (this.mEncryptionType == 1) {
                    return IrdetoUtils.buildTokenData(this.mTokensForAuthorization, sGson.toJson(hashMap).getBytes());
                }
                return Security.buildTokenData(this.mTokensForAuthorization, sGson.toJson(hashMap).getBytes());
            } catch (NullPointerException e) {
                LogUtils.e(TAG, "getAuthorization fail : " + e);
                return null;
            }
        }
        return null;
    }

    private static String getSignParameters(HttpHeaders httpHeaders, String str) {
        String queryParameter;
        ArrayList arrayList = new ArrayList(httpHeaders.headersMap.keySet());
        Uri parse = Uri.parse(str);
        Set<String> queryParameterNames = parse.getQueryParameterNames();
        arrayList.addAll(queryParameterNames);
        Collections.sort(arrayList);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrayList.size(); i++) {
            String str2 = (String) arrayList.get(i);
            if (queryParameterNames.contains(str2) || str2.startsWith(BizConstants.HEADER_PREFIX)) {
                if (httpHeaders.headersMap.containsKey(str2)) {
                    if (BuildInfoUtils.isEngVersion() && !TextUtils.isEmpty(parse.getQueryParameter(str2))) {
                        throw new RuntimeException("Duplicate keys in header and parameters!");
                    }
                    queryParameter = httpHeaders.headersMap.get(str2);
                    str2 = str2.toUpperCase();
                } else {
                    queryParameter = parse.getQueryParameter(str2);
                }
                if (!TextUtils.isEmpty(queryParameter)) {
                    if (sb.length() > 0) {
                        sb.append('&');
                    }
                    sb.append(str2 + "=" + queryParameter);
                }
            }
        }
        return sb.toString();
    }

    private String bizHeaderValue(String str) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1913874489:
                if (str.equals("XP-Appid")) {
                    c = 0;
                    break;
                }
                break;
            case -1901900614:
                if (str.equals(BizConstants.HEADER_NONCE)) {
                    c = 1;
                    break;
                }
                break;
            case -1700294693:
                if (str.equals(BizConstants.HEADER_UID)) {
                    c = 2;
                    break;
                }
                break;
            case -576067417:
                if (str.equals(BizConstants.HEADER_CLIENT_TYPE)) {
                    c = 3;
                    break;
                }
                break;
            case 366551663:
                if (str.equals(BizConstants.HEADER_ENCRYPTION_TYPE)) {
                    c = 4;
                    break;
                }
                break;
            case 800782916:
                if (str.equals(BizConstants.HEADER_AUTHORIZATION)) {
                    c = 5;
                    break;
                }
                break;
            case 852785248:
                if (str.equals(BizConstants.HEADER_CLIENT)) {
                    c = 6;
                    break;
                }
                break;
            case 1042155584:
                if (str.equals(BizConstants.HEADER_CLIENT_ENCODING)) {
                    c = 7;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return this.mAppId;
            case 1:
                return String.valueOf(System.currentTimeMillis());
            case 2:
                return this.mUid;
            case 3:
                return BizConstants.CLIENT_TYPE_CDU;
            case 4:
                return String.valueOf(this.mEncryptionType);
            case 5:
                return getAuthorization();
            case 6:
                return generateXpClient();
            case 7:
                return BizConstants.CLIENT_ENCODING_NONE;
            default:
                return null;
        }
    }

    private String generateXpClient() {
        String productModel = DeviceInfoUtils.getProductModel();
        String replaceAll = TextUtils.isEmpty(productModel) ? "" : productModel.toUpperCase().replaceAll(" ", "");
        StringBuilder sb = new StringBuilder();
        sb.append("di=" + getHardwareId() + ";");
        sb.append("pn=" + GlobalConfig.getApplicationName() + ";");
        sb.append("ve=" + BuildInfoUtils.getSystemVersion() + ";");
        sb.append("br=Xiaopeng;");
        sb.append("mo=" + replaceAll + ";");
        sb.append("st=1;");
        sb.append("sv=" + BuildInfoUtils.getSystemVersion() + ";");
        sb.append("nt=" + getNetworkType() + ";");
        sb.append("t=" + System.currentTimeMillis() + ";");
        if (DeviceInfoUtils.isInternationalVer()) {
            sb.append("ln=en;");
        }
        return sb.toString();
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() < 2) {
                sb.append(0);
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    private String generateSignature() {
        String value = this.mMethod.getValue();
        String signParameters = getSignParameters(getRequestHeaders(), this.mRequest.getUrl());
        try {
            String md5 = TextUtils.isEmpty(this.mBody) ? "" : MD5Utils.getMD5(this.mBody);
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] bytes = this.mSecretKey.getBytes("UTF-8");
            mac.init(new SecretKeySpec(bytes, 0, bytes.length, "HmacSHA256"));
            LogUtils.d(TAG, "generateSignature origin:" + (value + '&' + signParameters + '&' + md5));
            byte[] doFinal = mac.doFinal((value + '&' + signParameters + '&' + md5).getBytes("UTF-8"));
            if (this.mEncryptionType == 0) {
                return bytesToHexString(doFinal);
            }
            return Security.parseByte2HexStr(doFinal).toLowerCase();
        } catch (UnsupportedEncodingException | InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private HttpHeaders getRequestHeaders() {
        IRequest iRequest = this.mRequest;
        if (iRequest instanceof GetRequestAdapter) {
            return ((GetRequestAdapter) iRequest).getHeaders();
        }
        if (iRequest instanceof PostRequestAdapter) {
            return ((PostRequestAdapter) iRequest).getHeaders();
        }
        return null;
    }

    private String getNetworkType() {
        int networkType = NetUtils.getNetworkType(GlobalConfig.getApplicationContext());
        return networkType != 1 ? networkType != 2 ? networkType != 3 ? networkType != 10 ? NETWORK_TYPE_UNKNOWN : NETWORK_TYPE_WIFI : NETWORK_TYPE_4G : NETWORK_TYPE_3G : NETWORK_TYPE_2G;
    }

    private String getHardwareId() {
        String hardwareId = BuildInfoUtils.getHardwareId();
        if (BuildInfoUtils.isEngVersion() && TextUtils.isEmpty(hardwareId)) {
            throw new RuntimeException("Invalid hardware ID.");
        }
        return hardwareId;
    }
}
