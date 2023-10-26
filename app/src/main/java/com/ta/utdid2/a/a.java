package com.ta.utdid2.a;

import android.content.Context;
import android.util.Log;
import com.ta.utdid2.b.a.f;
import com.ta.utdid2.b.a.i;
import com.ta.utdid2.b.a.j;

/* compiled from: AidManager.java */
/* loaded from: classes.dex */
public class a {
    private static final String TAG = "com.ta.utdid2.a.a";
    private static a a;
    private Context mContext;

    public static synchronized a a(Context context) {
        a aVar;
        synchronized (a.class) {
            if (a == null) {
                a = new a(context);
            }
            aVar = a;
        }
        return aVar;
    }

    private a(Context context) {
        this.mContext = context;
    }

    public void a(String str, String str2, String str3, com.ut.device.a aVar) {
        if (aVar == null) {
            Log.e(TAG, "callback is null!");
        } else if (this.mContext == null || i.m74a(str) || i.m74a(str2)) {
            Log.e(TAG, "mContext:" + this.mContext + "; callback:" + aVar + "; has appName:" + (!i.m74a(str)) + "; has token:" + (!i.m74a(str2)));
            aVar.a(1002, "");
        } else {
            String m71a = c.m71a(this.mContext, str, str2);
            if (!i.m74a(m71a) && j.a(c.a(this.mContext, str, str2), 1)) {
                aVar.a(1001, m71a);
            } else if (f.m73a(this.mContext)) {
                b.a(this.mContext).a(str, str2, str3, m71a, aVar);
            } else {
                aVar.a(1003, m71a);
            }
        }
    }

    public String a(String str, String str2, String str3) {
        if (this.mContext == null || i.m74a(str) || i.m74a(str2)) {
            Log.e(TAG, "mContext:" + this.mContext + "; has appName:" + (!i.m74a(str)) + "; has token:" + (!i.m74a(str2)));
            return "";
        }
        String m71a = c.m71a(this.mContext, str, str2);
        return ((i.m74a(m71a) || !j.a(c.a(this.mContext, str, str2), 1)) && f.m73a(this.mContext)) ? b(str, str2, str3) : m71a;
    }

    private synchronized String b(String str, String str2, String str3) {
        Context context = this.mContext;
        if (context == null) {
            Log.e(TAG, "no context!");
            return "";
        }
        String a2 = f.m73a(context) ? b.a(this.mContext).a(str, str2, str3, c.m71a(this.mContext, str, str2)) : "";
        c.a(this.mContext, str, a2, str2);
        return a2;
    }
}
