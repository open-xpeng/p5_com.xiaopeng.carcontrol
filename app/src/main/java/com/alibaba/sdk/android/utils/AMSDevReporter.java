package com.alibaba.sdk.android.utils;

import android.content.Context;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes.dex */
public class AMSDevReporter {
    private static Context a;

    /* renamed from: a  reason: collision with other field name */
    private static final ExecutorService f111a = Executors.newSingleThreadExecutor(new a());

    /* renamed from: a  reason: collision with other field name */
    private static ConcurrentHashMap<AMSSdkTypeEnum, AMSReportStatusEnum> f110a = new ConcurrentHashMap<>();

    /* renamed from: a  reason: collision with other field name */
    private static boolean f112a = false;
    private static String TAG = "AMSDevReporter";

    /* loaded from: classes.dex */
    public enum AMSReportStatusEnum {
        UNREPORTED,
        REPORTED
    }

    /* loaded from: classes.dex */
    public enum AMSSdkTypeEnum {
        AMS_MAN("MAN"),
        AMS_HTTPDNS("HTTPDNS"),
        AMS_MPUSH("MPUSH"),
        AMS_MAC("MAC"),
        AMS_API("API"),
        AMS_HOTFIX("HOTFIX"),
        AMS_FEEDBACK("FEEDBACK"),
        AMS_IM("IM");
        
        private String description;

        AMSSdkTypeEnum(String str) {
            this.description = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.description;
        }
    }

    /* loaded from: classes.dex */
    public enum AMSSdkExtInfoKeyEnum {
        AMS_EXTINFO_KEY_VERSION("SdkVersion"),
        AMS_EXTINFO_KEY_PACKAGE("PackageName");
        
        private String description;

        AMSSdkExtInfoKeyEnum(String str) {
            this.description = str;
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.description;
        }
    }

    static {
        for (AMSSdkTypeEnum aMSSdkTypeEnum : AMSSdkTypeEnum.values()) {
            f110a.put(aMSSdkTypeEnum, AMSReportStatusEnum.UNREPORTED);
        }
    }

    public static void setLogEnabled(boolean z) {
        d.setLogEnabled(z);
    }

    public static AMSReportStatusEnum getReportStatus(AMSSdkTypeEnum aMSSdkTypeEnum) {
        return f110a.get(aMSSdkTypeEnum);
    }

    public static void asyncReport(Context context, AMSSdkTypeEnum aMSSdkTypeEnum) {
        asyncReport(context, aMSSdkTypeEnum, null);
    }

    public static void asyncReport(Context context, final AMSSdkTypeEnum aMSSdkTypeEnum, final Map<String, Object> map) {
        if (context == null) {
            d.c(TAG, "Context is null, return.");
            return;
        }
        a = context;
        d.b(TAG, "Add [" + aMSSdkTypeEnum.toString() + "] to report queue.");
        f112a = false;
        f111a.execute(new Runnable() { // from class: com.alibaba.sdk.android.utils.AMSDevReporter.1
            @Override // java.lang.Runnable
            public void run() {
                if (AMSDevReporter.f112a) {
                    d.c(AMSDevReporter.TAG, "Unable to execute remain task in queue, return.");
                    return;
                }
                d.b(AMSDevReporter.TAG, "Get [" + AMSSdkTypeEnum.this.toString() + "] from report queue.");
                AMSDevReporter.a(AMSSdkTypeEnum.this, (Map<String, Object>) map);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(AMSSdkTypeEnum aMSSdkTypeEnum, Map<String, Object> map) {
        String aMSSdkTypeEnum2 = aMSSdkTypeEnum.toString();
        if (f110a.get(aMSSdkTypeEnum) != AMSReportStatusEnum.UNREPORTED) {
            d.b(TAG, "[" + aMSSdkTypeEnum2 + "] already reported, return.");
            return;
        }
        int i = 0;
        int i2 = 5;
        while (true) {
            i++;
            d.b(TAG, "Report [" + aMSSdkTypeEnum2 + "], times: [" + i + "].");
            if (!m55a(aMSSdkTypeEnum, map)) {
                if (i <= 10) {
                    d.b(TAG, "Report [" + aMSSdkTypeEnum2 + "] failed, wait for [" + i2 + "] seconds.");
                    e.a(i2);
                    i2 *= 2;
                    if (i2 >= 60) {
                        i2 = 60;
                    }
                } else {
                    d.c(TAG, "Report [" + aMSSdkTypeEnum2 + "] stat failed, exceed max retry times, return.");
                    f110a.put(aMSSdkTypeEnum, AMSReportStatusEnum.UNREPORTED);
                    f112a = true;
                    break;
                }
            } else {
                d.b(TAG, "Report [" + aMSSdkTypeEnum2 + "] stat success.");
                f110a.put(aMSSdkTypeEnum, AMSReportStatusEnum.REPORTED);
                break;
            }
        }
        if (f112a) {
            d.c(TAG, "Report [" + aMSSdkTypeEnum2 + "] failed, clear remain report in queue.");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:70:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0201 A[Catch: IOException -> 0x01fd, TRY_LEAVE, TryCatch #12 {IOException -> 0x01fd, blocks: (B:72:0x01f9, B:76:0x0201), top: B:87:0x01f9 }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01f9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: a  reason: collision with other method in class */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static boolean m55a(com.alibaba.sdk.android.utils.AMSDevReporter.AMSSdkTypeEnum r10, java.util.Map<java.lang.String, java.lang.Object> r11) {
        /*
            Method dump skipped, instructions count: 523
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.utils.AMSDevReporter.m55a(com.alibaba.sdk.android.utils.AMSDevReporter$AMSSdkTypeEnum, java.util.Map):boolean");
    }

    private static String a(AMSSdkTypeEnum aMSSdkTypeEnum, String str, Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append(aMSSdkTypeEnum).append("-").append(str);
        if (map != null) {
            String str2 = (String) map.get(AMSSdkExtInfoKeyEnum.AMS_EXTINFO_KEY_VERSION.toString());
            if (!e.m62a(str2)) {
                sb.append("-").append(str2);
            }
            String str3 = (String) map.get(AMSSdkExtInfoKeyEnum.AMS_EXTINFO_KEY_PACKAGE.toString());
            if (!e.m62a(str3)) {
                sb.append("-").append(str3);
            }
        }
        return sb.toString();
    }
}
