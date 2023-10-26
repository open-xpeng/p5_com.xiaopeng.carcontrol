package com.xiaopeng.lib.framework.moduleinterface.appresourcemodule;

import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import io.reactivex.Observable;

/* loaded from: classes2.dex */
public interface IAppResourceManager {

    /* loaded from: classes2.dex */
    public interface IResourceObserver {
        void onChange(String str, AppResourceResponse appResourceResponse);
    }

    boolean addResource(AppResourceRequest appResourceRequest);

    Observable<AppResourceResponse> addResourceObSource(AppResourceRequest appResourceRequest);

    void checkUpdate(String str);

    Observable<AppResourceResponse> checkUpdateObSource(String str);

    Observable<AppResourceResponse> clearResourceObSource();

    void clearResources();

    boolean deleteResource(String str);

    Observable<AppResourceResponse> deleteResourceObSource(String str);

    AssetFileDescriptor getFileDescriptor(String str);

    Observable<AssetFileDescriptor> getFileDescriptorObSource(String str);

    Uri getFullUri(String str);

    Observable<Uri> getFullUrlObSource(String str);

    Status getStatus(String str);

    Observable<Status> getStatusObSource(String str);

    void subscribeChanges(IResourceObserver iResourceObserver);

    void unSubscribeChanges(IResourceObserver iResourceObserver);

    /* loaded from: classes2.dex */
    public enum UpdatePolicy {
        LOCAL(0),
        SYNC(1),
        ASYNC(2);
        
        final int mId;

        public int id() {
            return this.mId;
        }

        UpdatePolicy(int i) {
            this.mId = i;
        }
    }

    /* loaded from: classes2.dex */
    public enum Status {
        NOT_REGISTER(0),
        NOT_AUTH(1),
        NOT_AVAILABLE(2),
        OK(3),
        ERROR(4);
        
        final int mId;

        public int id() {
            return this.mId;
        }

        Status(int i) {
            this.mId = i;
        }
    }
}
