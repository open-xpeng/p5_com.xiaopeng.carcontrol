package com.xiaopeng.lib.http.logging;

import com.xiaopeng.lib.utils.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes2.dex */
public class LogCacher {
    private static final int MAX_CACHE_COUNT = 5;
    private List<Cache> mCache = new LinkedList();
    private IHandler mHandler = new LogHandler();
    private int mCacheCount = 5;

    /* loaded from: classes2.dex */
    public interface IHandler {
        void handle(String str);
    }

    /* loaded from: classes2.dex */
    public interface ILog {
        void code(int i);

        void end();

        void log(String str);
    }

    public ILog getLogger() {
        return new Cache(this);
    }

    public void setHandler(IHandler iHandler) {
        this.mHandler = iHandler;
    }

    public void setCacheCount(int i) {
        this.mCacheCount = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void end(Cache cache) {
        this.mCache.add(cache);
        if (this.mCache.size() > this.mCacheCount) {
            this.mCache.remove(0);
        }
        if (cache.code > 299 || cache.code < 0) {
            Iterator<Cache> it = this.mCache.iterator();
            while (it.hasNext()) {
                for (String str : it.next().msgs) {
                    this.mHandler.handle(str);
                }
                it.remove();
            }
        }
    }

    /* loaded from: classes2.dex */
    private class LogHandler implements IHandler {
        private LogHandler() {
        }

        @Override // com.xiaopeng.lib.http.logging.LogCacher.IHandler
        public void handle(String str) {
            LogUtils.d("HttpsUtils", str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Cache implements ILog {
        int code;
        LogCacher context;
        List<String> msgs = new ArrayList();

        public Cache(LogCacher logCacher) {
            this.context = logCacher;
        }

        @Override // com.xiaopeng.lib.http.logging.LogCacher.ILog
        public void code(int i) {
            this.code = i;
        }

        @Override // com.xiaopeng.lib.http.logging.LogCacher.ILog
        public void log(String str) {
            this.msgs.add(str);
        }

        @Override // com.xiaopeng.lib.http.logging.LogCacher.ILog
        public void end() {
            this.context.end(this);
        }
    }
}
