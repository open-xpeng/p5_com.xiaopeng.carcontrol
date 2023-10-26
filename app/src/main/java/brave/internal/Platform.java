package brave.internal;

import brave.Clock;
import brave.Tracer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.time.Instant;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/* loaded from: classes.dex */
public abstract class Platform {
    volatile String linkLocalIp;
    private static final Platform PLATFORM = findPlatform();
    private static final Logger LOG = Logger.getLogger(Tracer.class.getName());

    @Nullable
    public abstract String getHostString(InetSocketAddress inetSocketAddress);

    public abstract long nextTraceIdHigh();

    public abstract long randomLong();

    @Nullable
    public String linkLocalIp() {
        if (this.linkLocalIp != null) {
            return this.linkLocalIp;
        }
        synchronized (this) {
            if (this.linkLocalIp == null) {
                this.linkLocalIp = produceLinkLocalIp();
            }
        }
        return this.linkLocalIp;
    }

    String produceLinkLocalIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces == null) {
                return null;
            }
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress nextElement = inetAddresses.nextElement();
                    if (nextElement.isSiteLocalAddress()) {
                        return nextElement.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            log("error reading nics", e);
        }
        return null;
    }

    public static Platform get() {
        return PLATFORM;
    }

    public void log(String str, @Nullable Throwable th) {
        Logger logger = LOG;
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, str, th);
        }
    }

    public void log(String str, Object obj, @Nullable Throwable th) {
        Logger logger = LOG;
        if (logger.isLoggable(Level.FINE)) {
            LogRecord logRecord = new LogRecord(Level.FINE, str);
            logRecord.setParameters(new Object[]{obj});
            if (th != null) {
                logRecord.setThrown(th);
            }
            logger.log(logRecord);
        }
    }

    static Platform findPlatform() {
        Jre9 buildIfSupported = Jre9.buildIfSupported();
        if (buildIfSupported != null) {
            return buildIfSupported;
        }
        Jre7 buildIfSupported2 = Jre7.buildIfSupported();
        return buildIfSupported2 != null ? buildIfSupported2 : new Jre6();
    }

    public Clock clock() {
        return new Clock() { // from class: brave.internal.Platform.1
            public String toString() {
                return "System.currentTimeMillis()";
            }

            @Override // brave.Clock
            public long currentTimeMicroseconds() {
                return System.currentTimeMillis() * 1000;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class Jre9 extends Jre7 {
        @Override // brave.internal.Platform.Jre7
        public String toString() {
            return "Jre9{}";
        }

        Jre9() {
        }

        static Jre9 buildIfSupported() {
            try {
                Class.forName("java.time.Clock").getMethod("tickMillis", Class.forName("java.time.ZoneId"));
                return new Jre9();
            } catch (ClassNotFoundException | NoSuchMethodException unused) {
                return null;
            }
        }

        @Override // brave.internal.Platform
        public Clock clock() {
            return new Clock() { // from class: brave.internal.Platform.Jre9.1
                public String toString() {
                    return "Clock.systemUTC().instant()";
                }

                @Override // brave.Clock
                public long currentTimeMicroseconds() {
                    Instant instant = java.time.Clock.systemUTC().instant();
                    return (instant.getEpochSecond() * 1000000) + (instant.getNano() / 1000);
                }
            };
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class Jre7 extends Platform {
        public String toString() {
            return "Jre7{}";
        }

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

        @Override // brave.internal.Platform
        public String getHostString(InetSocketAddress inetSocketAddress) {
            return inetSocketAddress.getHostString();
        }

        @Override // brave.internal.Platform
        public long randomLong() {
            return ThreadLocalRandom.current().nextLong();
        }

        @Override // brave.internal.Platform
        public long nextTraceIdHigh() {
            return nextTraceIdHigh(ThreadLocalRandom.current().nextInt());
        }
    }

    static long nextTraceIdHigh(int i) {
        return (((System.currentTimeMillis() / 1000) & 4294967295L) << 32) | (4294967295L & i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class Jre6 extends Platform {
        final Random prng = new Random(System.nanoTime());

        public String toString() {
            return "Jre6{}";
        }

        @Override // brave.internal.Platform
        public String getHostString(InetSocketAddress inetSocketAddress) {
            return inetSocketAddress.getAddress().getHostAddress();
        }

        @Override // brave.internal.Platform
        public long randomLong() {
            return this.prng.nextLong();
        }

        @Override // brave.internal.Platform
        public long nextTraceIdHigh() {
            return nextTraceIdHigh(this.prng.nextInt());
        }

        Jre6() {
        }
    }
}
