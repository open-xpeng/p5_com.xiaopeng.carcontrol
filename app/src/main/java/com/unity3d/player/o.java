package com.unity3d.player;

import java.lang.reflect.Method;
import java.util.HashMap;

/* loaded from: classes.dex */
final class o {
    private HashMap a = new HashMap();
    private Class b;
    private Object c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class a {
        public Class[] a;
        public Method b = null;

        public a(Class[] clsArr) {
            this.a = clsArr;
        }
    }

    public o(Class cls, Object obj) {
        this.b = null;
        this.c = null;
        this.b = cls;
        this.c = obj;
    }

    private void a(String str, a aVar) {
        try {
            aVar.b = this.b.getMethod(str, aVar.a);
        } catch (Exception e) {
            g.Log(6, "Exception while trying to get method " + str + ". " + e.getLocalizedMessage());
            aVar.b = null;
        }
    }

    public final Object a(String str, Object... objArr) {
        StringBuilder append;
        Object obj = null;
        if (this.a.containsKey(str)) {
            a aVar = (a) this.a.get(str);
            if (aVar.b == null) {
                a(str, aVar);
            }
            if (aVar.b != null) {
                try {
                    obj = objArr.length == 0 ? aVar.b.invoke(this.c, new Object[0]) : aVar.b.invoke(this.c, objArr);
                } catch (Exception e) {
                    g.Log(6, "Error trying to call delegated method " + str + ". " + e.getLocalizedMessage());
                }
                return obj;
            }
            append = new StringBuilder("Unable to create method: ").append(str);
        } else {
            append = new StringBuilder("No definition for method ").append(str).append(" can be found");
        }
        g.Log(6, append.toString());
        return null;
    }

    public final void a(String str, Class[] clsArr) {
        this.a.put(str, new a(clsArr));
    }
}
