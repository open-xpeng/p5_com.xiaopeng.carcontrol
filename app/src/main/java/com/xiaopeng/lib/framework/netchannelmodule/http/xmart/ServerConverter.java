package com.xiaopeng.lib.framework.netchannelmodule.http.xmart;

import androidx.core.app.NotificationCompat;
import com.lzy.okgo.convert.Converter;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ServerConverter implements Converter<ServerBean> {
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.lzy.okgo.convert.Converter
    public ServerBean convertResponse(Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) {
            throw new IllegalStateException("null");
        }
        ServerBean serverBean = new ServerBean();
        JSONObject jSONObject = new JSONObject(body.string());
        serverBean.code(jSONObject.getInt("code"));
        try {
            serverBean.data(jSONObject.getString("data"));
        } catch (Throwable unused) {
        }
        try {
            serverBean.message(jSONObject.getString(NotificationCompat.CATEGORY_MESSAGE));
        } catch (Throwable unused2) {
        }
        return serverBean;
    }
}
