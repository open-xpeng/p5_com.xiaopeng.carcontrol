package com.xiaopeng.lludancemanager.model;

import android.text.TextUtils;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.appstore.storeprovider.store.bean.ResourceBean;
import com.xiaopeng.appstore.storeprovider.store.bean.ResourceContainerBean;
import com.xiaopeng.appstore.storeprovider.store.bean.ResourceDownloadInfo;
import com.xiaopeng.appstore.storeprovider.store.bean.ResourceType;
import com.xiaopeng.carcontrol.carmanager.XuiClientWrapper;
import com.xiaopeng.carcontrol.util.GsonUtil;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.lludancemanager.bean.LluDanceExpandContent;
import com.xiaopeng.lludancemanager.bean.LluDanceInstalledInfo;
import com.xiaopeng.lludancemanager.bean.LluDanceViewData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class LluDanceRepository {
    private static final String INVALID_ID_LOCAL = "PianoConcerto";
    private static final String INVALID_ID_NET = "23";
    private MutableLiveData<List<LluDanceViewData>> mResourcesMutableLiveData = new MutableLiveData<>();

    private int getStatus(int resourceStatus) {
        switch (resourceStatus) {
            case 1:
            default:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            case 7:
                return 7;
            case 8:
                return 8;
        }
    }

    public MutableLiveData<List<LluDanceViewData>> getResourcesMutableLiveData() {
        return this.mResourcesMutableLiveData;
    }

    public void requestResources() {
        XuiClientWrapper.getInstance().getResourceServiceApiCallExecutor().execute(new Runnable() { // from class: com.xiaopeng.lludancemanager.model.-$$Lambda$LluDanceRepository$kg6L_NUdoKnGt5D8na7r69lKvxY
            @Override // java.lang.Runnable
            public final void run() {
                LluDanceRepository.this.lambda$requestResources$0$LluDanceRepository();
            }
        });
    }

    public /* synthetic */ void lambda$requestResources$0$LluDanceRepository() {
        LogUtils.d("yjk", "requestResources");
        if (XuiClientWrapper.getInstance().getResourceManager() != null) {
            ResourceContainerBean queryResourceData = XuiClientWrapper.getInstance().getResourceManager().queryResourceData(ResourceType.LLU_DANCE);
            LogUtils.d("yjk", queryResourceData == null ? "" : queryResourceData.toString());
            this.mResourcesMutableLiveData.postValue(doDataFusion(queryResourceData));
            return;
        }
        LogUtils.d("yjk", "requestResources  --> ResourceManager can not work ");
    }

    private List<LluDanceViewData> doDataFusion(ResourceContainerBean resourceContainerBean) {
        List<LluDanceViewData> transToLluDanceViewData = transToLluDanceViewData(resourceContainerBean);
        String[] strArr = new String[transToLluDanceViewData.size()];
        for (int i = 0; i < transToLluDanceViewData.size(); i++) {
            strArr[i] = transToLluDanceViewData.get(i).getId();
        }
        List<ResourceDownloadInfo> queryDownloadInfo = XuiClientWrapper.getInstance().getResourceManager().queryDownloadInfo(strArr);
        HashMap hashMap = new HashMap();
        if (queryDownloadInfo != null) {
            for (ResourceDownloadInfo resourceDownloadInfo : queryDownloadInfo) {
                hashMap.put(resourceDownloadInfo.getRscId(), resourceDownloadInfo);
            }
        }
        for (int i2 = 0; i2 < transToLluDanceViewData.size(); i2++) {
            ResourceDownloadInfo resourceDownloadInfo2 = (ResourceDownloadInfo) hashMap.get(transToLluDanceViewData.get(i2).getId());
            if (resourceDownloadInfo2 != null) {
                int status = resourceDownloadInfo2.getStatus();
                long calculateProgress = calculateProgress(resourceDownloadInfo2);
                LogUtils.d("yjk", "requestResources  status :" + status);
                LogUtils.d("yjk", "requestResources  download byte:" + resourceDownloadInfo2.getDownloadedBytes());
                LogUtils.d("yjk", "requestResources  total byte:" + resourceDownloadInfo2.getTotalBytes());
                LogUtils.d("yjk", "requestResources  progress :" + calculateProgress);
                if (transToLluDanceViewData.get(i2).getState() != 8) {
                    transToLluDanceViewData.get(i2).setState(status);
                }
                transToLluDanceViewData.get(i2).setDownloadedPercentage(calculateProgress);
            }
        }
        return transToLluDanceViewData;
    }

    public void startDownload(final ResourceBean resourceBean) {
        XuiClientWrapper.getInstance().getResourceServiceApiCallExecutor().execute(new Runnable() { // from class: com.xiaopeng.lludancemanager.model.-$$Lambda$LluDanceRepository$-_UEERjzctcOq8-RMq83LTIuVt4
            @Override // java.lang.Runnable
            public final void run() {
                LluDanceRepository.lambda$startDownload$1(ResourceBean.this);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$startDownload$1(final ResourceBean resourceBean) {
        if (XuiClientWrapper.getInstance().getResourceManager() != null) {
            XuiClientWrapper.getInstance().getResourceManager().start(resourceBean);
        } else {
            LogUtils.d("yjk", "pauseDownload  -->  ResourceManager can not work ");
        }
    }

    public void pauseDownload(final String id) {
        XuiClientWrapper.getInstance().getResourceServiceApiCallExecutor().execute(new Runnable() { // from class: com.xiaopeng.lludancemanager.model.-$$Lambda$LluDanceRepository$gMjfMYzZ35bpecT-VwbHO_UXpjQ
            @Override // java.lang.Runnable
            public final void run() {
                LluDanceRepository.lambda$pauseDownload$2(id);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$pauseDownload$2(final String id) {
        if (XuiClientWrapper.getInstance().getResourceManager() != null) {
            XuiClientWrapper.getInstance().getResourceManager().pause(id);
        } else {
            LogUtils.d("yjk", "pauseDownload  -->  ResourceManager can not work ");
        }
    }

    public void resumeDownload(final String id) {
        XuiClientWrapper.getInstance().getResourceServiceApiCallExecutor().execute(new Runnable() { // from class: com.xiaopeng.lludancemanager.model.-$$Lambda$LluDanceRepository$JAwUMDaR8f2yU0-raD99fmIXLJ0
            @Override // java.lang.Runnable
            public final void run() {
                LluDanceRepository.lambda$resumeDownload$3(id);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$resumeDownload$3(final String id) {
        if (XuiClientWrapper.getInstance().getResourceManager() != null) {
            XuiClientWrapper.getInstance().getResourceManager().resume(id);
        } else {
            LogUtils.d("yjk", "resumeDownload  -->  ResourceManager can not work ");
        }
    }

    private List<LluDanceViewData> transToLluDanceViewData(ResourceContainerBean resourceContainerBean) {
        LluDanceExpandContent lluDanceExpandContent;
        ArrayList arrayList = new ArrayList();
        if (resourceContainerBean != null && resourceContainerBean.getResourceBeanList() != null) {
            LogUtils.d("yjk", resourceContainerBean.toString());
            List<ResourceBean> resourceBeanList = resourceContainerBean.getResourceBeanList();
            for (int i = 0; i < resourceBeanList.size(); i++) {
                ResourceBean resourceBean = resourceBeanList.get(i);
                if (resourceBean != null && (INVALID_ID_LOCAL.equals(resourceBean.getRscId()) || INVALID_ID_NET.equals(resourceBean.getRscId()))) {
                    LogUtils.w("yjk-skip", "Invalid resource id " + resourceBean.getRscIcon() + ", skip and continue!");
                } else {
                    LogUtils.d("yjk", resourceBean.toString());
                    String expandContent = resourceBean.getExpandContent();
                    LluDanceInstalledInfo lluDanceInstalledInfo = null;
                    if (TextUtils.isEmpty(expandContent)) {
                        lluDanceExpandContent = null;
                    } else {
                        lluDanceExpandContent = (LluDanceExpandContent) GsonUtil.fromJson(expandContent, (Class<Object>) LluDanceExpandContent.class);
                        LogUtils.d("yjk", lluDanceExpandContent != null ? lluDanceExpandContent.toString() : "expandContent is null");
                    }
                    String expandInstalledContent = resourceBean.getExpandInstalledContent();
                    if (!TextUtils.isEmpty(expandInstalledContent)) {
                        lluDanceInstalledInfo = (LluDanceInstalledInfo) GsonUtil.fromJson(expandInstalledContent, (Class<Object>) LluDanceInstalledInfo.class);
                        LogUtils.d("yjk", lluDanceInstalledInfo != null ? lluDanceInstalledInfo.toString() : "installedInfo is null");
                    }
                    arrayList.add(new LluDanceViewData(resourceBean.getRscName(), lluDanceExpandContent != null ? lluDanceExpandContent.getAuthor() : "", resourceBean.getRscIcon(), resourceBean.getRscId(), resourceBean.getDownloadUrl(), getStatus(resourceBean.getStatus()), lluDanceExpandContent != null ? lluDanceExpandContent.getDuration() : 0L, 0.0d, lluDanceInstalledInfo != null ? lluDanceInstalledInfo.getEffectName() : "", lluDanceInstalledInfo != null ? lluDanceInstalledInfo.getVideoPath() : ""));
                }
            }
        }
        return arrayList;
    }

    public static long calculateProgress(ResourceDownloadInfo info) {
        if (info == null) {
            return 0L;
        }
        long downloadedBytes = info.getDownloadedBytes();
        long totalBytes = info.getTotalBytes();
        if (downloadedBytes < 0 || totalBytes <= 0) {
            return 0L;
        }
        return Math.round((downloadedBytes / totalBytes) * 100.0d);
    }
}
