package com.O000000o.O000000o;

/* compiled from: LongSerializationPolicy.java */
/* loaded from: classes.dex */
public enum O000Oo0 {
    DEFAULT { // from class: com.O000000o.O000000o.O000Oo0.1
        @Override // com.O000000o.O000000o.O000Oo0
        public O00oOooO O000000o(Long l) {
            return new O000OO0o((Number) l);
        }
    },
    STRING { // from class: com.O000000o.O000000o.O000Oo0.2
        @Override // com.O000000o.O000000o.O000Oo0
        public O00oOooO O000000o(Long l) {
            return new O000OO0o(String.valueOf(l));
        }
    };

    public abstract O00oOooO O000000o(Long l);
}
