package com.xiaopeng.carcontrol.download;

import android.os.Build;
import android.os.StatFs;
import android.text.TextUtils;
import java.io.File;
import java.io.IOException;

/* loaded from: classes2.dex */
public class FileHelper {
    public void test() {
    }

    public static boolean createOrExistsDir(final String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    public static boolean createOrExistsDir(final File file) {
        return file != null && (!file.exists() ? !file.mkdirs() : !file.isDirectory());
    }

    public static boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    public static boolean createOrExistsFile(final File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (createOrExistsDir(file.getParentFile())) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static File[] listFilesInDir(final String filePath) {
        return listFilesInDir(getFileByPath(filePath));
    }

    public static File[] listFilesInDir(final File dir) {
        if (isDir(dir)) {
            return dir.listFiles();
        }
        return null;
    }

    public static boolean isDir(final String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    public static boolean isDir(final File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    public static File getFileByPath(final String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        return new File(filePath);
    }

    public static long getFsTotalSize(String anyPathInFs) {
        long blockSize;
        long blockCount;
        if (TextUtils.isEmpty(anyPathInFs)) {
            return 0L;
        }
        StatFs statFs = new StatFs(anyPathInFs);
        if (Build.VERSION.SDK_INT >= 18) {
            blockSize = statFs.getBlockSizeLong();
            blockCount = statFs.getBlockCountLong();
        } else {
            blockSize = statFs.getBlockSize();
            blockCount = statFs.getBlockCount();
        }
        return blockSize * blockCount;
    }

    public static long getFsAvailableSize(final String anyPathInFs) {
        long blockSize;
        long availableBlocks;
        if (TextUtils.isEmpty(anyPathInFs)) {
            return 0L;
        }
        StatFs statFs = new StatFs(anyPathInFs);
        if (Build.VERSION.SDK_INT >= 18) {
            blockSize = statFs.getBlockSizeLong();
            availableBlocks = statFs.getAvailableBlocksLong();
        } else {
            blockSize = statFs.getBlockSize();
            availableBlocks = statFs.getAvailableBlocks();
        }
        return blockSize * availableBlocks;
    }
}
