package com.alibaba.sdk.android.oss.common.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;

/* loaded from: classes.dex */
public class IOUtils {
    private static final int BUFFER_SIZE = 4096;

    public static String readStreamAsString(InputStream inputStream, String str) throws IOException {
        if (inputStream == null) {
            return "";
        }
        BufferedReader bufferedReader = null;
        StringWriter stringWriter = new StringWriter();
        char[] cArr = new char[4096];
        try {
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream, str));
            while (true) {
                try {
                    int read = bufferedReader2.read(cArr);
                    if (read > 0) {
                        stringWriter.write(cArr, 0, read);
                    } else {
                        String obj = stringWriter.toString();
                        safeClose(inputStream);
                        bufferedReader2.close();
                        stringWriter.close();
                        return obj;
                    }
                } catch (Throwable th) {
                    th = th;
                    bufferedReader = bufferedReader2;
                    safeClose(inputStream);
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    stringWriter.close();
                    throw th;
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static byte[] readStreamAsBytesArray(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return new byte[0];
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        while (true) {
            int read = inputStream.read(bArr);
            if (read > -1) {
                byteArrayOutputStream.write(bArr, 0, read);
            } else {
                byteArrayOutputStream.flush();
                safeClose(byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            }
        }
    }

    public static byte[] readStreamAsBytesArray(InputStream inputStream, int i) throws IOException {
        int read;
        if (inputStream == null) {
            return new byte[0];
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[4096];
        long j = 0;
        while (true) {
            long j2 = i;
            if (j >= j2 || (read = inputStream.read(bArr, 0, Math.min(2048, (int) (j2 - j)))) <= -1) {
                break;
            }
            byteArrayOutputStream.write(bArr, 0, read);
            j += read;
        }
        byteArrayOutputStream.flush();
        safeClose(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static void safeClose(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void safeClose(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException unused) {
            }
        }
    }
}
