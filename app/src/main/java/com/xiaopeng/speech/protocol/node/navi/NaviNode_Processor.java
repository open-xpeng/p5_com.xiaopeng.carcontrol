package com.xiaopeng.speech.protocol.node.navi;

import com.alibaba.fastjson.parser.JSONLexer;
import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.NaviEvent;

/* loaded from: classes2.dex */
public class NaviNode_Processor implements ICommandProcessor {
    private NaviNode mTarget;

    public NaviNode_Processor(NaviNode naviNode) {
        this.mTarget = naviNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2136970098:
                if (str.equals(NaviEvent.CONTROL_CLOSE)) {
                    c = 0;
                    break;
                }
                break;
            case -2132799004:
                if (str.equals(NaviEvent.DRIVE_RADAR_ROUTE)) {
                    c = 1;
                    break;
                }
                break;
            case -2121968904:
                if (str.equals(NaviEvent.CONTROL_START)) {
                    c = 2;
                    break;
                }
                break;
            case -1987448085:
                if (str.equals(NaviEvent.SEARCH_CLOSE)) {
                    c = 3;
                    break;
                }
                break;
            case -1909621251:
                if (str.equals(NaviEvent.AUTO_REROUTE_ASK_FIRST)) {
                    c = 4;
                    break;
                }
                break;
            case -1907058252:
                if (str.equals(NaviEvent.CONTROL_CHARGE_CLOSE)) {
                    c = 5;
                    break;
                }
                break;
            case -1667606279:
                if (str.equals(NaviEvent.MAP_ZOOMIN_MAX)) {
                    c = 6;
                    break;
                }
                break;
            case -1667539314:
                if (str.equals(NaviEvent.AUTO_REROUTE_NEVER)) {
                    c = 7;
                    break;
                }
                break;
            case -1518898323:
                if (str.equals(NaviEvent.CONTROL_OVERVIEW_CLOSE)) {
                    c = '\b';
                    break;
                }
                break;
            case -1475394262:
                if (str.equals(NaviEvent.DRIVE_AVOID_CHARGE)) {
                    c = '\t';
                    break;
                }
                break;
            case -1467020709:
                if (str.equals(NaviEvent.CONTROL_OPEN_RIBBON_MAP)) {
                    c = '\n';
                    break;
                }
                break;
            case -1380219901:
                if (str.equals(NaviEvent.MAP_ZOOMIN)) {
                    c = 11;
                    break;
                }
                break;
            case -1361583363:
                if (str.equals(NaviEvent.SELECT_ROUTE_COUNT)) {
                    c = '\f';
                    break;
                }
                break;
            case -1316967297:
                if (str.equals(NaviEvent.CONTROL_SETTINGS_OPEN)) {
                    c = '\r';
                    break;
                }
                break;
            case -1274994428:
                if (str.equals(NaviEvent.CONTROL_VOL_ON)) {
                    c = 14;
                    break;
                }
                break;
            case -1169768037:
                if (str.equals(NaviEvent.NAVIGATING_GET)) {
                    c = 15;
                    break;
                }
                break;
            case -1141056686:
                if (str.equals(NaviEvent.DRIVE_HIGHWAY_FIRST)) {
                    c = 16;
                    break;
                }
                break;
            case -1121697225:
                if (str.equals(NaviEvent.ADDRESS_GET)) {
                    c = 17;
                    break;
                }
                break;
            case -982285267:
                if (str.equals(NaviEvent.DRIVE_AVOID_CONTROLS_OFF)) {
                    c = 18;
                    break;
                }
                break;
            case -977315249:
                if (str.equals(NaviEvent.CONTROL_SPEECH_EYE)) {
                    c = 19;
                    break;
                }
                break;
            case -957206192:
                if (str.equals(NaviEvent.CONTROL_SPEECH_EYE_OFF)) {
                    c = 20;
                    break;
                }
                break;
            case -873701781:
                if (str.equals(NaviEvent.PARKING_SELECT)) {
                    c = 21;
                    break;
                }
                break;
            case -870121750:
                if (str.equals(NaviEvent.CONTROL_VOL_OFF)) {
                    c = 22;
                    break;
                }
                break;
            case -793405345:
                if (str.equals(NaviEvent.DRIVE_HIGHWAY_NO)) {
                    c = 23;
                    break;
                }
                break;
            case -780474528:
                if (str.equals(NaviEvent.DRIVE_HIGHWAY_NO_OFF)) {
                    c = 24;
                    break;
                }
                break;
            case -741372363:
                if (str.equals(NaviEvent.CONTROL_OVERVIEW_OPEN)) {
                    c = 25;
                    break;
                }
                break;
            case -735619553:
                if (str.equals(NaviEvent.ADDRESS_PENDING_ROUTE)) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case -682936661:
                if (str.equals(NaviEvent.DRIVE_AVOID_CHARGE_OFF)) {
                    c = 27;
                    break;
                }
                break;
            case -629123846:
                if (str.equals(NaviEvent.ROUTE_SELECT)) {
                    c = 28;
                    break;
                }
                break;
            case -585100656:
                if (str.equals(NaviEvent.WAYPOINT_SEARCH)) {
                    c = 29;
                    break;
                }
                break;
            case -573495894:
                if (str.equals(NaviEvent.CONTROL_HISTORY)) {
                    c = 30;
                    break;
                }
                break;
            case -550413506:
                if (str.equals(NaviEvent.SIDE_ROAD)) {
                    c = 31;
                    break;
                }
                break;
            case -483469262:
                if (str.equals(NaviEvent.CONTROL_PARK_RECOMMEND_OFF)) {
                    c = ' ';
                    break;
                }
                break;
            case -483373155:
                if (str.equals(NaviEvent.DRIVE_AVOID_CONGESTION)) {
                    c = '!';
                    break;
                }
                break;
            case -471273732:
                if (str.equals(NaviEvent.MAP_SHOW_SET)) {
                    c = '\"';
                    break;
                }
                break;
            case -463743293:
                if (str.equals(NaviEvent.CONFIRM_OK)) {
                    c = '#';
                    break;
                }
                break;
            case -432687108:
                if (str.equals(NaviEvent.CONTROL_SMART_SCALE_OFF)) {
                    c = '$';
                    break;
                }
                break;
            case -336305704:
                if (str.equals(NaviEvent.CONTROL_POI_DETAILS_FAVORITE_ADD)) {
                    c = '%';
                    break;
                }
                break;
            case -336302782:
                if (str.equals(NaviEvent.CONTROL_POI_DETAILS_FAVORITE_DEL)) {
                    c = '&';
                    break;
                }
                break;
            case -287805857:
                if (str.equals(NaviEvent.CONTROL_WAYPOINT_START)) {
                    c = '\'';
                    break;
                }
                break;
            case -257936759:
                if (str.equals(NaviEvent.CONTROL_SECURITY_REMIND)) {
                    c = '(';
                    break;
                }
                break;
            case -245623106:
                if (str.equals(NaviEvent.CONTROL_DISPLAY_CAR)) {
                    c = ')';
                    break;
                }
                break;
            case -60216351:
                if (str.equals(NaviEvent.CONFIRM_CANCEL)) {
                    c = '*';
                    break;
                }
                break;
            case 30697630:
                if (str.equals(NaviEvent.CONTROL_ROAD_AHEAD_OFF)) {
                    c = '+';
                    break;
                }
                break;
            case 162862128:
                if (str.equals(NaviEvent.MAP_ZOOMOUT)) {
                    c = ',';
                    break;
                }
                break;
            case 189975919:
                if (str.equals(NaviEvent.CONTROL_DISPLAY_NORTH)) {
                    c = '-';
                    break;
                }
                break;
            case 219354725:
                if (str.equals(NaviEvent.DRIVE_RADAR_ROUTE_OFF)) {
                    c = '.';
                    break;
                }
                break;
            case 235759773:
                if (str.equals(NaviEvent.CONTROL_ROAD_AHEAD)) {
                    c = '/';
                    break;
                }
                break;
            case 255171078:
                if (str.equals(NaviEvent.CONTROL_SPEECH_GENERAL)) {
                    c = '0';
                    break;
                }
                break;
            case 295754221:
                if (str.equals(NaviEvent.ROUTE_NEARBY_SEARCH)) {
                    c = '1';
                    break;
                }
                break;
            case 338160499:
                if (str.equals(NaviEvent.CONTROL_SPEECH_DETAIL)) {
                    c = '2';
                    break;
                }
                break;
            case 354485006:
                if (str.equals(NaviEvent.CONTROL_CHARGE_OPEN)) {
                    c = '3';
                    break;
                }
                break;
            case 398795396:
                if (str.equals(NaviEvent.MAP_OVERVIEW)) {
                    c = '4';
                    break;
                }
                break;
            case 431524135:
                if (str.equals(NaviEvent.CONTROL_SPEECH_SUPER_SIMPLE)) {
                    c = '5';
                    break;
                }
                break;
            case 560836717:
                if (str.equals(NaviEvent.ALERTS_PREFERENCE_SET)) {
                    c = '6';
                    break;
                }
                break;
            case 592257019:
                if (str.equals(NaviEvent.SCALE_LEVEL_SET)) {
                    c = '7';
                    break;
                }
                break;
            case 757845918:
                if (str.equals(NaviEvent.DRIVE_AVOID_CONGESTION_OFF)) {
                    c = '8';
                    break;
                }
                break;
            case 771097812:
                if (str.equals(NaviEvent.CONTROL_SPEECH_SIMPLE)) {
                    c = '9';
                    break;
                }
                break;
            case 784930555:
                if (str.equals(NaviEvent.CONTROL_SMART_SCALE)) {
                    c = ':';
                    break;
                }
                break;
            case 838195437:
                if (str.equals(NaviEvent.ROAD_INFO_CLOSE)) {
                    c = ';';
                    break;
                }
                break;
            case 858683573:
                if (str.equals(NaviEvent.ROAD_INFO_OPEN)) {
                    c = '<';
                    break;
                }
                break;
            case 950634676:
                if (str.equals(NaviEvent.CONTROL_HISTORY_CLOSE)) {
                    c = '=';
                    break;
                }
                break;
            case 1047011356:
                if (str.equals(NaviEvent.MAIN_ROAD)) {
                    c = '>';
                    break;
                }
                break;
            case 1082133391:
                if (str.equals(NaviEvent.ADDRESS_SET)) {
                    c = '?';
                    break;
                }
                break;
            case 1137052564:
                if (str.equals(NaviEvent.MAP_ZOOMOUT_MIN)) {
                    c = '@';
                    break;
                }
                break;
            case 1247853748:
                if (str.equals(NaviEvent.SETTINGS_INFO_GET)) {
                    c = 'A';
                    break;
                }
                break;
            case 1255072401:
                if (str.equals(NaviEvent.POI_SEARCH)) {
                    c = 'B';
                    break;
                }
                break;
            case 1356046610:
                if (str.equals(NaviEvent.NEARBY_SEARCH)) {
                    c = 'C';
                    break;
                }
                break;
            case 1631355563:
                if (str.equals(NaviEvent.AVOID_ROUTE_SET)) {
                    c = 'D';
                    break;
                }
                break;
            case 1632960358:
                if (str.equals(NaviEvent.CONTROL_CLOSE_SMALL_MAP)) {
                    c = 'E';
                    break;
                }
                break;
            case 1687314057:
                if (str.equals(NaviEvent.AUTO_REROUTE_BETTER_ROUTE)) {
                    c = 'F';
                    break;
                }
                break;
            case 1735961708:
                if (str.equals(NaviEvent.SELECT_PARKING_COUNT)) {
                    c = 'G';
                    break;
                }
                break;
            case 1755806727:
                if (str.equals(NaviEvent.CONTROL_CLOSE_RIBBON_MAP)) {
                    c = 'H';
                    break;
                }
                break;
            case 1759009930:
                if (str.equals(NaviEvent.CONTROL_SECURITY_REMIND_OFF)) {
                    c = 'I';
                    break;
                }
                break;
            case 1777247148:
                if (str.equals(NaviEvent.DRIVE_AVOID_CONTROLS)) {
                    c = 'J';
                    break;
                }
                break;
            case 1793190503:
                if (str.equals(NaviEvent.CONTROL_DISPLAY_3D)) {
                    c = 'K';
                    break;
                }
                break;
            case 1832000151:
                if (str.equals(NaviEvent.CONTROL_SETTINGS_CLOSE)) {
                    c = 'L';
                    break;
                }
                break;
            case 1854152476:
                if (str.equals(NaviEvent.CONTROL_OPEN_SMALL_MAP)) {
                    c = 'M';
                    break;
                }
                break;
            case 1890678995:
                if (str.equals(NaviEvent.DRIVE_HIGHWAY_FIRST_OFF)) {
                    c = 'N';
                    break;
                }
                break;
            case 1961191760:
                if (str.equals(NaviEvent.CONTROL_FAVORITE_CLOSE)) {
                    c = 'O';
                    break;
                }
                break;
            case 1963423819:
                if (str.equals(NaviEvent.MAP_ENTER_FIND_PATH)) {
                    c = 'P';
                    break;
                }
                break;
            case 2040031459:
                if (str.equals(NaviEvent.MAP_EXIT_FIND_PATH)) {
                    c = 'Q';
                    break;
                }
                break;
            case 2062614204:
                if (str.equals(NaviEvent.CONTROL_PARK_RECOMMEND_ON)) {
                    c = 'R';
                    break;
                }
                break;
            case 2141835250:
                if (str.equals(NaviEvent.CONTROL_FAVORITE_OPEN)) {
                    c = 'S';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onControlClose(str, str2);
                return;
            case 1:
                this.mTarget.onDriveRadarRoute(str, str2);
                return;
            case 2:
                this.mTarget.onControlStart(str, str2);
                return;
            case 3:
                this.mTarget.onSearchClose(str, str2);
                return;
            case 4:
                this.mTarget.onAutoRerouteAskFirst(str, str2);
                return;
            case 5:
                this.mTarget.onControlChargeClose(str, str2);
                return;
            case 6:
                this.mTarget.onMapZoominMax(str, str2);
                return;
            case 7:
                this.mTarget.onAutoRerouteNever(str, str2);
                return;
            case '\b':
                this.mTarget.onControlOverviewClose(str, str2);
                return;
            case '\t':
                this.mTarget.onDriveAvoidCharge(str, str2);
                return;
            case '\n':
                this.mTarget.onControlOpenRibbonMap(str, str2);
                return;
            case 11:
                this.mTarget.onMapZoomIn(str, str2);
                return;
            case '\f':
                this.mTarget.onSelectRouteCount(str, str2);
                return;
            case '\r':
                this.mTarget.onControlSettingsOpen(str, str2);
                return;
            case 14:
                this.mTarget.onControlVolOn(str, str2);
                return;
            case 15:
                this.mTarget.onNavigatingGet(str, str2);
                return;
            case 16:
                this.mTarget.onDriveHighwayFirst(str, str2);
                return;
            case 17:
                this.mTarget.onAddressGet(str, str2);
                return;
            case 18:
                this.mTarget.onDriveAvoidControlsOff(str, str2);
                return;
            case 19:
                this.mTarget.onControlSpeechEye(str, str2);
                return;
            case 20:
                this.mTarget.onControlSpeechEyeOff(str, str2);
                return;
            case 21:
                this.mTarget.onParkingSelect(str, str2);
                return;
            case 22:
                this.mTarget.onControlVolOff(str, str2);
                return;
            case 23:
                this.mTarget.onDriveHighwayNo(str, str2);
                return;
            case 24:
                this.mTarget.onDriveHighwayNoOff(str, str2);
                return;
            case 25:
                this.mTarget.onControlOverviewOpen(str, str2);
                return;
            case 26:
                this.mTarget.onAddressPendingRoute(str, str2);
                return;
            case 27:
                this.mTarget.onDriveAvoidChargeOff(str, str2);
                return;
            case 28:
                this.mTarget.onRouteSelect(str, str2);
                return;
            case 29:
                this.mTarget.onWaypointSearch(str, str2);
                return;
            case 30:
                this.mTarget.onControlHistory(str, str2);
                return;
            case 31:
                this.mTarget.onSideRoad(str, str2);
                return;
            case ' ':
                this.mTarget.onControlParkRecommendOff(str, str2);
                return;
            case '!':
                this.mTarget.onDriveAvoidCongestion(str, str2);
                return;
            case '\"':
                this.mTarget.onMapShowSet(str, str2);
                return;
            case '#':
                this.mTarget.onConfirmOk(str, str2);
                return;
            case '$':
                this.mTarget.onControlSmartScaleOff(str, str2);
                return;
            case '%':
                this.mTarget.onPoiDetailsFavoriteAdd(str, str2);
                return;
            case '&':
                this.mTarget.onPoiDetailsFavoriteDel(str, str2);
                return;
            case '\'':
                this.mTarget.onControlWaypointStart(str, str2);
                return;
            case '(':
                this.mTarget.onControlSecurityRemind(str, str2);
                return;
            case ')':
                this.mTarget.onControlDisPlayCar(str, str2);
                return;
            case '*':
                this.mTarget.onConfirmCancel(str, str2);
                return;
            case '+':
                this.mTarget.onControlRoadAheadOff(str, str2);
                return;
            case ',':
                this.mTarget.onMapZoomOut(str, str2);
                return;
            case '-':
                this.mTarget.onControlDisPlayNorth(str, str2);
                return;
            case '.':
                this.mTarget.onDriveRadarRouteOff(str, str2);
                return;
            case '/':
                this.mTarget.onControlRoadAhead(str, str2);
                return;
            case '0':
                this.mTarget.onControlSpeechGeneral(str, str2);
                return;
            case '1':
                this.mTarget.onRouteNearbySearch(str, str2);
                return;
            case '2':
                this.mTarget.onControlSpeechDetail(str, str2);
                return;
            case '3':
                this.mTarget.onControlChargeOpen(str, str2);
                return;
            case '4':
                this.mTarget.onMapOverview(str, str2);
                return;
            case '5':
                this.mTarget.onControlSpeechSuperSimple(str, str2);
                return;
            case '6':
                this.mTarget.onAlertsPreferenceSet(str, str2);
                return;
            case '7':
                this.mTarget.onSetScaleLevel(str, str2);
                return;
            case '8':
                this.mTarget.onDriveAvoidCongestionOff(str, str2);
                return;
            case '9':
                this.mTarget.onControlSpeechSimple(str, str2);
                return;
            case ':':
                this.mTarget.onControlSmartScale(str, str2);
                return;
            case ';':
                this.mTarget.onCloseTraffic(str, str2);
                return;
            case '<':
                this.mTarget.onOpenTraffic(str, str2);
                return;
            case '=':
                this.mTarget.onControlHistoryCLose(str, str2);
                return;
            case '>':
                this.mTarget.onMainRoad(str, str2);
                return;
            case '?':
                this.mTarget.onAddressSet(str, str2);
                return;
            case '@':
                this.mTarget.onMapZoomoutMin(str, str2);
                return;
            case 'A':
                this.mTarget.onGetSettingsInfo(str, str2);
                return;
            case 'B':
                this.mTarget.onPoiSearch(str, str2);
                return;
            case 'C':
                this.mTarget.onNearbySearch(str, str2);
                return;
            case 'D':
                this.mTarget.onAvoidRouteSet(str, str2);
                return;
            case 'E':
                this.mTarget.onControlCloseSmallMap(str, str2);
                return;
            case 'F':
                this.mTarget.onAutoRerouteBetterRoute(str, str2);
                return;
            case 'G':
                this.mTarget.onSelectParkingCount(str, str2);
                return;
            case 'H':
                this.mTarget.onControlCloseRibbonMap(str, str2);
                return;
            case 'I':
                this.mTarget.onControlSecurityRemindOff(str, str2);
                return;
            case 'J':
                this.mTarget.onDriveAvoidControls(str, str2);
                return;
            case 'K':
                this.mTarget.onControlDisplay3D(str, str2);
                return;
            case 'L':
                this.mTarget.onControlSettingsCLose(str, str2);
                return;
            case 'M':
                this.mTarget.onControlOpenSmallMap(str, str2);
                return;
            case 'N':
                this.mTarget.onDriveHighwayFirstOff(str, str2);
                return;
            case 'O':
                this.mTarget.onControlFavoriteClose(str, str2);
                return;
            case 'P':
                this.mTarget.onMapEnterFindPath(str, str2);
                return;
            case 'Q':
                this.mTarget.onMapExitFindPath(str, str2);
                return;
            case 'R':
                this.mTarget.onControlParkRecommendOn(str, str2);
                return;
            case 'S':
                this.mTarget.onControlFavoriteOpen(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{NaviEvent.CONTROL_CLOSE, NaviEvent.MAP_ZOOMIN, NaviEvent.MAP_ZOOMOUT, NaviEvent.ROAD_INFO_OPEN, NaviEvent.ROAD_INFO_CLOSE, NaviEvent.CONTROL_OVERVIEW_OPEN, NaviEvent.CONTROL_OVERVIEW_CLOSE, NaviEvent.MAP_OVERVIEW, NaviEvent.CONTROL_FAVORITE_OPEN, NaviEvent.CONTROL_SETTINGS_OPEN, NaviEvent.CONTROL_CHARGE_OPEN, NaviEvent.CONTROL_CHARGE_CLOSE, NaviEvent.DRIVE_AVOID_CONGESTION, NaviEvent.DRIVE_AVOID_CONGESTION_OFF, NaviEvent.DRIVE_AVOID_CHARGE, NaviEvent.DRIVE_AVOID_CHARGE_OFF, NaviEvent.DRIVE_HIGHWAY_FIRST_OFF, NaviEvent.DRIVE_AVOID_CONTROLS, NaviEvent.DRIVE_AVOID_CONTROLS_OFF, NaviEvent.DRIVE_RADAR_ROUTE, NaviEvent.DRIVE_RADAR_ROUTE_OFF, NaviEvent.CONTROL_SPEECH_SUPER_SIMPLE, NaviEvent.CONTROL_SPEECH_GENERAL, NaviEvent.CONTROL_SPEECH_EYE, NaviEvent.CONTROL_SPEECH_EYE_OFF, NaviEvent.CONTROL_SMART_SCALE, NaviEvent.CONTROL_SMART_SCALE_OFF, NaviEvent.CONTROL_SECURITY_REMIND, NaviEvent.CONTROL_ROAD_AHEAD, NaviEvent.DRIVE_HIGHWAY_NO, NaviEvent.DRIVE_HIGHWAY_NO_OFF, NaviEvent.DRIVE_HIGHWAY_FIRST, NaviEvent.NAVIGATING_GET, NaviEvent.POI_SEARCH, NaviEvent.CONTROL_SECURITY_REMIND_OFF, NaviEvent.MAP_ENTER_FIND_PATH, NaviEvent.MAP_EXIT_FIND_PATH, NaviEvent.SEARCH_CLOSE, NaviEvent.MAIN_ROAD, NaviEvent.SIDE_ROAD, NaviEvent.CONTROL_FAVORITE_CLOSE, NaviEvent.CONTROL_ROAD_AHEAD_OFF, NaviEvent.MAP_ZOOMIN_MAX, NaviEvent.MAP_ZOOMOUT_MIN, NaviEvent.NEARBY_SEARCH, NaviEvent.ADDRESS_GET, NaviEvent.ADDRESS_PENDING_ROUTE, NaviEvent.ADDRESS_SET, NaviEvent.CONTROL_START, NaviEvent.CONTROL_SPEECH_SIMPLE, NaviEvent.CONTROL_SPEECH_DETAIL, NaviEvent.CONTROL_DISPLAY_NORTH, NaviEvent.CONTROL_DISPLAY_CAR, NaviEvent.CONTROL_DISPLAY_3D, NaviEvent.CONTROL_VOL_ON, NaviEvent.CONTROL_VOL_OFF, NaviEvent.ROUTE_NEARBY_SEARCH, NaviEvent.PARKING_SELECT, NaviEvent.CONFIRM_OK, NaviEvent.CONFIRM_CANCEL, NaviEvent.ROUTE_SELECT, NaviEvent.SELECT_PARKING_COUNT, NaviEvent.SELECT_ROUTE_COUNT, NaviEvent.WAYPOINT_SEARCH, NaviEvent.CONTROL_WAYPOINT_START, NaviEvent.CONTROL_OPEN_SMALL_MAP, NaviEvent.CONTROL_CLOSE_SMALL_MAP, NaviEvent.CONTROL_OPEN_RIBBON_MAP, NaviEvent.CONTROL_CLOSE_RIBBON_MAP, NaviEvent.CONTROL_HISTORY, NaviEvent.SETTINGS_INFO_GET, NaviEvent.CONTROL_PARK_RECOMMEND_ON, NaviEvent.CONTROL_PARK_RECOMMEND_OFF, NaviEvent.SCALE_LEVEL_SET, NaviEvent.ALERTS_PREFERENCE_SET, NaviEvent.AVOID_ROUTE_SET, NaviEvent.AUTO_REROUTE_BETTER_ROUTE, NaviEvent.AUTO_REROUTE_ASK_FIRST, NaviEvent.AUTO_REROUTE_NEVER, NaviEvent.MAP_SHOW_SET, NaviEvent.CONTROL_POI_DETAILS_FAVORITE_ADD, NaviEvent.CONTROL_POI_DETAILS_FAVORITE_DEL, NaviEvent.CONTROL_SETTINGS_CLOSE, NaviEvent.CONTROL_HISTORY_CLOSE};
    }
}
