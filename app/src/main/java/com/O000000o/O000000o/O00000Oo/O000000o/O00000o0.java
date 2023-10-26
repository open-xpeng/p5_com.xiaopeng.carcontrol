package com.O000000o.O000000o.O00000Oo.O000000o;

import com.O000000o.O000000o.O000OOo;
import com.O000000o.O000000o.O000o00;
import com.O000000o.O000000o.O000o000;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* compiled from: DateTypeAdapter.java */
/* loaded from: classes.dex */
public final class O00000o0 extends O000o000<Date> {
    public static final O000o00 O000000o = new O000o00() { // from class: com.O000000o.O000000o.O00000Oo.O000000o.O00000o0.1
        @Override // com.O000000o.O000000o.O000o00
        public <T> O000o000<T> O000000o(com.O000000o.O000000o.O0000OOo o0000OOo, com.O000000o.O000000o.O00000o0.O000000o<T> o000000o) {
            if (o000000o.O000000o() == Date.class) {
                return new O00000o0();
            }
            return null;
        }
    };
    private final DateFormat O00000Oo = DateFormat.getDateTimeInstance(2, 2, Locale.US);
    private final DateFormat O00000o0 = DateFormat.getDateTimeInstance(2, 2);
    private final DateFormat O00000o = O00000Oo();

    private static DateFormat O00000Oo() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat;
    }

    private synchronized Date O00000Oo(String str) {
        try {
            try {
                try {
                } catch (ParseException unused) {
                    return this.O00000Oo.parse(str);
                }
            } catch (ParseException e) {
                throw new O000OOo(str, e);
            }
        } catch (ParseException unused2) {
            return this.O00000o.parse(str);
        }
        return this.O00000o0.parse(str);
    }

    @Override // com.O000000o.O000000o.O000o000
    /* renamed from: O000000o */
    public Date O00000Oo(com.O000000o.O000000o.O00000o.O000000o o000000o) throws IOException {
        if (o000000o.O00000oo() == com.O000000o.O000000o.O00000o.O00000o0.NULL) {
            o000000o.O0000Oo();
            return null;
        }
        return O00000Oo(o000000o.O0000OOo());
    }

    @Override // com.O000000o.O000000o.O000o000
    public synchronized void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, Date date) throws IOException {
        if (date == null) {
            o00000o.O00000oo();
        } else {
            o00000o.O00000Oo(this.O00000Oo.format(date));
        }
    }
}
