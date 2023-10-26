package com.xiaopeng.speech.speechwidget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class ListWidget extends SpeechWidget {
    public static final String EXTRA_TYPE_AUDIO = "audio";
    public static final String EXTRA_TYPE_NAVI = "navi";
    public static final String EXTRA_TYPE_NAVI_ROUTE = "navi_route";
    public static final String EXTRA_TYPE_PHONE = "phone";
    public static final String KEY_STATUS = "status";
    public static final String STATUS_EXISTS = "1";
    public static final String STATUS_NOT_FOUND = "0";

    public ListWidget(String str) {
        super(str);
    }

    public ListWidget() {
        super(SpeechWidget.TYPE_LIST);
    }

    @Override // com.xiaopeng.speech.speechwidget.SpeechWidget
    public ListWidget fromJson(String str) {
        super.fromJson(str);
        JSONArray optJSONArray = this.mWidget.optJSONArray("content");
        if (optJSONArray != null) {
            for (int i = 0; i < optJSONArray.length(); i++) {
                ContentWidget contentWidget = new ContentWidget();
                try {
                    contentWidget.fromJson((JSONObject) optJSONArray.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                this.mList.add(contentWidget);
            }
        }
        return this;
    }

    public ListWidget setTitle(String str) {
        addExtra(SpeechWidget.WIDGET_TITLE, str);
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

    public ListWidget addContentWidget(ContentWidget contentWidget) {
        return (ListWidget) super.addWidget(contentWidget);
    }

    @Override // com.xiaopeng.speech.speechwidget.SpeechWidget
    public ListWidget addExtra(String str, String str2) {
        return (ListWidget) super.addExtra(str, str2);
    }

    public ListWidget setExist(boolean z) {
        return (ListWidget) super.addContent("status", z ? "1" : "0");
    }
}
