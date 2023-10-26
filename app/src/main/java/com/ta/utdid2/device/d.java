package com.ta.utdid2.device;

import java.util.Random;

/* compiled from: UTUtdidHelper.java */
/* loaded from: classes.dex */
public class d {
    private static Random a = new Random();
    private String p;

    public d() {
        this.p = "XwYp8WL8bm6S4wu6yEYmLGy4RRRdJDIhxCBdk3CiNZTwGoj1bScVZEeVp9vBiiIsgwDtqZHP8QLoFM6o6MRYjW8QqyrZBI654mqoUk5SOLDyzordzOU5QhYguEJh54q3K1KqMEXpdEQJJjs1Urqjm2s4jgPfCZ4hMuIjAMRrEQluA7FeoqWMJOwghcLcPVleQ8PLzAcaKidybmwhvNAxIyKRpbZlcDjNCcUvsJYvyzEA9VUIaHkIAJ62lpA3EE3H";
        this.p = com.ta.utdid2.b.a.b.encodeToString("XwYp8WL8bm6S4wu6yEYmLGy4RRRdJDIhxCBdk3CiNZTwGoj1bScVZEeVp9vBiiIsgwDtqZHP8QLoFM6o6MRYjW8QqyrZBI654mqoUk5SOLDyzordzOU5QhYguEJh54q3K1KqMEXpdEQJJjs1Urqjm2s4jgPfCZ4hMuIjAMRrEQluA7FeoqWMJOwghcLcPVleQ8PLzAcaKidybmwhvNAxIyKRpbZlcDjNCcUvsJYvyzEA9VUIaHkIAJ62lpA3EE3H".getBytes(), 2);
    }

    public String c(byte[] bArr) {
        return com.ta.utdid2.b.a.a.d(this.p, com.ta.utdid2.b.a.b.encodeToString(bArr, 2));
    }

    public String a(String str) {
        return com.ta.utdid2.b.a.a.d(this.p, str);
    }

    public String b(String str) {
        return com.ta.utdid2.b.a.a.e(this.p, str);
    }
}
