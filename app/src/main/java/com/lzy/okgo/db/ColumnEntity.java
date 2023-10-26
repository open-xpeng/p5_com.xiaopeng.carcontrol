package com.lzy.okgo.db;

/* loaded from: classes.dex */
public class ColumnEntity {
    public String columnName;
    public String columnType;
    public String[] compositePrimaryKey;
    public boolean isAutoincrement;
    public boolean isNotNull;
    public boolean isPrimary;

    public ColumnEntity(String... strArr) {
        this.compositePrimaryKey = strArr;
    }

    public ColumnEntity(String str, String str2) {
        this(str, str2, false, false, false);
    }

    public ColumnEntity(String str, String str2, boolean z, boolean z2) {
        this(str, str2, z, z2, false);
    }

    public ColumnEntity(String str, String str2, boolean z, boolean z2, boolean z3) {
        this.columnName = str;
        this.columnType = str2;
        this.isPrimary = z;
        this.isNotNull = z2;
        this.isAutoincrement = z3;
    }
}
