package com.alibaba.sdk.android.httpdns;

import android.content.Context;
import android.text.TextUtils;
import com.alibaba.sdk.android.httpdns.probe.IPProbeItem;
import com.alibaba.sdk.android.utils.AMSConfigUtils;
import com.alibaba.sdk.android.utils.AMSDevReporter;
import com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class HttpDns implements HttpDnsService {
    private boolean isExpiredIPEnabled = false;
    private static d hostManager = d.a();
    private static DegradationFilter degradationFilter = null;
    static HttpDns instance = null;
    private static boolean inited = false;
    private static String sAccountId = null;
    private static String sSecretKey = null;
    private static Context sContext = null;

    private HttpDns(Context context, String str) {
        com.alibaba.sdk.android.httpdns.a.a.a().a(context, str);
        com.alibaba.sdk.android.httpdns.a.a.a().a(com.alibaba.sdk.android.httpdns.d.a.a(context));
    }

    private static String getAccountId() {
        if (TextUtils.isEmpty(sAccountId)) {
            String accountId = AMSConfigUtils.getAccountId(sContext);
            sAccountId = accountId;
            return accountId;
        }
        return sAccountId;
    }

    private String getIpByHost(String str) {
        if (!b.a()) {
            i.f("HttpDns service turned off");
            return null;
        }
        String[] ipsByHost = getIpsByHost(str);
        if (ipsByHost != null && ipsByHost.length > 0) {
            return ipsByHost[0];
        }
        return null;
    }

    private String[] getIpsByHost(String str) {
        if (!b.a()) {
            i.f("HttpDns service turned off");
        } else if (!k.b(str)) {
            return f.d;
        } else {
            if (k.c(str)) {
                return new String[]{str};
            }
            DegradationFilter degradationFilter2 = degradationFilter;
            if (degradationFilter2 != null && degradationFilter2.shouldDegradeHttpDNS(str)) {
                return f.d;
            }
            if (t.e()) {
                return getIpsByHostAsync(str);
            }
            e m43a = hostManager.m43a(str);
            if (m43a != null && m43a.m52b() && this.isExpiredIPEnabled) {
                if (!hostManager.m48a(str)) {
                    i.d("refresh host async: " + str);
                    c.a().submit(new m(str, o.QUERY_HOST));
                }
                return m43a.m51a();
            } else if (m43a != null && !m43a.m52b()) {
                return m43a.m51a();
            } else {
                i.d("refresh host sync: " + str);
                try {
                    return (String[]) c.a().submit(new m(str, o.QUERY_HOST)).get();
                } catch (Exception e) {
                    i.a(e);
                }
            }
        }
        return f.d;
    }

    private static String getSecretKey() {
        if (TextUtils.isEmpty(sSecretKey)) {
            String httpdnsSecretKey = AMSConfigUtils.getHttpdnsSecretKey(sContext);
            sSecretKey = httpdnsSecretKey;
            return httpdnsSecretKey;
        }
        return sSecretKey;
    }

    public static synchronized HttpDnsService getService(Context context) {
        HttpDns httpDns;
        synchronized (HttpDns.class) {
            if (instance == null && context != null) {
                Context applicationContext = context.getApplicationContext();
                sContext = applicationContext;
                b.init(applicationContext);
                com.alibaba.sdk.android.httpdns.d.a.a(sContext).a(new SDKMessageCallback() { // from class: com.alibaba.sdk.android.httpdns.HttpDns.3
                    @Override // com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback
                    public void crashDefendMessage(int i, int i2) {
                        boolean unused = HttpDns.inited = true;
                        if (i > i2) {
                            b.a(true);
                            return;
                        }
                        i.f("crash limit exceeds, httpdns disabled");
                        b.a(false);
                    }
                });
                if (!inited) {
                    i.f("sdk crash defend not returned");
                }
                if (b.a()) {
                    initHttpDns(sContext, getAccountId(), getSecretKey());
                } else {
                    instance = new HttpDns(sContext, getAccountId());
                }
            }
            httpDns = instance;
        }
        return httpDns;
    }

    public static synchronized HttpDnsService getService(Context context, String str) {
        HttpDns httpDns;
        synchronized (HttpDns.class) {
            if (instance == null && context != null) {
                sContext = context.getApplicationContext();
                setAccountId(str);
                b.init(sContext);
                com.alibaba.sdk.android.httpdns.d.a.a(sContext).a(new SDKMessageCallback() { // from class: com.alibaba.sdk.android.httpdns.HttpDns.1
                    @Override // com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback
                    public void crashDefendMessage(int i, int i2) {
                        boolean unused = HttpDns.inited = true;
                        if (i > i2) {
                            b.a(true);
                            return;
                        }
                        i.f("crash limit exceeds, httpdns disabled");
                        b.a(false);
                    }
                });
                if (!inited) {
                    i.f("sdk crash defend not returned");
                }
                if (b.a()) {
                    initHttpDns(sContext, getAccountId(), getSecretKey());
                } else {
                    instance = new HttpDns(sContext, getAccountId());
                }
            }
            httpDns = instance;
        }
        return httpDns;
    }

    public static synchronized HttpDnsService getService(Context context, String str, String str2) {
        HttpDns httpDns;
        synchronized (HttpDns.class) {
            if (instance == null && context != null) {
                sContext = context.getApplicationContext();
                setAccountId(str);
                setSecretKey(str2);
                b.init(sContext);
                com.alibaba.sdk.android.httpdns.d.a.a(sContext).a(new SDKMessageCallback() { // from class: com.alibaba.sdk.android.httpdns.HttpDns.2
                    @Override // com.alibaba.sdk.android.utils.crashdefend.SDKMessageCallback
                    public void crashDefendMessage(int i, int i2) {
                        boolean unused = HttpDns.inited = true;
                        if (i > i2) {
                            b.a(true);
                            return;
                        }
                        i.f("crash limit exceeds, httpdns disabled");
                        b.a(false);
                    }
                });
                if (!inited) {
                    i.f("sdk crash defend not returned");
                }
                if (b.a()) {
                    initHttpDns(sContext, getAccountId(), getSecretKey());
                } else {
                    instance = new HttpDns(sContext, getAccountId());
                }
            }
            httpDns = instance;
        }
        return httpDns;
    }

    private static void initHttpDns(Context context, String str, String str2) {
        if (instance == null) {
            HashMap hashMap = new HashMap();
            hashMap.put(AMSDevReporter.AMSSdkExtInfoKeyEnum.AMS_EXTINFO_KEY_VERSION.toString(), "1.2.3");
            AMSDevReporter.asyncReport(context, AMSDevReporter.AMSSdkTypeEnum.AMS_HTTPDNS, hashMap);
            l.setContext(context);
            m.setContext(context);
            com.alibaba.sdk.android.httpdns.b.b.init(context);
            com.alibaba.sdk.android.httpdns.b.b.a(context);
            t.init(context);
            f.c(str);
            p.a().init(context);
            if (!TextUtils.isEmpty(str2)) {
                a.setSecretKey(str2);
            }
            reportActive(context, str);
            instance = new HttpDns(context, str);
        }
    }

    private static void reportActive(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            i.f("report active failed due to missing context or accountid");
            return;
        }
        com.alibaba.sdk.android.httpdns.d.a.a(context).setAccountId(str);
        com.alibaba.sdk.android.httpdns.d.a.a(context).i();
    }

    private static void reportHttpDnsSuccess(String str, int i) {
        com.alibaba.sdk.android.httpdns.d.a a = com.alibaba.sdk.android.httpdns.d.a.a();
        if (a != null) {
            a.a(str, i, com.alibaba.sdk.android.httpdns.d.b.b(), com.alibaba.sdk.android.httpdns.b.b.m34a() ? 1 : 0);
        }
    }

    private static void reportUserGetIP(String str, int i) {
        com.alibaba.sdk.android.httpdns.d.a a = com.alibaba.sdk.android.httpdns.d.a.a();
        if (a != null) {
            a.b(str, i, com.alibaba.sdk.android.httpdns.d.b.b(), com.alibaba.sdk.android.httpdns.b.b.m34a() ? 1 : 0);
        }
    }

    private static void setAccountId(String str) {
        sAccountId = str;
    }

    private static void setSecretKey(String str) {
        sSecretKey = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static synchronized void switchDnsService(boolean z) {
        synchronized (HttpDns.class) {
            b.a(z);
            if (!b.a()) {
                i.f("httpdns service disabled");
            }
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public String getIpByHostAsync(String str) {
        if (!b.a()) {
            i.f("HttpDns service turned off");
            return null;
        }
        String[] ipsByHostAsync = getIpsByHostAsync(str);
        if (ipsByHostAsync != null && ipsByHostAsync.length > 0) {
            return ipsByHostAsync[0];
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x003d, code lost:
        if (r3 != false) goto L44;
     */
    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String[] getIpsByHostAsync(java.lang.String r7) {
        /*
            r6 = this;
            boolean r0 = com.alibaba.sdk.android.httpdns.b.a()
            if (r0 != 0) goto Le
            java.lang.String r7 = "HttpDns service turned off"
            com.alibaba.sdk.android.httpdns.i.f(r7)
            java.lang.String[] r7 = com.alibaba.sdk.android.httpdns.f.d
            return r7
        Le:
            boolean r0 = com.alibaba.sdk.android.httpdns.k.b(r7)
            if (r0 != 0) goto L17
            java.lang.String[] r7 = com.alibaba.sdk.android.httpdns.f.d
            return r7
        L17:
            boolean r0 = com.alibaba.sdk.android.httpdns.k.c(r7)
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L24
            java.lang.String[] r0 = new java.lang.String[r2]
            r0[r1] = r7
            return r0
        L24:
            com.alibaba.sdk.android.httpdns.DegradationFilter r0 = com.alibaba.sdk.android.httpdns.HttpDns.degradationFilter
            if (r0 == 0) goto L31
            boolean r0 = r0.shouldDegradeHttpDNS(r7)
            if (r0 == 0) goto L31
            java.lang.String[] r7 = com.alibaba.sdk.android.httpdns.f.d
            return r7
        L31:
            com.alibaba.sdk.android.httpdns.d r0 = com.alibaba.sdk.android.httpdns.HttpDns.hostManager
            com.alibaba.sdk.android.httpdns.e r0 = r0.m43a(r7)
            if (r0 == 0) goto L40
            boolean r3 = r0.m52b()
            if (r3 == 0) goto L7b
            goto L41
        L40:
            r3 = r1
        L41:
            com.alibaba.sdk.android.httpdns.d r4 = com.alibaba.sdk.android.httpdns.HttpDns.hostManager
            boolean r4 = r4.m48a(r7)
            if (r4 != 0) goto L7b
            boolean r4 = com.alibaba.sdk.android.httpdns.t.e()
            if (r4 == 0) goto L57
            com.alibaba.sdk.android.httpdns.s r4 = com.alibaba.sdk.android.httpdns.s.a()
            r4.g(r7)
            goto L7b
        L57:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "refresh host async: "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r4 = r4.append(r7)
            java.lang.String r4 = r4.toString()
            com.alibaba.sdk.android.httpdns.i.d(r4)
            com.alibaba.sdk.android.httpdns.m r4 = new com.alibaba.sdk.android.httpdns.m
            com.alibaba.sdk.android.httpdns.o r5 = com.alibaba.sdk.android.httpdns.o.QUERY_HOST
            r4.<init>(r7, r5)
            java.util.concurrent.ExecutorService r5 = com.alibaba.sdk.android.httpdns.c.a()
            r5.submit(r4)
        L7b:
            if (r0 != 0) goto L83
            reportUserGetIP(r7, r1)
            java.lang.String[] r7 = com.alibaba.sdk.android.httpdns.f.d
            return r7
        L83:
            boolean r4 = com.alibaba.sdk.android.httpdns.t.e()
            if (r4 == 0) goto L94
            java.lang.String r0 = "[HttpDns] disabled return Nil."
            com.alibaba.sdk.android.httpdns.i.d(r0)
            reportUserGetIP(r7, r1)
            java.lang.String[] r7 = com.alibaba.sdk.android.httpdns.f.d
            return r7
        L94:
            boolean r4 = r6.isExpiredIPEnabled
            if (r4 == 0) goto La3
            reportHttpDnsSuccess(r7, r2)
            reportUserGetIP(r7, r2)
            java.lang.String[] r7 = r0.m51a()
            return r7
        La3:
            boolean r4 = r0.c()
            if (r4 == 0) goto Lcc
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r1 = "[HttpDns] ips from cache:"
            java.lang.StringBuilder r7 = r7.append(r1)
            java.lang.String[] r1 = r0.m51a()
            java.lang.String r1 = java.util.Arrays.toString(r1)
            java.lang.StringBuilder r7 = r7.append(r1)
            java.lang.String r7 = r7.toString()
            com.alibaba.sdk.android.httpdns.i.d(r7)
            java.lang.String[] r7 = r0.m51a()
            return r7
        Lcc:
            if (r3 != 0) goto Lf7
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "[HttpDns] not expired return "
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String[] r3 = r0.m51a()
            java.lang.String r3 = java.util.Arrays.toString(r3)
            java.lang.StringBuilder r1 = r1.append(r3)
            java.lang.String r1 = r1.toString()
            com.alibaba.sdk.android.httpdns.i.d(r1)
            reportHttpDnsSuccess(r7, r2)
            reportUserGetIP(r7, r2)
            java.lang.String[] r7 = r0.m51a()
            return r7
        Lf7:
            java.lang.String r0 = "[HttpDns] return Nil."
            com.alibaba.sdk.android.httpdns.i.f(r0)
            reportUserGetIP(r7, r1)
            java.lang.String[] r7 = com.alibaba.sdk.android.httpdns.f.d
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.httpdns.HttpDns.getIpsByHostAsync(java.lang.String):java.lang.String[]");
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public String getSessionId() {
        return com.alibaba.sdk.android.httpdns.e.a.a().getSessionId();
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setAuthCurrentTime(long j) {
        if (b.a()) {
            a.setAuthCurrentTime(j);
        } else {
            i.f("HttpDns service turned off");
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setCachedIPEnabled(boolean z) {
        if (!b.a()) {
            i.f("HttpDns service turned off");
            return;
        }
        i.f("Httpdns DB cache enable.");
        com.alibaba.sdk.android.httpdns.b.b.a(z);
        d.a().m45a();
        com.alibaba.sdk.android.httpdns.d.a a = com.alibaba.sdk.android.httpdns.d.a.a();
        if (a != null) {
            a.c(z ? 1 : 0);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setDegradationFilter(DegradationFilter degradationFilter2) {
        if (b.a()) {
            degradationFilter = degradationFilter2;
        } else {
            i.f("HttpDns service turned off");
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setExpiredIPEnabled(boolean z) {
        if (!b.a()) {
            i.f("HttpDns service turned off");
            return;
        }
        this.isExpiredIPEnabled = z;
        com.alibaba.sdk.android.httpdns.d.a a = com.alibaba.sdk.android.httpdns.d.a.a();
        if (a != null) {
            a.d(z ? 1 : 0);
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setHTTPSRequestEnabled(boolean z) {
        if (b.a()) {
            f.setHTTPSRequestEnabled(z);
        } else {
            i.f("HttpDns service turned off");
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setIPProbeList(List<IPProbeItem> list) {
        if (b.a()) {
            f.a(list);
        } else {
            i.f("HttpDns service turned off");
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setLogEnabled(boolean z) {
        i.setLogEnabled(z);
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setLogger(ILogger iLogger) {
        i.setLogger(iLogger);
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setPreResolveAfterNetworkChanged(boolean z) {
        if (b.a()) {
            l.c = z;
        } else {
            i.f("HttpDns service turned off");
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setPreResolveHosts(ArrayList<String> arrayList) {
        if (!b.a()) {
            i.f("HttpDns service turned off");
            return;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            String str = arrayList.get(i);
            if (k.b(str) && !hostManager.m48a(str)) {
                c.a().submit(new m(str, o.QUERY_HOST));
            }
        }
    }

    @Override // com.alibaba.sdk.android.httpdns.HttpDnsService
    public void setTimeoutInterval(int i) {
        if (b.a()) {
            f.setTimeoutInterval(i);
        } else {
            i.f("HttpDns service turned off");
        }
    }
}
