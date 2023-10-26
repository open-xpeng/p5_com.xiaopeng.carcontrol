package com.O000000o.O000000o;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: GsonBuilder.java */
/* loaded from: classes.dex */
public final class O0000Oo0 {
    private boolean O0000O0o;
    private String O0000OOo;
    private boolean O0000OoO;
    private boolean O0000Ooo;
    private boolean O0000o0;
    private boolean O0000o0O;
    private com.O000000o.O000000o.O00000Oo.O00000o O000000o = com.O000000o.O000000o.O00000Oo.O00000o.O000000o;
    private O000Oo0 O00000Oo = O000Oo0.DEFAULT;
    private O0000O0o O00000o0 = O00000o.IDENTITY;
    private final Map<Type, O0000Oo<?>> O00000o = new HashMap();
    private final List<O000o00> O00000oO = new ArrayList();
    private final List<O000o00> O00000oo = new ArrayList();
    private int O0000Oo0 = 2;
    private int O0000Oo = 2;
    private boolean O0000o00 = true;

    private void O000000o(String str, int i, int i2, List<O000o00> list) {
        O000000o o000000o;
        if (str != null && !"".equals(str.trim())) {
            o000000o = new O000000o(str);
        } else if (i == 2 || i2 == 2) {
            return;
        } else {
            o000000o = new O000000o(i, i2);
        }
        list.add(O00O0Oo.O000000o(com.O000000o.O000000o.O00000o0.O000000o.O00000o0(Date.class), o000000o));
        list.add(O00O0Oo.O000000o(com.O000000o.O000000o.O00000o0.O000000o.O00000o0(Timestamp.class), o000000o));
        list.add(O00O0Oo.O000000o(com.O000000o.O000000o.O00000o0.O000000o.O00000o0(java.sql.Date.class), o000000o));
    }

    public O0000Oo0 O000000o() {
        this.O0000o0O = true;
        return this;
    }

    public O0000Oo0 O000000o(double d) {
        this.O000000o = this.O000000o.O000000o(d);
        return this;
    }

    public O0000Oo0 O000000o(int i) {
        this.O0000Oo0 = i;
        this.O0000OOo = null;
        return this;
    }

    public O0000Oo0 O000000o(int i, int i2) {
        this.O0000Oo0 = i;
        this.O0000Oo = i2;
        this.O0000OOo = null;
        return this;
    }

    public O0000Oo0 O000000o(O00000Oo o00000Oo) {
        this.O000000o = this.O000000o.O000000o(o00000Oo, true, false);
        return this;
    }

    public O0000Oo0 O000000o(O00000o o00000o) {
        this.O00000o0 = o00000o;
        return this;
    }

    public O0000Oo0 O000000o(O0000O0o o0000O0o) {
        this.O00000o0 = o0000O0o;
        return this;
    }

    public O0000Oo0 O000000o(O000Oo0 o000Oo0) {
        this.O00000Oo = o000Oo0;
        return this;
    }

    public O0000Oo0 O000000o(O000o00 o000o00) {
        this.O00000oO.add(o000o00);
        return this;
    }

    public O0000Oo0 O000000o(Class<?> cls, Object obj) {
        boolean z = obj instanceof O000OOOo;
        com.O000000o.O000000o.O00000Oo.O000000o.O000000o(z || (obj instanceof O0000o) || (obj instanceof O000o000));
        if ((obj instanceof O0000o) || z) {
            this.O00000oo.add(0, O00O0Oo.O000000o(cls, obj));
        }
        if (obj instanceof O000o000) {
            this.O00000oO.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O00000Oo(cls, (O000o000) obj));
        }
        return this;
    }

    public O0000Oo0 O000000o(String str) {
        this.O0000OOo = str;
        return this;
    }

    public O0000Oo0 O000000o(Type type, Object obj) {
        boolean z = obj instanceof O000OOOo;
        com.O000000o.O000000o.O00000Oo.O000000o.O000000o(z || (obj instanceof O0000o) || (obj instanceof O0000Oo) || (obj instanceof O000o000));
        if (obj instanceof O0000Oo) {
            this.O00000o.put(type, (O0000Oo) obj);
        }
        if (z || (obj instanceof O0000o)) {
            this.O00000oO.add(O00O0Oo.O00000Oo(com.O000000o.O000000o.O00000o0.O000000o.O00000Oo(type), obj));
        }
        if (obj instanceof O000o000) {
            this.O00000oO.add(com.O000000o.O000000o.O00000Oo.O000000o.O00oOooO.O000000o(com.O000000o.O000000o.O00000o0.O000000o.O00000Oo(type), (O000o000) obj));
        }
        return this;
    }

    public O0000Oo0 O000000o(int... iArr) {
        this.O000000o = this.O000000o.O000000o(iArr);
        return this;
    }

    public O0000Oo0 O000000o(O00000Oo... o00000OoArr) {
        for (O00000Oo o00000Oo : o00000OoArr) {
            this.O000000o = this.O000000o.O000000o(o00000Oo, true, true);
        }
        return this;
    }

    public O0000Oo0 O00000Oo() {
        this.O000000o = this.O000000o.O00000o0();
        return this;
    }

    public O0000Oo0 O00000Oo(O00000Oo o00000Oo) {
        this.O000000o = this.O000000o.O000000o(o00000Oo, false, true);
        return this;
    }

    public O0000Oo0 O00000o() {
        this.O0000OoO = true;
        return this;
    }

    public O0000Oo0 O00000o0() {
        this.O0000O0o = true;
        return this;
    }

    public O0000Oo0 O00000oO() {
        this.O000000o = this.O000000o.O00000Oo();
        return this;
    }

    public O0000Oo0 O00000oo() {
        this.O0000o0 = true;
        return this;
    }

    public O0000Oo0 O0000O0o() {
        this.O0000o00 = false;
        return this;
    }

    public O0000Oo0 O0000OOo() {
        this.O0000Ooo = true;
        return this;
    }

    public O0000OOo O0000Oo0() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.O00000oO);
        Collections.reverse(arrayList);
        arrayList.addAll(this.O00000oo);
        O000000o(this.O0000OOo, this.O0000Oo0, this.O0000Oo, arrayList);
        return new O0000OOo(this.O000000o, this.O00000o0, this.O00000o, this.O0000O0o, this.O0000OoO, this.O0000o0O, this.O0000o00, this.O0000o0, this.O0000Ooo, this.O00000Oo, arrayList);
    }
}
