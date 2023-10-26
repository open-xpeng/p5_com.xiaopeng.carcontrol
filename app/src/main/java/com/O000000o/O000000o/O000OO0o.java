package com.O000000o.O000000o;

import java.math.BigDecimal;
import java.math.BigInteger;

/* compiled from: JsonPrimitive.java */
/* loaded from: classes.dex */
public final class O000OO0o extends O00oOooO {
    private static final Class<?>[] O000000o = {Integer.TYPE, Long.TYPE, Short.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Boolean.TYPE, Character.TYPE, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class};
    private Object O00000Oo;

    public O000OO0o(Boolean bool) {
        O000000o(bool);
    }

    public O000OO0o(Character ch) {
        O000000o(ch);
    }

    public O000OO0o(Number number) {
        O000000o(number);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public O000OO0o(Object obj) {
        O000000o(obj);
    }

    public O000OO0o(String str) {
        O000000o(str);
    }

    private static boolean O000000o(O000OO0o o000OO0o) {
        Object obj = o000OO0o.O00000Oo;
        if (obj instanceof Number) {
            Number number = (Number) obj;
            return (number instanceof BigInteger) || (number instanceof Long) || (number instanceof Integer) || (number instanceof Short) || (number instanceof Byte);
        }
        return false;
    }

    private static boolean O00000Oo(Object obj) {
        if (obj instanceof String) {
            return true;
        }
        Class<?> cls = obj.getClass();
        for (Class<?> cls2 : O000000o) {
            if (cls2.isAssignableFrom(cls)) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.O000000o.O000000o.O00oOooO
    /* renamed from: O000000o */
    public O000OO0o O0000o0O() {
        return this;
    }

    void O000000o(Object obj) {
        if (obj instanceof Character) {
            obj = String.valueOf(((Character) obj).charValue());
        } else {
            com.O000000o.O000000o.O00000Oo.O000000o.O000000o((obj instanceof Number) || O00000Oo(obj));
        }
        this.O00000Oo = obj;
    }

    public boolean O00000Oo() {
        return this.O00000Oo instanceof Boolean;
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public String O00000o() {
        return O0000ooo() ? O00000o0().toString() : O00000Oo() ? O0000ooO().toString() : (String) this.O00000Oo;
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public Number O00000o0() {
        Object obj = this.O00000Oo;
        return obj instanceof String ? new com.O000000o.O000000o.O00000Oo.O0000OOo((String) this.O00000Oo) : (Number) obj;
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public double O00000oO() {
        return O0000ooo() ? O00000o0().doubleValue() : Double.parseDouble(O00000o());
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public BigDecimal O00000oo() {
        Object obj = this.O00000Oo;
        return obj instanceof BigDecimal ? (BigDecimal) obj : new BigDecimal(this.O00000Oo.toString());
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public BigInteger O0000O0o() {
        Object obj = this.O00000Oo;
        return obj instanceof BigInteger ? (BigInteger) obj : new BigInteger(this.O00000Oo.toString());
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public float O0000OOo() {
        return O0000ooo() ? O00000o0().floatValue() : Float.parseFloat(O00000o());
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public int O0000Oo() {
        return O0000ooo() ? O00000o0().intValue() : Integer.parseInt(O00000o());
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public long O0000Oo0() {
        return O0000ooo() ? O00000o0().longValue() : Long.parseLong(O00000o());
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public byte O0000OoO() {
        return O0000ooo() ? O00000o0().byteValue() : Byte.parseByte(O00000o());
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public char O0000Ooo() {
        return O00000o().charAt(0);
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public boolean O0000o0() {
        return O00000Oo() ? O0000ooO().booleanValue() : Boolean.parseBoolean(O00000o());
    }

    @Override // com.O000000o.O000000o.O00oOooO
    public short O0000o00() {
        return O0000ooo() ? O00000o0().shortValue() : Short.parseShort(O00000o());
    }

    @Override // com.O000000o.O000000o.O00oOooO
    Boolean O0000ooO() {
        return (Boolean) this.O00000Oo;
    }

    public boolean O0000ooo() {
        return this.O00000Oo instanceof Number;
    }

    public boolean O00oOooO() {
        return this.O00000Oo instanceof String;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        O000OO0o o000OO0o = (O000OO0o) obj;
        if (this.O00000Oo == null) {
            return o000OO0o.O00000Oo == null;
        } else if (O000000o(this) && O000000o(o000OO0o)) {
            return O00000o0().longValue() == o000OO0o.O00000o0().longValue();
        } else {
            Object obj2 = this.O00000Oo;
            if ((obj2 instanceof Number) && (o000OO0o.O00000Oo instanceof Number)) {
                double doubleValue = O00000o0().doubleValue();
                double doubleValue2 = o000OO0o.O00000o0().doubleValue();
                if (doubleValue != doubleValue2) {
                    return Double.isNaN(doubleValue) && Double.isNaN(doubleValue2);
                }
                return true;
            }
            return obj2.equals(o000OO0o.O00000Oo);
        }
    }

    public int hashCode() {
        long doubleToLongBits;
        if (this.O00000Oo == null) {
            return 31;
        }
        if (O000000o(this)) {
            doubleToLongBits = O00000o0().longValue();
        } else {
            Object obj = this.O00000Oo;
            if (!(obj instanceof Number)) {
                return obj.hashCode();
            }
            doubleToLongBits = Double.doubleToLongBits(O00000o0().doubleValue());
        }
        return (int) ((doubleToLongBits >>> 32) ^ doubleToLongBits);
    }
}
