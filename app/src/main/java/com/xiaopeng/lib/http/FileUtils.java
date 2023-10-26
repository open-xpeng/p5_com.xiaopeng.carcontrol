package com.xiaopeng.lib.http;

import android.text.TextUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/* loaded from: classes2.dex */
public class FileUtils {
    public static void deleteFile(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        File file = new File(str);
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
                return;
            }
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (int i = 0; i < listFiles.length; i++) {
                    deleteFile(listFiles[i].getAbsolutePath());
                    if (listFiles[i].isDirectory()) {
                        listFiles[i].delete();
                    }
                }
            }
        }
    }

    public static String readTextFile(File file, int i, String str) throws IOException {
        int read;
        boolean z;
        int read2;
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        try {
            long length = file.length();
            String str2 = "";
            if (i > 0 || (length > 0 && i == 0)) {
                if (length > 0 && (i == 0 || length < i)) {
                    i = (int) length;
                }
                byte[] bArr = new byte[i + 1];
                int read3 = bufferedInputStream.read(bArr);
                if (read3 > 0) {
                    if (read3 <= i) {
                        str2 = new String(bArr, 0, read3);
                    } else if (str == null) {
                        str2 = new String(bArr, 0, i);
                    } else {
                        str2 = new String(bArr, 0, i) + str;
                    }
                }
            } else if (i < 0) {
                byte[] bArr2 = null;
                byte[] bArr3 = null;
                boolean z2 = false;
                while (true) {
                    z = true;
                    if (bArr2 != null) {
                        z2 = true;
                    }
                    if (bArr2 == null) {
                        bArr2 = new byte[-i];
                    }
                    read2 = bufferedInputStream.read(bArr2);
                    if (read2 != bArr2.length) {
                        break;
                    }
                    byte[] bArr4 = bArr3;
                    bArr3 = bArr2;
                    bArr2 = bArr4;
                }
                if (bArr3 != null || read2 > 0) {
                    if (bArr3 == null) {
                        str2 = new String(bArr2, 0, read2);
                    } else {
                        if (read2 > 0) {
                            System.arraycopy(bArr3, read2, bArr3, 0, bArr3.length - read2);
                            System.arraycopy(bArr2, 0, bArr3, bArr3.length - read2, read2);
                        } else {
                            z = z2;
                        }
                        if (str != null && z) {
                            str2 = str + new String(bArr3);
                        }
                        str2 = new String(bArr3);
                    }
                }
            } else {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] bArr5 = new byte[1024];
                do {
                    read = bufferedInputStream.read(bArr5);
                    if (read > 0) {
                        byteArrayOutputStream.write(bArr5, 0, read);
                        continue;
                    }
                } while (read == 1024);
                str2 = byteArrayOutputStream.toString();
            }
            return str2;
        } finally {
            bufferedInputStream.close();
            fileInputStream.close();
        }
    }

    public static void stringToFile(String str, String str2) throws IOException {
        FileWriter fileWriter = new FileWriter(str);
        try {
            fileWriter.write(str2);
        } finally {
            fileWriter.close();
        }
    }
}
