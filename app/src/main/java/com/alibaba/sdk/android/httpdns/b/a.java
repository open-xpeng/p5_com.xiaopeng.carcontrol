package com.alibaba.sdk.android.httpdns.b;

import android.content.Context;
import java.util.List;

/* loaded from: classes.dex */
class a implements f {
    private final d a;

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(Context context) {
        this.a = new d(context);
    }

    @Override // com.alibaba.sdk.android.httpdns.b.f
    public List<e> a() {
        return this.a.b();
    }

    @Override // com.alibaba.sdk.android.httpdns.b.f
    public void a(e eVar) {
        this.a.m36a(eVar);
    }

    @Override // com.alibaba.sdk.android.httpdns.b.f
    public void b(e eVar) {
        this.a.b(eVar.i, eVar.h);
    }
}
