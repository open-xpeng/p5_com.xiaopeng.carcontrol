package org.apache.commons.compress.compressors;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes3.dex */
public class FileNameUtil {
    private final Map<String, String> compressSuffix = new HashMap();
    private final String defaultExtension;
    private final int longestCompressedSuffix;
    private final int longestUncompressedSuffix;
    private final int shortestCompressedSuffix;
    private final int shortestUncompressedSuffix;
    private final Map<String, String> uncompressSuffix;

    public FileNameUtil(Map<String, String> map, String str) {
        this.uncompressSuffix = Collections.unmodifiableMap(map);
        int i = Integer.MAX_VALUE;
        int i2 = Integer.MIN_VALUE;
        int i3 = Integer.MIN_VALUE;
        int i4 = Integer.MAX_VALUE;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            int length = entry.getKey().length();
            i2 = length > i2 ? length : i2;
            i = length < i ? length : i;
            String value = entry.getValue();
            int length2 = value.length();
            if (length2 > 0) {
                if (!this.compressSuffix.containsKey(value)) {
                    this.compressSuffix.put(value, entry.getKey());
                }
                i3 = length2 > i3 ? length2 : i3;
                if (length2 < i4) {
                    i4 = length2;
                }
            }
        }
        this.longestCompressedSuffix = i2;
        this.longestUncompressedSuffix = i3;
        this.shortestCompressedSuffix = i;
        this.shortestUncompressedSuffix = i4;
        this.defaultExtension = str;
    }

    public boolean isCompressedFilename(String str) {
        String lowerCase = str.toLowerCase(Locale.ENGLISH);
        int length = lowerCase.length();
        for (int i = this.shortestCompressedSuffix; i <= this.longestCompressedSuffix && i < length; i++) {
            if (this.uncompressSuffix.containsKey(lowerCase.substring(length - i))) {
                return true;
            }
        }
        return false;
    }

    public String getUncompressedFilename(String str) {
        String lowerCase = str.toLowerCase(Locale.ENGLISH);
        int length = lowerCase.length();
        for (int i = this.shortestCompressedSuffix; i <= this.longestCompressedSuffix && i < length; i++) {
            int i2 = length - i;
            String str2 = this.uncompressSuffix.get(lowerCase.substring(i2));
            if (str2 != null) {
                return str.substring(0, i2) + str2;
            }
        }
        return str;
    }

    public String getCompressedFilename(String str) {
        String lowerCase = str.toLowerCase(Locale.ENGLISH);
        int length = lowerCase.length();
        for (int i = this.shortestUncompressedSuffix; i <= this.longestUncompressedSuffix && i < length; i++) {
            int i2 = length - i;
            String str2 = this.compressSuffix.get(lowerCase.substring(i2));
            if (str2 != null) {
                return str.substring(0, i2) + str2;
            }
        }
        return str + this.defaultExtension;
    }
}
