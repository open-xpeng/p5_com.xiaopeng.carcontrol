package com.xiaopeng.lib.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes2.dex */
public class Zip7zUtils {
    public static void zip7z(String str, String str2) {
        SevenZOutputFile sevenZOutputFile = null;
        try {
            try {
                File file = new File(str2);
                File file2 = new File(str);
                SevenZOutputFile sevenZOutputFile2 = new SevenZOutputFile(file);
                try {
                    if (file2.isDirectory()) {
                        zip7zDirectory(file2, sevenZOutputFile2, "");
                    } else {
                        zip7zFile(file2, sevenZOutputFile2, "");
                    }
                    FileUtils.closeQuietly(sevenZOutputFile2);
                } catch (IOException e) {
                    e = e;
                    sevenZOutputFile = sevenZOutputFile2;
                    System.out.println(e.toString());
                    FileUtils.closeQuietly(sevenZOutputFile);
                } catch (Throwable th) {
                    th = th;
                    sevenZOutputFile = sevenZOutputFile2;
                    FileUtils.closeQuietly(sevenZOutputFile);
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v16 */
    private static void zip7zFile(File file, SevenZOutputFile sevenZOutputFile, String str) throws IOException {
        BufferedInputStream bufferedInputStream = 0;
        try {
            try {
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(new FileInputStream(file));
                try {
                    StringBuilder append = new StringBuilder().append(str);
                    String name = file.getName();
                    sevenZOutputFile.putArchiveEntry(sevenZOutputFile.createArchiveEntry(file, append.append(name).toString()));
                    byte[] bArr = new byte[4096];
                    String str2 = name;
                    while (true) {
                        int read = bufferedInputStream2.read(bArr);
                        if (read <= 0) {
                            break;
                        }
                        str2 = null;
                        sevenZOutputFile.write(bArr, 0, read);
                    }
                    FileUtils.closeQuietly(bufferedInputStream2);
                    bufferedInputStream = str2;
                    if (sevenZOutputFile == null) {
                        return;
                    }
                } catch (FileNotFoundException e) {
                    e = e;
                    bufferedInputStream = bufferedInputStream2;
                    e.printStackTrace();
                    FileUtils.closeQuietly(bufferedInputStream);
                    bufferedInputStream = bufferedInputStream;
                    if (sevenZOutputFile == null) {
                        return;
                    }
                    sevenZOutputFile.closeArchiveEntry();
                } catch (IOException e2) {
                    e = e2;
                    bufferedInputStream = bufferedInputStream2;
                    e.printStackTrace();
                    FileUtils.closeQuietly(bufferedInputStream);
                    bufferedInputStream = bufferedInputStream;
                    if (sevenZOutputFile == null) {
                        return;
                    }
                    sevenZOutputFile.closeArchiveEntry();
                } catch (Throwable th) {
                    th = th;
                    bufferedInputStream = bufferedInputStream2;
                    FileUtils.closeQuietly(bufferedInputStream);
                    if (sevenZOutputFile != null) {
                        sevenZOutputFile.closeArchiveEntry();
                    }
                    throw th;
                }
            } catch (FileNotFoundException e3) {
                e = e3;
            } catch (IOException e4) {
                e = e4;
            }
            sevenZOutputFile.closeArchiveEntry();
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static void zip7zDirectory(File file, SevenZOutputFile sevenZOutputFile, String str) throws IOException {
        File[] listFiles = file.listFiles();
        if (listFiles == null || listFiles.length <= 0) {
            return;
        }
        for (File file2 : listFiles) {
            if (file2.isDirectory()) {
                zip7zDirectory(file2, sevenZOutputFile, str + file2.getName() + MqttTopic.TOPIC_LEVEL_SEPARATOR);
            } else {
                zip7zFile(file2, sevenZOutputFile, str);
            }
        }
    }
}
