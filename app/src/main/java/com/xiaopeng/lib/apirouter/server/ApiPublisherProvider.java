package com.xiaopeng.lib.apirouter.server;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Pair;

/* loaded from: classes2.dex */
public class ApiPublisherProvider extends ContentProvider {
    public static Context CONTEXT;
    private AutoCodeMatcher mMatcher;

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        CONTEXT = getContext();
        this.mMatcher = new AutoCodeMatcher();
        return true;
    }

    @Override // android.content.ContentProvider
    public Bundle call(String str, String str2, Bundle bundle) {
        Pair<IBinder, String> match = this.mMatcher.match(str);
        Bundle bundle2 = new Bundle();
        if (match != null) {
            bundle2.putBinder("binder", (IBinder) match.first);
            bundle2.putString("manifest", (String) match.second);
        }
        return bundle2;
    }

    public static void setContext(Context context) {
        CONTEXT = context;
    }

    public static void addManifestHandler(IManifestHandler iManifestHandler) {
        AutoCodeMatcher.addManifestHandler(iManifestHandler);
    }
}
