package brave.handler;

import brave.Span;
import brave.internal.IpLiteral;
import brave.internal.Nullable;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes.dex */
public final class MutableSpan implements Cloneable {
    ArrayList<Object> annotations;
    Throwable error;
    long finishTimestamp;
    Span.Kind kind;
    String localIp;
    int localPort;
    String localServiceName;
    String name;
    String remoteIp;
    int remotePort;
    String remoteServiceName;
    boolean shared;
    long startTimestamp;
    ArrayList<String> tags = new ArrayList<>();

    /* loaded from: classes.dex */
    public interface AnnotationConsumer<T> {
        void accept(T t, long j, String str);
    }

    /* loaded from: classes.dex */
    public interface AnnotationUpdater {
        @Nullable
        String update(long j, String str);
    }

    /* loaded from: classes.dex */
    public interface TagConsumer<T> {
        void accept(T t, String str, String str2);
    }

    /* loaded from: classes.dex */
    public interface TagUpdater {
        @Nullable
        String update(String str, String str2);
    }

    @Nullable
    public String name() {
        return this.name;
    }

    public void name(String str) {
        Objects.requireNonNull(str, "name == null");
        this.name = str;
    }

    public long startTimestamp() {
        return this.startTimestamp;
    }

    public void startTimestamp(long j) {
        this.startTimestamp = j;
    }

    public long finishTimestamp() {
        return this.finishTimestamp;
    }

    public void finishTimestamp(long j) {
        this.finishTimestamp = j;
    }

    public Span.Kind kind() {
        return this.kind;
    }

    public void kind(Span.Kind kind) {
        Objects.requireNonNull(kind, "kind == null");
        this.kind = kind;
    }

    @Nullable
    public String localServiceName() {
        return this.localServiceName;
    }

    public void localServiceName(String str) {
        if (str == null || str.isEmpty()) {
            throw new NullPointerException("localServiceName is empty");
        }
        this.localServiceName = str.toLowerCase(Locale.ROOT);
    }

    @Nullable
    public String localIp() {
        return this.localIp;
    }

    public boolean localIp(@Nullable String str) {
        this.localIp = IpLiteral.ipOrNull(str);
        return true;
    }

    public int localPort() {
        return this.localPort;
    }

    public void localPort(int i) {
        if (i > 65535) {
            throw new IllegalArgumentException("invalid port " + i);
        }
        if (i < 0) {
            i = 0;
        }
        this.localPort = i;
    }

    @Nullable
    public String remoteServiceName() {
        return this.remoteServiceName;
    }

    public void remoteServiceName(String str) {
        if (str == null || str.isEmpty()) {
            throw new NullPointerException("remoteServiceName is empty");
        }
        this.remoteServiceName = str.toLowerCase(Locale.ROOT);
    }

    @Nullable
    public String remoteIp() {
        return this.remoteIp;
    }

    public int remotePort() {
        return this.remotePort;
    }

    public boolean remoteIpAndPort(@Nullable String str, int i) {
        if (str == null) {
            return false;
        }
        String ipOrNull = IpLiteral.ipOrNull(str);
        this.remoteIp = ipOrNull;
        if (ipOrNull == null) {
            return false;
        }
        if (i > 65535) {
            throw new IllegalArgumentException("invalid port " + i);
        }
        if (i < 0) {
            i = 0;
        }
        this.remotePort = i;
        return true;
    }

    public void annotate(long j, String str) {
        Objects.requireNonNull(str, "value == null");
        if (j == 0) {
            return;
        }
        if (this.annotations == null) {
            this.annotations = new ArrayList<>();
        }
        this.annotations.add(Long.valueOf(j));
        this.annotations.add(str);
    }

    public Throwable error() {
        return this.error;
    }

    public void error(Throwable th) {
        this.error = th;
    }

    @Nullable
    public String tag(String str) {
        Objects.requireNonNull(str, "key == null");
        if (str.isEmpty()) {
            throw new IllegalArgumentException("key is empty");
        }
        String str2 = null;
        int size = this.tags.size();
        for (int i = 0; i < size; i += 2) {
            if (str.equals(this.tags.get(i))) {
                str2 = this.tags.get(i + 1);
            }
        }
        return str2;
    }

    public void tag(String str, String str2) {
        Objects.requireNonNull(str, "key == null");
        if (str.isEmpty()) {
            throw new IllegalArgumentException("key is empty");
        }
        if (str2 == null) {
            throw new NullPointerException("value of " + str + " == null");
        }
        int size = this.tags.size();
        for (int i = 0; i < size; i += 2) {
            if (str.equals(this.tags.get(i))) {
                this.tags.set(i + 1, str2);
                return;
            }
        }
        this.tags.add(str);
        this.tags.add(str2);
    }

    public <T> void forEachTag(TagConsumer<T> tagConsumer, T t) {
        int size = this.tags.size();
        for (int i = 0; i < size; i += 2) {
            tagConsumer.accept(t, this.tags.get(i), this.tags.get(i + 1));
        }
    }

    public void forEachTag(TagUpdater tagUpdater) {
        int size = this.tags.size();
        int i = 0;
        while (i < size) {
            String str = this.tags.get(i + 1);
            if (updateOrRemove(this.tags, i, str, tagUpdater.update(this.tags.get(i), str))) {
                size -= 2;
                i -= 2;
            }
            i += 2;
        }
    }

    public <T> void forEachAnnotation(AnnotationConsumer<T> annotationConsumer, T t) {
        ArrayList<Object> arrayList = this.annotations;
        if (arrayList == null) {
            return;
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i += 2) {
            annotationConsumer.accept(t, ((Long) this.annotations.get(i)).longValue(), this.annotations.get(i + 1).toString());
        }
    }

    public void forEachAnnotation(AnnotationUpdater annotationUpdater) {
        ArrayList<Object> arrayList = this.annotations;
        if (arrayList == null) {
            return;
        }
        int i = 0;
        int size = arrayList.size();
        while (i < size) {
            String obj = this.annotations.get(i + 1).toString();
            if (updateOrRemove(this.annotations, i, obj, annotationUpdater.update(((Long) this.annotations.get(i)).longValue(), obj))) {
                size -= 2;
                i -= 2;
            }
            i += 2;
        }
    }

    static boolean updateOrRemove(ArrayList arrayList, int i, Object obj, @Nullable Object obj2) {
        if (obj2 == null) {
            arrayList.remove(i);
            arrayList.remove(i);
            return true;
        } else if (obj.equals(obj2)) {
            return false;
        } else {
            arrayList.set(i + 1, obj2);
            return false;
        }
    }

    public boolean shared() {
        return this.shared;
    }

    public void setShared() {
        this.shared = true;
    }
}
