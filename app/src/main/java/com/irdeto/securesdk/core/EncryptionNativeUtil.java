package com.irdeto.securesdk.core;

import android.util.Log;
import com.irdeto.securesdk.CryptoException;
import com.irdeto.securesdk.Feedback;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes.dex */
public class EncryptionNativeUtil {
    private static final String TAG = "EncryptionNativeUtil";

    private static native String _encry2String(String str, String str2, String str3);

    private static native byte[] _joseDecString(String str, String str2, Feedback feedback);

    private static native String _joseEncString(byte[] bArr, String str, Feedback feedback);

    private static String bytes2Hex(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(hexString);
        }
        return stringBuffer.toString();
    }

    public static byte[] decryptData(String str) throws CryptoException {
        Feedback feedback = new Feedback();
        byte[] _joseDecString = _joseDecString(str, "AES-DIR", feedback);
        if (feedback.errorCode == 0) {
            return _joseDecString;
        }
        Log.d(TAG, "jose dec errorCode = " + feedback.errorCode + ", message = " + feedback.message);
        throw new CryptoException(feedback.errorCode, feedback.message);
    }

    public static String encodePassword(String str) {
        byte[] bytes = str.getBytes();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(bytes);
            return bytes2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            Log.e("Invalid algorithm.", e.toString());
            return null;
        }
    }

    public static String encry2String(String str, String str2, String str3) {
        return _encry2String(str, str2, str3);
    }

    public static String encryptData(byte[] bArr) throws CryptoException {
        Feedback feedback = new Feedback();
        String _joseEncString = _joseEncString(bArr, "AES-DIR", feedback);
        if (feedback.errorCode == 0) {
            return _joseEncString;
        }
        Log.d(TAG, "jose enc errorCode = " + feedback.errorCode + ", message = " + feedback.message);
        throw new CryptoException(feedback.errorCode, feedback.message);
    }

    public static String generateKID() {
        return UUID.randomUUID().toString();
    }

    public static String generateRandom() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static byte[] joseDecString(String str, String str2) throws CryptoException {
        Feedback feedback = new Feedback();
        byte[] _joseDecString = _joseDecString(str, str2, feedback);
        if (feedback.errorCode == 0) {
            return _joseDecString;
        }
        Log.d(TAG, "jose dec errorCode = " + feedback.errorCode + ", message = " + feedback.message);
        throw new CryptoException(feedback.errorCode, feedback.message);
    }

    public static String joseEncString(byte[] bArr, String str) throws CryptoException {
        Feedback feedback = new Feedback();
        String _joseEncString = _joseEncString(bArr, str, feedback);
        if (feedback.errorCode == 0) {
            return _joseEncString;
        }
        Log.d(TAG, "jose enc errorCode = " + feedback.errorCode + ", message = " + feedback.message);
        throw new CryptoException(feedback.errorCode, feedback.message);
    }

    public static String md5Encryption(String str) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                int i = b & 255;
                if (i < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            int lastIndexOf = str.lastIndexOf(MqttTopic.TOPIC_LEVEL_SEPARATOR) + 1;
            int lastIndexOf2 = str.lastIndexOf(".");
            if (lastIndexOf2 <= 0) {
                lastIndexOf2 = str.length();
            }
            return str.substring(lastIndexOf, lastIndexOf2);
        }
    }
}
