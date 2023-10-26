package org.apache.commons.compress.compressors.pack200;

import com.xiaopeng.lludancemanager.Constant;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;

/* loaded from: classes3.dex */
public class Pack200Utils {
    private Pack200Utils() {
    }

    public static void normalize(File file) throws IOException {
        normalize(file, file, null);
    }

    public static void normalize(File file, Map<String, String> map) throws IOException {
        normalize(file, file, map);
    }

    public static void normalize(File file, File file2) throws IOException {
        normalize(file, file2, null);
    }

    public static void normalize(File file, File file2, Map<String, String> map) throws IOException {
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("pack.segment.limit", Constant.DEFAULT_ERROR_RSC_ID);
        File createTempFile = File.createTempFile("commons-compress", "pack200normalize");
        createTempFile.deleteOnExit();
        try {
            OutputStream fileOutputStream = new FileOutputStream(createTempFile);
            JarFile jarFile = null;
            try {
                Pack200.Packer newPacker = Pack200.newPacker();
                newPacker.properties().putAll(map);
                JarFile jarFile2 = new JarFile(file);
                try {
                    newPacker.pack(jarFile2, fileOutputStream);
                    fileOutputStream.close();
                } catch (Throwable th) {
                    th = th;
                    jarFile = jarFile2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                Pack200.Unpacker newUnpacker = Pack200.newUnpacker();
                fileOutputStream = new JarOutputStream(new FileOutputStream(file2));
                newUnpacker.unpack(createTempFile, (JarOutputStream) fileOutputStream);
                fileOutputStream.close();
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = null;
                if (jarFile != null) {
                    jarFile.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th;
            }
        } finally {
            createTempFile.delete();
        }
    }
}
