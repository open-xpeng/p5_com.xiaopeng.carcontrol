package com.xiaopeng.lib.framework.netchannelmodule.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket.WebSocketSubscriber;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* loaded from: classes2.dex */
public abstract class WebSocketSubscriber2<T> extends WebSocketSubscriber {
    private static final Gson GSON = new Gson();
    protected Type type;

    public abstract void onMessage(T t);

    public WebSocketSubscriber2() {
        analysisType();
    }

    private void analysisType() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            throw new RuntimeException("No generics found!");
        }
        this.type = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.websocket.WebSocketSubscriber
    public void onMessage(String str) {
        Observable.just(str).map(new Function<String, T>() { // from class: com.xiaopeng.lib.framework.netchannelmodule.websocket.WebSocketSubscriber2.2
            @Override // io.reactivex.functions.Function
            public T apply(String str2) throws Exception {
                try {
                    return (T) WebSocketSubscriber2.GSON.fromJson(str2, WebSocketSubscriber2.this.type);
                } catch (JsonSyntaxException unused) {
                    return (T) WebSocketSubscriber2.GSON.fromJson((String) WebSocketSubscriber2.GSON.fromJson(str2, (Class<Object>) String.class), WebSocketSubscriber2.this.type);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((Consumer<T>) new Consumer<T>() { // from class: com.xiaopeng.lib.framework.netchannelmodule.websocket.WebSocketSubscriber2.1
            @Override // io.reactivex.functions.Consumer
            public void accept(T t) throws Exception {
                WebSocketSubscriber2.this.onMessage((WebSocketSubscriber2) t);
            }
        });
    }
}
