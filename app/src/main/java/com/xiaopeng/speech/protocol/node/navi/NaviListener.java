package com.xiaopeng.speech.protocol.node.navi;

import com.xiaopeng.speech.INodeListener;
import com.xiaopeng.speech.protocol.node.navi.bean.AddressBean;
import com.xiaopeng.speech.protocol.node.navi.bean.NaviPreferenceBean;
import com.xiaopeng.speech.protocol.node.navi.bean.NearbySearchBean;
import com.xiaopeng.speech.protocol.node.navi.bean.PathBean;
import com.xiaopeng.speech.protocol.node.navi.bean.PoiBean;
import com.xiaopeng.speech.protocol.node.navi.bean.PoiSearchBean;
import com.xiaopeng.speech.protocol.node.navi.bean.SelectParkingBean;
import com.xiaopeng.speech.protocol.node.navi.bean.SelectRouteBean;
import com.xiaopeng.speech.protocol.node.navi.bean.StartNaviBean;
import com.xiaopeng.speech.protocol.node.navi.bean.WaypointSearchBean;

/* loaded from: classes2.dex */
public interface NaviListener extends INodeListener {
    void onAddressGet(AddressBean addressBean);

    void onAddressSet(AddressBean addressBean, PoiBean poiBean);

    default void onAlertsPreferenceSet(NaviPreferenceBean naviPreferenceBean) {
    }

    default void onAutoRerouteAskFirst() {
    }

    default void onAutoRerouteBetterRoute() {
    }

    default void onAutoRerouteNever() {
    }

    default void onAvoidRouteSet(NaviPreferenceBean naviPreferenceBean) {
    }

    void onCloseTraffic();

    void onConfirmCancel();

    void onConfirmOk();

    void onControlChargeClose();

    void onControlChargeOpen();

    void onControlClose();

    void onControlCloseRibbonMap();

    void onControlCloseSmallMap();

    void onControlDisPlayCar();

    void onControlDisPlayNorth();

    void onControlDisplay3D();

    void onControlFavoriteClose();

    void onControlFavoriteOpen();

    void onControlHistory();

    default void onControlHistoryClose() {
    }

    void onControlOpenRibbonMap();

    void onControlOpenSmallMap();

    void onControlOverviewClose();

    void onControlOverviewOpen();

    default void onControlParkRecommendOff() {
    }

    default void onControlParkRecommendOn() {
    }

    void onControlRoadAhead();

    void onControlRoadAheadOff();

    void onControlSecurityRemind();

    void onControlSecurityRemindOff();

    default void onControlSettingsClose() {
    }

    void onControlSettingsOpen();

    void onControlSmartScale();

    void onControlSmartScaleOff();

    void onControlSpeechDetail();

    void onControlSpeechEye();

    void onControlSpeechEyeOff();

    void onControlSpeechGeneral();

    void onControlSpeechSimple();

    void onControlSpeechSuperSimple();

    void onControlStart(StartNaviBean startNaviBean);

    void onControlVolOff(boolean z, int i);

    void onControlVolOn(boolean z, int i);

    void onControlWaypointStart(PathBean pathBean);

    void onDataControlDisplay3dTts();

    void onDataControlDisplayCarTts();

    void onDataControlDisplayNorthTts();

    void onDriveAdvoidTrafficControl();

    void onDriveAvoidCharge();

    void onDriveAvoidChargeOff();

    void onDriveAvoidCongestion();

    void onDriveAvoidCongestionOff();

    void onDriveAvoidControls();

    void onDriveAvoidControlsOff();

    void onDriveHighwayFirst();

    void onDriveHighwayFirstOff();

    void onDriveHighwayNo();

    void onDriveHighwayNoOff();

    void onDriveRadarRoute();

    void onDriveRadarRouteOff();

    void onGetSettingsInfo();

    void onMainRoad();

    void onMapEnterFindPath();

    void onMapExitFindPath();

    void onMapOverview();

    default void onMapShowSet(NaviPreferenceBean naviPreferenceBean) {
    }

    void onMapZoomIn();

    void onMapZoomOut();

    void onMapZoominMax();

    void onMapZoomoutMin();

    boolean onNavigatingGet();

    void onNearbySearch(NearbySearchBean nearbySearchBean);

    void onOpenTraffic();

    void onParkingSelect(SelectParkingBean selectParkingBean);

    default void onPoiDetailsFavoriteAdd() {
    }

    default void onPoiDetailsFavoriteDel() {
    }

    void onPoiSearch(PoiSearchBean poiSearchBean);

    void onRouteNearbySearch(NearbySearchBean nearbySearchBean);

    void onRouteSelect(SelectRouteBean selectRouteBean);

    void onSearchClose();

    void onSelectParkingCount(SelectParkingBean selectParkingBean);

    void onSelectRouteCount(SelectRouteBean selectRouteBean);

    default void onSetScaleLevel(int i) {
    }

    void onSideRoad();

    void onWaypointSearch(WaypointSearchBean waypointSearchBean);
}
