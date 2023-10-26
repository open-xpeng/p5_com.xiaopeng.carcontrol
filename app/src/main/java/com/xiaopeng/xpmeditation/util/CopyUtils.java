package com.xiaopeng.xpmeditation.util;

import com.xiaopeng.carcontrol.util.LogUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/* loaded from: classes2.dex */
public class CopyUtils {
    public static <T> List<T> deepCopy(List<T> srcList) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(srcList);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            List<T> list = (List) objectInputStream.readObject();
            CloseUtils.closeIOQuietly(objectOutputStream, byteArrayInputStream, objectInputStream);
            return list;
        } catch (IOException e) {
            LogUtils.e("Utils", "deepCopy: ", e);
            CloseUtils.closeIOQuietly(byteArrayOutputStream);
            return null;
        } catch (ClassNotFoundException e2) {
            LogUtils.e("Utils", "deepCopy: ", e2);
            CloseUtils.closeIOQuietly(byteArrayOutputStream);
            return null;
        }
    }
}
