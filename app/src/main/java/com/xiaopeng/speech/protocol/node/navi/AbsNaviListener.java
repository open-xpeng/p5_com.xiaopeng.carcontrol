package com.xiaopeng.speech.protocol.node.navi;

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
public abstract class AbsNaviListener implements NaviListener {
    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onAddressGet(AddressBean addressBean) {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onAddressSet(AddressBean addressBean, PoiBean poiBean) {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onAlertsPreferenceSet(NaviPreferenceBean naviPreferenceBean) {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onAutoRerouteAskFirst() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onAutoRerouteBetterRoute() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onAutoRerouteNever() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onAvoidRouteSet(NaviPreferenceBean naviPreferenceBean) {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onCloseTraffic() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onConfirmCancel() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onConfirmOk() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlChargeClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlChargeOpen() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlCloseRibbonMap() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlCloseSmallMap() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlDisPlayCar() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlDisPlayNorth() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlDisplay3D() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlFavoriteClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlFavoriteOpen() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlHistory() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlHistoryClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlOpenRibbonMap() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlOpenSmallMap() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlOverviewClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlOverviewOpen() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlParkRecommendOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlParkRecommendOn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlRoadAhead() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlRoadAheadOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlSecurityRemind() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlSecurityRemindOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlSettingsClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlSettingsOpen() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlSmartScale() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlSmartScaleOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlSpeechDetail() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlSpeechEye() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlSpeechEyeOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlSpeechGeneral() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlSpeechSimple() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlSpeechSuperSimple() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlStart(StartNaviBean startNaviBean) {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlVolOff(boolean z, int i) {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlVolOn(boolean z, int i) {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onControlWaypointStart(PathBean pathBean) {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onDataControlDisplay3dTts() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onDataControlDisplayCarTts() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onDataControlDisplayNorthTts() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onDriveAdvoidTrafficControl() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onDriveAvoidCharge() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onDriveAvoidChargeOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onDriveAvoidCongestion() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onDriveAvoidCongestionOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onDriveAvoidControls() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onDriveAvoidControlsOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onDriveHighwayFirst() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onDriveHighwayFirstOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onDriveHighwayNo() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onDriveHighwayNoOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onDriveRadarRoute() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onDriveRadarRouteOff() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onGetSettingsInfo() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onMainRoad() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onMapEnterFindPath() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onMapExitFindPath() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onMapOverview() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onMapShowSet(NaviPreferenceBean naviPreferenceBean) {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onMapZoomIn() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onMapZoomOut() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onMapZoominMax() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onMapZoomoutMin() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public boolean onNavigatingGet() {
        return false;
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onNearbySearch(NearbySearchBean nearbySearchBean) {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onOpenTraffic() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onParkingSelect(SelectParkingBean selectParkingBean) {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onPoiDetailsFavoriteAdd() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onPoiDetailsFavoriteDel() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onPoiSearch(PoiSearchBean poiSearchBean) {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onRouteNearbySearch(NearbySearchBean nearbySearchBean) {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onRouteSelect(SelectRouteBean selectRouteBean) {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onSearchClose() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onSelectParkingCount(SelectParkingBean selectParkingBean) {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onSelectRouteCount(SelectRouteBean selectRouteBean) {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onSetScaleLevel(int i) {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onSideRoad() {
    }

    @Override // com.xiaopeng.speech.protocol.node.navi.NaviListener
    public void onWaypointSearch(WaypointSearchBean waypointSearchBean) {
    }
}
