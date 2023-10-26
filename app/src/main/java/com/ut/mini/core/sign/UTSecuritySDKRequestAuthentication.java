package com.ut.mini.core.sign;

import com.alibaba.mtl.log.e.i;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/* loaded from: classes.dex */
public class UTSecuritySDKRequestAuthentication implements IUTRequestAuthentication {
    private String Z;
    private String g;
    private Object b = null;
    private Object c = null;
    private Class a = null;

    /* renamed from: a  reason: collision with other field name */
    private Field f161a = null;

    /* renamed from: b  reason: collision with other field name */
    private Field f163b = null;

    /* renamed from: c  reason: collision with other field name */
    private Field f164c = null;

    /* renamed from: a  reason: collision with other field name */
    private Method f162a = null;
    private int z = 1;
    private boolean E = false;

    @Override // com.ut.mini.core.sign.IUTRequestAuthentication
    public String getAppkey() {
        return this.g;
    }

    public UTSecuritySDKRequestAuthentication(String str, String str2) {
        this.g = null;
        this.g = str;
        this.Z = str2;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0050 A[Catch: all -> 0x00d2, TRY_ENTER, TRY_LEAVE, TryCatch #1 {, blocks: (B:3:0x0001, B:39:0x00dc, B:15:0x0045, B:17:0x0050, B:23:0x008a, B:35:0x00b5, B:28:0x00a1, B:21:0x007e), top: B:45:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized void E() {
        /*
            Method dump skipped, instructions count: 227
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.core.sign.UTSecuritySDKRequestAuthentication.E():void");
    }

    @Override // com.ut.mini.core.sign.IUTRequestAuthentication
    public String getSign(String str) {
        Class cls;
        if (!this.E) {
            E();
        }
        if (this.g == null) {
            i.a("UTSecuritySDKRequestAuthentication:getSign", "There is no appkey,please check it!");
            return null;
        } else if (str == null || this.b == null || (cls = this.a) == null || this.f161a == null || this.f163b == null || this.f164c == null || this.f162a == null || this.c == null) {
            return null;
        } else {
            try {
                Object newInstance = cls.newInstance();
                this.f161a.set(newInstance, this.g);
                ((Map) this.f163b.get(newInstance)).put("INPUT", str);
                this.f164c.set(newInstance, Integer.valueOf(this.z));
                return (String) this.f162a.invoke(this.c, newInstance, this.Z);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalArgumentException e2) {
                e2.printStackTrace();
                return null;
            } catch (InstantiationException e3) {
                e3.printStackTrace();
                return null;
            } catch (InvocationTargetException e4) {
                e4.printStackTrace();
                return null;
            }
        }
    }

    public String getAuthCode() {
        return this.Z;
    }
}
