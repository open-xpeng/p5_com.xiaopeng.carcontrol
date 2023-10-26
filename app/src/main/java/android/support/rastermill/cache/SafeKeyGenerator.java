package android.support.rastermill.cache;

import android.support.rastermill.util.Util;
import android.util.LruCache;

/* loaded from: classes.dex */
public class SafeKeyGenerator {
    private final LruCache<String, String> loadIdToSafeHash = new LruCache<>(1000);

    public String getSafeKey(String str) {
        String str2;
        if (str == null) {
            return null;
        }
        synchronized (this.loadIdToSafeHash) {
            str2 = this.loadIdToSafeHash.get(str);
        }
        if (str2 == null && (str2 = Util.getSha265Hex(str)) != null) {
            synchronized (this.loadIdToSafeHash) {
                this.loadIdToSafeHash.put(str, str2);
            }
        }
        return str2;
    }
}
