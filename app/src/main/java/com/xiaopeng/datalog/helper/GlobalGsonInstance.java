package com.xiaopeng.datalog.helper;

import com.google.gson.Gson;

/* loaded from: classes2.dex */
public class GlobalGsonInstance {
    private Gson mGson;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Holder {
        static final GlobalGsonInstance INSTANCE = new GlobalGsonInstance();

        private Holder() {
        }
    }

    public static GlobalGsonInstance getInstance() {
        return Holder.INSTANCE;
    }

    private GlobalGsonInstance() {
        this.mGson = new Gson();
    }

    public Gson getGson() {
        return this.mGson;
    }
}
