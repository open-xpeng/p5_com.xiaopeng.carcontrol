package zipkin2.internal;

import java.io.IOException;
import java.io.UncheckedIOException;

/* loaded from: classes3.dex */
public abstract class Platform {
    private static final Platform PLATFORM = findPlatform();

    Platform() {
    }

    public RuntimeException uncheckedIOException(IOException iOException) {
        return new RuntimeException(iOException);
    }

    public AssertionError assertionError(String str, Throwable th) {
        AssertionError assertionError = new AssertionError(str);
        assertionError.initCause(th);
        throw assertionError;
    }

    public static Platform get() {
        return PLATFORM;
    }

    static Platform findPlatform() {
        Jre8 buildIfSupported = Jre8.buildIfSupported();
        if (buildIfSupported != null) {
            return buildIfSupported;
        }
        Jre7 buildIfSupported2 = Jre7.buildIfSupported();
        return buildIfSupported2 != null ? buildIfSupported2 : Jre6.build();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class Jre8 extends Jre7 {
        Jre8() {
        }

        static Jre8 buildIfSupported() {
            try {
                Class.forName("java.io.UncheckedIOException");
                return new Jre8();
            } catch (ClassNotFoundException unused) {
                return null;
            }
        }

        @Override // zipkin2.internal.Platform
        public RuntimeException uncheckedIOException(IOException iOException) {
            return new UncheckedIOException(iOException);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class Jre7 extends Platform {
        Jre7() {
        }

        static Jre7 buildIfSupported() {
            try {
                Class.forName("java.util.concurrent.ThreadLocalRandom");
                return new Jre7();
            } catch (ClassNotFoundException unused) {
                return null;
            }
        }

        @Override // zipkin2.internal.Platform
        public AssertionError assertionError(String str, Throwable th) {
            return new AssertionError(str, th);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static final class Jre6 extends Platform {
        Jre6() {
        }

        static Jre6 build() {
            return new Jre6();
        }
    }
}
