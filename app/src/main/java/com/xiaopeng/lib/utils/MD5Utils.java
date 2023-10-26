package com.xiaopeng.lib.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes2.dex */
public class MD5Utils {
    public static String getMD5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes("UTF-8"));
            byte[] digest = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < digest.length; i++) {
                if (Integer.toHexString(digest[i] & 255).length() == 1) {
                    stringBuffer.append("0").append(Integer.toHexString(digest[i] & 255));
                } else {
                    stringBuffer.append(Integer.toHexString(digest[i] & 255));
                }
            }
            return stringBuffer.toString();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException unused) {
            return "";
        }
    }

    public static String getFileMd5(File file) throws FileNotFoundException {
        RandomAccessFile randomAccessFile = null;
        try {
            try {
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                    if (file != null && file.exists()) {
                        RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "r");
                        try {
                            byte[] bArr = new byte[10485760];
                            while (true) {
                                int read = randomAccessFile2.read(bArr);
                                if (read == -1) {
                                    break;
                                }
                                messageDigest.update(bArr, 0, read);
                            }
                            String bigInteger = new BigInteger(1, messageDigest.digest()).toString(16);
                            while (bigInteger.length() < 32) {
                                bigInteger = "0" + bigInteger;
                            }
                            try {
                                randomAccessFile2.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return bigInteger;
                        } catch (FileNotFoundException e2) {
                            e = e2;
                            randomAccessFile = randomAccessFile2;
                            e.printStackTrace();
                            if (randomAccessFile != null) {
                                randomAccessFile.close();
                            }
                            return "";
                        } catch (IOException e3) {
                            e = e3;
                            randomAccessFile = randomAccessFile2;
                            e.printStackTrace();
                            if (randomAccessFile != null) {
                                randomAccessFile.close();
                            }
                            return "";
                        } catch (NoSuchAlgorithmException e4) {
                            e = e4;
                            randomAccessFile = randomAccessFile2;
                            e.printStackTrace();
                            if (randomAccessFile != null) {
                                randomAccessFile.close();
                            }
                            return "";
                        } catch (Throwable th) {
                            th = th;
                            randomAccessFile = randomAccessFile2;
                            if (randomAccessFile != null) {
                                try {
                                    randomAccessFile.close();
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    }
                    return "";
                } catch (FileNotFoundException e6) {
                    e = e6;
                } catch (IOException e7) {
                    e = e7;
                } catch (NoSuchAlgorithmException e8) {
                    e = e8;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e9) {
            e9.printStackTrace();
        }
    }
}
