package com.xiaopeng.speech.protocol.query.navi;

import com.xiaopeng.speech.SpeechQuery;
import com.xiaopeng.speech.protocol.node.navi.bean.AddressBean;

/* loaded from: classes2.dex */
public class MapQuery extends SpeechQuery<IMapQueryCaller> {
    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isCruise(String str, String str2) {
        return ((IMapQueryCaller) this.mQueryCaller).isCruise();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isExplorePath(String str, String str2) {
        return ((IMapQueryCaller) this.mQueryCaller).isExplorePath();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isNavigation(String str, String str2) {
        return ((IMapQueryCaller) this.mQueryCaller).isNavigation();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getAddress(String str, String str2) {
        return ((IMapQueryCaller) this.mQueryCaller).getCommonAddress(AddressBean.fromJson(str2));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public String getNavigationInfo(String str, String str2) {
        return ((IMapQueryCaller) this.mQueryCaller).getNavigationInfo();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isZoominMax(String str, String str2) {
        return ((IMapQueryCaller) this.mQueryCaller).isZoominMax();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isZoomoutMax(String str, String str2) {
        return ((IMapQueryCaller) this.mQueryCaller).isZoomoutMin();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isCalculatePath(String str, String str2) {
        return ((IMapQueryCaller) this.mQueryCaller).isCalculatePath();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getFavoriteOpenStatus(String str, String str2) {
        return ((IMapQueryCaller) this.mQueryCaller).getFavoriteOpenStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getOpenControlStatus(String str, String str2) {
        return ((IMapQueryCaller) this.mQueryCaller).getOpenControlStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSwitchMainRoadStatus(String str, String str2) {
        return ((IMapQueryCaller) this.mQueryCaller).getSwitchMainRoadStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getSwitchSideRoadStatus(String str, String str2) {
        return ((IMapQueryCaller) this.mQueryCaller).getSwitchSideRoadStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getCurrentScaleLevel(String str, String str2) {
        return ((IMapQueryCaller) this.mQueryCaller).getCurrentScaleLevel();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public int getPoiDetailsFavoriteStatus(String str, String str2) {
        return ((IMapQueryCaller) this.mQueryCaller).getPoiDetailsFavoriteStatus();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSRMap(String str, String str2) {
        return ((IMapQueryCaller) this.mQueryCaller).isSRMap();
    }
}
