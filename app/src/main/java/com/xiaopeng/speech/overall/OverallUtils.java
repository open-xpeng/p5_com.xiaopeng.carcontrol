package com.xiaopeng.speech.overall;

import android.util.ArrayMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class OverallUtils {
    public static Map<String, String[]> sEvents;
    public static Map<String, String[]> sPackageEvents;
    public static Map<String, String[]> sPackageQueryEvents;
    public static Map<String, String[]> sQueryEvents;

    static {
        ArrayMap arrayMap = new ArrayMap();
        sEvents = arrayMap;
        arrayMap.put("com.youku.iot", new String[]{"command://video.control.play", "command://video.control.resume", "command://video.control.pause", "command://video.control.stop", "command://video.forward", "command://video.backward", "command://video.settime", "command://video.control.collect", "command://video.control.collect.cancel", "command://video.control.prev", "command://video.control.next", "command://video.fullscreen", "command://video.fullscreen.exit", "command://video.definition.set", "command://video.play.page.exit", "command://video.play.select"});
        ArrayMap arrayMap2 = new ArrayMap();
        sQueryEvents = arrayMap2;
        arrayMap2.put("com.youku.iot", new String[]{"native://com.youku.iot.video.info.query"});
        ArrayMap arrayMap3 = new ArrayMap();
        sPackageEvents = arrayMap3;
        arrayMap3.put("com.youku.iot", new String[]{"isPlaying:false|play", "isPlaying:false|continue", "isPlaying:true|pause", "isPlaying:true|stop", "isInPlayPage:true|fastforward", "isInPlayPage:true|backforward", "isInPlayPage:true|settime", "isInPlayPage:true|favor", "isInPlayPage:true|unfavor", "isInPlayPage:true|previous", "isInPlayPage:true|next", "isFullScreen:false|fullscreen", "isFullScreen:true|unfullscreen", "isInPlayPage:true|definition", "isInPlayPage:true|back", "isInPlayPage:true|select"});
        ArrayMap arrayMap4 = new ArrayMap();
        sPackageQueryEvents = arrayMap4;
        arrayMap4.put("com.youku.iot", new String[]{"isInPlayPage|isFullScreen|isPlaying|videoDuration|definitions|isLogin"});
    }

    public static String[] getObserverEvent(String str) {
        if (sEvents.containsKey(str) && sQueryEvents.containsKey(str)) {
            String[] strArr = sEvents.get(str);
            String[] strArr2 = sQueryEvents.get(str);
            String[] strArr3 = new String[strArr.length + strArr2.length];
            System.arraycopy(strArr, 0, strArr3, 0, strArr.length);
            System.arraycopy(strArr2, 0, strArr3, strArr.length, strArr2.length);
            return strArr3;
        } else if (sEvents.containsKey(str)) {
            return sEvents.get(str);
        } else {
            if (sQueryEvents.containsKey(str)) {
                return sQueryEvents.get(str);
            }
            return null;
        }
    }

    public static List<String> getPackageEvents(String str) {
        if (sPackageEvents.containsKey(str)) {
            return Arrays.asList(sPackageEvents.get(str));
        }
        return null;
    }

    public static List<String> getPackageQueryEvents(String str) {
        if (sPackageQueryEvents.containsKey(str)) {
            return Arrays.asList(sPackageQueryEvents.get(str));
        }
        return null;
    }

    public static List<String> getEvents(String str) {
        if (sEvents.containsKey(str)) {
            return Arrays.asList(sEvents.get(str));
        }
        return null;
    }

    public static List<String> getQueryEvents(String str) {
        if (sQueryEvents.containsKey(str)) {
            return Arrays.asList(sQueryEvents.get(str));
        }
        return null;
    }
}
