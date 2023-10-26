package okhttp3;

import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import java.nio.charset.Charset;
import okhttp3.internal.Util;
import okio.ByteString;

/* loaded from: classes3.dex */
public final class Credentials {
    private Credentials() {
    }

    public static String basic(String str, String str2) {
        return basic(str, str2, Util.ISO_8859_1);
    }

    public static String basic(String str, String str2, Charset charset) {
        return "Basic " + ByteString.encodeString(str + QuickSettingConstants.JOINER + str2, charset).base64();
    }
}
