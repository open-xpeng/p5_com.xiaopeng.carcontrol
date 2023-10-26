package zipkin2.internal;

/* loaded from: classes3.dex */
public final class JsonEscaper {
    private static final String[] REPLACEMENT_CHARS = new String[128];
    private static final String U2028 = "\\u2028";
    private static final String U2029 = "\\u2029";

    /* JADX WARN: Removed duplicated region for block: B:19:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String jsonEscape(java.lang.String r6) {
        /*
            boolean r0 = r6.isEmpty()
            if (r0 == 0) goto L7
            return r6
        L7:
            int r0 = r6.length()
            r1 = 0
            r2 = 0
            r3 = r2
        Le:
            if (r2 >= r0) goto L47
            char r4 = r6.charAt(r2)
            r5 = 128(0x80, float:1.8E-43)
            if (r4 >= r5) goto L1f
            java.lang.String[] r5 = zipkin2.internal.JsonEscaper.REPLACEMENT_CHARS
            r4 = r5[r4]
            if (r4 != 0) goto L2c
            goto L44
        L1f:
            r5 = 8232(0x2028, float:1.1535E-41)
            if (r4 != r5) goto L26
            java.lang.String r4 = "\\u2028"
            goto L2c
        L26:
            r5 = 8233(0x2029, float:1.1537E-41)
            if (r4 != r5) goto L44
            java.lang.String r4 = "\\u2029"
        L2c:
            if (r3 >= r2) goto L38
            if (r1 != 0) goto L35
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
        L35:
            r1.append(r6, r3, r2)
        L38:
            if (r1 != 0) goto L3f
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
        L3f:
            r1.append(r4)
            int r3 = r2 + 1
        L44:
            int r2 = r2 + 1
            goto Le
        L47:
            if (r1 != 0) goto L4a
            goto L53
        L4a:
            if (r3 >= r0) goto L4f
            r1.append(r6, r3, r0)
        L4f:
            java.lang.String r6 = r1.toString()
        L53:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: zipkin2.internal.JsonEscaper.jsonEscape(java.lang.String):java.lang.String");
    }

    static {
        for (int i = 0; i <= 31; i++) {
            REPLACEMENT_CHARS[i] = String.format("\\u%04x", Integer.valueOf(i));
        }
        String[] strArr = REPLACEMENT_CHARS;
        strArr[34] = "\\\"";
        strArr[92] = "\\\\";
        strArr[9] = "\\t";
        strArr[8] = "\\b";
        strArr[10] = "\\n";
        strArr[13] = "\\r";
        strArr[12] = "\\f";
    }

    public static int jsonEscapedSizeInBytes(String str) {
        int utf8SizeInBytes;
        int length = str.length();
        int i = 0;
        boolean z = true;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt == 8232 || charAt == 8233) {
                i += 5;
            } else if (charAt >= 128) {
                z = false;
            } else {
                String str2 = REPLACEMENT_CHARS[charAt];
                if (str2 != null) {
                    i += str2.length() - 1;
                }
            }
        }
        if (z) {
            utf8SizeInBytes = str.length();
        } else {
            utf8SizeInBytes = Buffer.utf8SizeInBytes(str);
        }
        return utf8SizeInBytes + i;
    }
}
