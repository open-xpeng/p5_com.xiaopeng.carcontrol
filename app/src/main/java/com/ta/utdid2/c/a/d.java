package com.ta.utdid2.c.a;

import com.ta.utdid2.c.a.b;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: TransactionXMLFile.java */
/* loaded from: classes.dex */
public class d {
    private static final Object c = new Object();
    private File a;
    private final Object b = new Object();

    /* renamed from: a  reason: collision with other field name */
    private HashMap<File, a> f150a = new HashMap<>();

    public d(String str) {
        if (str != null && str.length() > 0) {
            this.a = new File(str);
            return;
        }
        throw new RuntimeException("Directory can not be empty");
    }

    private File a(File file, String str) {
        if (str.indexOf(File.separatorChar) < 0) {
            return new File(file, str);
        }
        throw new IllegalArgumentException("File " + str + " contains a path separator");
    }

    private File a() {
        File file;
        synchronized (this.b) {
            file = this.a;
        }
        return file;
    }

    private File b(String str) {
        return a(a(), String.valueOf(str) + ".xml");
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x0080, code lost:
        if (r2 == null) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00b9, code lost:
        if (r3 == null) goto L55;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:109:0x00c6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x00d3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00be A[Catch: all -> 0x0085, TRY_ENTER, TRY_LEAVE, TryCatch #4 {all -> 0x0085, blocks: (B:50:0x0082, B:74:0x00be), top: B:107:0x003e }] */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v18 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v27 */
    /* JADX WARN: Type inference failed for: r0v28 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.ta.utdid2.c.a.b a(java.lang.String r10, int r11) {
        /*
            Method dump skipped, instructions count: 246
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.c.a.d.a(java.lang.String, int):com.ta.utdid2.c.a.b");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static File a(File file) {
        return new File(String.valueOf(file.getPath()) + ".bak");
    }

    /* compiled from: TransactionXMLFile.java */
    /* loaded from: classes.dex */
    private static final class a implements b {
        private static final Object d = new Object();
        private WeakHashMap<b.InterfaceC0016b, Object> a;
        private final File b;
        private final int c;

        /* renamed from: c  reason: collision with other field name */
        private final File f151c;

        /* renamed from: c  reason: collision with other field name */
        private Map f152c;
        private boolean k = false;

        a(File file, int i, Map map) {
            this.b = file;
            this.f151c = d.a(file);
            this.c = i;
            this.f152c = map == null ? new HashMap() : map;
            this.a = new WeakHashMap<>();
        }

        @Override // com.ta.utdid2.c.a.b
        /* renamed from: a */
        public boolean mo75a() {
            return this.b != null && new File(this.b.getAbsolutePath()).exists();
        }

        public void a(boolean z) {
            synchronized (this) {
                this.k = z;
            }
        }

        public boolean c() {
            boolean z;
            synchronized (this) {
                z = this.k;
            }
            return z;
        }

        public void a(Map map) {
            if (map != null) {
                synchronized (this) {
                    this.f152c = map;
                }
            }
        }

        @Override // com.ta.utdid2.c.a.b
        public Map<String, ?> getAll() {
            HashMap hashMap;
            synchronized (this) {
                hashMap = new HashMap(this.f152c);
            }
            return hashMap;
        }

        @Override // com.ta.utdid2.c.a.b
        public String getString(String str, String str2) {
            synchronized (this) {
                String str3 = (String) this.f152c.get(str);
                if (str3 != null) {
                    str2 = str3;
                }
            }
            return str2;
        }

        @Override // com.ta.utdid2.c.a.b
        public long getLong(String str, long j) {
            synchronized (this) {
                Long l = (Long) this.f152c.get(str);
                if (l != null) {
                    j = l.longValue();
                }
            }
            return j;
        }

        /* compiled from: TransactionXMLFile.java */
        /* renamed from: com.ta.utdid2.c.a.d$a$a  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        public final class C0017a implements b.a {
            private final Map<String, Object> d = new HashMap();
            private boolean l = false;

            public C0017a() {
            }

            @Override // com.ta.utdid2.c.a.b.a
            public b.a a(String str, String str2) {
                synchronized (this) {
                    this.d.put(str, str2);
                }
                return this;
            }

            @Override // com.ta.utdid2.c.a.b.a
            public b.a a(String str, int i) {
                synchronized (this) {
                    this.d.put(str, Integer.valueOf(i));
                }
                return this;
            }

            @Override // com.ta.utdid2.c.a.b.a
            public b.a a(String str, long j) {
                synchronized (this) {
                    this.d.put(str, Long.valueOf(j));
                }
                return this;
            }

            @Override // com.ta.utdid2.c.a.b.a
            public b.a a(String str, float f) {
                synchronized (this) {
                    this.d.put(str, Float.valueOf(f));
                }
                return this;
            }

            @Override // com.ta.utdid2.c.a.b.a
            public b.a a(String str, boolean z) {
                synchronized (this) {
                    this.d.put(str, Boolean.valueOf(z));
                }
                return this;
            }

            @Override // com.ta.utdid2.c.a.b.a
            public b.a a(String str) {
                synchronized (this) {
                    this.d.put(str, this);
                }
                return this;
            }

            @Override // com.ta.utdid2.c.a.b.a
            public b.a b() {
                synchronized (this) {
                    this.l = true;
                }
                return this;
            }

            @Override // com.ta.utdid2.c.a.b.a
            public boolean commit() {
                boolean z;
                ArrayList arrayList;
                HashSet<b.InterfaceC0016b> hashSet;
                boolean d;
                synchronized (d.c) {
                    z = a.this.a.size() > 0;
                    arrayList = null;
                    if (z) {
                        arrayList = new ArrayList();
                        hashSet = new HashSet(a.this.a.keySet());
                    } else {
                        hashSet = null;
                    }
                    synchronized (this) {
                        if (this.l) {
                            a.this.f152c.clear();
                            this.l = false;
                        }
                        for (Map.Entry<String, Object> entry : this.d.entrySet()) {
                            String key = entry.getKey();
                            Object value = entry.getValue();
                            if (value == this) {
                                a.this.f152c.remove(key);
                            } else {
                                a.this.f152c.put(key, value);
                            }
                            if (z) {
                                arrayList.add(key);
                            }
                        }
                        this.d.clear();
                    }
                    d = a.this.d();
                    if (d) {
                        a.this.a(true);
                    }
                }
                if (z) {
                    for (int size = arrayList.size() - 1; size >= 0; size--) {
                        String str = (String) arrayList.get(size);
                        for (b.InterfaceC0016b interfaceC0016b : hashSet) {
                            if (interfaceC0016b != null) {
                                interfaceC0016b.a(a.this, str);
                            }
                        }
                    }
                }
                return d;
            }
        }

        @Override // com.ta.utdid2.c.a.b
        public b.a a() {
            return new C0017a();
        }

        private FileOutputStream a(File file) {
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (FileNotFoundException unused) {
                if (!file.getParentFile().mkdir()) {
                    return null;
                }
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (FileNotFoundException unused2) {
                    return null;
                }
            }
            return fileOutputStream;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean d() {
            if (this.b.exists()) {
                if (!this.f151c.exists()) {
                    if (!this.b.renameTo(this.f151c)) {
                        return false;
                    }
                } else {
                    this.b.delete();
                }
            }
            try {
                FileOutputStream a = a(this.b);
                if (a == null) {
                    return false;
                }
                e.a(this.f152c, a);
                a.close();
                this.f151c.delete();
                return true;
            } catch (IOException | XmlPullParserException unused) {
                if (this.b.exists()) {
                    this.b.delete();
                }
                return false;
            }
        }
    }
}
