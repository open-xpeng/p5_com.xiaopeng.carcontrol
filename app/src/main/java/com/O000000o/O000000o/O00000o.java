package com.O000000o.O000000o;

import java.lang.reflect.Field;

/* compiled from: FieldNamingPolicy.java */
/* loaded from: classes.dex */
public enum O00000o implements O0000O0o {
    IDENTITY { // from class: com.O000000o.O000000o.O00000o.1
        @Override // com.O000000o.O000000o.O0000O0o
        public String O000000o(Field field) {
            return field.getName();
        }
    },
    UPPER_CAMEL_CASE { // from class: com.O000000o.O000000o.O00000o.2
        @Override // com.O000000o.O000000o.O0000O0o
        public String O000000o(Field field) {
            return O00000o.O00000Oo(field.getName());
        }
    },
    UPPER_CAMEL_CASE_WITH_SPACES { // from class: com.O000000o.O000000o.O00000o.3
        @Override // com.O000000o.O000000o.O0000O0o
        public String O000000o(Field field) {
            return O00000o.O00000Oo(O00000o.O00000Oo(field.getName(), " "));
        }
    },
    LOWER_CASE_WITH_UNDERSCORES { // from class: com.O000000o.O000000o.O00000o.4
        @Override // com.O000000o.O000000o.O0000O0o
        public String O000000o(Field field) {
            return O00000o.O00000Oo(field.getName(), "_").toLowerCase();
        }
    },
    LOWER_CASE_WITH_DASHES { // from class: com.O000000o.O000000o.O00000o.5
        @Override // com.O000000o.O000000o.O0000O0o
        public String O000000o(Field field) {
            return O00000o.O00000Oo(field.getName(), "-").toLowerCase();
        }
    };

    private static String O000000o(char c, String str, int i) {
        return i < str.length() ? c + str.substring(i) : String.valueOf(c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String O00000Oo(String str) {
        char charAt;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (true) {
            charAt = str.charAt(i);
            if (i >= str.length() - 1 || Character.isLetter(charAt)) {
                break;
            }
            sb.append(charAt);
            i++;
        }
        return i == str.length() ? sb.toString() : !Character.isUpperCase(charAt) ? sb.append(O000000o(Character.toUpperCase(charAt), str, i + 1)).toString() : str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String O00000Oo(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (Character.isUpperCase(charAt) && sb.length() != 0) {
                sb.append(str2);
            }
            sb.append(charAt);
        }
        return sb.toString();
    }
}
