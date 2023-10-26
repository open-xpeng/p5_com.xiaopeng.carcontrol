package com.alibaba.sdk.android.oss.internal;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.OSSLog;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;

/* loaded from: classes.dex */
public class OSSRetryHandler {
    private int maxRetryCount = 2;

    public OSSRetryHandler(int i) {
        setMaxRetryCount(i);
    }

    public void setMaxRetryCount(int i) {
        this.maxRetryCount = i;
    }

    public OSSRetryType shouldRetry(Exception exc, int i) {
        if (i >= this.maxRetryCount) {
            return OSSRetryType.OSSRetryTypeShouldNotRetry;
        }
        if (exc instanceof ClientException) {
            if (((ClientException) exc).isCanceledException().booleanValue()) {
                return OSSRetryType.OSSRetryTypeShouldNotRetry;
            }
            Exception exc2 = (Exception) exc.getCause();
            if ((exc2 instanceof InterruptedIOException) && !(exc2 instanceof SocketTimeoutException)) {
                OSSLog.logError("[shouldRetry] - is interrupted!");
                return OSSRetryType.OSSRetryTypeShouldNotRetry;
            } else if (exc2 instanceof IllegalArgumentException) {
                return OSSRetryType.OSSRetryTypeShouldNotRetry;
            } else {
                OSSLog.logDebug("shouldRetry - " + exc.toString());
                exc.getCause().printStackTrace();
                return OSSRetryType.OSSRetryTypeShouldRetry;
            }
        } else if (exc instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) exc;
            if (serviceException.getErrorCode() != null && serviceException.getErrorCode().equalsIgnoreCase("RequestTimeTooSkewed")) {
                return OSSRetryType.OSSRetryTypeShouldFixedTimeSkewedAndRetry;
            }
            if (serviceException.getStatusCode() >= 500) {
                return OSSRetryType.OSSRetryTypeShouldRetry;
            }
            return OSSRetryType.OSSRetryTypeShouldNotRetry;
        } else {
            return OSSRetryType.OSSRetryTypeShouldNotRetry;
        }
    }

    /* renamed from: com.alibaba.sdk.android.oss.internal.OSSRetryHandler$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$alibaba$sdk$android$oss$internal$OSSRetryType;

        static {
            int[] iArr = new int[OSSRetryType.values().length];
            $SwitchMap$com$alibaba$sdk$android$oss$internal$OSSRetryType = iArr;
            try {
                iArr[OSSRetryType.OSSRetryTypeShouldRetry.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public long timeInterval(int i, OSSRetryType oSSRetryType) {
        if (AnonymousClass1.$SwitchMap$com$alibaba$sdk$android$oss$internal$OSSRetryType[oSSRetryType.ordinal()] != 1) {
            return 0L;
        }
        return ((long) Math.pow(2.0d, i)) * 200;
    }
}
