package com.unity3d.player;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class n {
    private static boolean a = false;
    private boolean b = !j.c;
    private boolean c = false;
    private boolean d = false;
    private boolean e = true;

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a() {
        a = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void b() {
        a = false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static boolean c() {
        return a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void a(boolean z) {
        this.c = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void b(boolean z) {
        this.e = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void c(boolean z) {
        this.d = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void d() {
        this.b = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean e() {
        return this.e;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean f() {
        return a && this.c && this.b && !this.e && !this.d;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final boolean g() {
        return this.d;
    }

    public final String toString() {
        return super.toString();
    }
}
