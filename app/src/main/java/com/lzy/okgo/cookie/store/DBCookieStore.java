package com.lzy.okgo.cookie.store;

import android.content.Context;
import com.lzy.okgo.cookie.SerializableCookie;
import com.lzy.okgo.db.CookieManager;
import com.xiaopeng.xvs.xid.sync.api.ISync;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

/* loaded from: classes.dex */
public class DBCookieStore implements CookieStore {
    private final Map<String, ConcurrentHashMap<String, Cookie>> cookies;

    public DBCookieStore(Context context) {
        CookieManager.init(context);
        this.cookies = new HashMap();
        for (SerializableCookie serializableCookie : CookieManager.getInstance().queryAll()) {
            if (!this.cookies.containsKey(serializableCookie.host)) {
                this.cookies.put(serializableCookie.host, new ConcurrentHashMap<>());
            }
            Cookie cookie = serializableCookie.getCookie();
            this.cookies.get(serializableCookie.host).put(getCookieToken(cookie), cookie);
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
            this.cookies.get(httpUrl.host()).put(getCookieToken(cookie), cookie);
            CookieManager.getInstance().replace((CookieManager) new SerializableCookie(httpUrl.host(), cookie));
        }
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public synchronized List<Cookie> loadCookie(HttpUrl httpUrl) {
        ArrayList arrayList = new ArrayList();
        if (this.cookies.containsKey(httpUrl.host())) {
            for (SerializableCookie serializableCookie : CookieManager.getInstance().query("host=?", new String[]{httpUrl.host()})) {
                Cookie cookie = serializableCookie.getCookie();
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
                CookieManager.getInstance().delete("host=? and name=? and domain=?", new String[]{httpUrl.host(), cookie.name(), cookie.domain()});
                return true;
            }
            return false;
        }
        return false;
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public synchronized boolean removeCookie(HttpUrl httpUrl) {
        if (this.cookies.containsKey(httpUrl.host())) {
            this.cookies.remove(httpUrl.host());
            CookieManager.getInstance().delete("host=?", new String[]{httpUrl.host()});
            return true;
        }
        return false;
    }

    @Override // com.lzy.okgo.cookie.store.CookieStore
    public synchronized boolean removeAllCookie() {
        this.cookies.clear();
        CookieManager.getInstance().deleteAll();
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
