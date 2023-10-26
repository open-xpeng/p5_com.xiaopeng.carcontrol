package com.xiaopeng.speech.protocol.node.dialog.bean;

import com.xiaopeng.speech.jarvisproto.DMStart;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class WakeupReason {
    public static final int REASON_BOSS = 7;
    public static final int REASON_FAST_WAKEUP = 2;
    public static final int REASON_MAJOR = 1;
    public static final int REASON_MANUALLY = 3;
    public static final int REASON_MUSIC = 6;
    public static final int REASON_REAR_BOSS = 8;
    public static final int REASON_SEND_TEXT = 4;
    public static final int REASON_TRIGGER_INTENT = 5;
    public int reason;
    public String sessionId;
    public int soundArea;

    public WakeupReason(int i, String str) {
        this.soundArea = -1;
        this.reason = i;
        this.sessionId = str;
    }

    public WakeupReason(int i, String str, int i2) {
        this.soundArea = -1;
        this.reason = i;
        this.sessionId = str;
        this.soundArea = i2;
    }

    public static WakeupReason fromJson(String str) {
        char c;
        String str2 = "";
        int i = -1;
        int i2 = 1;
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("reason");
            switch (optString.hashCode()) {
                case -1990314378:
                    if (optString.equals("api.startDialog")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1958887862:
                    if (optString.equals(DMStart.REASON_WAKEUP_MAJOR)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1872304479:
                    if (optString.equals(DMStart.REASON_BOSS_START)) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -1792656369:
                    if (optString.equals(DMStart.REASON_WHEEL_START)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -1177155684:
                    if (optString.equals(DMStart.REASON_WAKEUP_CMD)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 104263205:
                    if (optString.equals("music")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 282538108:
                    if (optString.equals(DMStart.REASON_CLICK_START)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 687136219:
                    if (optString.equals("api.avatarClick")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 699317398:
                    if (optString.equals("api.avatarPress")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 790870569:
                    if (optString.equals(DMStart.REASON_SEND_TEXT)) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case 1100434848:
                    if (optString.equals("api.triggerIntent")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 1:
                    i2 = 2;
                    break;
                case 2:
                    i2 = 6;
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    i2 = 3;
                    break;
                case '\b':
                    i2 = 4;
                    break;
                case '\t':
                    i2 = 5;
                    break;
                case '\n':
                    i2 = 7;
                    break;
            }
            str2 = jSONObject.optString("sessionId");
            i = jSONObject.optInt("soundArea");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new WakeupReason(i2, str2, i);
    }
}
