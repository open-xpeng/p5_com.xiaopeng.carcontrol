package com.O000000o.O000000o.O00000Oo.O000000o;

import com.O000000o.O000000o.O000OOo;
import com.O000000o.O000000o.O000o00;
import com.O000000o.O000000o.O000o000;
import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: TimeTypeAdapter.java */
/* loaded from: classes.dex */
public final class O0000o0 extends O000o000<Time> {
    public static final O000o00 O000000o = new O000o00() { // from class: com.O000000o.O000000o.O00000Oo.O000000o.O0000o0.1
        @Override // com.O000000o.O000000o.O000o00
        public <T> O000o000<T> O000000o(com.O000000o.O000000o.O0000OOo o0000OOo, com.O000000o.O000000o.O00000o0.O000000o<T> o000000o) {
            if (o000000o.O000000o() == Time.class) {
                return new O0000o0();
            }
            return null;
        }
    };
    private final DateFormat O00000Oo = new SimpleDateFormat("hh:mm:ss a");

    @Override // com.O000000o.O000000o.O000o000
    /* renamed from: O000000o */
    public synchronized Time O00000Oo(com.O000000o.O000000o.O00000o.O000000o o000000o) throws IOException {
        if (o000000o.O00000oo() == com.O000000o.O000000o.O00000o.O00000o0.NULL) {
            o000000o.O0000Oo();
            return null;
        }
        try {
            return new Time(this.O00000Oo.parse(o000000o.O0000OOo()).getTime());
        } catch (ParseException e) {
            throw new O000OOo(e);
        }
    }

    @Override // com.O000000o.O000000o.O000o000
    public synchronized void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, Time time) throws IOException {
        o00000o.O00000Oo(time == null ? null : this.O00000Oo.format((Date) time));
    }
}
