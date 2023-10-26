package com.xiaopeng.lib.apirouter;

import android.text.TextUtils;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
class ApiMatcher {
    private static final String PROCESS_TAG_DIVIDER = "@";
    private ConcurrentHashMap<String, RemoteOperator> mRemoteOperators = new ConcurrentHashMap<>();

    public synchronized void publishUri(UriStruct uriStruct, RemoteOperator remoteOperator) {
        RemoteOperator put;
        String str = uriStruct.processTag;
        RemoteOperator put2 = put(str, remoteOperator.getAuthority(), remoteOperator);
        if (put2 != null) {
            put2.unLinkToDeath("getAuthority");
        }
        if (!remoteOperator.getAuthority().equals(remoteOperator.getDescription()) && (put = put(str, remoteOperator.getDescription(), remoteOperator)) != null) {
            put.unLinkToDeath("getDescription");
        }
        remoteOperator.linkToDeath();
    }

    public synchronized void unpublishUri(UriStruct uriStruct) {
        RemoteOperator remoteOperator = getRemoteOperator(uriStruct);
        if (remoteOperator != null) {
            String str = uriStruct.processTag;
            remove(str, remoteOperator.getAuthority());
            if (!remoteOperator.getAuthority().equals(remoteOperator.getDescription())) {
                remove(str, remoteOperator.getDescription());
            }
            remoteOperator.unLinkToDeath("unpublishUri");
        }
    }

    public synchronized RemoteOperator matchRemoteOperator(UriStruct uriStruct) {
        return getRemoteOperator(uriStruct);
    }

    private RemoteOperator put(String str, String str2, RemoteOperator remoteOperator) {
        if (!TextUtils.isEmpty(str)) {
            str2 = str + "@" + str2;
        }
        return this.mRemoteOperators.put(str2, remoteOperator);
    }

    private void remove(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            str2 = str + "@" + str2;
        }
        this.mRemoteOperators.remove(str2);
    }

    private RemoteOperator getRemoteOperator(UriStruct uriStruct) {
        String str;
        Set<Map.Entry<String, RemoteOperator>> entrySet = this.mRemoteOperators.entrySet();
        String str2 = uriStruct.processTag;
        if (!TextUtils.isEmpty(str2)) {
            str = str2 + "@" + uriStruct.applicationId + ".";
        } else {
            str = uriStruct.applicationId + ".";
        }
        String str3 = "." + uriStruct.serviceName;
        for (Map.Entry<String, RemoteOperator> entry : entrySet) {
            String key = entry.getKey();
            if (key.startsWith(str) && key.endsWith(str3)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
