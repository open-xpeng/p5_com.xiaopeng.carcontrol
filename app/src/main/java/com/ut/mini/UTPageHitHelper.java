package com.ut.mini;

import android.app.Activity;
import android.net.Uri;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.alibaba.mtl.log.c;
import com.alibaba.mtl.log.e.i;
import com.ut.mini.UTHitBuilders;
import com.ut.mini.base.UTMIVariables;
import com.xiaopeng.carcontrol.carmanager.controller.IScenarioController;
import com.xiaopeng.xvs.xid.sync.api.ISync;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/* loaded from: classes.dex */
public class UTPageHitHelper {
    private static UTPageHitHelper a = new UTPageHitHelper();
    private boolean N = false;
    private Map<String, String> z = new HashMap();
    private Map<String, UTPageEventObject> A = new HashMap();
    private String ah = null;
    private Map<String, String> B = new HashMap();
    private String ai = null;

    /* renamed from: a  reason: collision with other field name */
    private Queue<UTPageEventObject> f157a = new LinkedList();
    private Map<Object, String> C = new HashMap();

    /* loaded from: classes.dex */
    public static class UTPageEventObject {
        private Map<String, String> z = new HashMap();
        private long A = 0;
        private Uri a = null;
        private String aj = null;
        private String ak = null;

        /* renamed from: a  reason: collision with other field name */
        private UTPageStatus f158a = null;
        private boolean O = false;
        private boolean P = false;
        private boolean Q = false;
        private String al = null;

        public void setCacheKey(String str) {
            this.al = str;
        }

        public String getCacheKey() {
            return this.al;
        }

        public void resetPropertiesWithoutSkipFlagAndH5Flag() {
            this.z = new HashMap();
            this.A = 0L;
            this.a = null;
            this.aj = null;
            this.ak = null;
            UTPageStatus uTPageStatus = this.f158a;
            if (uTPageStatus == null || uTPageStatus != UTPageStatus.UT_H5_IN_WebView) {
                this.f158a = null;
            }
            this.O = false;
            this.Q = false;
        }

        public boolean isH5Called() {
            return this.Q;
        }

        public void setH5Called() {
            this.Q = true;
        }

        public void setToSkipPage() {
            this.P = true;
        }

        public boolean isSkipPage() {
            return this.P;
        }

        public void setPageAppearCalled() {
            this.O = true;
        }

        public boolean isPageAppearCalled() {
            return this.O;
        }

        public void setPageStatus(UTPageStatus uTPageStatus) {
            this.f158a = uTPageStatus;
        }

        public UTPageStatus getPageStatus() {
            return this.f158a;
        }

        public Map<String, String> getPageProperties() {
            return this.z;
        }

        public void setPageProperties(Map<String, String> map) {
            this.z = map;
        }

        public long getPageStayTimstamp() {
            return this.A;
        }

        public void setPageStayTimstamp(long j) {
            this.A = j;
        }

        public Uri getPageUrl() {
            return this.a;
        }

        public void setPageUrl(Uri uri) {
            this.a = uri;
        }

        public void setPageName(String str) {
            this.aj = str;
        }

        public String getPageName() {
            return this.aj;
        }

        public void setRefPage(String str) {
            this.ak = str;
        }

        public String getRefPage() {
            return this.ak;
        }
    }

    public static UTPageHitHelper getInstance() {
        return a;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized Map<String, String> c() {
        Map<String, String> map = this.B;
        if (map == null || map.size() <= 0) {
            return null;
        }
        HashMap hashMap = new HashMap();
        hashMap.putAll(this.B);
        this.B.clear();
        return hashMap;
    }

    synchronized void a(UTPageEventObject uTPageEventObject) {
        uTPageEventObject.resetPropertiesWithoutSkipFlagAndH5Flag();
        if (!this.f157a.contains(uTPageEventObject)) {
            this.f157a.add(uTPageEventObject);
        }
        if (this.f157a.size() > 200) {
            for (int i = 0; i < 100; i++) {
                UTPageEventObject poll = this.f157a.poll();
                if (poll != null && this.A.containsKey(poll.getCacheKey())) {
                    this.A.remove(poll.getCacheKey());
                }
            }
        }
    }

    @Deprecated
    public synchronized void turnOffAutoPageTrack() {
        this.N = true;
    }

    public String getCurrentPageName() {
        return this.ai;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void pageAppearByAuto(Activity activity) {
        if (this.N) {
            return;
        }
        pageAppear(activity);
    }

    /* renamed from: a  reason: collision with other method in class */
    private String m82a(Object obj) {
        String simpleName;
        if (obj instanceof String) {
            simpleName = (String) obj;
        } else {
            simpleName = obj.getClass().getSimpleName();
        }
        return simpleName + obj.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a  reason: collision with other method in class */
    public synchronized boolean m85a(Object obj) {
        if (obj != null) {
            UTPageEventObject a2 = a(obj);
            if (a2.getPageStatus() != null) {
                if (a2.getPageStatus() == UTPageStatus.UT_H5_IN_WebView) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a  reason: collision with other method in class */
    public synchronized void m84a(Object obj) {
        if (obj != null) {
            UTPageEventObject a2 = a(obj);
            if (a2.getPageStatus() != null) {
                a2.setH5Called();
            }
        }
    }

    private synchronized UTPageEventObject a(Object obj) {
        String m82a = m82a(obj);
        if (this.A.containsKey(m82a)) {
            return this.A.get(m82a);
        }
        UTPageEventObject uTPageEventObject = new UTPageEventObject();
        this.A.put(m82a, uTPageEventObject);
        uTPageEventObject.setCacheKey(m82a);
        return uTPageEventObject;
    }

    private synchronized void a(String str, UTPageEventObject uTPageEventObject) {
        this.A.put(str, uTPageEventObject);
    }

    private synchronized void b(UTPageEventObject uTPageEventObject) {
        if (this.A.containsKey(uTPageEventObject.getCacheKey())) {
            this.A.remove(uTPageEventObject.getCacheKey());
        }
    }

    /* renamed from: b  reason: collision with other method in class */
    private synchronized void m83b(Object obj) {
        String m82a = m82a(obj);
        if (this.A.containsKey(m82a)) {
            this.A.remove(m82a);
        }
    }

    @Deprecated
    public synchronized void pageAppear(Object obj) {
        a(obj, null, false);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void a(Object obj, String str, boolean z) {
        if (obj != null) {
            String m82a = m82a(obj);
            if (m82a != null && m82a.equals(this.ah)) {
                return;
            }
            if (this.ah != null) {
                i.a("lost 2001", "Last page requires leave(" + this.ah + ").");
            }
            UTPageEventObject a2 = a(obj);
            if (!z && a2.isSkipPage()) {
                i.a("skip page[pageAppear]", "page name:" + obj.getClass().getSimpleName());
                return;
            }
            String h5Url = UTMIVariables.getInstance().getH5Url();
            if (h5Url != null) {
                this.z.put("spm", Uri.parse(h5Url).getQueryParameter("spm"));
                UTMIVariables.getInstance().setH5Url(null);
            }
            String b = b(obj);
            if (TextUtils.isEmpty(str)) {
                str = b;
            }
            if (!TextUtils.isEmpty(a2.getPageName())) {
                str = a2.getPageName();
            }
            this.ai = str;
            a2.setPageName(str);
            a2.setPageStayTimstamp(SystemClock.elapsedRealtime());
            a2.setRefPage(UTMIVariables.getInstance().getRefPage());
            a2.setPageAppearCalled();
            if (this.B != null) {
                Map<String, String> pageProperties = a2.getPageProperties();
                if (pageProperties == null) {
                    a2.setPageProperties(this.B);
                } else {
                    HashMap hashMap = new HashMap();
                    hashMap.putAll(pageProperties);
                    hashMap.putAll(this.B);
                    a2.setPageProperties(hashMap);
                }
            }
            this.B = null;
            this.ah = m82a(obj);
            b(a2);
            a(m82a(obj), a2);
        } else {
            i.a("pageAppear", "The page object should not be null");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void pageAppear(Object obj, String str) {
        a(obj, str, false);
    }

    @Deprecated
    public synchronized void updatePageProperties(Map<String, String> map) {
        if (map != null) {
            this.z.putAll(map);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void updatePageProperties(Object obj, Map<String, String> map) {
        if (obj != null && map != null) {
            if (map.size() != 0) {
                HashMap hashMap = new HashMap();
                hashMap.putAll(map);
                UTPageEventObject a2 = a(obj);
                Map<String, String> pageProperties = a2.getPageProperties();
                if (pageProperties == null) {
                    a2.setPageProperties(hashMap);
                } else {
                    HashMap hashMap2 = new HashMap();
                    hashMap2.putAll(pageProperties);
                    hashMap2.putAll(hashMap);
                    a2.setPageProperties(hashMap2);
                }
                return;
            }
        }
        i.a("updatePageProperties", "failed to update project, parameters should not be null and the map should not be empty");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void updatePageName(Object obj, String str) {
        if (obj != null) {
            if (!TextUtils.isEmpty(str)) {
                a(obj).setPageName(str);
                this.ai = str;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void updatePageUrl(Object obj, Uri uri) {
        if (obj == null || uri == null) {
            return;
        }
        Log.i("url", "url" + uri.toString());
        a(obj).setPageUrl(uri);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void updatePageStatus(Object obj, UTPageStatus uTPageStatus) {
        if (obj == null || uTPageStatus == null) {
            return;
        }
        a(obj).setPageStatus(uTPageStatus);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void updateNextPageProperties(Map<String, String> map) {
        if (map != null) {
            HashMap hashMap = new HashMap();
            hashMap.putAll(map);
            this.B = hashMap;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void pageDisAppearByAuto(Activity activity) {
        if (this.N) {
            return;
        }
        pageDisAppear(activity);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public synchronized void skipPage(Object obj) {
        if (obj == null) {
            return;
        }
        a(obj).setToSkipPage();
    }

    @Deprecated
    public synchronized void pageDisAppear(Object obj) {
        if (obj != null) {
            if (this.ah == null) {
                return;
            }
            UTPageEventObject a2 = a(obj);
            if (a2.isPageAppearCalled()) {
                if (a2.getPageStatus() != null && UTPageStatus.UT_H5_IN_WebView == a2.getPageStatus() && a2.isH5Called()) {
                    a(a2);
                    return;
                }
                long elapsedRealtime = SystemClock.elapsedRealtime() - a2.getPageStayTimstamp();
                if (a2.getPageUrl() == null && (obj instanceof Activity) && ((Activity) obj).getIntent() != null) {
                    a2.setPageUrl(((Activity) obj).getIntent().getData());
                }
                String pageName = a2.getPageName();
                String refPage = a2.getRefPage();
                refPage = (refPage == null || refPage.length() == 0) ? "-" : "-";
                Map<String, String> map = this.z;
                if (map == null) {
                    map = new HashMap<>();
                }
                if (a2.getPageProperties() != null) {
                    map.putAll(a2.getPageProperties());
                }
                if (obj instanceof IUTPageTrack) {
                    IUTPageTrack iUTPageTrack = (IUTPageTrack) obj;
                    String referPage = iUTPageTrack.getReferPage();
                    if (!TextUtils.isEmpty(referPage)) {
                        refPage = referPage;
                    }
                    Map<String, String> pageProperties = iUTPageTrack.getPageProperties();
                    if (pageProperties != null && pageProperties.size() > 0) {
                        this.z.putAll(pageProperties);
                        map = this.z;
                    }
                    String pageName2 = iUTPageTrack.getPageName();
                    if (!TextUtils.isEmpty(pageName2)) {
                        pageName = pageName2;
                    }
                }
                Uri pageUrl = a2.getPageUrl();
                if (pageUrl != null) {
                    HashMap hashMap = new HashMap();
                    String queryParameter = pageUrl.getQueryParameter("spm");
                    if (TextUtils.isEmpty(queryParameter)) {
                        try {
                            pageUrl = Uri.parse(URLDecoder.decode(pageUrl.toString(), "UTF-8"));
                            queryParameter = pageUrl.getQueryParameter("spm");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!TextUtils.isEmpty(queryParameter)) {
                        boolean z = false;
                        if (this.C.containsKey(obj) && queryParameter.equals(this.C.get(obj))) {
                            z = true;
                        }
                        if (!z) {
                            hashMap.put("spm", queryParameter);
                            this.C.put(obj, queryParameter);
                        }
                    }
                    String queryParameter2 = pageUrl.getQueryParameter("scm");
                    if (!TextUtils.isEmpty(queryParameter2)) {
                        hashMap.put("scm", queryParameter2);
                    }
                    String a3 = a(pageUrl);
                    if (!TextUtils.isEmpty(a3)) {
                        c.a().e(a3);
                    }
                    if (hashMap.size() > 0) {
                        map.putAll(hashMap);
                    }
                }
                UTHitBuilders.UTPageHitBuilder uTPageHitBuilder = new UTHitBuilders.UTPageHitBuilder(pageName);
                uTPageHitBuilder.setReferPage(refPage).setDurationOnPage(elapsedRealtime).setProperties(map);
                UTMIVariables.getInstance().setRefPage(pageName);
                UTTracker defaultTracker = UTAnalytics.getInstance().getDefaultTracker();
                if (defaultTracker != null) {
                    defaultTracker.send(uTPageHitBuilder.build());
                } else {
                    i.a("Record page event error", "Fatal Error,must call setRequestAuthentication method first.");
                }
            } else {
                i.a("UT", "Please call pageAppear first(" + b(obj) + ").");
            }
            this.z = new HashMap();
            if (a2.isSkipPage()) {
                a(a2);
            } else if (a2.getPageStatus() != null && UTPageStatus.UT_H5_IN_WebView == a2.getPageStatus()) {
                a(a2);
            } else {
                m83b(obj);
            }
            this.ah = null;
            this.ai = null;
        } else {
            i.a("pageDisAppear", "The page object should not be null");
        }
    }

    private static String a(Uri uri) {
        List<String> queryParameters;
        if (uri == null || (queryParameters = uri.getQueryParameters("ttid")) == null) {
            return null;
        }
        for (String str : queryParameters) {
            if (!str.contains(ISync.EXTRA_SYNC_GROUP_KEY_SEPARATOR) && !str.contains("%40")) {
                return str;
            }
        }
        return null;
    }

    private static String b(Object obj) {
        String simpleName = obj.getClass().getSimpleName();
        return (simpleName == null || !simpleName.toLowerCase().endsWith(IScenarioController.SPACE_CAPSULE_SOURCE_ACTIVITY)) ? simpleName : simpleName.substring(0, simpleName.length() - 8);
    }
}
