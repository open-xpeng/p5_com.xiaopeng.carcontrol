package com.O000000o.O000000o;

import java.util.Map;
import java.util.Set;

/* compiled from: JsonObject.java */
/* loaded from: classes.dex */
public final class O000O0o0 extends O00oOooO {
    private final com.O000000o.O000000o.O00000Oo.O0000Oo0<String, O00oOooO> O000000o = new com.O000000o.O000000o.O00000Oo.O0000Oo0<>();

    private O00oOooO O000000o(Object obj) {
        return obj == null ? O000O0OO.O000000o : new O000OO0o(obj);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.O000000o.O000000o.O00oOooO
    /* renamed from: O000000o */
    public O000O0o0 O0000o0O() {
        O000O0o0 o000O0o0 = new O000O0o0();
        for (Map.Entry<String, O00oOooO> entry : this.O000000o.entrySet()) {
            o000O0o0.O000000o(entry.getKey(), entry.getValue().O0000o0O());
        }
        return o000O0o0;
    }

    public O00oOooO O000000o(String str) {
        return this.O000000o.remove(str);
    }

    public void O000000o(String str, O00oOooO o00oOooO) {
        if (o00oOooO == null) {
            o00oOooO = O000O0OO.O000000o;
        }
        this.O000000o.put(str, o00oOooO);
    }

    public void O000000o(String str, Boolean bool) {
        O000000o(str, O000000o(bool));
    }

    public void O000000o(String str, Character ch) {
        O000000o(str, O000000o(ch));
    }

    public void O000000o(String str, Number number) {
        O000000o(str, O000000o(number));
    }

    public void O000000o(String str, String str2) {
        O000000o(str, O000000o((Object) str2));
    }

    public Set<Map.Entry<String, O00oOooO>> O00000Oo() {
        return this.O000000o.entrySet();
    }

    public boolean O00000Oo(String str) {
        return this.O000000o.containsKey(str);
    }

    public O000OO0o O00000o(String str) {
        return (O000OO0o) this.O000000o.get(str);
    }

    public O00oOooO O00000o0(String str) {
        return this.O000000o.get(str);
    }

    public O0000o00 O00000oO(String str) {
        return (O0000o00) this.O000000o.get(str);
    }

    public O000O0o0 O00000oo(String str) {
        return (O000O0o0) this.O000000o.get(str);
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof O000O0o0) && ((O000O0o0) obj).O000000o.equals(this.O000000o));
    }

    public int hashCode() {
        return this.O000000o.hashCode();
    }
}
