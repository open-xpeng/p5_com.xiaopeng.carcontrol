package com.xiaopeng.lib.apirouter.server;

import android.os.Parcel;
import android.os.RemoteException;
import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.speech.protocol.event.OOBEEvent;

/* loaded from: classes2.dex */
public class TransactTranslator {
    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T read(String str, String str2) throws RemoteException {
        char c = 65535;
        try {
            switch (str2.hashCode()) {
                case -2056817302:
                    if (str2.equals("java.lang.Integer")) {
                        c = 7;
                        break;
                    }
                    break;
                case -1325958191:
                    if (str2.equals("double")) {
                        c = 14;
                        break;
                    }
                    break;
                case -527879800:
                    if (str2.equals("java.lang.Float")) {
                        c = '\r';
                        break;
                    }
                    break;
                case -515992664:
                    if (str2.equals("java.lang.Short")) {
                        c = 5;
                        break;
                    }
                    break;
                case 104431:
                    if (str2.equals("int")) {
                        c = 6;
                        break;
                    }
                    break;
                case 3039496:
                    if (str2.equals("byte")) {
                        c = 2;
                        break;
                    }
                    break;
                case 3052374:
                    if (str2.equals("char")) {
                        c = 11;
                        break;
                    }
                    break;
                case 3327612:
                    if (str2.equals(GlobalConstant.EXTRA.VALUE_X_LONG)) {
                        c = '\b';
                        break;
                    }
                    break;
                case 64711720:
                    if (str2.equals(TypedValues.Custom.S_BOOLEAN)) {
                        c = 0;
                        break;
                    }
                    break;
                case 97526364:
                    if (str2.equals(TypedValues.Custom.S_FLOAT)) {
                        c = '\f';
                        break;
                    }
                    break;
                case 109413500:
                    if (str2.equals(GlobalConstant.EXTRA.VALUE_X_SHORT)) {
                        c = 4;
                        break;
                    }
                    break;
                case 155276373:
                    if (str2.equals("java.lang.Character")) {
                        c = '\n';
                        break;
                    }
                    break;
                case 344809556:
                    if (str2.equals("java.lang.Boolean")) {
                        c = 1;
                        break;
                    }
                    break;
                case 398507100:
                    if (str2.equals("java.lang.Byte")) {
                        c = 3;
                        break;
                    }
                    break;
                case 398795216:
                    if (str2.equals("java.lang.Long")) {
                        c = '\t';
                        break;
                    }
                    break;
                case 761287205:
                    if (str2.equals("java.lang.Double")) {
                        c = 15;
                        break;
                    }
                    break;
                case 1195259493:
                    if (str2.equals("java.lang.String")) {
                        c = 16;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    boolean isEmpty = TextUtils.isEmpty(str);
                    String str3 = str;
                    if (isEmpty) {
                        str3 = (T) OOBEEvent.STRING_FALSE;
                    }
                    return (T) Boolean.valueOf(str3);
                case 1:
                    return (T) Boolean.valueOf(str);
                case 2:
                    boolean isEmpty2 = TextUtils.isEmpty(str);
                    String str4 = str;
                    if (isEmpty2) {
                        str4 = (T) "0";
                    }
                    return (T) Byte.valueOf(str4);
                case 3:
                    return (T) Byte.valueOf(str);
                case 4:
                    boolean isEmpty3 = TextUtils.isEmpty(str);
                    String str5 = str;
                    if (isEmpty3) {
                        str5 = (T) "0";
                    }
                    return (T) Short.valueOf(str5);
                case 5:
                    return (T) Short.valueOf(str);
                case 6:
                    boolean isEmpty4 = TextUtils.isEmpty(str);
                    String str6 = str;
                    if (isEmpty4) {
                        str6 = (T) "0";
                    }
                    return (T) Integer.valueOf(str6);
                case 7:
                    return (T) Integer.valueOf(str);
                case '\b':
                    boolean isEmpty5 = TextUtils.isEmpty(str);
                    String str7 = str;
                    if (isEmpty5) {
                        str7 = (T) "0";
                    }
                    return (T) Long.valueOf(str7);
                case '\t':
                    return (T) Long.valueOf(str);
                case '\n':
                case 11:
                    if (TextUtils.isEmpty(str)) {
                        return (T) (char) 0;
                    }
                    return (T) Character.valueOf(str.charAt(0));
                case '\f':
                    boolean isEmpty6 = TextUtils.isEmpty(str);
                    String str8 = str;
                    if (isEmpty6) {
                        str8 = (T) "0";
                    }
                    return (T) Float.valueOf(str8);
                case '\r':
                    return (T) Float.valueOf(str);
                case 14:
                    boolean isEmpty7 = TextUtils.isEmpty(str);
                    String str9 = str;
                    if (isEmpty7) {
                        str9 = (T) "0";
                    }
                    return (T) Double.valueOf(str9);
                case 15:
                    return (T) Double.valueOf(str);
                case 16:
                    return str;
                default:
                    throw new UnsupportedOperationException("un support type");
            }
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    public static <T> void reply(Parcel parcel, T t) {
        parcel.writeValue(t);
    }
}
