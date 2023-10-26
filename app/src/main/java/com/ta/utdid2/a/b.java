package com.ta.utdid2.a;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.ta.utdid2.b.a.d;
import com.ta.utdid2.b.a.f;
import com.xiaopeng.speech.protocol.event.OOBEEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: AidRequester.java */
/* loaded from: classes.dex */
public class b {
    private static final String TAG = "com.ta.utdid2.a.b";
    private static b a;

    /* renamed from: a  reason: collision with other field name */
    private Object f133a = new Object();
    private Context mContext;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: AidRequester.java */
    /* loaded from: classes.dex */
    public class a extends Thread {
        com.ut.device.a a;

        /* renamed from: a  reason: collision with other field name */
        String f134a;

        /* renamed from: a  reason: collision with other field name */
        HttpPost f135a;

        /* renamed from: b  reason: collision with other field name */
        String f136b;
        String c;
        String d;

        public a(HttpPost httpPost) {
            this.f134a = "";
            this.d = "";
            this.f135a = httpPost;
        }

        public a(HttpPost httpPost, com.ut.device.a aVar, String str, String str2, String str3) {
            this.f134a = "";
            this.d = "";
            this.f135a = httpPost;
            this.a = aVar;
            this.f136b = str;
            this.c = str2;
            this.d = str3;
        }

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0060 -> B:56:0x006b). Please submit an issue!!! */
        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            HttpResponse httpResponse;
            com.ut.device.a aVar = this.a;
            if (aVar != null) {
                aVar.a(1000, this.f136b);
            }
            BufferedReader bufferedReader = null;
            try {
                httpResponse = new DefaultHttpClient().execute(this.f135a);
            } catch (Exception e) {
                com.ut.device.a aVar2 = this.a;
                if (aVar2 != null) {
                    aVar2.a(1002, this.f136b);
                }
                Log.e(b.TAG, e.toString());
                httpResponse = null;
            }
            try {
                if (httpResponse == null) {
                    Log.e(b.TAG, "response is null!");
                } else {
                    bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), Charset.forName("UTF-8")));
                }
            } catch (Exception e2) {
                com.ut.device.a aVar3 = this.a;
                if (aVar3 != null) {
                    aVar3.a(1002, this.f136b);
                }
                Log.e(b.TAG, e2.toString());
            }
            try {
                if (bufferedReader != null) {
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        if (d.e) {
                            Log.d(b.TAG, readLine);
                        }
                        this.f134a = readLine;
                    }
                } else {
                    Log.e(b.TAG, "BufferredReader is null!");
                }
            } catch (Exception e3) {
                com.ut.device.a aVar4 = this.a;
                if (aVar4 != null) {
                    aVar4.a(1002, this.f136b);
                }
                Log.e(b.TAG, e3.toString());
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                    if (d.e) {
                        Log.d(b.TAG, "close the bufferreader");
                    }
                } catch (IOException e4) {
                    Log.e(b.TAG, e4.toString());
                }
            }
            if (this.a == null) {
                synchronized (b.this.f133a) {
                    b.this.f133a.notifyAll();
                }
                return;
            }
            String a = b.a(this.f134a, this.f136b);
            this.a.a(1001, a);
            c.a(b.this.mContext, this.c, a, this.d);
        }

        public String b() {
            return this.f134a;
        }
    }

    public static synchronized b a(Context context) {
        b bVar;
        synchronized (b.class) {
            if (a == null) {
                a = new b(context);
            }
            bVar = a;
        }
        return bVar;
    }

    public b(Context context) {
        this.mContext = context;
    }

    public void a(String str, String str2, String str3, String str4, com.ut.device.a aVar) {
        String b = b(str, str2, str3, str4);
        if (d.e) {
            Log.d(TAG, "url:" + b + "; len:" + b.length());
        }
        new a(new HttpPost(b), aVar, str4, str, str2).start();
    }

    public String a(String str, String str2, String str3, String str4) {
        String b = b(str, str2, str3, str4);
        int i = f.b(this.mContext) ? 3000 : 1000;
        if (d.e) {
            Log.d(TAG, "url:" + b + "; timeout:" + i);
        }
        a aVar = new a(new HttpPost(b));
        aVar.start();
        try {
            synchronized (this.f133a) {
                this.f133a.wait(i);
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        String b2 = aVar.b();
        if (d.e) {
            Log.d(TAG, "mLine:" + b2);
        }
        return a(b2, str4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String a(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has("data")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                if (jSONObject2.has("action") && jSONObject2.has("aid")) {
                    String string = jSONObject2.getString("action");
                    return (string.equalsIgnoreCase("new") || string.equalsIgnoreCase("changed")) ? jSONObject2.getString("aid") : str2;
                }
                return str2;
            } else if (jSONObject.has("isError") && jSONObject.has("status")) {
                String string2 = jSONObject.getString("isError");
                String string3 = jSONObject.getString("status");
                if (string2.equalsIgnoreCase(OOBEEvent.STRING_TRUE)) {
                    if (string3.equalsIgnoreCase("404") || string3.equalsIgnoreCase("401")) {
                        if (d.e) {
                            Log.d(TAG, "remove the AID, status:" + string3);
                        }
                        return "";
                    }
                    return str2;
                }
                return str2;
            } else {
                return str2;
            }
        } catch (JSONException e) {
            Log.e(TAG, e.toString());
            return str2;
        } catch (Exception e2) {
            Log.e(TAG, e2.toString());
            return str2;
        }
    }

    private static String b(String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder();
        try {
            str3 = URLEncoder.encode(str3, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.append("http://hydra.alibaba.com/").append(str).append("/get_aid/").append("?").append("auth[token]=").append(str2).append("&type=").append("utdid").append("&id=").append(str3).append("&aid=").append(str4).toString();
    }
}
