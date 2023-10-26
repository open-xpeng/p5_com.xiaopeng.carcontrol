package zipkin2;

import zipkin2.internal.Nullable;

/* loaded from: classes3.dex */
public final class CheckResult {
    public static final CheckResult OK = new CheckResult(true, null);
    final Throwable error;
    final boolean ok;

    public static CheckResult failed(Throwable th) {
        return new CheckResult(false, th);
    }

    public boolean ok() {
        return this.ok;
    }

    @Nullable
    public Throwable error() {
        return this.error;
    }

    CheckResult(boolean z, @Nullable Throwable th) {
        this.ok = z;
        this.error = th;
    }

    public String toString() {
        return "CheckResult{ok=" + this.ok + ", error=" + this.error + "}";
    }
}
