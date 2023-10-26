package com.alibaba.mtl.log;

import java.util.Map;

/* compiled from: UTMCVariables.java */
/* loaded from: classes.dex */
public class c {
    public static final c a = new c();
    private boolean u = false;
    private boolean v = false;
    private String H = null;
    private Map<String, String> t = null;
    private boolean w = false;
    private boolean x = false;
    private String I = null;
    private String J = null;
    private String K = null;
    private boolean y = false;

    public static c a() {
        return a;
    }

    public synchronized void e(String str) {
        this.I = str;
    }

    public synchronized void o() {
        this.x = true;
    }

    public synchronized boolean d() {
        return this.x;
    }

    public synchronized void c(Map<String, String> map) {
        this.t = map;
    }

    /* renamed from: a  reason: collision with other method in class */
    public synchronized Map<String, String> m24a() {
        return this.t;
    }
}
