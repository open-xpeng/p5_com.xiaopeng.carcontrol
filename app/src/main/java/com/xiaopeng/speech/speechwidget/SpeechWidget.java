package com.xiaopeng.speech.speechwidget;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class SpeechWidget {
    public static final String DATA_SOURCE_API = "api";
    public static final String DATA_SOURCE_MATCH = "partialMatch";
    public static final String DATA_SOURCE_SELECT = "selected";
    public static final String TYPE_CARD = "card";
    public static final String TYPE_CONTENT = "content";
    public static final String TYPE_CUSTOM = "custom";
    public static final String TYPE_LIST = "list";
    public static final String TYPE_MEDIA = "media";
    public static final String TYPE_SEARCH = "search";
    public static final String TYPE_TEXT = "text";
    public static final String TYPE_WEB = "web";
    protected static final String WIDGET_CONTENT = "content";
    protected static final String WIDGET_COUNT = "count";
    public static final String WIDGET_DATA_SOURCE = "dataSource";
    public static final String WIDGET_EXTRA = "extra";
    protected static final String WIDGET_EXTRA_TYPE = "extraType";
    public static final String WIDGET_IMAGEURL = "imageUrl";
    public static final String WIDGET_LABEL = "label";
    public static final String WIDGET_LINKURL = "linkUrl";
    public static final String WIDGET_SEARCH_CONTENT = "searchContent";
    public static final String WIDGET_SUBTITLE = "subTitle";
    public static final String WIDGET_TEXT = "text";
    public static final String WIDGET_TITLE = "title";
    protected static final String WIDGET_TYPE = "type";
    protected static final String WIDGET_TYPE_EX = "duiWidget";
    protected static final String WIDGET_UNIQUE_ID = "widgetId";
    public static final String WIDGET_URL = "url";
    protected JSONObject mWidget = new JSONObject();
    protected JSONObject mExtra = new JSONObject();
    protected List<SpeechWidget> mList = new ArrayList();

    public static String getType(JSONObject jSONObject) {
        return jSONObject.optString("type");
    }

    public SpeechWidget() {
    }

    public SpeechWidget(String str) {
        try {
            this.mWidget.put("type", str);
            this.mWidget.put(WIDGET_TYPE_EX, str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public SpeechWidget fromJson(String str) {
        JSONObject jSONObject;
        try {
            jSONObject = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
            jSONObject = null;
        }
        return fromJson(jSONObject);
    }

    public SpeechWidget fromJson(JSONObject jSONObject) {
        if (jSONObject != null) {
            this.mWidget = jSONObject;
            JSONObject optJSONObject = jSONObject.optJSONObject(WIDGET_EXTRA);
            if (optJSONObject != null) {
                this.mExtra = optJSONObject;
            }
        }
        return this;
    }

    public SpeechWidget setType(String str) {
        try {
            this.mWidget.put("type", str);
            this.mWidget.put(WIDGET_TYPE_EX, str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getType() {
        return this.mWidget.optString("type", "");
    }

    public SpeechWidget setExtraType(String str) {
        return addExtra(WIDGET_EXTRA_TYPE, str);
    }

    public String getExtraType() {
        return getExtra(WIDGET_EXTRA_TYPE);
    }

    public SpeechWidget addContent(String str, String str2) {
        try {
            this.mWidget.put(str, str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getContent(String str) {
        return this.mWidget.optString(str, "");
    }

    public int getIntContent(String str) {
        try {
            return Integer.parseInt(this.mWidget.optString(str, ""));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public SpeechWidget addWidget(SpeechWidget speechWidget) {
        this.mList.add(speechWidget);
        return this;
    }

    public SpeechWidget addExtra(String str, String str2) {
        try {
            this.mExtra.put(str, str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public SpeechWidget setExtra(JSONObject jSONObject) {
        this.mExtra = jSONObject;
        return this;
    }

    public JSONObject getExtra() {
        return this.mExtra;
    }

    public String getExtra(String str) {
        return this.mExtra.optString(str, "");
    }

    public String getDataSource() {
        return this.mWidget.optString(WIDGET_DATA_SOURCE);
    }

    public List<SpeechWidget> getList() {
        return this.mList;
    }

    public String toString() {
        if (this.mExtra.length() > 0) {
            try {
                this.mWidget.put(WIDGET_EXTRA, this.mExtra);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String optString = this.mWidget.optString("type", "");
        if (TYPE_LIST.equals(optString) || "media".equals(optString)) {
            try {
                JSONArray jSONArray = new JSONArray();
                for (SpeechWidget speechWidget : this.mList) {
                    jSONArray.put(new JSONObject(speechWidget.toString()));
                }
                this.mWidget.put(WIDGET_COUNT, jSONArray.length());
                this.mWidget.put("content", jSONArray);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return this.mWidget.toString();
    }

    public SpeechWidget setWidgetId(String str) {
        try {
            this.mWidget.put(WIDGET_UNIQUE_ID, str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this;
    }

    public String getWidgetId() {
        return this.mWidget.optString(WIDGET_UNIQUE_ID, "");
    }
}
