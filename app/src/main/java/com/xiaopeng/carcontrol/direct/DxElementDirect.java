package com.xiaopeng.carcontrol.direct;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.carcontrol.direct.action.PageAction;
import com.xiaopeng.carcontrol.direct.support.SupportCheckAction;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import java.util.Arrays;
import java.util.HashMap;
import org.eclipse.paho.client.mqttv3.MqttTopic;

/* loaded from: classes2.dex */
public class DxElementDirect implements IElementDirect {
    private static final String TAG = "DxElementDirect";
    private volatile boolean mIsActivityResumed;
    private final HashMap<String, ElementDirectItem> mItemHashMap;
    private final HashMap<String, PageDirectItem> mPageHashMap;
    private final HashMap<String, String> mPathMap;

    @Override // com.xiaopeng.carcontrol.direct.IElementDirect
    public boolean closePageDirect(Context context, String url) {
        return false;
    }

    @Override // com.xiaopeng.carcontrol.direct.IElementDirect
    public boolean isPageOpen(Uri uri) {
        return false;
    }

    /* loaded from: classes2.dex */
    private static class SingletonHolder {
        private static final DxElementDirect INSTANCE = new DxElementDirect();

        private SingletonHolder() {
        }
    }

    public static DxElementDirect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private DxElementDirect() {
        this.mIsActivityResumed = false;
        this.mPageHashMap = DirectData.loadPageData();
        this.mItemHashMap = DirectData.loadItemData();
        this.mPathMap = DirectData.loadPathMapData();
    }

    public void setMainActivityResumed(boolean isResumed) {
        this.mIsActivityResumed = isResumed;
    }

    public boolean isMainActivityResumed() {
        return this.mIsActivityResumed;
    }

    public boolean isPageDirect(Intent intent) {
        return isPageDirect(intent.getData());
    }

    @Override // com.xiaopeng.carcontrol.direct.IElementDirect
    public boolean isPageDirect(Uri uri) {
        if (uri != null) {
            return DirectData.SCHEME.equals(uri.getScheme()) && "carcontrol".equals(uri.getAuthority());
        }
        return false;
    }

    private boolean isElementDirect(Intent intent) {
        return isPageDirect(intent) && isElementDirect(intent.getData());
    }

    @Override // com.xiaopeng.carcontrol.direct.IElementDirect
    public String convertUrl(String url) {
        Uri parse = Uri.parse(url);
        String path = parse.getPath();
        log("convertUrl url: " + url + ", path: " + path);
        String queryParameter = parse.getQueryParameter("position");
        if (queryParameter != null) {
            String str = this.mPathMap.get(queryParameter);
            if (TextUtils.isEmpty(str)) {
                return url;
            }
            log("convertUrl newPath: " + str);
            String replace = url.replace(path, str);
            log("convertUrl new url: " + replace);
            return replace;
        } else if (path != null) {
            LogUtils.i(TAG, "getPath: " + parse.getPath());
            LogUtils.i(TAG, "getLastPathSegment: " + parse.getLastPathSegment());
            String str2 = this.mPathMap.get(path);
            if (TextUtils.isEmpty(str2)) {
                return url;
            }
            log("convertUrl newPath: " + str2);
            String replace2 = url.replace(path, str2);
            log("convertUrl new url: " + replace2);
            return replace2;
        } else {
            return url;
        }
    }

    @Override // com.xiaopeng.carcontrol.direct.IElementDirect
    public boolean isElementDirect(Uri uri) {
        if (uri != null) {
            return !TextUtils.isEmpty(uri.getQueryParameter("position"));
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.direct.IElementDirect
    public PageDirectItem getPage(Uri uri) {
        if (uri != null) {
            return this.mPageHashMap.get(uri.getPath());
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.direct.IElementDirect
    public ElementDirectItem getElement(Uri uri) {
        if (uri != null) {
            return this.mItemHashMap.get(uri.getPath() + MqttTopic.TOPIC_LEVEL_SEPARATOR + uri.getQueryParameter("position"));
        }
        return null;
    }

    @Override // com.xiaopeng.carcontrol.direct.IElementDirect
    public boolean showSecondPageDirect(Intent intent, String[] supportSecondPage, OnPageDirectShowListener showListener) {
        PageDirectItem page;
        if (supportSecondPage != null && (page = getPage(intent.getData())) != null && page.getData() != null) {
            String secondName = page.getSecondName();
            if (TextUtils.isEmpty(secondName)) {
                return false;
            }
            log("showSecondPageDirect name: " + secondName + ", supportSecondPage: " + Arrays.toString(supportSecondPage));
            for (String str : supportSecondPage) {
                if (secondName.equals(str)) {
                    log("showSecondPageDirect name: " + secondName);
                    if (showListener != null) {
                        showListener.onPageDirectShow(secondName);
                        return true;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.direct.IElementDirect
    public boolean showSecondPageDirectForList(Intent intent, String[] supportSecondPage, RecyclerView rootView, int itmIndex, OnPageDirectShowListener showListener) {
        PageDirectItem page;
        if (supportSecondPage != null && (page = getPage(intent.getData())) != null && page.getData() != null) {
            String secondName = page.getSecondName();
            if (!TextUtils.isEmpty(secondName) && itmIndex >= 0) {
                rootView.scrollToPosition(itmIndex);
                for (String str : supportSecondPage) {
                    if (secondName.equals(str)) {
                        log("showSecondPageDirect name: " + secondName);
                        if (showListener != null) {
                            showListener.onPageDirectShow(secondName);
                            return true;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.direct.IElementDirect
    public boolean showElementItemView(Intent intent, String className, View rootView, OnPageDirectShowListener showListener) {
        if (isElementDirect(intent)) {
            PageDirectItem page = getPage(intent.getData());
            if (page == null || page.getData() == null || className.equals(page.getData().toString())) {
                ElementDirectItem element = getElement(intent.getData());
                log("showElementItemView 1 : " + (element == null ? " item is null " : element.toString()) + (rootView == null ? " rootView is null " : rootView));
                if (element != null && rootView != null) {
                    if (showListener != null) {
                        showListener.onElementDirectShow(element.getKey());
                    }
                    if (element.getParentId() > 0) {
                        View findViewById = rootView.findViewById(element.getParentId());
                        if (findViewById != null) {
                            showElementItemView(findViewById.findViewById(element.getId()));
                            return true;
                        }
                    } else {
                        showElementItemView(rootView.findViewById(element.getId()));
                        return true;
                    }
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Override // com.xiaopeng.carcontrol.direct.IElementDirect
    public boolean showElementItemViewForList(Intent intent, String className, final RecyclerView rootView, final int itmIndex, OnPageDirectShowListener showListener) {
        if (isElementDirect(intent)) {
            PageDirectItem page = getPage(intent.getData());
            if (page == null || page.getData() == null || className.equals(page.getData().toString())) {
                ElementDirectItem element = getElement(intent.getData());
                log("showElementItemView 1 : " + (element == null ? " item is null " : element.toString()) + (rootView == null ? " rootView is null " : rootView) + ", itemIdx: " + itmIndex);
                if (element != null && rootView != null) {
                    if (itmIndex >= 0) {
                        rootView.scrollToPosition(itmIndex);
                        ThreadUtils.runOnMainThreadDelay(new Runnable() { // from class: com.xiaopeng.carcontrol.direct.-$$Lambda$DxElementDirect$apIXm4nPKX7jpvxbNv9maTCtn7Q
                            @Override // java.lang.Runnable
                            public final void run() {
                                DxElementDirect.this.lambda$showElementItemViewForList$0$DxElementDirect(rootView, itmIndex);
                            }
                        }, 50L);
                        return true;
                    }
                    log("showElementItemView failed view is null");
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public /* synthetic */ void lambda$showElementItemViewForList$0$DxElementDirect(final RecyclerView rootView, final int itmIndex) {
        RecyclerView.ViewHolder findViewHolderForAdapterPosition = rootView.findViewHolderForAdapterPosition(itmIndex);
        if (findViewHolderForAdapterPosition != null) {
            showElementItemView(findViewHolderForAdapterPosition.itemView);
        }
    }

    public void showElementItemView(final View view) {
        log("showElementItemView 2 : " + (view == null ? " view is null " : " "));
        if (view != null) {
            view.post(new Runnable() { // from class: com.xiaopeng.carcontrol.direct.-$$Lambda$DxElementDirect$7Q-kibSOMDW27XAF_7lPPRtWKg8
                @Override // java.lang.Runnable
                public final void run() {
                    VuiFloatingLayerManager.show(view, 1);
                }
            });
        }
    }

    @Override // com.xiaopeng.carcontrol.direct.IElementDirect
    public void showPageDirect(Context context, String url) {
        Uri parse;
        if (url == null || !isPageDirect(Uri.parse(url)) || (parse = Uri.parse(url)) == null) {
            return;
        }
        PageDirectItem pageDirectItem = this.mPageHashMap.get(parse.getPath());
        if (pageDirectItem != null) {
            log("showPageDirect " + pageDirectItem.toString());
            PageAction pageAction = pageDirectItem.getPageAction();
            if (pageAction != null) {
                pageAction.doAction(context, parse);
            }
        }
    }

    @Override // com.xiaopeng.carcontrol.direct.IElementDirect
    public boolean checkSupport(String url) {
        if (url != null) {
            Uri parse = Uri.parse(url);
            if (isElementDirect(parse)) {
                ElementDirectItem element = getElement(parse);
                if (element != null) {
                    SupportCheckAction supportAction = element.getSupportAction();
                    return supportAction == null || supportAction.checkSupport();
                }
                return false;
            }
            PageDirectItem page = getPage(parse);
            if (page != null) {
                SupportCheckAction supportAction2 = page.getSupportAction();
                return supportAction2 == null || supportAction2.checkSupport();
            }
        }
        LogUtils.w(TAG, "Unsupported url: " + url, false);
        return false;
    }

    private void log(String msg) {
        LogUtils.i(TAG, "element direct :" + msg);
    }
}
