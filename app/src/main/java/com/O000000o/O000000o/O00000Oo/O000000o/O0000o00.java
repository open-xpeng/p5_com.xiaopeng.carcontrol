package com.O000000o.O000000o.O00000Oo.O000000o;

import com.O000000o.O000000o.O000OOo;
import com.O000000o.O000000o.O000o00;
import com.O000000o.O000000o.O000o000;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/* compiled from: SqlDateTypeAdapter.java */
/* loaded from: classes.dex */
public final class O0000o00 extends O000o000<Date> {
    public static final O000o00 O000000o = new O000o00() { // from class: com.O000000o.O000000o.O00000Oo.O000000o.O0000o00.1
        @Override // com.O000000o.O000000o.O000o00
        public <T> O000o000<T> O000000o(com.O000000o.O000000o.O0000OOo o0000OOo, com.O000000o.O000000o.O00000o0.O000000o<T> o000000o) {
            if (o000000o.O000000o() == Date.class) {
                return new O0000o00();
            }
            return null;
        }
    };
    private final DateFormat O00000Oo = new SimpleDateFormat("MMM d, yyyy");

    @Override // com.O000000o.O000000o.O000o000
    /* renamed from: O000000o */
    public synchronized Date O00000Oo(com.O000000o.O000000o.O00000o.O000000o o000000o) throws IOException {
        if (o000000o.O00000oo() == com.O000000o.O000000o.O00000o.O00000o0.NULL) {
            o000000o.O0000Oo();
            return null;
        }
        try {
            return new Date(this.O00000Oo.parse(o000000o.O0000OOo()).getTime());
        } catch (ParseException e) {
            throw new O000OOo(e);
        }
    }

    @Override // com.O000000o.O000000o.O000o000
    public synchronized void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, Date date) throws IOException {
        o00000o.O00000Oo(date == null ? null : this.O00000Oo.format((java.util.Date) date));
    }
}
