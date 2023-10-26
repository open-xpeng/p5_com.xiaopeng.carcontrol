package com.O000000o.O000000o.O00000Oo;

import java.io.ObjectStreamException;
import java.math.BigDecimal;

/* compiled from: LazilyParsedNumber.java */
/* loaded from: classes.dex */
public final class O0000OOo extends Number {
    private final String O000000o;

    public O0000OOo(String str) {
        this.O000000o = str;
    }

    private Object O000000o() throws ObjectStreamException {
        return new BigDecimal(this.O000000o);
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return Double.parseDouble(this.O000000o);
    }

    @Override // java.lang.Number
    public float floatValue() {
        return Float.parseFloat(this.O000000o);
    }

    @Override // java.lang.Number
    public int intValue() {
        try {
            try {
                return Integer.parseInt(this.O000000o);
            } catch (NumberFormatException unused) {
                return new BigDecimal(this.O000000o).intValue();
            }
        } catch (NumberFormatException unused2) {
            return (int) Long.parseLong(this.O000000o);
        }
    }

    @Override // java.lang.Number
    public long longValue() {
        try {
            return Long.parseLong(this.O000000o);
        } catch (NumberFormatException unused) {
            return new BigDecimal(this.O000000o).longValue();
        }
    }

    public String toString() {
        return this.O000000o;
    }
}
