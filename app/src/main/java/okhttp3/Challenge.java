package okhttp3;

import java.nio.charset.Charset;
import java.util.Objects;
import javax.annotation.Nullable;
import okhttp3.internal.Util;

/* loaded from: classes3.dex */
public final class Challenge {
    private final Charset charset;
    private final String realm;
    private final String scheme;

    public Challenge(String str, String str2) {
        this(str, str2, Util.ISO_8859_1);
    }

    private Challenge(String str, String str2, Charset charset) {
        Objects.requireNonNull(str, "scheme == null");
        Objects.requireNonNull(str2, "realm == null");
        Objects.requireNonNull(charset, "charset == null");
        this.scheme = str;
        this.realm = str2;
        this.charset = charset;
    }

    public Challenge withCharset(Charset charset) {
        return new Challenge(this.scheme, this.realm, charset);
    }

    public String scheme() {
        return this.scheme;
    }

    public String realm() {
        return this.realm;
    }

    public Charset charset() {
        return this.charset;
    }

    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Challenge) {
            Challenge challenge = (Challenge) obj;
            if (challenge.scheme.equals(this.scheme) && challenge.realm.equals(this.realm) && challenge.charset.equals(this.charset)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((899 + this.realm.hashCode()) * 31) + this.scheme.hashCode()) * 31) + this.charset.hashCode();
    }

    public String toString() {
        return this.scheme + " realm=\"" + this.realm + "\" charset=\"" + this.charset + "\"";
    }
}
