package brave.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class PredefinedPropagationFields extends PropagationFields {
    final String[] fieldNames;
    volatile String[] values;

    /* JADX INFO: Access modifiers changed from: protected */
    public PredefinedPropagationFields(String... strArr) {
        this.fieldNames = strArr;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public PredefinedPropagationFields(PredefinedPropagationFields predefinedPropagationFields, String... strArr) {
        this.fieldNames = strArr;
        checkSameFields(predefinedPropagationFields);
        this.values = predefinedPropagationFields.values;
    }

    @Override // brave.internal.PropagationFields
    public String get(String str) {
        int indexOf = indexOf(str);
        if (indexOf != -1) {
            return get(indexOf);
        }
        return null;
    }

    public String get(int i) {
        String[] strArr;
        if (i >= this.fieldNames.length) {
            return null;
        }
        synchronized (this) {
            strArr = this.values;
        }
        if (strArr != null) {
            return strArr[i];
        }
        return null;
    }

    @Override // brave.internal.PropagationFields
    public final void put(String str, String str2) {
        int indexOf = indexOf(str);
        if (indexOf == -1) {
            return;
        }
        put(indexOf, str2);
    }

    public final void put(int i, String str) {
        String[] strArr;
        if (i >= this.fieldNames.length) {
            return;
        }
        synchronized (this) {
            String[] strArr2 = this.values;
            if (strArr2 == null) {
                strArr = new String[this.fieldNames.length];
                strArr[i] = str;
            } else if (str.equals(strArr2[i])) {
                return;
            } else {
                strArr = (String[]) Arrays.copyOf(strArr2, strArr2.length);
                strArr[i] = str;
            }
            this.values = strArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // brave.internal.PropagationFields
    public final void putAllIfAbsent(PropagationFields propagationFields) {
        if (propagationFields instanceof PredefinedPropagationFields) {
            PredefinedPropagationFields predefinedPropagationFields = (PredefinedPropagationFields) propagationFields;
            checkSameFields(predefinedPropagationFields);
            String[] strArr = predefinedPropagationFields.values;
            if (strArr == null) {
                return;
            }
            for (int i = 0; i < strArr.length; i++) {
                if (strArr[i] != null && get(i) == null) {
                    put(i, strArr[i]);
                }
            }
        }
    }

    void checkSameFields(PredefinedPropagationFields predefinedPropagationFields) {
        if (!Arrays.equals(this.fieldNames, predefinedPropagationFields.fieldNames)) {
            throw new IllegalStateException(String.format("Mixed name configuration unsupported: found %s, expected %s", Arrays.toString(this.fieldNames), Arrays.toString(predefinedPropagationFields.fieldNames)));
        }
    }

    @Override // brave.internal.PropagationFields
    public final Map<String, String> toMap() {
        String[] strArr;
        synchronized (this) {
            strArr = this.values;
        }
        if (strArr == null) {
            return Collections.emptyMap();
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int length = this.fieldNames.length;
        for (int i = 0; i < length; i++) {
            String str = strArr[i];
            if (str != null) {
                linkedHashMap.put(this.fieldNames[i], str);
            }
        }
        return linkedHashMap;
    }

    int indexOf(String str) {
        int length = this.fieldNames.length;
        for (int i = 0; i < length; i++) {
            if (this.fieldNames[i].equals(str)) {
                return i;
            }
        }
        return -1;
    }

    public int hashCode() {
        if (this.values == null) {
            return 0;
        }
        return Arrays.hashCode(this.values);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof PredefinedPropagationFields) {
            PredefinedPropagationFields predefinedPropagationFields = (PredefinedPropagationFields) obj;
            return this.values == null ? predefinedPropagationFields.values == null : Arrays.equals(this.values, predefinedPropagationFields.values);
        }
        return false;
    }
}
