package com.xiaopeng.lib.apirouter;

import android.net.Uri;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.xiaopeng.lib.apirouter.ClientConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
class RemoteOperator {
    private static String TAG = "RemoteOperator";
    private String mAuthority;
    private IBinder.DeathRecipient mDeathRecipient;
    private String mDescription;
    private IBinder mIBinder;
    private AtomicBoolean mRemoteAlive = new AtomicBoolean(true);
    private Map<String, RemoteMethod> mRemoteApis;

    public static RemoteOperator fromJson(IBinder iBinder, String str) {
        ArrayList arrayList;
        try {
            JSONObject jSONObject = new JSONObject(str);
            String string = jSONObject.getString(ClientConstants.TRANSACT.DESCRIPTOR);
            String optString = jSONObject.optString(ClientConstants.ALIAS.AUTHORITY, string);
            JSONArray jSONArray = jSONObject.getJSONArray(ClientConstants.TRANSACT.TRANSACTION);
            HashMap hashMap = new HashMap();
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                JSONArray optJSONArray = jSONObject2.optJSONArray(ClientConstants.ALIAS.PARAMETER);
                if (optJSONArray != null) {
                    arrayList = new ArrayList(optJSONArray.length());
                    for (int i2 = 0; i2 < optJSONArray.length(); i2++) {
                        JSONObject jSONObject3 = optJSONArray.getJSONObject(i2);
                        arrayList.add(new Pair(jSONObject3.getString(ClientConstants.ALIAS.P_ALIAS), jSONObject3.getString("name")));
                    }
                } else {
                    arrayList = null;
                }
                String string2 = jSONObject2.getString(ClientConstants.TRANSACT.METHOD);
                String optString2 = jSONObject2.optString(ClientConstants.ALIAS.PATH, string2);
                RemoteMethod remoteMethod = new RemoteMethod(string2, jSONObject2.getInt(ClientConstants.TRANSACT.ID), arrayList);
                hashMap.put(string2, remoteMethod);
                if (!string2.equals(optString2)) {
                    hashMap.put(optString2, remoteMethod);
                }
            }
            return new RemoteOperator(iBinder, hashMap, string, optString);
        } catch (JSONException unused) {
            Log.e(TAG, "Remote IDL parsed error");
            return null;
        }
    }

    private RemoteOperator(IBinder iBinder, Map<String, RemoteMethod> map, String str, String str2) {
        this.mIBinder = iBinder;
        this.mRemoteApis = map;
        this.mDescription = str;
        this.mAuthority = str2;
    }

    public String getAuthority() {
        return this.mAuthority;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public boolean isRemoteAlive() {
        IBinder iBinder = this.mIBinder;
        return iBinder != null && iBinder.isBinderAlive() && this.mRemoteAlive.get();
    }

    public Object call(Uri uri, boolean z, byte[] bArr) throws RemoteException {
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() == 0) {
            throw new RemoteException("Can not find uri path segment");
        }
        String str = pathSegments.get(0);
        Map<String, RemoteMethod> map = this.mRemoteApis;
        if (map == null || map.size() == 0) {
            throw new RemoteException("Server do not provide any method");
        }
        RemoteMethod remoteMethod = this.mRemoteApis.get(str);
        if (remoteMethod == null) {
            throw new RemoteException("No matching method");
        }
        Uri.Builder builder = new Uri.Builder();
        builder.authority(this.mDescription).path(remoteMethod.getMethodName());
        if (remoteMethod.getParamsList() != null) {
            for (Pair<String, String> pair : remoteMethod.getParamsList()) {
                String queryParameter = uri.getQueryParameter((String) pair.second);
                if (!TextUtils.isEmpty(queryParameter)) {
                    builder.appendQueryParameter((String) pair.second, queryParameter);
                } else {
                    String queryParameter2 = uri.getQueryParameter((String) pair.first);
                    if (!TextUtils.isEmpty(queryParameter2)) {
                        builder.appendQueryParameter((String) pair.second, queryParameter2);
                    }
                }
            }
        } else {
            for (String str2 : uri.getQueryParameterNames()) {
                builder.appendQueryParameter(str2, uri.getQueryParameter(str2));
            }
        }
        Uri build = builder.build();
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(this.mDescription);
            Uri.writeToParcel(obtain, build);
            if (z) {
                obtain.writeBlob(bArr);
            }
            this.mIBinder.transact(remoteMethod.getId(), obtain, obtain2, 0);
            obtain2.readException();
            return readResult(obtain2);
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    private Object readResult(Parcel parcel) {
        return parcel.readValue(getClass().getClassLoader());
    }

    public synchronized void linkToDeath() {
        if (this.mDeathRecipient == null) {
            this.mDeathRecipient = new IBinder.DeathRecipient() { // from class: com.xiaopeng.lib.apirouter.RemoteOperator.1
                @Override // android.os.IBinder.DeathRecipient
                public void binderDied() {
                    Log.d(RemoteOperator.TAG, "binderDied:desc=" + RemoteOperator.this.mDescription + ", auth=" + RemoteOperator.this.mAuthority + ",rec=" + Integer.toHexString(hashCode()) + ",obj=" + Integer.toHexString(RemoteOperator.this.hashCode()));
                    RemoteOperator.this.unLinkToDeath("binderDied");
                }
            };
            try {
                Log.d(TAG, "linkToDeath:desc=" + this.mDescription + ",auth=" + this.mAuthority + ",rec=" + Integer.toHexString(this.mDeathRecipient.hashCode()) + ",obj=" + Integer.toHexString(hashCode()));
                this.mIBinder.linkToDeath(this.mDeathRecipient, 0);
            } catch (RemoteException unused) {
                this.mRemoteAlive.set(false);
                Log.e(TAG, "linkToDeath:" + this.mAuthority + "'s process has already died");
            }
        }
    }

    public synchronized void unLinkToDeath(String str) {
        this.mRemoteAlive.set(false);
        if (this.mDeathRecipient != null) {
            Log.d(TAG, "unLinkToDeath(" + str + "):desc=" + this.mDescription + ",auth= " + this.mAuthority + ",rec=" + Integer.toHexString(this.mDeathRecipient.hashCode()) + ",obj=" + Integer.toHexString(hashCode()));
            if (!this.mIBinder.unlinkToDeath(this.mDeathRecipient, 0)) {
                Log.e(TAG, "unLinkToDeath(" + str + "):" + this.mAuthority + "'s process has already died");
            }
            this.mDeathRecipient = null;
        }
    }
}
