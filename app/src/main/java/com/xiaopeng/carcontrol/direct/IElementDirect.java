package com.xiaopeng.carcontrol.direct;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes2.dex */
public interface IElementDirect {
    boolean checkSupport(String url);

    boolean closePageDirect(Context context, String url);

    String convertUrl(String pageUrl);

    ElementDirectItem getElement(Uri uri);

    PageDirectItem getPage(Uri uri);

    boolean isElementDirect(Uri uri);

    boolean isPageDirect(Uri uri);

    boolean isPageOpen(Uri uri);

    boolean showElementItemView(Intent intent, String className, View rootView, OnPageDirectShowListener listener);

    boolean showElementItemViewForList(Intent intent, String className, RecyclerView rootView, int itmIndex, OnPageDirectShowListener listener);

    void showPageDirect(Context context, String url);

    boolean showSecondPageDirect(Intent intent, String[] supportSecondPage, OnPageDirectShowListener listener);

    boolean showSecondPageDirectForList(Intent intent, String[] supportSecondPage, RecyclerView rootView, int itmIndex, OnPageDirectShowListener listener);
}
