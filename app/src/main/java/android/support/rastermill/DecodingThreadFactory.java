package android.support.rastermill;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.SparseArray;

/* loaded from: classes.dex */
class DecodingThreadFactory {
    public static final int DEFAULT_DECODING_THREAD_ID = 0;
    private static SparseArray<Integer> sDecodingThreadHandlerCountMap;
    private static SparseArray<Handler> sDecodingThreadHandlerMap;
    private static final Object sLock = new Object();

    DecodingThreadFactory() {
    }

    private static void initializeDecodingThread() {
        synchronized (sLock) {
            if (sDecodingThreadHandlerMap != null) {
                return;
            }
            sDecodingThreadHandlerMap = new SparseArray<>();
            sDecodingThreadHandlerCountMap = new SparseArray<>();
            createHandler(0);
        }
    }

    private static String getThreadName(int i) {
        return "FrameSequence decoding thread" + i;
    }

    private static Handler createHandler(int i) {
        HandlerThread handlerThread = new HandlerThread(getThreadName(i), 0);
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        sDecodingThreadHandlerMap.put(i, handler);
        return handler;
    }

    public static Handler getDecodingThreadHandler(int i) {
        Handler handler;
        int i2;
        initializeDecodingThread();
        synchronized (sLock) {
            handler = sDecodingThreadHandlerMap.get(i);
            if (handler == null) {
                handler = createHandler(i);
            }
            if (handler != null && i != 0) {
                Integer num = sDecodingThreadHandlerCountMap.get(i);
                if (num != null) {
                    i2 = Integer.valueOf(num.intValue() + 1);
                } else {
                    i2 = 1;
                }
                sDecodingThreadHandlerCountMap.put(i, i2);
            }
        }
        return handler;
    }

    public static void releaseDecodingThreadHandler(int i) {
        if (i == 0) {
            return;
        }
        synchronized (sLock) {
            Integer num = sDecodingThreadHandlerCountMap.get(i);
            if (num != null) {
                Integer valueOf = Integer.valueOf(num.intValue() - 1);
                if (valueOf.intValue() <= 0) {
                    sDecodingThreadHandlerMap.get(i).getLooper().quit();
                    sDecodingThreadHandlerMap.remove(i);
                    sDecodingThreadHandlerCountMap.remove(i);
                } else {
                    sDecodingThreadHandlerCountMap.put(i, valueOf);
                }
            }
        }
    }
}
