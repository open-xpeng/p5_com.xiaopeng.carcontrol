package com.O000000o.O000000o.O00000Oo;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

/* compiled from: LinkedTreeMap.java */
/* loaded from: classes.dex */
public final class O0000Oo0<K, V> extends AbstractMap<K, V> implements Serializable {
    static final /* synthetic */ boolean O00000oo = true;
    private static final Comparator<Comparable> O0000O0o = new Comparator<Comparable>() { // from class: com.O000000o.O000000o.O00000Oo.O0000Oo0.1
        @Override // java.util.Comparator
        /* renamed from: O000000o */
        public int compare(Comparable comparable, Comparable comparable2) {
            return comparable.compareTo(comparable2);
        }
    };
    Comparator<? super K> O000000o;
    O00000o<K, V> O00000Oo;
    int O00000o;
    int O00000o0;
    final O00000o<K, V> O00000oO;
    private O0000Oo0<K, V>.O000000o O0000OOo;
    private O0000Oo0<K, V>.O00000Oo O0000Oo0;

    /* compiled from: LinkedTreeMap.java */
    /* loaded from: classes.dex */
    class O000000o extends AbstractSet<Map.Entry<K, V>> {
        O000000o() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            O0000Oo0.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return (obj instanceof Map.Entry) && O0000Oo0.this.O000000o((Map.Entry) obj) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<Map.Entry<K, V>> iterator() {
            return new O0000Oo0<K, V>.O00000o0<Map.Entry<K, V>>() { // from class: com.O000000o.O000000o.O00000Oo.O0000Oo0.O000000o.1
                {
                    O0000Oo0 o0000Oo0 = O0000Oo0.this;
                }

                @Override // java.util.Iterator
                /* renamed from: O000000o */
                public Map.Entry<K, V> next() {
                    return O00000Oo();
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            O00000o<K, V> O000000o;
            if ((obj instanceof Map.Entry) && (O000000o = O0000Oo0.this.O000000o((Map.Entry) obj)) != null) {
                O0000Oo0.this.O000000o((O00000o) O000000o, true);
                return true;
            }
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return O0000Oo0.this.O00000o0;
        }
    }

    /* compiled from: LinkedTreeMap.java */
    /* loaded from: classes.dex */
    class O00000Oo extends AbstractSet<K> {
        O00000Oo() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public void clear() {
            O0000Oo0.this.clear();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean contains(Object obj) {
            return O0000Oo0.this.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public Iterator<K> iterator() {
            return new O0000Oo0<K, V>.O00000o0<K>() { // from class: com.O000000o.O000000o.O00000Oo.O0000Oo0.O00000Oo.1
                {
                    O0000Oo0 o0000Oo0 = O0000Oo0.this;
                }

                @Override // java.util.Iterator
                public K next() {
                    return O00000Oo().O00000oo;
                }
            };
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean remove(Object obj) {
            return O0000Oo0.this.O00000Oo(obj) != null;
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return O0000Oo0.this.O00000o0;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: LinkedTreeMap.java */
    /* loaded from: classes.dex */
    public static final class O00000o<K, V> implements Map.Entry<K, V> {
        O00000o<K, V> O000000o;
        O00000o<K, V> O00000Oo;
        O00000o<K, V> O00000o;
        O00000o<K, V> O00000o0;
        O00000o<K, V> O00000oO;
        final K O00000oo;
        V O0000O0o;
        int O0000OOo;

        O00000o() {
            this.O00000oo = null;
            this.O00000oO = this;
            this.O00000o = this;
        }

        O00000o(O00000o<K, V> o00000o, K k, O00000o<K, V> o00000o2, O00000o<K, V> o00000o3) {
            this.O000000o = o00000o;
            this.O00000oo = k;
            this.O0000OOo = 1;
            this.O00000o = o00000o2;
            this.O00000oO = o00000o3;
            o00000o3.O00000o = this;
            o00000o2.O00000oO = this;
        }

        public O00000o<K, V> O000000o() {
            O00000o<K, V> o00000o = this;
            for (O00000o<K, V> o00000o2 = this.O00000Oo; o00000o2 != null; o00000o2 = o00000o2.O00000Oo) {
                o00000o = o00000o2;
            }
            return o00000o;
        }

        public O00000o<K, V> O00000Oo() {
            O00000o<K, V> o00000o = this;
            for (O00000o<K, V> o00000o2 = this.O00000o0; o00000o2 != null; o00000o2 = o00000o2.O00000o0) {
                o00000o = o00000o2;
            }
            return o00000o;
        }

        @Override // java.util.Map.Entry
        public boolean equals(Object obj) {
            if (obj instanceof Map.Entry) {
                Map.Entry entry = (Map.Entry) obj;
                K k = this.O00000oo;
                if (k == null) {
                    if (entry.getKey() != null) {
                        return false;
                    }
                } else if (!k.equals(entry.getKey())) {
                    return false;
                }
                V v = this.O0000O0o;
                Object value = entry.getValue();
                if (v == null) {
                    if (value != null) {
                        return false;
                    }
                } else if (!v.equals(value)) {
                    return false;
                }
                return true;
            }
            return false;
        }

        @Override // java.util.Map.Entry
        public K getKey() {
            return this.O00000oo;
        }

        @Override // java.util.Map.Entry
        public V getValue() {
            return this.O0000O0o;
        }

        @Override // java.util.Map.Entry
        public int hashCode() {
            K k = this.O00000oo;
            int hashCode = k == null ? 0 : k.hashCode();
            V v = this.O0000O0o;
            return hashCode ^ (v != null ? v.hashCode() : 0);
        }

        @Override // java.util.Map.Entry
        public V setValue(V v) {
            V v2 = this.O0000O0o;
            this.O0000O0o = v;
            return v2;
        }

        public String toString() {
            return this.O00000oo + "=" + this.O0000O0o;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* compiled from: LinkedTreeMap.java */
    /* loaded from: classes.dex */
    public abstract class O00000o0<T> implements Iterator<T> {
        O00000o<K, V> O00000Oo;
        int O00000o;
        O00000o<K, V> O00000o0;

        private O00000o0() {
            this.O00000Oo = O0000Oo0.this.O00000oO.O00000o;
            this.O00000o0 = null;
            this.O00000o = O0000Oo0.this.O00000o;
        }

        final O00000o<K, V> O00000Oo() {
            O00000o<K, V> o00000o = this.O00000Oo;
            if (o00000o != O0000Oo0.this.O00000oO) {
                if (O0000Oo0.this.O00000o == this.O00000o) {
                    this.O00000Oo = o00000o.O00000o;
                    this.O00000o0 = o00000o;
                    return o00000o;
                }
                throw new ConcurrentModificationException();
            }
            throw new NoSuchElementException();
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.O00000Oo != O0000Oo0.this.O00000oO;
        }

        @Override // java.util.Iterator
        public final void remove() {
            O00000o<K, V> o00000o = this.O00000o0;
            if (o00000o == null) {
                throw new IllegalStateException();
            }
            O0000Oo0.this.O000000o((O00000o) o00000o, true);
            this.O00000o0 = null;
            this.O00000o = O0000Oo0.this.O00000o;
        }
    }

    public O0000Oo0() {
        this(O0000O0o);
    }

    public O0000Oo0(Comparator<? super K> comparator) {
        this.O00000o0 = 0;
        this.O00000o = 0;
        this.O00000oO = new O00000o<>();
        this.O000000o = comparator == null ? O0000O0o : comparator;
    }

    private Object O000000o() throws ObjectStreamException {
        return new LinkedHashMap(this);
    }

    private void O000000o(O00000o<K, V> o00000o) {
        O00000o<K, V> o00000o2 = o00000o.O00000Oo;
        O00000o<K, V> o00000o3 = o00000o.O00000o0;
        O00000o<K, V> o00000o4 = o00000o3.O00000Oo;
        O00000o<K, V> o00000o5 = o00000o3.O00000o0;
        o00000o.O00000o0 = o00000o4;
        if (o00000o4 != null) {
            o00000o4.O000000o = o00000o;
        }
        O000000o((O00000o) o00000o, (O00000o) o00000o3);
        o00000o3.O00000Oo = o00000o;
        o00000o.O000000o = o00000o3;
        o00000o.O0000OOo = Math.max(o00000o2 != null ? o00000o2.O0000OOo : 0, o00000o4 != null ? o00000o4.O0000OOo : 0) + 1;
        o00000o3.O0000OOo = Math.max(o00000o.O0000OOo, o00000o5 != null ? o00000o5.O0000OOo : 0) + 1;
    }

    private void O000000o(O00000o<K, V> o00000o, O00000o<K, V> o00000o2) {
        O00000o<K, V> o00000o3 = o00000o.O000000o;
        o00000o.O000000o = null;
        if (o00000o2 != null) {
            o00000o2.O000000o = o00000o3;
        }
        if (o00000o3 == null) {
            this.O00000Oo = o00000o2;
        } else if (o00000o3.O00000Oo == o00000o) {
            o00000o3.O00000Oo = o00000o2;
        } else if (!O00000oo && o00000o3.O00000o0 != o00000o) {
            throw new AssertionError();
        } else {
            o00000o3.O00000o0 = o00000o2;
        }
    }

    private boolean O000000o(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    private void O00000Oo(O00000o<K, V> o00000o) {
        O00000o<K, V> o00000o2 = o00000o.O00000Oo;
        O00000o<K, V> o00000o3 = o00000o.O00000o0;
        O00000o<K, V> o00000o4 = o00000o2.O00000Oo;
        O00000o<K, V> o00000o5 = o00000o2.O00000o0;
        o00000o.O00000Oo = o00000o5;
        if (o00000o5 != null) {
            o00000o5.O000000o = o00000o;
        }
        O000000o((O00000o) o00000o, (O00000o) o00000o2);
        o00000o2.O00000o0 = o00000o;
        o00000o.O000000o = o00000o2;
        o00000o.O0000OOo = Math.max(o00000o3 != null ? o00000o3.O0000OOo : 0, o00000o5 != null ? o00000o5.O0000OOo : 0) + 1;
        o00000o2.O0000OOo = Math.max(o00000o.O0000OOo, o00000o4 != null ? o00000o4.O0000OOo : 0) + 1;
    }

    private void O00000Oo(O00000o<K, V> o00000o, boolean z) {
        while (o00000o != null) {
            O00000o<K, V> o00000o2 = o00000o.O00000Oo;
            O00000o<K, V> o00000o3 = o00000o.O00000o0;
            int i = o00000o2 != null ? o00000o2.O0000OOo : 0;
            int i2 = o00000o3 != null ? o00000o3.O0000OOo : 0;
            int i3 = i - i2;
            if (i3 == -2) {
                O00000o<K, V> o00000o4 = o00000o3.O00000Oo;
                O00000o<K, V> o00000o5 = o00000o3.O00000o0;
                int i4 = (o00000o4 != null ? o00000o4.O0000OOo : 0) - (o00000o5 != null ? o00000o5.O0000OOo : 0);
                if (i4 != -1 && (i4 != 0 || z)) {
                    if (!O00000oo && i4 != 1) {
                        throw new AssertionError();
                    }
                    O00000Oo((O00000o) o00000o3);
                }
                O000000o((O00000o) o00000o);
                if (z) {
                    return;
                }
            } else if (i3 == 2) {
                O00000o<K, V> o00000o6 = o00000o2.O00000Oo;
                O00000o<K, V> o00000o7 = o00000o2.O00000o0;
                int i5 = (o00000o6 != null ? o00000o6.O0000OOo : 0) - (o00000o7 != null ? o00000o7.O0000OOo : 0);
                if (i5 != 1 && (i5 != 0 || z)) {
                    if (!O00000oo && i5 != -1) {
                        throw new AssertionError();
                    }
                    O000000o((O00000o) o00000o2);
                }
                O00000Oo((O00000o) o00000o);
                if (z) {
                    return;
                }
            } else if (i3 == 0) {
                o00000o.O0000OOo = i + 1;
                if (z) {
                    return;
                }
            } else if (!O00000oo && i3 != -1 && i3 != 1) {
                throw new AssertionError();
            } else {
                o00000o.O0000OOo = Math.max(i, i2) + 1;
                if (!z) {
                    return;
                }
            }
            o00000o = o00000o.O000000o;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    O00000o<K, V> O000000o(Object obj) {
        if (obj != 0) {
            try {
                return O000000o((O0000Oo0<K, V>) obj, false);
            } catch (ClassCastException unused) {
                return null;
            }
        }
        return null;
    }

    O00000o<K, V> O000000o(K k, boolean z) {
        int i;
        O00000o<K, V> o00000o;
        Comparator<? super K> comparator = this.O000000o;
        O00000o<K, V> o00000o2 = this.O00000Oo;
        if (o00000o2 != null) {
            Comparable comparable = comparator == O0000O0o ? (Comparable) k : null;
            while (true) {
                Object obj = (K) o00000o2.O00000oo;
                i = comparable != null ? comparable.compareTo(obj) : comparator.compare(k, obj);
                if (i == 0) {
                    return o00000o2;
                }
                O00000o<K, V> o00000o3 = i < 0 ? o00000o2.O00000Oo : o00000o2.O00000o0;
                if (o00000o3 == null) {
                    break;
                }
                o00000o2 = o00000o3;
            }
        } else {
            i = 0;
        }
        if (z) {
            O00000o<K, V> o00000o4 = this.O00000oO;
            if (o00000o2 != null) {
                o00000o = new O00000o<>(o00000o2, k, o00000o4, o00000o4.O00000oO);
                if (i < 0) {
                    o00000o2.O00000Oo = o00000o;
                } else {
                    o00000o2.O00000o0 = o00000o;
                }
                O00000Oo(o00000o2, true);
            } else if (comparator == O0000O0o && !(k instanceof Comparable)) {
                throw new ClassCastException(k.getClass().getName() + " is not Comparable");
            } else {
                o00000o = new O00000o<>(o00000o2, k, o00000o4, o00000o4.O00000oO);
                this.O00000Oo = o00000o;
            }
            this.O00000o0++;
            this.O00000o++;
            return o00000o;
        }
        return null;
    }

    O00000o<K, V> O000000o(Map.Entry<?, ?> entry) {
        O00000o<K, V> O000000o2 = O000000o(entry.getKey());
        if (O000000o2 != null && O000000o(O000000o2.O0000O0o, entry.getValue())) {
            return O000000o2;
        }
        return null;
    }

    void O000000o(O00000o<K, V> o00000o, boolean z) {
        int i;
        if (z) {
            o00000o.O00000oO.O00000o = o00000o.O00000o;
            o00000o.O00000o.O00000oO = o00000o.O00000oO;
        }
        O00000o<K, V> o00000o2 = o00000o.O00000Oo;
        O00000o<K, V> o00000o3 = o00000o.O00000o0;
        O00000o<K, V> o00000o4 = o00000o.O000000o;
        int i2 = 0;
        if (o00000o2 == null || o00000o3 == null) {
            if (o00000o2 != null) {
                O000000o((O00000o) o00000o, (O00000o) o00000o2);
                o00000o.O00000Oo = null;
            } else if (o00000o3 != null) {
                O000000o((O00000o) o00000o, (O00000o) o00000o3);
                o00000o.O00000o0 = null;
            } else {
                O000000o((O00000o) o00000o, (O00000o) null);
            }
            O00000Oo(o00000o4, false);
            this.O00000o0--;
            this.O00000o++;
            return;
        }
        O00000o<K, V> O00000Oo2 = o00000o2.O0000OOo > o00000o3.O0000OOo ? o00000o2.O00000Oo() : o00000o3.O000000o();
        O000000o((O00000o) O00000Oo2, false);
        O00000o<K, V> o00000o5 = o00000o.O00000Oo;
        if (o00000o5 != null) {
            i = o00000o5.O0000OOo;
            O00000Oo2.O00000Oo = o00000o5;
            o00000o5.O000000o = O00000Oo2;
            o00000o.O00000Oo = null;
        } else {
            i = 0;
        }
        O00000o<K, V> o00000o6 = o00000o.O00000o0;
        if (o00000o6 != null) {
            i2 = o00000o6.O0000OOo;
            O00000Oo2.O00000o0 = o00000o6;
            o00000o6.O000000o = O00000Oo2;
            o00000o.O00000o0 = null;
        }
        O00000Oo2.O0000OOo = Math.max(i, i2) + 1;
        O000000o((O00000o) o00000o, (O00000o) O00000Oo2);
    }

    O00000o<K, V> O00000Oo(Object obj) {
        O00000o<K, V> O000000o2 = O000000o(obj);
        if (O000000o2 != null) {
            O000000o((O00000o) O000000o2, true);
        }
        return O000000o2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        this.O00000Oo = null;
        this.O00000o0 = 0;
        this.O00000o++;
        O00000o<K, V> o00000o = this.O00000oO;
        o00000o.O00000oO = o00000o;
        o00000o.O00000o = o00000o;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        return O000000o(obj) != null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<Map.Entry<K, V>> entrySet() {
        O0000Oo0<K, V>.O000000o o000000o = this.O0000OOo;
        if (o000000o != null) {
            return o000000o;
        }
        O0000Oo0<K, V>.O000000o o000000o2 = new O000000o();
        this.O0000OOo = o000000o2;
        return o000000o2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V get(Object obj) {
        O00000o<K, V> O000000o2 = O000000o(obj);
        if (O000000o2 != null) {
            return O000000o2.O0000O0o;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Set<K> keySet() {
        O0000Oo0<K, V>.O00000Oo o00000Oo = this.O0000Oo0;
        if (o00000Oo != null) {
            return o00000Oo;
        }
        O0000Oo0<K, V>.O00000Oo o00000Oo2 = new O00000Oo();
        this.O0000Oo0 = o00000Oo2;
        return o00000Oo2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V put(K k, V v) {
        Objects.requireNonNull(k, "key == null");
        O00000o<K, V> O000000o2 = O000000o((O0000Oo0<K, V>) k, true);
        V v2 = O000000o2.O0000O0o;
        O000000o2.O0000O0o = v;
        return v2;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public V remove(Object obj) {
        O00000o<K, V> O00000Oo2 = O00000Oo(obj);
        if (O00000Oo2 != null) {
            return O00000Oo2.O0000O0o;
        }
        return null;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public int size() {
        return this.O00000o0;
    }
}
