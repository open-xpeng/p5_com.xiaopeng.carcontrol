package com.alibaba.mtl.log.e;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.mtl.appmonitor.SdkMeta;
import com.alibaba.mtl.log.model.LogField;
import com.alibaba.mtl.log.sign.BaseRequestAuth;
import com.alibaba.mtl.log.sign.IRequestAuth;
import com.alibaba.mtl.log.sign.SecurityRequestAuth;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* compiled from: UrlWrapper.java */
/* loaded from: classes.dex */
public class t {
    private static final String TAG = "t";

    public static String a(String str, Map<String, Object> map, Map<String, Object> map2) throws Exception {
        String a;
        String str2;
        String str3 = "";
        if (map2 != null && map2.size() > 0) {
            Set<String> keySet = map2.keySet();
            String[] strArr = new String[keySet.size()];
            keySet.toArray(strArr);
            for (String str4 : g.a().a(strArr, true)) {
                str3 = str3 + str4 + j.b((byte[]) map2.get(str4));
            }
        }
        try {
            a = a(str, null, null, str3);
        } catch (Throwable unused) {
            a = a(com.alibaba.mtl.log.a.a.g(), null, null, str3);
        }
        return !TextUtils.isEmpty(com.alibaba.mtl.log.a.a.N) ? a + "&dk=" + URLEncoder.encode(str2, "UTF-8") : a;
    }

    public static String b(String str, Map<String, Object> map, Map<String, Object> map2) throws Exception {
        if (map == null) {
            new HashMap();
        }
        Context context = com.alibaba.mtl.log.a.getContext();
        String appkey = b.getAppkey();
        String o = b.o();
        if (o == null) {
            o = "";
        }
        String str2 = d.a(context).get(LogField.APPVERSION.toString());
        String str3 = d.a(context).get(LogField.OS.toString());
        String str4 = d.a(context).get(LogField.UTDID.toString());
        String valueOf = String.valueOf(System.currentTimeMillis());
        IRequestAuth a = com.alibaba.mtl.log.a.a();
        String str5 = a instanceof SecurityRequestAuth ? "1" : "0";
        String sign = a.getSign(j.b((appkey + str2 + o + str3 + str4 + SdkMeta.SDK_VERSION + valueOf + str5 + map.get("_b01n15") + map.get("_b01na")).getBytes()));
        StringBuilder sb = new StringBuilder(str);
        sb.append("?");
        sb.append("ak").append("=").append(appkey);
        sb.append("&").append("av").append("=").append(str2);
        sb.append("&").append(QuickSettingConstants.SUFFIX).append("=").append(URLEncoder.encode(o));
        sb.append("&").append("d").append("=").append(str4);
        sb.append("&").append("sv").append("=").append(SdkMeta.SDK_VERSION);
        sb.append("&").append(TAG).append("=").append(valueOf);
        sb.append("&").append("is").append("=").append(str5);
        sb.append("&").append("_b01n15").append("=").append(map.get("_b01n15"));
        sb.append("&").append("_b01na").append("=").append(map.get("_b01na"));
        sb.append("&").append("s").append("=").append(sign);
        return sb.toString();
    }

    private static String a(String str, String str2, String str3, String str4) throws Exception {
        String str5;
        Context context = com.alibaba.mtl.log.a.getContext();
        String appkey = b.getAppkey();
        String o = b.o();
        if (o == null) {
            o = "";
        }
        String str6 = d.a(context).get(LogField.APPVERSION.toString());
        String str7 = d.a(context).get(LogField.OS.toString());
        String str8 = d.a(context).get(LogField.UTDID.toString());
        String valueOf = String.valueOf(System.currentTimeMillis());
        IRequestAuth a = com.alibaba.mtl.log.a.a();
        str5 = "1";
        String str9 = "0";
        if (!(a instanceof SecurityRequestAuth)) {
            if (a instanceof BaseRequestAuth) {
                str9 = ((BaseRequestAuth) a).isEncode() ? "1" : "0";
                str5 = "0";
            } else {
                str5 = "0";
            }
        }
        return String.format("%s?%sak=%s&av=%s&c=%s&v=%s&s=%s&d=%s&sv=%s&p=%s&t=%s&u=%s&is=%s&k=%s", str, !TextUtils.isEmpty(str2) ? str2 + "&" : "", e(appkey), e(str6), e(o), e("3.0"), e(a.getSign(j.b((appkey + o + str6 + str7 + SdkMeta.SDK_VERSION + str8 + valueOf + "3.0" + str5 + (str3 == null ? "" : str3) + (str4 == null ? "" : str4)).getBytes()))), e(str8), SdkMeta.SDK_VERSION, str7, valueOf, "", str5, str9);
    }

    private static String e(String str) {
        if (str == null) {
            return "";
        }
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return str;
        }
    }
}
