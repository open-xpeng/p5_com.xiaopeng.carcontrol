package com.ut.mini;

import android.net.Uri;
import android.text.TextUtils;
import com.alibaba.mtl.log.e.i;
import com.ut.mini.base.UTMIVariables;
import com.ut.mini.internal.UTOriginalCustomHitBuilder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class UTHybridHelper {
    private static UTHybridHelper a = new UTHybridHelper();

    public static UTHybridHelper getInstance() {
        return a;
    }

    public void setH5Url(String str) {
        if (str != null) {
            UTMIVariables.getInstance().setH5Url(str);
        }
    }

    public void h5UT(Map<String, String> map, Object obj) {
        if (map == null || map.size() == 0) {
            i.a("h5UT", "dataMap is empty");
            return;
        }
        String str = map.get("functype");
        if (str == null) {
            i.a("h5UT", "funcType is null");
            return;
        }
        String str2 = map.get("utjstype");
        if (str2 != null && !str2.equals("0") && !str2.equals("1")) {
            i.a("h5UT", "utjstype should be 1 or 0 or null");
            return;
        }
        map.remove("functype");
        Date date = new Date();
        if (str.equals("2001")) {
            a(date, map, obj);
        } else if (str.equals("2101")) {
            a(date, map);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void a(java.util.Date r10, java.util.Map<java.lang.String, java.lang.String> r11, java.lang.Object r12) {
        /*
            r9 = this;
            if (r11 == 0) goto Lbb
            int r10 = r11.size()
            if (r10 != 0) goto La
            goto Lbb
        La:
            java.lang.String r10 = "urlpagename"
            java.lang.Object r10 = r11.get(r10)
            java.lang.String r10 = (java.lang.String) r10
            java.lang.String r0 = "url"
            java.lang.Object r0 = r11.get(r0)
            java.lang.String r0 = (java.lang.String) r0
            java.lang.String r10 = r9.b(r10, r0)
            if (r10 == 0) goto Lb4
            boolean r0 = android.text.TextUtils.isEmpty(r10)
            if (r0 == 0) goto L2a
            goto Lb4
        L2a:
            com.ut.mini.base.UTMIVariables r0 = com.ut.mini.base.UTMIVariables.getInstance()
            java.lang.String r4 = r0.getRefPage()
            r0 = 0
            java.lang.String r1 = "utjstype"
            java.lang.Object r2 = r11.get(r1)
            java.lang.String r2 = (java.lang.String) r2
            r11.remove(r1)
            if (r2 == 0) goto L59
            java.lang.String r1 = "0"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L4a
            goto L59
        L4a:
            java.lang.String r1 = "1"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L57
            java.util.Map r11 = r9.d(r11)
            goto L5d
        L57:
            r7 = r0
            goto L5e
        L59:
            java.util.Map r11 = r9.c(r11)
        L5d:
            r7 = r11
        L5e:
            r11 = 2006(0x7d6, float:2.811E-42)
            com.ut.mini.UTPageHitHelper r0 = com.ut.mini.UTPageHitHelper.getInstance()
            boolean r0 = r0.m85a(r12)
            r8 = 2001(0x7d1, float:2.804E-42)
            if (r0 == 0) goto L6d
            r11 = r8
        L6d:
            com.ut.mini.internal.UTOriginalCustomHitBuilder r0 = new com.ut.mini.internal.UTOriginalCustomHitBuilder
            r5 = 0
            r6 = 0
            r1 = r0
            r2 = r10
            r3 = r11
            r1.<init>(r2, r3, r4, r5, r6, r7)
            if (r8 != r11) goto L80
            com.ut.mini.base.UTMIVariables r11 = com.ut.mini.base.UTMIVariables.getInstance()
            r11.setRefPage(r10)
        L80:
            com.ut.mini.UTPageHitHelper r10 = com.ut.mini.UTPageHitHelper.getInstance()
            java.util.Map r10 = r10.c()
            if (r10 == 0) goto L93
            int r11 = r10.size()
            if (r11 <= 0) goto L93
            r0.setProperties(r10)
        L93:
            com.ut.mini.UTAnalytics r10 = com.ut.mini.UTAnalytics.getInstance()
            com.ut.mini.UTTracker r10 = r10.getDefaultTracker()
            if (r10 == 0) goto La5
            java.util.Map r11 = r0.build()
            r10.send(r11)
            goto Lac
        La5:
            java.lang.String r10 = "h5Page event error"
            java.lang.String r11 = "Fatal Error,must call setRequestAuthentication method first."
            com.alibaba.mtl.log.e.i.a(r10, r11)
        Lac:
            com.ut.mini.UTPageHitHelper r10 = com.ut.mini.UTPageHitHelper.getInstance()
            r10.m84a(r12)
            return
        Lb4:
            java.lang.String r10 = "h5Page"
            java.lang.String r11 = "pageName is null,return"
            com.alibaba.mtl.log.e.i.a(r10, r11)
        Lbb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.UTHybridHelper.a(java.util.Date, java.util.Map, java.lang.Object):void");
    }

    private void a(Date date, Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return;
        }
        String b = b(map.get("urlpagename"), map.get("url"));
        if (b == null || TextUtils.isEmpty(b)) {
            i.a("h5Ctrl", "pageName is null,return");
            return;
        }
        String str = map.get("logkey");
        if (str == null || TextUtils.isEmpty(str)) {
            i.a("h5Ctrl", "logkey is null,return");
            return;
        }
        Map<String, String> map2 = null;
        String str2 = map.get("utjstype");
        map.remove("utjstype");
        if (str2 == null || str2.equals("0")) {
            map2 = e(map);
        } else if (str2.equals("1")) {
            map2 = f(map);
        }
        UTOriginalCustomHitBuilder uTOriginalCustomHitBuilder = new UTOriginalCustomHitBuilder(b, 2101, str, null, null, map2);
        UTTracker defaultTracker = UTAnalytics.getInstance().getDefaultTracker();
        if (defaultTracker != null) {
            defaultTracker.send(uTOriginalCustomHitBuilder.build());
        } else {
            i.a("h5Ctrl event error", "Fatal Error,must call setRequestAuthentication method first.");
        }
    }

    private Map<String, String> c(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        HashMap hashMap = new HashMap();
        String str = map.get("url");
        hashMap.put("_h5url", str == null ? "" : str);
        if (str != null) {
            Uri parse = Uri.parse(str);
            String queryParameter = parse.getQueryParameter("spm");
            if (queryParameter != null && !TextUtils.isEmpty(queryParameter)) {
                hashMap.put("spm", queryParameter);
            } else {
                hashMap.put("spm", "0.0.0.0");
            }
            String queryParameter2 = parse.getQueryParameter("scm");
            if (queryParameter2 != null && !TextUtils.isEmpty(queryParameter2)) {
                hashMap.put("scm", queryParameter2);
            }
        } else {
            hashMap.put("spm", "0.0.0.0");
        }
        String str2 = map.get("spmcnt");
        if (str2 == null) {
            str2 = "";
        }
        hashMap.put("_spmcnt", str2);
        String str3 = map.get("spmpre");
        if (str3 == null) {
            str3 = "";
        }
        hashMap.put("_spmpre", str3);
        String str4 = map.get("lzsid");
        if (str4 == null) {
            str4 = "";
        }
        hashMap.put("_lzsid", str4);
        String str5 = map.get("extendargs");
        if (str5 == null) {
            str5 = "";
        }
        hashMap.put("_h5ea", str5);
        String str6 = map.get("cna");
        hashMap.put("_cna", str6 != null ? str6 : "");
        hashMap.put("_ish5", "1");
        return hashMap;
    }

    private Map<String, String> d(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        HashMap hashMap = new HashMap();
        String str = map.get("url");
        if (str == null) {
            str = "";
        }
        hashMap.put("_h5url", str);
        String str2 = map.get("extendargs");
        hashMap.put("_h5ea", str2 != null ? str2 : "");
        hashMap.put("_ish5", "1");
        return hashMap;
    }

    private Map<String, String> e(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        HashMap hashMap = new HashMap();
        String str = map.get("logkeyargs");
        if (str == null) {
            str = "";
        }
        hashMap.put("_lka", str);
        String str2 = map.get("cna");
        if (str2 == null) {
            str2 = "";
        }
        hashMap.put("_cna", str2);
        String str3 = map.get("extendargs");
        hashMap.put("_h5ea", str3 != null ? str3 : "");
        hashMap.put("_ish5", "1");
        return hashMap;
    }

    private Map<String, String> f(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        HashMap hashMap = new HashMap();
        String str = map.get("extendargs");
        if (str == null) {
            str = "";
        }
        hashMap.put("_h5ea", str);
        hashMap.put("_ish5", "1");
        return hashMap;
    }

    private String b(String str, String str2) {
        if (str == null || TextUtils.isEmpty(str)) {
            if (TextUtils.isEmpty(str2)) {
                return "";
            }
            int indexOf = str2.indexOf("?");
            return indexOf == -1 ? str2 : str2.substring(0, indexOf);
        }
        return str;
    }
}
