package com.xiaopeng.lib.utils;

import android.os.Environment;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

/* loaded from: classes2.dex */
public class StorageUtils {
    private static final int NORMAL_SIZE = 2;

    public static boolean isTFcardMounted() {
        List<StorageInfo> storageList = getStorageList();
        if (storageList.size() < 2) {
            return false;
        }
        String str = storageList.remove(1).path;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return checkTFWritable(str + File.separator);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public static class StorageInfo {
        int number;
        String path;
        boolean readonly;
        boolean removable;

        StorageInfo(String str, boolean z, boolean z2, int i) {
            this.path = str;
            this.readonly = z;
            this.removable = z2;
            this.number = i;
        }
    }

    private static List<StorageInfo> getStorageList() {
        int i;
        String readLine;
        int i2;
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        String path = Environment.getExternalStorageDirectory().getPath();
        boolean isExternalStorageRemovable = Environment.isExternalStorageRemovable();
        String externalStorageState = Environment.getExternalStorageState();
        boolean z = externalStorageState.equals("mounted") || externalStorageState.equals("mounted_ro");
        boolean equals = Environment.getExternalStorageState().equals("mounted_ro");
        if (z) {
            hashSet.add(path);
            if (isExternalStorageRemovable) {
                i = 2;
                i2 = 1;
            } else {
                i2 = -1;
                i = 1;
            }
            arrayList.add(0, new StorageInfo(path, equals, isExternalStorageRemovable, i2));
        } else {
            i = 1;
        }
        BufferedReader bufferedReader = null;
        try {
            try {
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader("/proc/mounts"));
                    while (true) {
                        try {
                            readLine = bufferedReader2.readLine();
                            if (readLine == null) {
                                break;
                            } else if (readLine.contains("vfat") || readLine.contains("/mnt")) {
                                StringTokenizer stringTokenizer = new StringTokenizer(readLine, " ");
                                stringTokenizer.nextToken();
                                String nextToken = stringTokenizer.nextToken();
                                if (!hashSet.contains(nextToken)) {
                                    stringTokenizer.nextToken();
                                    boolean contains = Arrays.asList(stringTokenizer.nextToken().split(",")).contains("ro");
                                    if (readLine.contains("/dev/block/vold") && !readLine.contains("/mnt/secure") && !readLine.contains("/mnt/asec") && !readLine.contains("/mnt/obb") && !readLine.contains("/dev/mapper") && !readLine.contains("tmpfs")) {
                                        hashSet.add(nextToken);
                                        int i3 = i + 1;
                                        arrayList.add(new StorageInfo(nextToken, contains, true, i));
                                        i = i3;
                                    }
                                }
                            }
                        } catch (FileNotFoundException e) {
                            e = e;
                            bufferedReader = bufferedReader2;
                            e.printStackTrace();
                            if (bufferedReader != null) {
                                bufferedReader.close();
                                bufferedReader = bufferedReader;
                            }
                            return arrayList;
                        } catch (IOException e2) {
                            e = e2;
                            bufferedReader = bufferedReader2;
                            e.printStackTrace();
                            if (bufferedReader != null) {
                                bufferedReader.close();
                                bufferedReader = bufferedReader;
                            }
                            return arrayList;
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    }
                    bufferedReader2.close();
                    bufferedReader = readLine;
                } catch (FileNotFoundException e4) {
                    e = e4;
                } catch (IOException e5) {
                    e = e5;
                }
            } catch (IOException e6) {
                e6.printStackTrace();
            }
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static boolean checkTFWritable(String str) {
        if (str == null) {
            return false;
        }
        File file = new File(str);
        if (file.isDirectory() || file.exists() || file.mkdirs()) {
            File file2 = new File(file, ".xpTF");
            try {
                if (file2.exists()) {
                    file2.delete();
                }
                if (file2.createNewFile()) {
                    file2.delete();
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
