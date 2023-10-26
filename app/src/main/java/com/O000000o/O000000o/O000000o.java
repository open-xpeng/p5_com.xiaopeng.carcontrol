package com.O000000o.O000000o;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* compiled from: DefaultDateTypeAdapter.java */
/* loaded from: classes.dex */
final class O000000o implements O0000o<Date>, O000OOOo<Date> {
    private final DateFormat O000000o;
    private final DateFormat O00000Oo;
    private final DateFormat O00000o0;

    O000000o() {
        this(DateFormat.getDateTimeInstance(2, 2, Locale.US), DateFormat.getDateTimeInstance(2, 2));
    }

    O000000o(int i) {
        this(DateFormat.getDateInstance(i, Locale.US), DateFormat.getDateInstance(i));
    }

    public O000000o(int i, int i2) {
        this(DateFormat.getDateTimeInstance(i, i2, Locale.US), DateFormat.getDateTimeInstance(i, i2));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public O000000o(String str) {
        this(new SimpleDateFormat(str, Locale.US), new SimpleDateFormat(str));
    }

    O000000o(DateFormat dateFormat, DateFormat dateFormat2) {
        this.O000000o = dateFormat;
        this.O00000Oo = dateFormat2;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        this.O00000o0 = simpleDateFormat;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private Date O000000o(O00oOooO o00oOooO) {
        Date parse;
        synchronized (this.O00000Oo) {
            try {
                try {
                    try {
                        parse = this.O00000Oo.parse(o00oOooO.O00000o());
                    } catch (ParseException unused) {
                        return this.O00000o0.parse(o00oOooO.O00000o());
                    }
                } catch (ParseException e) {
                    throw new O000OOo(o00oOooO.O00000o(), e);
                }
            } catch (ParseException unused2) {
                return this.O000000o.parse(o00oOooO.O00000o());
            }
        }
        return parse;
    }

    @Override // com.O000000o.O000000o.O000OOOo
    public O00oOooO O000000o(Date date, Type type, O000OO o000oo) {
        O000OO0o o000OO0o;
        synchronized (this.O00000Oo) {
            o000OO0o = new O000OO0o(this.O000000o.format(date));
        }
        return o000OO0o;
    }

    @Override // com.O000000o.O000000o.O0000o
    /* renamed from: O000000o */
    public Date O00000Oo(O00oOooO o00oOooO, Type type, O0000o0 o0000o0) throws O000O0o {
        if (o00oOooO instanceof O000OO0o) {
            Date O000000o = O000000o(o00oOooO);
            if (type == Date.class) {
                return O000000o;
            }
            if (type == Timestamp.class) {
                return new Timestamp(O000000o.getTime());
            }
            if (type == java.sql.Date.class) {
                return new java.sql.Date(O000000o.getTime());
            }
            throw new IllegalArgumentException(getClass() + " cannot deserialize to " + type);
        }
        throw new O000O0o("The date should be a string value");
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(O000000o.class.getSimpleName());
        sb.append('(').append(this.O00000Oo.getClass().getSimpleName()).append(')');
        return sb.toString();
    }
}
