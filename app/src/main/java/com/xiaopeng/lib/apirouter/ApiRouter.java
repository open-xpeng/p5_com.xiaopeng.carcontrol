package com.xiaopeng.lib.apirouter;

import android.content.ContentProviderClient;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.xiaopeng.lib.apirouter.ClientConstants;
import com.xiaopeng.lib.apirouter.server.ApiPublisherProvider;
import java.util.List;

/* loaded from: classes2.dex */
public class ApiRouter {
    private static ApiMatcher mApiMatcher = new ApiMatcher();

    private ApiRouter() {
    }

    private static RemoteOperator publishModule(UriStruct uriStruct, IBinder iBinder, String str) {
        RemoteOperator fromJson = RemoteOperator.fromJson(iBinder, str);
        mApiMatcher.publishUri(uriStruct, fromJson);
        return fromJson;
    }

    private static RemoteOperator wakeRemoteService(UriStruct uriStruct) throws RemoteException {
        if (ApiPublisherProvider.CONTEXT == null) {
            throw new RemoteException("ApiRouter can not route. If it is an asynchronous thread, please check your Context first!");
        }
        Uri.Builder builder = new Uri.Builder();
        if (TextUtils.isEmpty(uriStruct.processTag)) {
            builder.scheme("content").authority(uriStruct.applicationId + ClientConstants.BINDER.API_SUFFIX);
        } else {
            builder.scheme("content").authority(uriStruct.applicationId + "." + uriStruct.processTag + ClientConstants.BINDER.API_SUFFIX);
        }
        ContentProviderClient acquireContentProviderClient = ApiPublisherProvider.CONTEXT.getContentResolver().acquireContentProviderClient(builder.build());
        if (acquireContentProviderClient == null) {
            throw new RemoteException("Unknown service " + uriStruct);
        }
        try {
            Bundle call = acquireContentProviderClient.call(uriStruct.serviceName, null, null);
            if (call == null) {
                throw new RemoteException("Server does not implement call");
            }
            IBinder binder = call.getBinder("binder");
            String string = call.getString("manifest");
            if (binder == null || TextUtils.isEmpty(string)) {
                throw new RemoteException("No matching method");
            }
            return publishModule(uriStruct, binder, string);
        } finally {
            acquireContentProviderClient.release();
        }
    }

    public static <T> T route(Uri uri) throws RemoteException {
        return (T) route(uri, false, null);
    }

    public static <T> T route(Uri uri, byte[] bArr) throws RemoteException {
        return (T) route(uri, true, bArr);
    }

    private static <T> T route(Uri uri, boolean z, byte[] bArr) throws RemoteException {
        UriStruct uriStruct = toUriStruct(uri);
        RemoteOperator matchRemoteOperator = mApiMatcher.matchRemoteOperator(uriStruct);
        if (matchRemoteOperator == null) {
            matchRemoteOperator = wakeRemoteService(uriStruct);
        } else if (!matchRemoteOperator.isRemoteAlive()) {
            mApiMatcher.unpublishUri(uriStruct);
            matchRemoteOperator = wakeRemoteService(uriStruct);
        }
        return (T) matchRemoteOperator.call(uri, z, bArr);
    }

    private static UriStruct toUriStruct(Uri uri) throws RemoteException {
        String authority = uri.getAuthority();
        if (TextUtils.isEmpty(authority)) {
            throw new RemoteException("Can not find authority in uri");
        }
        int lastIndexOf = authority.lastIndexOf(".");
        if (lastIndexOf == -1) {
            throw new RemoteException("Illegal uri authority");
        }
        String substring = authority.substring(0, lastIndexOf);
        String substring2 = authority.substring(lastIndexOf + 1);
        UriStruct uriStruct = new UriStruct();
        uriStruct.applicationId = substring;
        uriStruct.serviceName = substring2;
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments != null && pathSegments.size() > 1) {
            uriStruct.processTag = pathSegments.get(1);
        }
        return uriStruct;
    }
}
