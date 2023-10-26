package com.xiaopeng.lludancemanager.adapter;

import androidx.collection.ArrayMap;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.lludancemanager.R;
import com.xiaopeng.lludancemanager.data.DanceFragmentPageAdapterData;
import com.xiaopeng.lludancemanager.view.AILluFragment;
import com.xiaopeng.lludancemanager.view.LluDanceMainFragment;
import com.xiaopeng.xui.widget.XTabsBar;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class LluDanceFragmentPagerAdapter extends FragmentPagerAdapter {
    public static final String FRAGMENT_AI_LLU_TITLE = "ai_llu";
    public static final String FRAGMENT_LLU_DANCE_TITLE = "llu_fragment";
    private static final String TAG = "LluDanceFragmentPagerAdapter";
    private ArrayList<DanceFragmentPageAdapterData> mAdapterDataList;
    private final ArrayMap<DanceFragmentPageAdapterData, Fragment> mFragmentCache;

    public LluDanceFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.mFragmentCache = new ArrayMap<>();
    }

    public LluDanceFragmentPagerAdapter(FragmentManager fm, ArrayList<DanceFragmentPageAdapterData> adapterDataSparseArray) {
        super(fm);
        this.mFragmentCache = new ArrayMap<>();
        this.mAdapterDataList = adapterDataSparseArray;
    }

    public void setAdapterDataList(ArrayList<DanceFragmentPageAdapterData> adapterDataList) {
        this.mAdapterDataList = adapterDataList;
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    public Fragment getItem(int position) {
        String str = TAG;
        LogUtils.d(str, "karl log about get item position = " + position);
        DanceFragmentPageAdapterData danceFragmentPageAdapterData = this.mAdapterDataList.get(position);
        Fragment fragment = this.mFragmentCache.get(danceFragmentPageAdapterData);
        LogUtils.d(str, "mFragmentCache = " + this.mFragmentCache);
        if (fragment == null) {
            Fragment createFragmentByData = FragmentFactory.createFragmentByData(danceFragmentPageAdapterData);
            this.mFragmentCache.put(danceFragmentPageAdapterData, createFragmentByData);
            return createFragmentByData;
        }
        return fragment;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.mAdapterDataList.size();
    }

    public void feedInTabData(XTabsBar tabsBar) {
        ArrayList<DanceFragmentPageAdapterData> arrayList = this.mAdapterDataList;
        if (arrayList != null) {
            Iterator<DanceFragmentPageAdapterData> it = arrayList.iterator();
            while (it.hasNext()) {
                FragmentFactory.feedFragmentTitleByData(it.next(), tabsBar);
            }
            return;
        }
        LogUtils.i(TAG, "feed in tab data , but adapter list is null");
    }

    /* loaded from: classes2.dex */
    private static class FragmentFactory {
        private FragmentFactory() {
        }

        public static void feedFragmentTitleByData(DanceFragmentPageAdapterData fragmentData, XTabsBar tabsBar) {
            String str = fragmentData.mFragmentTitle;
            str.hashCode();
            if (str.equals(LluDanceFragmentPagerAdapter.FRAGMENT_AI_LLU_TITLE)) {
                tabsBar.addTab(App.getInstance().getString(R.string.llu_dance_ai_llu_main_page_title));
            } else if (str.equals(LluDanceFragmentPagerAdapter.FRAGMENT_LLU_DANCE_TITLE)) {
                tabsBar.addTab(App.getInstance().getString(R.string.llu_dance_main_page_title));
            }
        }

        public static Fragment createFragmentByData(DanceFragmentPageAdapterData fragmentData) {
            String str = fragmentData.mFragmentTitle;
            str.hashCode();
            if (str.equals(LluDanceFragmentPagerAdapter.FRAGMENT_AI_LLU_TITLE)) {
                return new AILluFragment();
            }
            if (str.equals(LluDanceFragmentPagerAdapter.FRAGMENT_LLU_DANCE_TITLE)) {
                return new LluDanceMainFragment();
            }
            return null;
        }
    }
}
