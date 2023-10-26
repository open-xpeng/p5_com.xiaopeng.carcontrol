package com.alibaba.sdk.android.httpdns;

import com.alibaba.sdk.android.httpdns.probe.IPProbeItem;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface HttpDnsService {
    String getIpByHostAsync(String str);

    String[] getIpsByHostAsync(String str);

    String getSessionId();

    void setAuthCurrentTime(long j);

    void setCachedIPEnabled(boolean z);

    void setDegradationFilter(DegradationFilter degradationFilter);

    void setExpiredIPEnabled(boolean z);

    void setHTTPSRequestEnabled(boolean z);

    void setIPProbeList(List<IPProbeItem> list);

    void setLogEnabled(boolean z);

    void setLogger(ILogger iLogger);

    void setPreResolveAfterNetworkChanged(boolean z);

    void setPreResolveHosts(ArrayList<String> arrayList);

    void setTimeoutInterval(int i);
}
