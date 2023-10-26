package com.alibaba.sdk.android.oss.common.utils;

import java.net.URLEncoder;
import java.util.Map;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes.dex */
public class HttpUtil {
    public static String urlEncode(String str, String str2) {
        if (str == null) {
            return "";
        }
        try {
            return URLEncoder.encode(str, str2).replace(MqttTopic.SINGLE_LEVEL_WILDCARD, "%20").replace("*", "%2A").replace("%7E", "~").replace("%2F", MqttTopic.TOPIC_LEVEL_SEPARATOR);
        } catch (Exception e) {
            throw new IllegalArgumentException("failed to encode url!", e);
        }
    }

    public static String paramToQueryString(Map<String, String> map, String str) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!z) {
                sb.append("&");
            }
            sb.append(urlEncode(key, str));
            if (value != null) {
                sb.append("=").append(urlEncode(value, str));
            }
            z = false;
        }
        return sb.toString();
    }
}
