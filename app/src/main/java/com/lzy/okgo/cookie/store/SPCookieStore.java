package com.lzy.okgo.cookie.store;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.lzy.okgo.cookie.SerializableCookie;
import com.xiaopeng.xvs.xid.sync.api.ISync;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public class SPCookieStore implements CookieStore {
    private static final String COOKIE_NAME_PREFIX = "cookie_";
    private static final String COOKIE_PREFS = "okgo_cookie";
    private final SharedPreferences cookiePrefs;
    private final Map<String, ConcurrentHashMap<String, Cookie>> cookies;

    public SPCookieStore(Context context) {
        String[] split;
        Cookie decodeCookie;
        SharedPreferences sharedPreferences = context.getSharedPreferences(COOKIE_PREFS, 0);
        this.cookiePrefs = sharedPreferences;
        this.cookies = new HashMap();
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            if (entry.getValue() != null && !entry.getKey().startsWith(COOKIE_NAME_PREFIX)) {
                for (String str : TextUtils.split((String) entry.getValue(), ",")) {
                    String string = this.cookiePrefs.getString(COOKIE_NAME_PREFIX + str, null);
                    if (string != null && (decodeCookie = SerializableCookie.decodeCookie(string)) != null) {
                        if (!this.cookies.containsKey(entry.getKey())) {
                            this.cookies.put(entry.getKey(), new ConcurrentHashMap<>());
                        }
                        this.cookies.get(entry.getKey()).put(str, decodeCookie);
                    }
                }
            }
        }
    }

    private String getCookieToken(Cookie cookie) {
        return cookie.name() + ISync.EXTRA_SYNC_GROUP_KEY_SEPARATOR + cookie.domain();
    }

    private static boolean isCookieExpired(Cookie cookie) {
        return cookie.expiresAt() < System.currentTimeMillis();
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public synchronized void saveCookie(HttpUrl httpUrl, List<Cookie> list) {
        for (Cookie cookie : list) {
            saveCookie(httpUrl, cookie);
        }
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public synchronized void saveCookie(HttpUrl httpUrl, Cookie cookie) {
        if (!this.cookies.containsKey(httpUrl.host())) {
            this.cookies.put(httpUrl.host(), new ConcurrentHashMap<>());
        }
        if (isCookieExpired(cookie)) {
            removeCookie(httpUrl, cookie);
        } else {
            saveCookie(httpUrl, cookie, getCookieToken(cookie));
        }
    }

    private void saveCookie(HttpUrl httpUrl, Cookie cookie, String str) {
        this.cookies.get(httpUrl.host()).put(str, cookie);
        SharedPreferences.Editor edit = this.cookiePrefs.edit();
        edit.putString(httpUrl.host(), TextUtils.join(",", this.cookies.get(httpUrl.host()).keySet()));
        edit.putString(COOKIE_NAME_PREFIX + str, SerializableCookie.encodeCookie(httpUrl.host(), cookie));
        edit.apply();
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public synchronized List<Cookie> loadCookie(HttpUrl httpUrl) {
        ArrayList arrayList = new ArrayList();
        if (this.cookies.containsKey(httpUrl.host())) {
            for (Cookie cookie : this.cookies.get(httpUrl.host()).values()) {
                if (isCookieExpired(cookie)) {
                    removeCookie(httpUrl, cookie);
                } else {
                    arrayList.add(cookie);
                }
            }
            return arrayList;
        }
        return arrayList;
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public synchronized boolean removeCookie(HttpUrl httpUrl, Cookie cookie) {
        if (this.cookies.containsKey(httpUrl.host())) {
            String cookieToken = getCookieToken(cookie);
            if (this.cookies.get(httpUrl.host()).containsKey(cookieToken)) {
                this.cookies.get(httpUrl.host()).remove(cookieToken);
                SharedPreferences.Editor edit = this.cookiePrefs.edit();
                if (this.cookiePrefs.contains(COOKIE_NAME_PREFIX + cookieToken)) {
                    edit.remove(COOKIE_NAME_PREFIX + cookieToken);
                }
                edit.putString(httpUrl.host(), TextUtils.join(",", this.cookies.get(httpUrl.host()).keySet()));
                edit.apply();
                return true;
            }
            return false;
        }
        return false;
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public synchronized boolean removeCookie(HttpUrl httpUrl) {
        if (this.cookies.containsKey(httpUrl.host())) {
            Set<String> keySet = this.cookies.remove(httpUrl.host()).keySet();
            SharedPreferences.Editor edit = this.cookiePrefs.edit();
            for (String str : keySet) {
                if (this.cookiePrefs.contains(COOKIE_NAME_PREFIX + str)) {
                    edit.remove(COOKIE_NAME_PREFIX + str);
                }
            }
            edit.remove(httpUrl.host());
            edit.apply();
            return true;
        }
        return false;
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public synchronized boolean removeAllCookie() {
        this.cookies.clear();
        SharedPreferences.Editor edit = this.cookiePrefs.edit();
        edit.clear();
        edit.apply();
        return true;
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public synchronized List<Cookie> getAllCookie() {
        ArrayList arrayList;
        arrayList = new ArrayList();
        for (String str : this.cookies.keySet()) {
            arrayList.addAll(this.cookies.get(str).values());
        }
        return arrayList;
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public synchronized List<Cookie> getCookie(HttpUrl httpUrl) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        ConcurrentHashMap<String, Cookie> concurrentHashMap = this.cookies.get(httpUrl.host());
        if (concurrentHashMap != null) {
            arrayList.addAll(concurrentHashMap.values());
        }
        return arrayList;
    }
}
