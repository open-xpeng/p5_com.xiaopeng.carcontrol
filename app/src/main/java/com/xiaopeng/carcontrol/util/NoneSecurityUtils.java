package com.xiaopeng.carcontrol.util;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpHeaders;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest;
import com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter.GetRequestAdapter;
import com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter.PostRequestAdapter;
import com.xiaopeng.lib.framework.netchannelmodule.http.xmart.bizapi.BizConstants;
import com.xiaopeng.lib.security.SecurityCommon;
import com.xiaopeng.lib.utils.MD5Utils;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import java.io.UnsupportedEncodingException;
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
public class NoneSecurityUtils {
    private static final String OTP = "otp";
    private static final String TAG = "NoneSecurityUtils";
    private static final List<String> AUTH_EXCLUDE = Arrays.asList(BizConstants.HEADER_SIGNATURE, BizConstants.HEADER_AUTHORIZATION);
    private static final List<String> SIGN_EXCLUDE = Arrays.asList(BizConstants.HEADER_SIGNATURE);

    public static String getAuthorization(IRequest request, String method, String body, String key, String otp) {
        String generateSignature = generateSignature(request, method, body, key, AUTH_EXCLUDE);
        HashMap hashMap = new HashMap(1);
        hashMap.put(BizConstants.AUTHORIZATION_XPSIGN, generateSignature);
        hashMap.put(OTP, otp);
        byte[] bArr = new byte[0];
        try {
            bArr = new Gson().toJson(hashMap).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return buildTokenData(otp, bArr);
    }

    public static String getSignature(IRequest request, String method, String body, String key) {
        return generateSignature(request, method, body, key, SIGN_EXCLUDE);
    }

    private static String generateSignature(IRequest request, String method, String body, String key, List<String> excludeSet) {
        HttpHeaders requestHeaders = getRequestHeaders(request);
        HashMap hashMap = new HashMap();
        if (requestHeaders != null) {
            hashMap.putAll(requestHeaders.headersMap);
        }
        for (String str : excludeSet) {
            hashMap.remove(str);
        }
        String signParameters = getSignParameters(hashMap, request.getUrl());
        try {
            String md5 = TextUtils.isEmpty(body) ? "" : MD5Utils.getMD5(body);
            Mac mac = Mac.getInstance("HmacSHA256");
            byte[] bytes = key.getBytes("UTF-8");
            mac.init(new SecretKeySpec(bytes, 0, bytes.length, "HmacSHA256"));
            return SecurityCommon.parseByte2HexStr(mac.doFinal((method + '&' + signParameters + '&' + md5).getBytes("UTF-8"))).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String buildTokenData(String otp, byte[] data) {
        try {
            com.xiaopeng.lib.utils.LogUtils.d(TAG, "xp-sign:" + new String(data));
            return new String(Base64.encode(data, 2), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static HttpHeaders getRequestHeaders(IRequest request) {
        if (request instanceof GetRequestAdapter) {
            return ((GetRequestAdapter) request).getHeaders();
        }
        if (request instanceof PostRequestAdapter) {
            return ((PostRequestAdapter) request).getHeaders();
        }
        return null;
    }

    private static String getSignParameters(Map<String, String> headMap, String url) {
        String queryParameter;
        ArrayList arrayList = new ArrayList(headMap.keySet());
        Uri parse = Uri.parse(url);
        Set<String> queryParameterNames = parse.getQueryParameterNames();
        arrayList.addAll(queryParameterNames);
        Collections.sort(arrayList);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrayList.size(); i++) {
            String str = (String) arrayList.get(i);
            if (queryParameterNames.contains(str) || str.startsWith(BizConstants.HEADER_PREFIX)) {
                if (headMap.containsKey(str)) {
                    if (BuildInfoUtils.isEngVersion() && !TextUtils.isEmpty(parse.getQueryParameter(str))) {
                        throw new RuntimeException("Duplicate keys in header and parameters!");
                    }
                    queryParameter = headMap.get(str);
                    str = str.toUpperCase();
                } else {
                    queryParameter = parse.getQueryParameter(str);
                }
                if (!TextUtils.isEmpty(queryParameter)) {
                    if (sb.length() > 0) {
                        sb.append('&');
                    }
                    sb.append(str + "=" + queryParameter);
                }
            }
        }
        return sb.toString();
    }
}
