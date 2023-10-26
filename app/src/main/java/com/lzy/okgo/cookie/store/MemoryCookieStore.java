package com.lzy.okgo.cookie.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public class MemoryCookieStore implements CookieStore {
    private final Map<String, List<Cookie>> memoryCookies = new HashMap();

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public synchronized void saveCookie(HttpUrl httpUrl, List<Cookie> list) {
        List<Cookie> list2 = this.memoryCookies.get(httpUrl.host());
        ArrayList arrayList = new ArrayList();
        for (Cookie cookie : list) {
            for (Cookie cookie2 : list2) {
                if (cookie.name().equals(cookie2.name())) {
                    arrayList.add(cookie2);
                }
            }
        }
        list2.removeAll(arrayList);
        list2.addAll(list);
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public synchronized void saveCookie(HttpUrl httpUrl, Cookie cookie) {
        List<Cookie> list = this.memoryCookies.get(httpUrl.host());
        ArrayList arrayList = new ArrayList();
        for (Cookie cookie2 : list) {
            if (cookie.name().equals(cookie2.name())) {
                arrayList.add(cookie2);
            }
        }
        list.removeAll(arrayList);
        list.add(cookie);
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public synchronized List<Cookie> loadCookie(HttpUrl httpUrl) {
        List<Cookie> list;
        list = this.memoryCookies.get(httpUrl.host());
        if (list == null) {
            list = new ArrayList<>();
            this.memoryCookies.put(httpUrl.host(), list);
        }
        return list;
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public synchronized List<Cookie> getAllCookie() {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (String str : this.memoryCookies.keySet()) {
            arrayList.addAll(this.memoryCookies.get(str));
        }
        return arrayList;
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public List<Cookie> getCookie(HttpUrl httpUrl) {
        ArrayList arrayList = new ArrayList();
        List<Cookie> list = this.memoryCookies.get(httpUrl.host());
        if (list != null) {
            arrayList.addAll(list);
        }
        return arrayList;
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public synchronized boolean removeCookie(HttpUrl httpUrl, Cookie cookie) {
        boolean z;
        List<Cookie> list = this.memoryCookies.get(httpUrl.host());
        if (cookie != null) {
            z = list.remove(cookie);
        }
        return z;
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public synchronized boolean removeCookie(HttpUrl httpUrl) {
        return this.memoryCookies.remove(httpUrl.host()) != null;
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public synchronized boolean removeAllCookie() {
        this.memoryCookies.clear();
        return true;
    }
}
