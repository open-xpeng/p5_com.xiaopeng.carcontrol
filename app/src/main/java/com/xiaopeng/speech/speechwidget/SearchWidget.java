package com.xiaopeng.speech.speechwidget;

import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SearchWidget extends SpeechWidget {
    private static final String SEARCH_CHARGING_STATION = "chargingStation";
    private static final String SEARCH_FOOD = "food";
    public static final int SEARCH_TYPE_CHARGING = 1;
    public static final int SEARCH_TYPE_FOOD = 2;
    public static final int SEARCH_TYPE_NONE = 0;
    private int searchType;

    public SearchWidget() {
        super(SpeechWidget.TYPE_SEARCH);
    }

    @Override // com.xiaopeng.speech.speechwidget.SpeechWidget
    public SearchWidget fromJson(String str) {
        super.fromJson(str);
        JSONObject optJSONObject = this.mWidget.optJSONObject(SpeechWidget.WIDGET_SEARCH_CONTENT);
        if (optJSONObject != null) {
            String optString = optJSONObject.optString("searchType");
            optString.hashCode();
            if (optString.equals(SEARCH_CHARGING_STATION)) {
                this.searchType = 1;
            } else if (optString.equals(SEARCH_FOOD)) {
                this.searchType = 2;
            } else {
                this.searchType = 0;
            }
        }
        return this;
    }

    public String getTitle() {
        return getExtra(SpeechWidget.WIDGET_TITLE);
    }

    public int getCurrentPage() {
        return getIntContent("currentPage") - 1;
    }

    public int getPageSize() {
        return getIntContent("itemsPerPage");
    }

    public int getOpenDetailPosition() {
        return getIntContent("curPageOpenSerial") - 1;
    }

    public JSONObject getWidget() {
        return this.mWidget;
    }

    public int getSearchType() {
        return this.searchType;
    }
}
