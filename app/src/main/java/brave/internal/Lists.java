package brave.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class Lists {
    public static List<Object> ensureMutable(List<Object> list) {
        if (list instanceof ArrayList) {
            return list;
        }
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(list.get(i));
        }
        return arrayList;
    }

    public static List<Object> ensureImmutable(List<Object> list) {
        return isImmutable(list) ? list : list.size() == 1 ? Collections.singletonList(list.get(0)) : Collections.unmodifiableList(new ArrayList(list));
    }

    static boolean isImmutable(List<Object> list) {
        if (list == Collections.EMPTY_LIST) {
            return true;
        }
        String simpleName = list.getClass().getSimpleName();
        return simpleName.equals("SingletonList") || simpleName.startsWith("Unmodifiable") || simpleName.contains("Immutable");
    }

    public static List<Object> concatImmutableLists(List<Object> list, List<Object> list2) {
        int size = list.size();
        if (size == 0) {
            return list2;
        }
        int size2 = list2.size();
        if (size2 == 0) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < size; i++) {
            arrayList.add(list.get(i));
        }
        for (int i2 = 0; i2 < size2; i2++) {
            arrayList.add(list2.get(i2));
        }
        return Collections.unmodifiableList(arrayList);
    }

    Lists() {
    }
}
