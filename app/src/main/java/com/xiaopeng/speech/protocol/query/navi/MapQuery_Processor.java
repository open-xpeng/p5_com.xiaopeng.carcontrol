package com.xiaopeng.speech.protocol.query.navi;

import com.xiaopeng.speech.annotation.IQueryProcessor;
import com.xiaopeng.speech.protocol.event.query.QueryNaviEvent;

/* loaded from: classes2.dex */
public class MapQuery_Processor implements IQueryProcessor {
    private MapQuery mTarget;

    public MapQuery_Processor(MapQuery mapQuery) {
        this.mTarget = mapQuery;
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public Object querySensor(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1925707028:
                if (str.equals(QueryNaviEvent.GET_POI_DETAILS_FAVORITE_STATUS)) {
                    c = 0;
                    break;
                }
                break;
            case -1697099311:
                if (str.equals(QueryNaviEvent.IS_CRUISE)) {
                    c = 1;
                    break;
                }
                break;
            case -1571917085:
                if (str.equals(QueryNaviEvent.SCALE_CURRENT_LEVEL)) {
                    c = 2;
                    break;
                }
                break;
            case -1195178996:
                if (str.equals(QueryNaviEvent.REPLY_FAVORITE_OPEN_STATUS)) {
                    c = 3;
                    break;
                }
                break;
            case -851414168:
                if (str.equals(QueryNaviEvent.GET_NAVIGATION_INFO)) {
                    c = 4;
                    break;
                }
                break;
            case -372606704:
                if (str.equals(QueryNaviEvent.IS_NAVIGATION)) {
                    c = 5;
                    break;
                }
                break;
            case -98628845:
                if (str.equals(QueryNaviEvent.REPLY_MAIN_ROAD_STATUS)) {
                    c = 6;
                    break;
                }
                break;
            case 18796803:
                if (str.equals(QueryNaviEvent.IS_ZOOMOUT_MIN)) {
                    c = 7;
                    break;
                }
                break;
            case 238106971:
                if (str.equals(QueryNaviEvent.IS_SR_MAP)) {
                    c = '\b';
                    break;
                }
                break;
            case 334610996:
                if (str.equals(QueryNaviEvent.GET_ADDRESS)) {
                    c = '\t';
                    break;
                }
                break;
            case 1182098865:
                if (str.equals(QueryNaviEvent.REPLY_SIDE_ROAD_STATUS)) {
                    c = '\n';
                    break;
                }
                break;
            case 1454660265:
                if (str.equals(QueryNaviEvent.IS_CALCULATE_PATH)) {
                    c = 11;
                    break;
                }
                break;
            case 1693312668:
                if (str.equals(QueryNaviEvent.IS_EXPLORE)) {
                    c = '\f';
                    break;
                }
                break;
            case 2037098922:
                if (str.equals(QueryNaviEvent.IS_ZOOMIN_MAX)) {
                    c = '\r';
                    break;
                }
                break;
            case 2041908174:
                if (str.equals(QueryNaviEvent.REPLY_OPEN_CONTROLS_STATUS)) {
                    c = 14;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                return Integer.valueOf(this.mTarget.getPoiDetailsFavoriteStatus(str, str2));
            case 1:
                return Boolean.valueOf(this.mTarget.isCruise(str, str2));
            case 2:
                return Integer.valueOf(this.mTarget.getCurrentScaleLevel(str, str2));
            case 3:
                return Integer.valueOf(this.mTarget.getFavoriteOpenStatus(str, str2));
            case 4:
                return this.mTarget.getNavigationInfo(str, str2);
            case 5:
                return Boolean.valueOf(this.mTarget.isNavigation(str, str2));
            case 6:
                return Integer.valueOf(this.mTarget.getSwitchMainRoadStatus(str, str2));
            case 7:
                return Boolean.valueOf(this.mTarget.isZoomoutMax(str, str2));
            case '\b':
                return Boolean.valueOf(this.mTarget.isSRMap(str, str2));
            case '\t':
                return this.mTarget.getAddress(str, str2);
            case '\n':
                return Integer.valueOf(this.mTarget.getSwitchSideRoadStatus(str, str2));
            case 11:
                return Boolean.valueOf(this.mTarget.isCalculatePath(str, str2));
            case '\f':
                return Boolean.valueOf(this.mTarget.isExplorePath(str, str2));
            case '\r':
                return Boolean.valueOf(this.mTarget.isZoominMax(str, str2));
            case 14:
                return Integer.valueOf(this.mTarget.getOpenControlStatus(str, str2));
            default:
                return null;
        }
    }

    @Override // com.xiaopeng.speech.annotation.IQueryProcessor
    public String[] getQueryEvents() {
        return new String[]{QueryNaviEvent.IS_CRUISE, QueryNaviEvent.IS_EXPLORE, QueryNaviEvent.IS_NAVIGATION, QueryNaviEvent.GET_ADDRESS, QueryNaviEvent.GET_NAVIGATION_INFO, QueryNaviEvent.IS_ZOOMIN_MAX, QueryNaviEvent.IS_ZOOMOUT_MIN, QueryNaviEvent.IS_CALCULATE_PATH, QueryNaviEvent.REPLY_FAVORITE_OPEN_STATUS, QueryNaviEvent.REPLY_OPEN_CONTROLS_STATUS, QueryNaviEvent.REPLY_MAIN_ROAD_STATUS, QueryNaviEvent.REPLY_SIDE_ROAD_STATUS, QueryNaviEvent.SCALE_CURRENT_LEVEL, QueryNaviEvent.GET_POI_DETAILS_FAVORITE_STATUS, QueryNaviEvent.IS_SR_MAP};
    }
}
