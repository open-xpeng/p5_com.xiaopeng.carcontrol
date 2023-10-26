package com.O000000o.O000000o.O00000Oo.O000000o;

import com.O000000o.O000000o.O00000Oo.O000000o.O0000Oo;
import com.O000000o.O000000o.O000o000;
import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TypeAdapterRuntimeTypeWrapper.java */
/* loaded from: classes.dex */
public final class O0000o<T> extends O000o000<T> {
    private final com.O000000o.O000000o.O0000OOo O000000o;
    private final O000o000<T> O00000Oo;
    private final Type O00000o0;

    /* JADX INFO: Access modifiers changed from: package-private */
    public O0000o(com.O000000o.O000000o.O0000OOo o0000OOo, O000o000<T> o000o000, Type type) {
        this.O000000o = o0000OOo;
        this.O00000Oo = o000o000;
        this.O00000o0 = type;
    }

    private Type O000000o(Type type, Object obj) {
        return obj != null ? (type == Object.class || (type instanceof TypeVariable) || (type instanceof Class)) ? obj.getClass() : type : type;
    }

    @Override // com.O000000o.O000000o.O000o000
    public void O000000o(com.O000000o.O000000o.O00000o.O00000o o00000o, T t) throws IOException {
        O000o000<T> o000o000 = this.O00000Oo;
        Type O000000o = O000000o(this.O00000o0, t);
        if (O000000o != this.O00000o0) {
            o000o000 = this.O000000o.O000000o((com.O000000o.O000000o.O00000o0.O000000o) com.O000000o.O000000o.O00000o0.O000000o.O00000Oo(O000000o));
            if (o000o000 instanceof O0000Oo.O000000o) {
                O000o000<T> o000o0002 = this.O00000Oo;
                if (!(o000o0002 instanceof O0000Oo.O000000o)) {
                    o000o000 = o000o0002;
                }
            }
        }
        o000o000.O000000o(o00000o, (com.O000000o.O000000o.O00000o.O00000o) t);
    }

    @Override // com.O000000o.O000000o.O000o000
    public T O00000Oo(com.O000000o.O000000o.O00000o.O000000o o000000o) throws IOException {
        return this.O00000Oo.O00000Oo(o000000o);
    }
}
