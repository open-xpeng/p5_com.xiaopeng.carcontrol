package brave.internal;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class MapPropagationFields extends PropagationFields {
    volatile Map<String, String> values;

    protected MapPropagationFields() {
    }

    protected MapPropagationFields(Map<String, String> map) {
        this.values = Collections.unmodifiableMap(map);
    }

    protected MapPropagationFields(MapPropagationFields mapPropagationFields) {
        this.values = mapPropagationFields.values;
    }

    @Override // brave.internal.PropagationFields
    public String get(String str) {
        Map<String, String> map;
        synchronized (this) {
            map = this.values;
        }
        if (map != null) {
            return map.get(str);
        }
        return null;
    }

    @Override // brave.internal.PropagationFields
    public final void put(String str, String str2) {
        LinkedHashMap linkedHashMap;
        synchronized (this) {
            Map<String, String> map = this.values;
            if (map == null) {
                linkedHashMap = new LinkedHashMap();
                linkedHashMap.put(str, str2);
            } else if (str2.equals(map.get(str))) {
                return;
            } else {
                LinkedHashMap linkedHashMap2 = new LinkedHashMap(map);
                linkedHashMap2.put(str, str2);
                linkedHashMap = linkedHashMap2;
            }
            this.values = Collections.unmodifiableMap(linkedHashMap);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // brave.internal.PropagationFields
    public final void putAllIfAbsent(PropagationFields propagationFields) {
        Map<String, String> map;
        if ((propagationFields instanceof MapPropagationFields) && (map = ((MapPropagationFields) propagationFields).values) != null) {
            synchronized (this) {
                if (this.values == null) {
                    this.values = map;
                    return;
                }
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    if (!this.values.containsKey(entry.getKey())) {
                        put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
    }

    @Override // brave.internal.PropagationFields
    public final Map<String, String> toMap() {
        Map<String, String> map;
        synchronized (this) {
            map = this.values;
        }
        return map == null ? Collections.emptyMap() : map;
    }

    public int hashCode() {
        if (this.values == null) {
            return 0;
        }
        return this.values.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof MapPropagationFields) {
            MapPropagationFields mapPropagationFields = (MapPropagationFields) obj;
            return this.values == null ? mapPropagationFields.values == null : this.values.equals(mapPropagationFields.values);
        }
        return false;
    }
}
