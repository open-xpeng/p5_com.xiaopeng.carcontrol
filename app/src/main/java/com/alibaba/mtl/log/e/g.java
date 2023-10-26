package com.alibaba.mtl.log.e;

import android.text.TextUtils;
import java.util.Arrays;
import java.util.Comparator;

/* compiled from: KeyArraySorter.java */
/* loaded from: classes.dex */
public class g {
    private static g a = new g();

    /* renamed from: a  reason: collision with other field name */
    private b f60a = new b();

    /* renamed from: a  reason: collision with other field name */
    private a f59a = new a();

    private g() {
    }

    public static g a() {
        return a;
    }

    public String[] a(String[] strArr, boolean z) {
        Comparator comparator;
        if (z) {
            comparator = this.f59a;
        } else {
            comparator = this.f60a;
        }
        if (comparator == null || strArr == null || strArr.length <= 0) {
            return null;
        }
        Arrays.sort(strArr, comparator);
        return strArr;
    }

    /* compiled from: KeyArraySorter.java */
    /* loaded from: classes.dex */
    private class b implements Comparator<String> {
        private b() {
        }

        @Override // java.util.Comparator
        public int compare(String str, String str2) {
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                return 0;
            }
            return str.compareTo(str2) * (-1);
        }
    }

    /* compiled from: KeyArraySorter.java */
    /* loaded from: classes.dex */
    private class a implements Comparator<String> {
        private a() {
        }

        @Override // java.util.Comparator
        public int compare(String str, String str2) {
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                return 0;
            }
            return str.compareTo(str2);
        }
    }
}
