package com.xiaopeng.libtheme;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xvs.xid.sync.api.ISync;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes2.dex */
public class ThemeParser {
    private static final boolean DEBUG = false;
    private static final String NODE = "view";
    private static final String TAG = "ThemeXmlParser";
    private static final String _ATTR = "attr";
    private static final String _ID = "id";
    private static final String _ROOT = "root";
    private static final String _VALUE = "value";

    public static synchronized List<ThemeView> parseXml(Context context, String str) {
        synchronized (ThemeParser.class) {
            if (context != null) {
                try {
                    if (!TextUtils.isEmpty(str)) {
                        return parseXml(context, context.getAssets().open(str));
                    }
                } catch (Exception unused) {
                }
            }
            return null;
        }
    }

    public static synchronized List<ThemeView> parseXml(Context context, InputStream inputStream) {
        ArrayList arrayList;
        synchronized (ThemeParser.class) {
            arrayList = new ArrayList();
            if (inputStream != null) {
                try {
                    XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                    newPullParser.setInput(inputStream, "utf-8");
                    ThemeView themeView = null;
                    for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                        String name = newPullParser.getName();
                        if (!TextUtils.isEmpty(name)) {
                            if (eventType != 2) {
                                if (eventType == 3) {
                                    if (themeView != null) {
                                        arrayList.add(themeView);
                                    }
                                    themeView = null;
                                }
                            } else if (NODE.equals(name.toLowerCase())) {
                                String attributeValue = newPullParser.getAttributeValue(null, "id");
                                String attributeValue2 = newPullParser.getAttributeValue(null, _ROOT);
                                String attributeValue3 = newPullParser.getAttributeValue(null, _ATTR);
                                String attributeValue4 = newPullParser.getAttributeValue(null, "value");
                                if (!TextUtils.isEmpty(attributeValue) && !TextUtils.isEmpty(attributeValue3) && !TextUtils.isEmpty(attributeValue4)) {
                                    ThemeView themeView2 = new ThemeView();
                                    themeView2.xmlId = attributeValue;
                                    themeView2.xmlRoot = attributeValue2;
                                    themeView2.xmlAttr = attributeValue3;
                                    themeView2.xmlValue = attributeValue4;
                                    themeView = resolveThemeView(context, themeView2);
                                }
                                themeView = null;
                            }
                        }
                    }
                } catch (Exception unused) {
                }
            }
        }
        return arrayList;
    }

    private static ThemeView resolveThemeView(Context context, ThemeView themeView) {
        if (context == null || themeView == null) {
            return null;
        }
        try {
            Resources resources = context.getResources();
            String packageName = context.getPackageName();
            if (!TextUtils.isEmpty(themeView.xmlId)) {
                themeView.resId = resources.getIdentifier(themeView.xmlId, "id", packageName);
            }
            if (!TextUtils.isEmpty(themeView.xmlAttr)) {
                themeView.resAttr = themeView.xmlAttr;
            }
            if (!TextUtils.isEmpty(themeView.xmlRoot)) {
                themeView.resRoot = resources.getIdentifier(themeView.xmlRoot, "id", packageName);
            }
            if (!TextUtils.isEmpty(themeView.xmlValue) && themeView.xmlValue.startsWith(ISync.EXTRA_SYNC_GROUP_KEY_SEPARATOR) && themeView.xmlValue.contains(MqttTopic.TOPIC_LEVEL_SEPARATOR)) {
                String substring = themeView.xmlValue.substring(1, themeView.xmlValue.indexOf(MqttTopic.TOPIC_LEVEL_SEPARATOR));
                int identifier = resources.getIdentifier(themeView.xmlValue.substring(themeView.xmlValue.indexOf(MqttTopic.TOPIC_LEVEL_SEPARATOR) + 1), substring, packageName);
                themeView.resType = ThemeManager.ResourceType.getType(substring);
                themeView.resValue = Integer.valueOf(identifier);
            }
            return themeView;
        } catch (Exception unused) {
            return null;
        }
    }
}
