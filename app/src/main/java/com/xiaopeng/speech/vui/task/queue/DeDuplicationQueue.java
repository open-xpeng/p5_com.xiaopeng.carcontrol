package com.xiaopeng.speech.vui.task.queue;

import android.view.View;
import com.xiaopeng.speech.vui.task.base.BaseTask;
import com.xiaopeng.speech.vui.utils.LogUtils;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/* loaded from: classes2.dex */
public class DeDuplicationQueue<K, T extends BaseTask> implements BlockingQueue<T> {
    private static final String TAG = "DeDuplicationQueue";
    protected final PriorityBlockingQueue<T> queue = new PriorityBlockingQueue<>(20, new TaskComparator());
    protected final HashMap<K, T> map = new HashMap<>();
    protected final HashMap<SoftReference<View>, T> viewMap = new HashMap<>();
    protected final HashMap<K, List<BaseTask>> sceneMap = new HashMap<>();
    protected ReentrantLock lock = new ReentrantLock();

    public boolean add(T t) {
        return false;
    }

    @Override // java.util.Collection
    public boolean addAll(Collection<? extends T> collection) {
        return false;
    }

    @Override // java.util.Collection
    public void clear() {
    }

    @Override // java.util.concurrent.BlockingQueue, java.util.Collection
    public boolean contains(Object obj) {
        return false;
    }

    @Override // java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        return false;
    }

    @Override // java.util.concurrent.BlockingQueue
    public int drainTo(Collection<? super T> collection) {
        return 0;
    }

    @Override // java.util.concurrent.BlockingQueue
    public int drainTo(Collection<? super T> collection, int i) {
        return 0;
    }

    @Override // java.util.Queue
    public T element() {
        return null;
    }

    @Override // java.util.Collection, java.lang.Iterable
    public Iterator<T> iterator() {
        return null;
    }

    public boolean offer(T t) {
        return false;
    }

    public boolean offer(T t, long j, TimeUnit timeUnit) throws InterruptedException {
        return false;
    }

    @Override // java.util.Queue
    public T peek() {
        return null;
    }

    @Override // java.util.Queue
    public T poll() {
        return null;
    }

    @Override // java.util.concurrent.BlockingQueue
    public T poll(long j, TimeUnit timeUnit) throws InterruptedException {
        return null;
    }

    public void put(T t) throws InterruptedException {
    }

    @Override // java.util.concurrent.BlockingQueue
    public int remainingCapacity() {
        return 0;
    }

    @Override // java.util.Queue
    public T remove() {
        return null;
    }

    @Override // java.util.concurrent.BlockingQueue, java.util.Collection
    public boolean remove(Object obj) {
        return false;
    }

    @Override // java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        return false;
    }

    @Override // java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        return false;
    }

    @Override // java.util.concurrent.BlockingQueue
    public T take() throws InterruptedException {
        return null;
    }

    @Override // java.util.Collection
    public Object[] toArray() {
        return null;
    }

    @Override // java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.concurrent.BlockingQueue, java.util.Queue, java.util.Collection
    public /* bridge */ /* synthetic */ boolean add(Object obj) {
        return add((DeDuplicationQueue<K, T>) ((BaseTask) obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.concurrent.BlockingQueue, java.util.Queue
    public /* bridge */ /* synthetic */ boolean offer(Object obj) {
        return offer((DeDuplicationQueue<K, T>) ((BaseTask) obj));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.concurrent.BlockingQueue
    public /* bridge */ /* synthetic */ boolean offer(Object obj, long j, TimeUnit timeUnit) throws InterruptedException {
        return offer((DeDuplicationQueue<K, T>) ((BaseTask) obj), j, timeUnit);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.concurrent.BlockingQueue
    public /* bridge */ /* synthetic */ void put(Object obj) throws InterruptedException {
        put((DeDuplicationQueue<K, T>) ((BaseTask) obj));
    }

    @Override // java.util.Collection
    public int size() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return this.queue.size();
        } finally {
            reentrantLock.unlock();
        }
    }

    @Override // java.util.Collection
    public boolean isEmpty() {
        ReentrantLock reentrantLock = this.lock;
        reentrantLock.lock();
        try {
            return this.queue.isEmpty();
        } finally {
            reentrantLock.unlock();
        }
    }

    public synchronized void removeTask(String str) {
        LogUtils.logInfo(TAG, "removeTask:" + str);
        if (str == null) {
            return;
        }
        try {
            HashMap<K, List<BaseTask>> hashMap = this.sceneMap;
            if (hashMap != null && hashMap.containsKey(str)) {
                List<BaseTask> list = this.sceneMap.get(str);
                for (int i = 0; i < list.size(); i++) {
                    BaseTask baseTask = list.get(i);
                    this.queue.remove(baseTask);
                    String vid = baseTask.wrapper.getVid();
                    if (vid != null && this.map.containsKey(vid)) {
                        this.map.remove(vid);
                    }
                    SoftReference<View> view = baseTask.wrapper.getView();
                    if (view != null && this.map.containsKey(view)) {
                        this.map.remove(view);
                    }
                }
                this.sceneMap.remove(str);
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "e:" + e.getMessage());
        }
    }

    public void removeTaskFromSceneMap(BaseTask baseTask) {
        List<BaseTask> list;
        int indexOf;
        String sceneId = baseTask.wrapper != null ? baseTask.wrapper.getSceneId() : null;
        HashMap<K, List<BaseTask>> hashMap = this.sceneMap;
        if (hashMap == null || sceneId == null || !hashMap.containsKey(sceneId) || (list = this.sceneMap.get(sceneId)) == null || (indexOf = list.indexOf(baseTask)) == -1) {
            return;
        }
        list.remove(indexOf);
    }
}
