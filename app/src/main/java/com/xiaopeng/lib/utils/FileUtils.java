package com.xiaopeng.lib.utils;

import android.graphics.Bitmap;
import android.os.Environment;
import android.os.SystemProperties;
import android.text.TextUtils;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

/* loaded from: classes2.dex */
public class FileUtils {
    private static final String FILE_DIR_NAME = "files";
    private static final String PACKAGE_NAME = "com.xiaopeng.xmart";
    public static final int SIZETYPE_B = 1;
    public static final int SIZETYPE_GB = 4;
    public static final int SIZETYPE_KB = 2;
    public static final int SIZETYPE_MB = 3;
    public static final long SIZE_1GB = 1073741824;
    public static final long SIZE_1KB = 1024;
    public static final long SIZE_1MB = 1048576;
    private static final String TAG = "FileUtils";
    private static final String XMART_USB_PATH = "/mnt/usbhost";

    /* loaded from: classes2.dex */
    public interface OnUpdateFileCopyFromUsbListener {
        void onCopyFail();

        void onCopySuccess();
    }

    public static double getFileSize(String str, int i) {
        long j;
        File file = new File(str);
        try {
            if (file.isDirectory()) {
                j = getFileSizes(file);
            } else {
                j = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "getFileSize error! e = " + e);
            j = 0;
        }
        return FormatFileSize(j, i);
    }

    public static String getFileSize(String str) {
        long j;
        File file = new File(str);
        try {
            if (file.isDirectory()) {
                j = getFileSizes(file);
            } else {
                j = getFileSize(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e(TAG, "getFileSize error!");
            j = 0;
        }
        return FormatFileSize(j);
    }

    private static long getFileSize(File file) throws FileNotFoundException {
        if (file != null && file.exists()) {
            return file.length();
        }
        throw new FileNotFoundException();
    }

    private static long getFileSizes(File file) throws Exception {
        long fileSize;
        File[] listFiles = file.listFiles();
        long j = 0;
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                if (listFiles[i].isDirectory()) {
                    fileSize = getFileSizes(listFiles[i]);
                } else {
                    fileSize = getFileSize(listFiles[i]);
                }
                j += fileSize;
            }
        } else {
            LogUtils.e(TAG, "File not exist");
        }
        return j;
    }

    private static String FormatFileSize(long j) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        if (j == 0) {
            return "0B";
        }
        if (j < 1024) {
            return decimalFormat.format(j).replace(",", ".") + "B";
        }
        if (j < 1048576) {
            return decimalFormat.format(j / 1024.0d).replace(",", ".") + "KB";
        }
        if (j < SIZE_1GB) {
            return decimalFormat.format(j / 1048576.0d).replace(",", ".") + "MB";
        }
        return decimalFormat.format(j / 1.073741824E9d).replace(",", ".") + "GB";
    }

    private static double FormatFileSize(long j, int i) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        return 0.0d;
                    }
                    return Double.valueOf(decimalFormat.format(j / 1.073741824E9d).replace(",", ".")).doubleValue();
                }
                return Double.valueOf(decimalFormat.format(j / 1048576.0d).replace(",", ".")).doubleValue();
            }
            return Double.valueOf(decimalFormat.format(j / 1024.0d).replace(",", ".")).doubleValue();
        }
        return Double.valueOf(decimalFormat.format(j).replace(",", ".")).doubleValue();
    }

    public static String getFileBaseName(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        int lastIndexOf = str.lastIndexOf(File.separator);
        if (lastIndexOf >= 0 && lastIndexOf < str.length()) {
            str = str.substring(lastIndexOf + 1);
        }
        int lastIndexOf2 = str.lastIndexOf(".");
        return (lastIndexOf2 <= 0 || lastIndexOf2 >= str.length()) ? str : str.substring(0, lastIndexOf2);
    }

    private static boolean hasSDCard() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static String getRootFilePath() {
        if (hasSDCard()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        }
        return Environment.getDataDirectory().getAbsolutePath() + "/data/";
    }

    public static String getSaveFilePath() {
        if (hasSDCard()) {
            return getRootFilePath() + PACKAGE_NAME + File.separator + FILE_DIR_NAME + File.separator;
        }
        return getRootFilePath() + PACKAGE_NAME + File.separator + FILE_DIR_NAME;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static String storePicture(Bitmap bitmap, String str, int i) {
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(file, System.currentTimeMillis() + ".jpg");
        FileOutputStream fileOutputStream = null;
        try {
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
                file2.createNewFile();
                FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                try {
                    Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
                    bitmap.compress(compressFormat, i, fileOutputStream2);
                    fileOutputStream2.flush();
                    fileOutputStream2.close();
                    fileOutputStream = compressFormat;
                } catch (FileNotFoundException e2) {
                    e = e2;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                        fileOutputStream = fileOutputStream;
                    }
                    return file2.getAbsolutePath();
                } catch (IOException e3) {
                    e = e3;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                        fileOutputStream = fileOutputStream;
                    }
                    return file2.getAbsolutePath();
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
            } catch (IOException e6) {
                e = e6;
            }
            return file2.getAbsolutePath();
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0085 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r6v0, types: [android.graphics.Bitmap] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x005f -> B:48:0x0062). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void saveImageToGallery(android.content.Context r5, android.graphics.Bitmap r6, java.lang.String r7) {
        /*
            java.io.File r0 = new java.io.File
            java.io.File r1 = android.os.Environment.getExternalStorageDirectory()
            r0.<init>(r1, r7)
            boolean r7 = r0.exists()
            if (r7 != 0) goto L12
            r0.mkdir()
        L12:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            long r1 = java.lang.System.currentTimeMillis()
            java.lang.StringBuilder r7 = r7.append(r1)
            java.lang.String r1 = ".jpg"
            java.lang.StringBuilder r7 = r7.append(r1)
            java.lang.String r7 = r7.toString()
            java.io.File r1 = new java.io.File
            r1.<init>(r0, r7)
            r0 = 0
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L46 java.io.IOException -> L48 java.io.FileNotFoundException -> L53
            r2.<init>(r1)     // Catch: java.lang.Throwable -> L46 java.io.IOException -> L48 java.io.FileNotFoundException -> L53
            android.graphics.Bitmap$CompressFormat r3 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch: java.io.IOException -> L42 java.io.FileNotFoundException -> L44 java.lang.Throwable -> L81
            r4 = 100
            r6.compress(r3, r4, r2)     // Catch: java.io.IOException -> L42 java.io.FileNotFoundException -> L44 java.lang.Throwable -> L81
            r2.flush()     // Catch: java.io.IOException -> L42 java.io.FileNotFoundException -> L44 java.lang.Throwable -> L81
            r2.close()     // Catch: java.io.IOException -> L5e
            goto L62
        L42:
            r6 = move-exception
            goto L4a
        L44:
            r6 = move-exception
            goto L55
        L46:
            r5 = move-exception
            goto L83
        L48:
            r6 = move-exception
            r2 = r0
        L4a:
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L81
            if (r2 == 0) goto L62
            r2.close()     // Catch: java.io.IOException -> L5e
            goto L62
        L53:
            r6 = move-exception
            r2 = r0
        L55:
            r6.printStackTrace()     // Catch: java.lang.Throwable -> L81
            if (r2 == 0) goto L62
            r2.close()     // Catch: java.io.IOException -> L5e
            goto L62
        L5e:
            r6 = move-exception
            r6.printStackTrace()
        L62:
            android.content.ContentResolver r6 = r5.getContentResolver()     // Catch: java.io.FileNotFoundException -> L6e
            java.lang.String r2 = r1.getAbsolutePath()     // Catch: java.io.FileNotFoundException -> L6e
            android.provider.MediaStore.Images.Media.insertImage(r6, r2, r7, r0)     // Catch: java.io.FileNotFoundException -> L6e
            goto L72
        L6e:
            r6 = move-exception
            r6.printStackTrace()
        L72:
            android.content.Intent r6 = new android.content.Intent
            android.net.Uri r7 = android.net.Uri.fromFile(r1)
            java.lang.String r0 = "android.intent.action.MEDIA_SCANNER_SCAN_FILE"
            r6.<init>(r0, r7)
            r5.sendBroadcast(r6)
            return
        L81:
            r5 = move-exception
            r0 = r2
        L83:
            if (r0 == 0) goto L8d
            r0.close()     // Catch: java.io.IOException -> L89
            goto L8d
        L89:
            r6 = move-exception
            r6.printStackTrace()
        L8d:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.lib.utils.FileUtils.saveImageToGallery(android.content.Context, android.graphics.Bitmap, java.lang.String):void");
    }

    public static String getFileStorePath(String str) {
        String saveFilePath = getSaveFilePath();
        if (saveFilePath.endsWith(File.separator)) {
            return saveFilePath + str;
        }
        return saveFilePath + File.separator + str;
    }

    public static String[] getUsbMountDir() {
        String str = SystemProperties.get("sys.usb.label", (String) null);
        LogUtils.d(TAG, "UsbMountDir-->" + str);
        if (TextUtils.isEmpty(str)) {
            return new String[]{XMART_USB_PATH};
        }
        return str.contains(",") ? str.split(",") : new String[]{str};
    }

    public static boolean isUsbAvailable() {
        File[] listFiles;
        String[] usbMountDir = getUsbMountDir();
        if (usbMountDir != null) {
            for (String str : usbMountDir) {
                LogUtils.d(TAG, "subMountDir-->" + str);
                File file = new File(str);
                if (file.isDirectory() && (listFiles = file.listFiles()) != null && listFiles.length > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void copyFile(String str, String str2, OnUpdateFileCopyFromUsbListener onUpdateFileCopyFromUsbListener) {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    if (new File(str).exists()) {
                        fileInputStream = new FileInputStream(str);
                        try {
                            FileOutputStream fileOutputStream2 = new FileOutputStream(str2);
                            try {
                                byte[] bArr = new byte[1024];
                                while (true) {
                                    int read = fileInputStream.read(bArr);
                                    if (read == -1) {
                                        break;
                                    }
                                    fileOutputStream2.write(bArr, 0, read);
                                }
                                fileOutputStream2.flush();
                                if (onUpdateFileCopyFromUsbListener != null) {
                                    onUpdateFileCopyFromUsbListener.onCopySuccess();
                                }
                                fileOutputStream = fileOutputStream2;
                            } catch (Exception e) {
                                e = e;
                                fileOutputStream = fileOutputStream2;
                                e.printStackTrace();
                                onUpdateFileCopyFromUsbListener.onCopyFail();
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                    }
                                }
                                if (fileInputStream != null) {
                                    fileInputStream.close();
                                }
                                return;
                            } catch (Throwable th) {
                                th = th;
                                fileOutputStream = fileOutputStream2;
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e3) {
                                        e3.printStackTrace();
                                    }
                                }
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (IOException e4) {
                                        e4.printStackTrace();
                                    }
                                }
                                throw th;
                            }
                        } catch (Exception e5) {
                            e = e5;
                        }
                    } else {
                        fileInputStream = null;
                    }
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                } catch (Exception e7) {
                    e = e7;
                    fileInputStream = null;
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream = null;
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e8) {
                e8.printStackTrace();
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static boolean copyFile(String str, String str2) {
        FileInputStream fileInputStream;
        boolean z = false;
        FileOutputStream fileOutputStream = null;
        try {
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
                if (new File(str).exists()) {
                    fileInputStream = new FileInputStream(str);
                    try {
                        FileOutputStream fileOutputStream2 = new FileOutputStream(str2);
                        try {
                            byte[] bArr = new byte[1024];
                            while (true) {
                                int read = fileInputStream.read(bArr);
                                if (read == -1) {
                                    break;
                                }
                                fileOutputStream2.write(bArr, 0, read);
                            }
                            fileOutputStream2.flush();
                            z = true;
                            fileOutputStream = fileOutputStream2;
                        } catch (Exception e2) {
                            e = e2;
                            fileOutputStream = fileOutputStream2;
                            e.printStackTrace();
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            return z;
                        } catch (Throwable th) {
                            th = th;
                            fileOutputStream = fileOutputStream2;
                            if (fileOutputStream != null) {
                                try {
                                    fileOutputStream.close();
                                } catch (IOException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            if (fileInputStream != null) {
                                try {
                                    fileInputStream.close();
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                }
                            }
                            throw th;
                        }
                    } catch (Exception e6) {
                        e = e6;
                    }
                } else {
                    fileInputStream = null;
                }
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
            } catch (Exception e8) {
                e = e8;
                fileInputStream = null;
            } catch (Throwable th2) {
                th = th2;
                fileInputStream = null;
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            return z;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public static File makeFilePath(String str, String str2) {
        File file;
        try {
            File file2 = new File(str);
            if (!file2.exists()) {
                file2.mkdirs();
            }
        } catch (Exception unused) {
        }
        File file3 = null;
        try {
            file = new File(str + str2);
        } catch (Exception e) {
            e = e;
        }
        try {
            if (file.exists()) {
                return file;
            }
            file.createNewFile();
            return file;
        } catch (Exception e2) {
            e = e2;
            file3 = file;
            e.printStackTrace();
            return file3;
        }
    }

    public static void makeDirectory(String str) {
        try {
            File file = new File(str);
            if (file.exists()) {
                return;
            }
            file.mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeTxtToFile(String str, String str2, String str3) {
        makeFilePath(str2, str3);
        String str4 = str2 + str3;
        String str5 = str + "\r\n";
        RandomAccessFile randomAccessFile = null;
        try {
            try {
                try {
                    File file = new File(str4);
                    if (!file.exists()) {
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                    }
                    RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "rwd");
                    try {
                        randomAccessFile2.seek(file.length());
                        randomAccessFile2.write(str5.getBytes());
                        randomAccessFile2.close();
                    } catch (Exception e) {
                        e = e;
                        randomAccessFile = randomAccessFile2;
                        LogUtils.w(TAG, "writeTxtToFile error!", e);
                        e.printStackTrace();
                        if (randomAccessFile != null) {
                            randomAccessFile.close();
                        }
                    } catch (Throwable th) {
                        th = th;
                        randomAccessFile = randomAccessFile2;
                        if (randomAccessFile != null) {
                            try {
                                randomAccessFile.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
    }

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
            new File(str).delete();
        }
    }

    public static long getDirSize(File file) {
        long j = 0;
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null && listFiles.length != 0) {
                    for (File file2 : listFiles) {
                        j += getDirSize(file2);
                    }
                }
                return j;
            }
            return (file.length() / 1024) / 1024;
        }
        System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
        return 0L;
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
