package com.xiaopeng.carcontrol.carmanager;

import android.car.Car;
import android.car.hardware.CarEcuManager;
import android.car.hardware.CarPropertyValue;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.carmanager.IBaseCallback;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.model.DataSyncModel;
import com.xiaopeng.carcontrol.model.FunctionModel;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public abstract class BaseCarController<C extends CarEcuManager, T extends IBaseCallback> implements IBaseCarController<T> {
    protected static final boolean MOCK_HVAC = false;
    protected static final boolean MOCK_MSM = false;
    protected final CarBaseConfig mCarConfig;
    protected C mCarManager;
    protected DataSyncModel mDataSync;
    protected FunctionModel mFunctionModel;
    protected final boolean mIsMainProcess;
    protected final List<Integer> mPropertyIds;
    protected static final ExecutorService SINGLE_THREAD_POOL = Executors.newSingleThreadExecutor();
    public static boolean EXHIBITION_MODE = false;
    protected final Object mCallbackLock = new Object();
    protected final CopyOnWriteArrayList<T> mCallbacks = new CopyOnWriteArrayList<>();
    protected final ConcurrentHashMap<Integer, CarPropertyValue<?>> mCarPropertyMap = new ConcurrentHashMap<>();

    /* loaded from: classes.dex */
    public interface ThrowingConsumer<T, E extends Exception> {
        void accept(T t) throws Exception;
    }

    /* loaded from: classes.dex */
    public interface ThrowingFunction<T, R, E extends Exception> {
        R apply(T t) throws Exception;
    }

    protected void buildPropIdList(List<Integer> container, String key) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean dependOnXuiManager() {
        return false;
    }

    protected abstract void disconnect();

    protected CarEcuManager.CarEcuEventCallback getCarEventCallback() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: getContentProviderValueBySync */
    public void lambda$getContentProviderValue$0$BaseCarController(String key) {
    }

    protected abstract List<Integer> getRegisterPropertyIds();

    protected abstract void handleEventsUpdate(CarPropertyValue<?> value);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void initCarManager(Car carClient);

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract void initXuiManager();

    /* JADX INFO: Access modifiers changed from: protected */
    public BaseCarController() {
        CarBaseConfig carBaseConfig = CarBaseConfig.getInstance();
        this.mCarConfig = carBaseConfig;
        boolean z = !carBaseConfig.isSupportUnity3D() || App.isMainProcess();
        this.mIsMainProcess = z;
        this.mPropertyIds = getRegisterPropertyIds();
        if (z) {
            this.mDataSync = DataSyncModel.getInstance();
            this.mFunctionModel = FunctionModel.getInstance();
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.IBaseCarController
    public final void registerCallback(T callback) {
        if (callback != null) {
            this.mCallbacks.add(callback);
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.IBaseCarController
    public final void unregisterCallback(T callback) {
        this.mCallbacks.remove(callback);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void handleCarEventsUpdate(final CarPropertyValue<?> value) {
        this.mCarPropertyMap.put(Integer.valueOf(value.getPropertyId()), value);
        handleEventsUpdate(value);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int getIntProperty(int propertyId) throws Exception {
        return ((Integer) getValue(getCarProperty(propertyId))).intValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int[] getIntArrayProperty(int propertyId) throws Exception {
        return getIntArrayProperty(getCarProperty(propertyId));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final int[] getIntArrayProperty(CarPropertyValue<?> value) {
        Object[] objArr = (Object[]) getValue(value);
        if (objArr != null) {
            int[] iArr = new int[objArr.length];
            for (int i = 0; i < objArr.length; i++) {
                Object obj = objArr[i];
                if (obj instanceof Integer) {
                    iArr[i] = ((Integer) obj).intValue();
                }
            }
            return iArr;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final float getFloatProperty(int propertyId) throws Exception {
        return ((Float) getValue(getCarProperty(propertyId))).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final float[] getFloatArrayProperty(int propertyId) throws Exception {
        return getFloatArrayProperty(getCarProperty(propertyId));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final float[] getFloatArrayProperty(CarPropertyValue<?> value) {
        Object[] objArr = (Object[]) getValue(value);
        if (objArr != null) {
            float[] fArr = new float[objArr.length];
            for (int i = 0; i < objArr.length; i++) {
                Object obj = objArr[i];
                if (obj instanceof Float) {
                    fArr[i] = ((Float) obj).floatValue();
                }
            }
            return fArr;
        }
        return null;
    }

    private CarPropertyValue<?> getCarProperty(int propertyId) throws Exception {
        CarPropertyValue<?> carPropertyValue = this.mCarPropertyMap.get(Integer.valueOf(propertyId));
        if (carPropertyValue != null) {
            return carPropertyValue;
        }
        throw new Exception("Car property not found");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final <E> E getValue(CarPropertyValue<?> value) {
        return (E) value.getValue();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public final <E> E getContentProviderValue(final String key, E currentValue, E defaultVlaue) {
        if (currentValue != null) {
            return currentValue;
        }
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.-$$Lambda$BaseCarController$nlC3JU3R2VsTjoYu58MihF28bMo
            @Override // java.lang.Runnable
            public final void run() {
                BaseCarController.this.lambda$getContentProviderValue$0$BaseCarController(key);
            }
        });
        return defaultVlaue;
    }

    private List<Integer> getPropIdList(String... keys) {
        if (keys == null || keys.length == 0) {
            LogUtils.e(getClass().getSimpleName(), "getPropIdList error with keys=" + Arrays.toString(keys), false);
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (String str : keys) {
            if (TextUtils.isEmpty(str)) {
                LogUtils.d(getClass().getSimpleName(), "can not buildPropIdList with null key", false);
            } else {
                buildPropIdList(arrayList, str);
            }
        }
        return arrayList;
    }

    @Override // com.xiaopeng.carcontrol.carmanager.IBaseCarController
    public void registerBusiness(final String... keys) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.-$$Lambda$BaseCarController$dLQ2yO6ITb9MldtTovBSsSjKzeA
            @Override // java.lang.Runnable
            public final void run() {
                BaseCarController.this.lambda$registerBusiness$1$BaseCarController(keys);
            }
        });
    }

    public /* synthetic */ void lambda$registerBusiness$1$BaseCarController(final String[] keys) {
        if (this.mCarManager != null) {
            try {
                List<Integer> propIdList = getPropIdList(keys);
                this.mCarManager.unregisterPropCallback(propIdList, getCarEventCallback());
                this.mCarManager.registerPropCallback(propIdList, getCarEventCallback());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.carmanager.IBaseCarController
    public void unregisterBusiness(final String... keys) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.carmanager.-$$Lambda$BaseCarController$qBnR9q84_pLwZ0eQlLdf4yvMQNQ
            @Override // java.lang.Runnable
            public final void run() {
                BaseCarController.this.lambda$unregisterBusiness$2$BaseCarController(keys);
            }
        });
    }

    public /* synthetic */ void lambda$unregisterBusiness$2$BaseCarController(final String[] keys) {
        C c = this.mCarManager;
        if (c != null) {
            try {
                c.unregisterPropCallback(getPropIdList(keys), getCarEventCallback());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public <R> R getValue(ThrowingFunction<Void, R, Exception> getter, R defaultVal) {
        return (R) getValue(null, -1, getter, defaultVal);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public <R> R getValue(int propertyId, ThrowingFunction<Void, R, Exception> getter, R defaultVal) {
        return (R) getValue(Integer.class, propertyId, getter, defaultVal);
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected <R> R getValue(Class<?> type, int propertyId, ThrowingFunction<Void, R, Exception> getter, R defaultVal) {
        Object obj;
        try {
            try {
                if (type == null && propertyId < 0) {
                    throw new Exception("Car property not found");
                }
                if (Integer.class.equals(type)) {
                    obj = Integer.valueOf(getIntProperty(propertyId));
                } else if (Integer[].class.equals(type)) {
                    obj = getIntArrayProperty(propertyId);
                } else if (Float.class.equals(type)) {
                    obj = Float.valueOf(getFloatProperty(propertyId));
                } else if (!Float[].class.equals(type)) {
                    return null;
                } else {
                    obj = getFloatArrayProperty(propertyId);
                }
                return obj;
            } catch (Throwable unused) {
                return getter.apply(null);
            }
        } catch (Throwable th) {
            LogUtils.e(getClass().getSimpleName(), "getValue failed: " + th.getMessage(), false);
            return defaultVal;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setValue(ThrowingConsumer<Void, Exception> setter) {
        try {
            setter.accept(null);
        } catch (Throwable th) {
            LogUtils.e(getClass().getSimpleName(), "setValue failed: " + th.getMessage(), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public <R> void handleSignal(R result, BiConsumer<T, R> handler) {
        synchronized (this.mCallbackLock) {
            Iterator<T> it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                handler.accept(it.next(), result);
            }
        }
    }
}
