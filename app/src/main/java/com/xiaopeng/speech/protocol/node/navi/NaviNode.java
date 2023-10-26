package com.xiaopeng.speech.protocol.node.navi;

import android.text.TextUtils;
import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.actorapi.ResultActor;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.jarvisproto.FeedUIEvent;
import com.xiaopeng.speech.jarvisproto.WakeupResult;
import com.xiaopeng.speech.protocol.bean.FeedListUIValue;
import com.xiaopeng.speech.protocol.event.ContextEvent;
import com.xiaopeng.speech.protocol.event.NaviEvent;
import com.xiaopeng.speech.protocol.event.OOBEEvent;
import com.xiaopeng.speech.protocol.node.navi.bean.AddressBean;
import com.xiaopeng.speech.protocol.node.navi.bean.NaviPreferenceBean;
import com.xiaopeng.speech.protocol.node.navi.bean.NearbySearchBean;
import com.xiaopeng.speech.protocol.node.navi.bean.PathBean;
import com.xiaopeng.speech.protocol.node.navi.bean.PoiBean;
import com.xiaopeng.speech.protocol.node.navi.bean.PoiSearchBean;
import com.xiaopeng.speech.protocol.node.navi.bean.RouteSelectBean;
import com.xiaopeng.speech.protocol.node.navi.bean.SelectParkingBean;
import com.xiaopeng.speech.protocol.node.navi.bean.SelectRouteBean;
import com.xiaopeng.speech.protocol.node.navi.bean.StartNaviBean;
import com.xiaopeng.speech.protocol.node.navi.bean.WaypointSearchBean;
import com.xiaopeng.speech.speechwidget.ContentWidget;
import com.xiaopeng.speech.speechwidget.ListWidget;
import com.xiaopeng.speech.speechwidget.SpeechWidget;
import com.xiaopeng.speech.speechwidget.TextWidget;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class NaviNode extends SpeechNode<NaviListener> {
    private static final String ACTIVE_VOICE_TASK = "主动语音";
    public static final String ALL_ROUTE_WIDGET_ID = "-Route-2";
    public static final String BASE_ROUTE_WIDGET_ID = "-Route-1";
    private static final String COMMAND_SPLIT = "#";
    private static final String KEY_MODE = "mode";
    private static final String KEY_PULLUP_NAVI = "pullUpNavi";
    private static final String OFFLINE_SKILL = "离线命令词";
    private static final String SELECT_PARKING_INTENT = "停车场主动语音";
    private static final String SELECT_ROUTE_INTENT = "路线主动语音";
    private static final int STOP_DIALOG_OPT_FORCE = 0;
    private static final int STOP_DIALOG_OPT_OPTIONAL = 1;
    private boolean mAddressPendingRoute = false;

    public void onControlClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlClose();
            }
        }
    }

    public void onMapZoomIn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onMapZoomIn();
            }
        }
    }

    public void onMapZoomOut(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onMapZoomOut();
            }
        }
    }

    public void onOpenTraffic(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onOpenTraffic();
            }
        }
    }

    public void onCloseTraffic(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onCloseTraffic();
            }
        }
    }

    public void onControlOverviewOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlOverviewOpen();
            }
        }
    }

    public void onControlOverviewClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlOverviewClose();
            }
        }
    }

    public void onMapOverview(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onMapOverview();
            }
        }
    }

    public void onControlFavoriteOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlFavoriteOpen();
            }
        }
    }

    public void onControlSettingsOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlSettingsOpen();
            }
        }
    }

    public void onControlChargeOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlChargeOpen();
            }
        }
    }

    public void onControlChargeClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlChargeClose();
            }
        }
    }

    public void onDriveAvoidCongestion(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onDriveAvoidCongestion();
            }
        }
    }

    public void onDriveAvoidCongestionOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onDriveAvoidCongestionOff();
            }
        }
    }

    public void onDriveAvoidCharge(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onDriveAvoidCharge();
            }
        }
    }

    public void onDriveAvoidChargeOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onDriveAvoidChargeOff();
            }
        }
    }

    public void onDriveHighwayFirstOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onDriveHighwayFirstOff();
            }
        }
    }

    public void onDriveAvoidControls(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onDriveAvoidControls();
            }
        }
    }

    public void onDriveAvoidControlsOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onDriveAvoidControlsOff();
            }
        }
    }

    public void onDriveRadarRoute(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onDriveRadarRoute();
            }
        }
    }

    public void onDriveRadarRouteOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onDriveRadarRouteOff();
            }
        }
    }

    public void onControlSpeechSuperSimple(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlSpeechSuperSimple();
            }
        }
    }

    public void onControlSpeechGeneral(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlSpeechGeneral();
            }
        }
    }

    public void onControlSpeechEye(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlSpeechEye();
            }
        }
    }

    public void onControlSpeechEyeOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlSpeechEyeOff();
            }
        }
    }

    public void onControlSmartScale(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlSmartScale();
            }
        }
    }

    public void onControlSmartScaleOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlSmartScaleOff();
            }
        }
    }

    public void onControlSecurityRemind(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlSecurityRemind();
            }
        }
    }

    public void onControlRoadAhead(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlRoadAhead();
            }
        }
    }

    public void onDriveHighwayNo(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onDriveHighwayNo();
            }
        }
    }

    public void onDriveHighwayNoOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onDriveHighwayNoOff();
            }
        }
    }

    public void onDriveHighwayFirst(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onDriveHighwayFirst();
            }
        }
    }

    public void onNavigatingGet(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onNavigatingGet();
            }
        }
    }

    public void onPoiSearch(String str, String str2) {
        PoiSearchBean fromJson = PoiSearchBean.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onPoiSearch(fromJson);
            }
        }
    }

    public void onControlSecurityRemindOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlSecurityRemindOff();
            }
        }
    }

    public void onMapEnterFindPath(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onMapEnterFindPath();
            }
        }
    }

    public void onMapExitFindPath(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onMapExitFindPath();
            }
        }
    }

    public void onSearchClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onSearchClose();
            }
        }
    }

    public void onMainRoad(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onMainRoad();
            }
        }
    }

    public void onSideRoad(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onSideRoad();
            }
        }
    }

    public void onControlFavoriteClose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlFavoriteClose();
            }
        }
    }

    public void onControlRoadAheadOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlRoadAheadOff();
            }
        }
    }

    public void onMapZoominMax(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onMapZoominMax();
            }
        }
    }

    public void onMapZoomoutMin(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onMapZoomoutMin();
            }
        }
    }

    public void onNearbySearch(String str, String str2) {
        NearbySearchBean fromJson = NearbySearchBean.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onNearbySearch(fromJson);
            }
        }
    }

    public void postPoiResult(String str, List<PoiBean> list) {
        postPoiResult(NaviEvent.POI_SEARCH, str, list);
    }

    public void postNearbyResult(String str, List<PoiBean> list) {
        postPoiResult(NaviEvent.NEARBY_SEARCH, str, list);
    }

    public void postSearchPoiResult(String str, String str2, List<PoiBean> list) {
        postPoiResult(str, str2, list);
    }

    public void postAddressPosResult(String str, List<PoiBean> list) {
        postPoiResult(NaviEvent.POI_SEARCH, str, list);
    }

    public void postWaypointsFull(String str) {
        postWaypointSearchResult(str, null, true, true);
    }

    public void postWaypointsNotExitRoute(String str) {
        postWaypointSearchResult(str, null, false, false);
    }

    public void postWaypointSearchResult(String str, List<PoiBean> list) {
        postWaypointSearchResult(str, list, true, false);
    }

    private void postWaypointSearchResult(String str, List<PoiBean> list, boolean z, boolean z2) {
        ListWidget listWidget = new ListWidget();
        listWidget.setTitle(str);
        listWidget.setExist(z);
        listWidget.setExtraType("navi");
        listWidget.addContent("isWaypointListFull", String.valueOf(z2));
        if (list != null) {
            for (PoiBean poiBean : list) {
                ContentWidget contentWidget = new ContentWidget();
                contentWidget.setTitle(poiBean.getName());
                contentWidget.setSubTitle(poiBean.getAddress());
                try {
                    contentWidget.addExtra("navi", poiBean.toJson().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listWidget.addContentWidget(contentWidget);
            }
        }
        SpeechClient.instance().getActorBridge().send(new ResultActor(NaviEvent.WAYPOINT_SEARCH).setResult(listWidget));
    }

    private void postPoiResult(String str, String str2, List<PoiBean> list) {
        ListWidget listWidget = new ListWidget();
        listWidget.setTitle(str2);
        listWidget.setExtraType("navi");
        if (list != null) {
            for (PoiBean poiBean : list) {
                ContentWidget contentWidget = new ContentWidget();
                contentWidget.setTitle(poiBean.getName());
                contentWidget.setSubTitle(poiBean.getAddress());
                try {
                    contentWidget.addExtra("navi", poiBean.toJson().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listWidget.addContentWidget(contentWidget);
            }
        }
        SpeechClient.instance().getActorBridge().send(new ResultActor(str).setResult(listWidget));
    }

    public void onAddressGet(String str, String str2) {
        AddressBean fromJson = AddressBean.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onAddressGet(fromJson);
            }
        }
        this.mAddressPendingRoute = false;
    }

    public void postAddressGetResult(boolean z, boolean z2, PoiBean poiBean) {
        TextWidget textWidget = new TextWidget();
        textWidget.setText(z ? IScenarioController.RET_SUCCESS : "fail");
        textWidget.addContent("hasBigData", z2 ? OOBEEvent.STRING_TRUE : OOBEEvent.STRING_FALSE);
        if (poiBean != null) {
            try {
                textWidget.addContent("address", poiBean.getAddress());
                textWidget.addExtra("navi", poiBean.toJson().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        SpeechClient.instance().getActorBridge().send(new ResultActor(NaviEvent.ADDRESS_GET).setResult(textWidget));
    }

    public void onAddressPendingRoute(String str, String str2) {
        LogUtils.i(this, "pending route");
        this.mAddressPendingRoute = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006b  */
    /* JADX WARN: Type inference failed for: r11v12 */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r11v4, types: [com.xiaopeng.speech.protocol.node.navi.bean.PoiBean] */
    /* JADX WARN: Type inference failed for: r11v9, types: [com.xiaopeng.speech.protocol.node.navi.bean.PoiBean] */
    /* JADX WARN: Type inference failed for: r7v2, types: [com.xiaopeng.speech.protocol.node.navi.NaviListener] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onAddressSet(java.lang.String r10, java.lang.String r11) {
        /*
            r9 = this;
            com.xiaopeng.speech.protocol.node.navi.bean.AddressBean r10 = new com.xiaopeng.speech.protocol.node.navi.bean.AddressBean
            r10.<init>()
            r0 = 0
            r1 = 0
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch: org.json.JSONException -> L46
            r2.<init>(r11)     // Catch: org.json.JSONException -> L46
            java.lang.String r11 = "addressType"
            java.lang.String r11 = r2.optString(r11)     // Catch: org.json.JSONException -> L46
            r10.setAddressType(r11)     // Catch: org.json.JSONException -> L46
            java.lang.String r11 = "poi"
            java.lang.String r11 = r2.optString(r11)     // Catch: org.json.JSONException -> L46
            com.xiaopeng.speech.protocol.node.navi.bean.PoiBean r11 = com.xiaopeng.speech.protocol.node.navi.bean.PoiBean.fromJson(r11)     // Catch: org.json.JSONException -> L46
            java.lang.String r3 = "pref"
            java.lang.String r3 = r2.optString(r3)     // Catch: org.json.JSONException -> L40
            java.lang.String r4 = "type"
            java.lang.String r0 = r2.optString(r4)     // Catch: org.json.JSONException -> L3a
            java.lang.String r4 = "naviType"
            int r4 = r2.optInt(r4)     // Catch: org.json.JSONException -> L3a
            java.lang.String r5 = "routeSelectRef"
            int r2 = r2.optInt(r5)     // Catch: org.json.JSONException -> L38
            goto L51
        L38:
            r2 = move-exception
            goto L3c
        L3a:
            r2 = move-exception
            r4 = r1
        L3c:
            r8 = r0
            r0 = r11
            r11 = r8
            goto L4a
        L40:
            r2 = move-exception
            r3 = r0
            r4 = r1
            r0 = r11
            r11 = r3
            goto L4a
        L46:
            r2 = move-exception
            r11 = r0
            r3 = r11
            r4 = r1
        L4a:
            r2.printStackTrace()
            r2 = r1
            r8 = r0
            r0 = r11
            r11 = r8
        L51:
            com.xiaopeng.speech.common.util.SimpleCallbackList<T> r5 = r9.mListenerList
            java.lang.Object[] r5 = r5.collectCallbacks()
            if (r5 == 0) goto L67
            r6 = r1
        L5a:
            int r7 = r5.length
            if (r6 >= r7) goto L67
            r7 = r5[r6]
            com.xiaopeng.speech.protocol.node.navi.NaviListener r7 = (com.xiaopeng.speech.protocol.node.navi.NaviListener) r7
            r7.onAddressSet(r10, r11)
            int r6 = r6 + 1
            goto L5a
        L67:
            boolean r10 = r9.mAddressPendingRoute
            if (r10 == 0) goto L79
            com.xiaopeng.speech.protocol.node.navi.bean.StartNaviBean r10 = new com.xiaopeng.speech.protocol.node.navi.bean.StartNaviBean
            r10.<init>(r11, r3, r0)
            r10.setNaviType(r4)
            r10.setRouteSelectRef(r2)
            r9.doControlStart(r10)
        L79:
            r9.mAddressPendingRoute = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.protocol.node.navi.NaviNode.onAddressSet(java.lang.String, java.lang.String):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4, types: [com.xiaopeng.speech.protocol.node.navi.bean.PoiBean] */
    /* JADX WARN: Type inference failed for: r8v9, types: [com.xiaopeng.speech.protocol.node.navi.bean.PoiBean] */
    public void onControlStart(String str, String str2) {
        int i;
        String str3;
        String str4;
        int i2;
        ?? r8;
        String str5 = null;
        try {
            JSONObject jSONObject = new JSONObject(str2);
            ?? fromJson = PoiBean.fromJson(jSONObject.optString("poi"));
            try {
                str4 = jSONObject.optString("pref");
                try {
                    str5 = jSONObject.optString(VuiConstants.ELEMENT_TYPE);
                    i = jSONObject.optInt("naviType");
                } catch (JSONException e) {
                    e = e;
                    i = 0;
                }
                try {
                    i2 = jSONObject.optInt("routeSelectRef");
                    r8 = fromJson;
                } catch (JSONException e2) {
                    e = e2;
                    String str6 = str5;
                    str5 = fromJson;
                    str3 = str6;
                    e.printStackTrace();
                    i2 = 0;
                    String str7 = str5;
                    str5 = str3;
                    r8 = str7;
                    StartNaviBean startNaviBean = new StartNaviBean(r8, str4, str5);
                    startNaviBean.setNaviType(i);
                    startNaviBean.setRouteSelectRef(i2);
                    LogUtils.d("NaviNode", "StartNaviBean = %s", startNaviBean.toString());
                    doControlStart(startNaviBean);
                }
            } catch (JSONException e3) {
                e = e3;
                i = 0;
                str4 = null;
                str5 = fromJson;
                str3 = null;
            }
        } catch (JSONException e4) {
            e = e4;
            i = 0;
            str3 = null;
            str4 = null;
        }
        StartNaviBean startNaviBean2 = new StartNaviBean(r8, str4, str5);
        startNaviBean2.setNaviType(i);
        startNaviBean2.setRouteSelectRef(i2);
        LogUtils.d("NaviNode", "StartNaviBean = %s", startNaviBean2.toString());
        doControlStart(startNaviBean2);
    }

    private void doControlStart(StartNaviBean startNaviBean) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlStart(startNaviBean);
            }
        }
    }

    public void onControlSpeechSimple(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlSpeechSimple();
            }
        }
    }

    public void onControlSpeechDetail(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlSpeechDetail();
            }
        }
    }

    public void onControlDisPlayNorth(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlDisPlayNorth();
            }
        }
    }

    public void onControlDisPlayCar(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlDisPlayCar();
            }
        }
    }

    public void onControlDisplay3D(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlDisplay3D();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0027 A[LOOP:0: B:11:0x0027->B:13:0x002a, LOOP_START, PHI: r2 
      PHI: (r2v1 int) = (r2v0 int), (r2v2 int) binds: [B:10:0x0025, B:13:0x002a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0034 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onControlVolOn(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            java.lang.String r5 = "mode"
            com.xiaopeng.speech.common.util.SimpleCallbackList<T> r0 = r4.mListenerList
            java.lang.Object[] r0 = r0.collectCallbacks()
            r1 = 1
            r2 = 0
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch: java.lang.Exception -> L20
            r3.<init>(r6)     // Catch: java.lang.Exception -> L20
            java.lang.String r6 = "pullUpNavi"
            boolean r1 = r3.optBoolean(r6, r2)     // Catch: java.lang.Exception -> L20
            boolean r6 = r3.has(r5)     // Catch: java.lang.Exception -> L20
            if (r6 == 0) goto L24
            int r5 = r3.optInt(r5)     // Catch: java.lang.Exception -> L20
            goto L25
        L20:
            r5 = move-exception
            r5.printStackTrace()
        L24:
            r5 = r2
        L25:
            if (r0 == 0) goto L34
        L27:
            int r6 = r0.length
            if (r2 >= r6) goto L34
            r6 = r0[r2]
            com.xiaopeng.speech.protocol.node.navi.NaviListener r6 = (com.xiaopeng.speech.protocol.node.navi.NaviListener) r6
            r6.onControlVolOn(r1, r5)
            int r2 = r2 + 1
            goto L27
        L34:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.protocol.node.navi.NaviNode.onControlVolOn(java.lang.String, java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0027 A[LOOP:0: B:11:0x0027->B:13:0x002a, LOOP_START, PHI: r2 
      PHI: (r2v1 int) = (r2v0 int), (r2v2 int) binds: [B:10:0x0025, B:13:0x002a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0034 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onControlVolOff(java.lang.String r5, java.lang.String r6) {
        /*
            r4 = this;
            java.lang.String r5 = "mode"
            com.xiaopeng.speech.common.util.SimpleCallbackList<T> r0 = r4.mListenerList
            java.lang.Object[] r0 = r0.collectCallbacks()
            r1 = 1
            r2 = 0
            org.json.JSONObject r3 = new org.json.JSONObject     // Catch: java.lang.Exception -> L20
            r3.<init>(r6)     // Catch: java.lang.Exception -> L20
            java.lang.String r6 = "pullUpNavi"
            boolean r1 = r3.optBoolean(r6, r2)     // Catch: java.lang.Exception -> L20
            boolean r6 = r3.has(r5)     // Catch: java.lang.Exception -> L20
            if (r6 == 0) goto L24
            int r5 = r3.optInt(r5)     // Catch: java.lang.Exception -> L20
            goto L25
        L20:
            r5 = move-exception
            r5.printStackTrace()
        L24:
            r5 = r2
        L25:
            if (r0 == 0) goto L34
        L27:
            int r6 = r0.length
            if (r2 >= r6) goto L34
            r6 = r0[r2]
            com.xiaopeng.speech.protocol.node.navi.NaviListener r6 = (com.xiaopeng.speech.protocol.node.navi.NaviListener) r6
            r6.onControlVolOff(r1, r5)
            int r2 = r2 + 1
            goto L27
        L34:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.protocol.node.navi.NaviNode.onControlVolOff(java.lang.String, java.lang.String):void");
    }

    public void onRouteNearbySearch(String str, String str2) {
        NearbySearchBean fromJson = NearbySearchBean.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onRouteNearbySearch(fromJson);
            }
        }
    }

    public void onParkingSelect(String str, String str2) {
        SelectParkingBean fromJson = SelectParkingBean.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onParkingSelect(fromJson);
            }
        }
    }

    public void onConfirmOk(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onConfirmOk();
            }
        }
    }

    public void onConfirmCancel(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onConfirmCancel();
            }
        }
    }

    public void onRouteSelect(String str, String str2) {
        SelectRouteBean fromJson = SelectRouteBean.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onRouteSelect(fromJson);
            }
        }
    }

    public void onSelectParkingCount(String str, String str2) {
        SelectParkingBean fromJson = SelectParkingBean.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onSelectParkingCount(fromJson);
            }
        }
    }

    public void onSelectRouteCount(String str, String str2) {
        SelectRouteBean fromJson = SelectRouteBean.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onSelectRouteCount(fromJson);
            }
        }
    }

    public void onDataControlDisplay3dTts(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onDataControlDisplay3dTts();
            }
        }
    }

    public void onDataControlDisplayCarTts(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onDataControlDisplayCarTts();
            }
        }
    }

    public void onDataControlDisplayNorthTts(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onDataControlDisplayNorthTts();
            }
        }
    }

    public void onDriveAdvoidTrafficControl(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onDriveAdvoidTrafficControl();
            }
        }
    }

    public void onWaypointSearch(String str, String str2) {
        WaypointSearchBean fromJson = WaypointSearchBean.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onWaypointSearch(fromJson);
            }
        }
    }

    public void onControlWaypointStart(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        PathBean fromJson = PathBean.fromJson(str2);
        LogUtils.d("NaviNode, pathBean =%s", fromJson == null ? "data is null" : fromJson.toString());
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlWaypointStart(fromJson);
            }
        }
    }

    public void onControlOpenSmallMap(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlOpenSmallMap();
            }
        }
    }

    public void onControlCloseSmallMap(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlCloseSmallMap();
            }
        }
    }

    public void onControlOpenRibbonMap(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlOpenRibbonMap();
            }
        }
    }

    public void onControlCloseRibbonMap(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlCloseRibbonMap();
            }
        }
    }

    public void selectParking(String str) {
        String str2;
        SpeechClient.instance().getWakeupEngine().stopDialog();
        try {
            str2 = new JSONObject().put("tts", str).put("isLocalSkill", true).put("intent", SELECT_PARKING_INTENT).put("isAsrModeOffline", false).put(WakeupResult.REASON_COMMAND, "native://navi.select.parking.count#command://navi.parking.select#command://navi.confirm.cancel").toString();
        } catch (JSONException e) {
            e.printStackTrace();
            str2 = "";
        }
        SpeechClient.instance().getAgent().triggerIntent(OFFLINE_SKILL, ACTIVE_VOICE_TASK, SELECT_PARKING_INTENT, str2);
    }

    @Deprecated
    public void selectRoute(String str) {
        String str2;
        SpeechClient.instance().getWakeupEngine().stopDialog();
        try {
            str2 = new JSONObject().put("tts", str).put("isLocalSkill", true).put("intent", SELECT_ROUTE_INTENT).put("isAsrModeOffline", false).put(WakeupResult.REASON_COMMAND, "native://navi.select.route.count#command://navi.route.select#command://navi.confirm.cancel").toString();
        } catch (JSONException e) {
            e.printStackTrace();
            str2 = "";
        }
        SpeechClient.instance().getAgent().triggerIntent(OFFLINE_SKILL, ACTIVE_VOICE_TASK, SELECT_ROUTE_INTENT, str2);
    }

    public void selectRoute(List<RouteSelectBean> list) {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            if (list != null && list.size() > 0) {
                for (RouteSelectBean routeSelectBean : list) {
                    jSONArray.put(routeSelectBean.toJson());
                }
            }
            jSONObject.put("route_list", jSONArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SpeechClient.instance().getAgent().triggerEvent(FeedUIEvent.LIST_ROUT_UPLOAD, jSONObject.toString());
    }

    public void updateRouteSelect(List<RouteSelectBean> list) {
        ListWidget listWidget = new ListWidget();
        listWidget.setTitle(FeedListUIValue.TYPE_ROUTE);
        listWidget.setExtraType(ListWidget.EXTRA_TYPE_NAVI_ROUTE);
        if (list != null && list.size() > 0) {
            for (RouteSelectBean routeSelectBean : list) {
                ContentWidget contentWidget = new ContentWidget();
                contentWidget.setTitle(routeSelectBean.totalTimeLine1);
                contentWidget.setSubTitle(routeSelectBean.routeTypeName);
                try {
                    contentWidget.addExtra(ListWidget.EXTRA_TYPE_NAVI_ROUTE, routeSelectBean.toJson().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listWidget.addContentWidget(contentWidget);
            }
        }
        LogUtils.i("updateRouteSelect", "updateRouteSelect:" + listWidget.toString());
        SpeechClient.instance().getAgent().sendEvent(ContextEvent.WIDGET_LIST, listWidget.toString());
    }

    private SpeechWidget getRouteSelectWidget(List<RouteSelectBean> list) {
        ListWidget listWidget = new ListWidget();
        listWidget.setTitle(FeedListUIValue.TYPE_ROUTE);
        listWidget.setExtraType(ListWidget.EXTRA_TYPE_NAVI_ROUTE);
        if (list != null && list.size() > 0) {
            for (RouteSelectBean routeSelectBean : list) {
                ContentWidget contentWidget = new ContentWidget();
                contentWidget.setTitle(routeSelectBean.totalTimeLine1);
                contentWidget.setSubTitle(routeSelectBean.routeTypeName);
                try {
                    contentWidget.addExtra(ListWidget.EXTRA_TYPE_NAVI_ROUTE, routeSelectBean.toJson().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listWidget.addContentWidget(contentWidget);
            }
        }
        return listWidget;
    }

    public void stopSpeechDialog() {
        SpeechClient.instance().getWakeupEngine().stopDialog();
    }

    public void stopSpeechDialog(int i) {
        LogUtils.i(this, "stopDialog option: " + i);
        if (i == 0) {
            SpeechClient.instance().getWakeupEngine().stopDialog();
        } else {
            SpeechClient.instance().getAgent().sendUIEvent(FeedUIEvent.SCRIPT_QUIT, null);
        }
    }

    public void startSpeechDialog() {
        SpeechClient.instance().getAgent().triggerEvent(FeedUIEvent.SCRIPT_QUIT, null);
    }

    public void directNavigation() {
        SpeechClient.instance().getAgent().triggerEvent(FeedUIEvent.SCRIPT_QUIT, null);
    }

    public void onControlHistory(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlHistory();
            }
        }
    }

    public void syncRoute(List<RouteSelectBean> list, String str, boolean z) {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            if (list != null && list.size() > 0) {
                for (RouteSelectBean routeSelectBean : list) {
                    jSONArray.put(routeSelectBean.toJson());
                }
            }
            jSONObject.put("route_list", jSONArray.toString());
            jSONObject.put("localStory", str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (z) {
            SpeechClient.instance().getAgent().triggerEvent(FeedUIEvent.LIST_ROUT_UPLOAD, jSONObject.toString());
        }
        syncRouteToInfoFlow(list, str, z);
    }

    public void syncRouteToInfoFlow(List<RouteSelectBean> list, String str, boolean z) {
        ListWidget listWidget = new ListWidget();
        listWidget.setTitle(FeedListUIValue.TYPE_ROUTE);
        listWidget.setExtraType(ListWidget.EXTRA_TYPE_NAVI_ROUTE);
        if (z) {
            listWidget.setWidgetId(str + BASE_ROUTE_WIDGET_ID);
        } else {
            listWidget.setWidgetId(str + ALL_ROUTE_WIDGET_ID);
        }
        if (list != null && list.size() > 0) {
            for (RouteSelectBean routeSelectBean : list) {
                ContentWidget contentWidget = new ContentWidget();
                contentWidget.setTitle(routeSelectBean.totalTimeLine1);
                contentWidget.setSubTitle(routeSelectBean.routeTypeName);
                try {
                    contentWidget.addExtra(ListWidget.EXTRA_TYPE_NAVI_ROUTE, routeSelectBean.toJson().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listWidget.addContentWidget(contentWidget);
            }
        }
        LogUtils.i("NaviNode", "syncRouteToInfoFlow:" + listWidget.toString());
        SpeechClient.instance().getAgent().sendEvent(ContextEvent.WIDGET_LIST, listWidget.toString());
    }

    public void onGetSettingsInfo(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onGetSettingsInfo();
            }
        }
    }

    public void postSettingsInfo(String str) {
        SpeechClient.instance().getActorBridge().send(new ResultActor(NaviEvent.SETTINGS_INFO_GET).setResult(str));
    }

    public void onControlParkRecommendOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlParkRecommendOn();
            }
        }
    }

    public void onControlParkRecommendOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlParkRecommendOff();
            }
        }
    }

    public void onSetScaleLevel(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks == null || TextUtils.isEmpty(str2)) {
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str2);
            if (jSONObject.has("level")) {
                int i = jSONObject.getInt("level");
                for (Object obj : collectCallbacks) {
                    ((NaviListener) obj).onSetScaleLevel(i);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onAlertsPreferenceSet(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks == null || TextUtils.isEmpty(str2)) {
            return;
        }
        NaviPreferenceBean fromJson = NaviPreferenceBean.fromJson(str2);
        for (Object obj : collectCallbacks) {
            ((NaviListener) obj).onAlertsPreferenceSet(fromJson);
        }
    }

    public void onAvoidRouteSet(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks == null || TextUtils.isEmpty(str2)) {
            return;
        }
        NaviPreferenceBean fromJson = NaviPreferenceBean.fromJson(str2);
        for (Object obj : collectCallbacks) {
            ((NaviListener) obj).onAvoidRouteSet(fromJson);
        }
    }

    public void onAutoRerouteBetterRoute(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onAutoRerouteBetterRoute();
            }
        }
    }

    public void onAutoRerouteAskFirst(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onAutoRerouteAskFirst();
            }
        }
    }

    public void onAutoRerouteNever(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onAutoRerouteNever();
            }
        }
    }

    public void onMapShowSet(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks == null || TextUtils.isEmpty(str2)) {
            return;
        }
        NaviPreferenceBean fromJson = NaviPreferenceBean.fromJson(str2);
        for (Object obj : collectCallbacks) {
            ((NaviListener) obj).onMapShowSet(fromJson);
        }
    }

    public void onPoiDetailsFavoriteAdd(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onPoiDetailsFavoriteAdd();
            }
        }
    }

    public void onPoiDetailsFavoriteDel(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onPoiDetailsFavoriteDel();
            }
        }
    }

    public void onControlSettingsCLose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlSettingsClose();
            }
        }
    }

    public void onControlHistoryCLose(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((NaviListener) obj).onControlHistoryClose();
            }
        }
    }
}
