package com.xiaopeng.xui.widget.pageindicator;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.widget.XLinearLayout;

/* loaded from: classes2.dex */
public abstract class XViewPagerIndicatorAbs extends XLinearLayout {
    private DataSetObserver mDataSetObserver;
    private ViewPager.OnAdapterChangeListener mOnAdapterChangeListener;
    private ViewPager.SimpleOnPageChangeListener mOnPageChangeListener;
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    public abstract void populateData(int i);

    public abstract void select(int i);

    public XViewPagerIndicatorAbs(Context context) {
        this(context, null);
    }

    public XViewPagerIndicatorAbs(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.style.XPagerIndicator);
    }

    public XViewPagerIndicatorAbs(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.XPagerIndicator);
    }

    public XViewPagerIndicatorAbs(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDataSetObserver = new DataSetObserver() { // from class: com.xiaopeng.xui.widget.pageindicator.XViewPagerIndicatorAbs.1
            @Override // android.database.DataSetObserver
            public void onChanged() {
                super.onChanged();
                XViewPagerIndicatorAbs xViewPagerIndicatorAbs = XViewPagerIndicatorAbs.this;
                xViewPagerIndicatorAbs.populateData(xViewPagerIndicatorAbs.mPagerAdapter != null ? XViewPagerIndicatorAbs.this.mPagerAdapter.getCount() : 0);
            }
        };
        this.mOnAdapterChangeListener = new ViewPager.OnAdapterChangeListener() { // from class: com.xiaopeng.xui.widget.pageindicator.XViewPagerIndicatorAbs.2
            @Override // androidx.viewpager.widget.ViewPager.OnAdapterChangeListener
            public void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
                XViewPagerIndicatorAbs.this.setAdapter(pagerAdapter2);
            }
        };
        this.mOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() { // from class: com.xiaopeng.xui.widget.pageindicator.XViewPagerIndicatorAbs.3
            @Override // androidx.viewpager.widget.ViewPager.SimpleOnPageChangeListener, androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i3) {
                XViewPagerIndicatorAbs.this.select(i3);
            }
        };
        init();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void init() {
        setOrientation(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setAdapter(PagerAdapter pagerAdapter) {
        if (pagerAdapter == null) {
            return;
        }
        this.mPagerAdapter = pagerAdapter;
        pagerAdapter.registerDataSetObserver(this.mDataSetObserver);
        populateData(this.mPagerAdapter.getCount());
    }

    public void setupWithViewPager(ViewPager viewPager) {
        if (viewPager == null) {
            return;
        }
        this.mViewPager = viewPager;
        setAdapter(viewPager.getAdapter());
        int currentItem = viewPager.getCurrentItem();
        if (currentItem > -1) {
            select(currentItem);
        }
        viewPager.removeOnAdapterChangeListener(this.mOnAdapterChangeListener);
        viewPager.addOnAdapterChangeListener(this.mOnAdapterChangeListener);
        viewPager.removeOnPageChangeListener(this.mOnPageChangeListener);
        viewPager.addOnPageChangeListener(this.mOnPageChangeListener);
    }

    public void clear() {
        ViewPager viewPager = this.mViewPager;
        if (viewPager != null) {
            viewPager.removeOnAdapterChangeListener(this.mOnAdapterChangeListener);
            this.mViewPager.removeOnPageChangeListener(this.mOnPageChangeListener);
            this.mViewPager = null;
        }
        PagerAdapter pagerAdapter = this.mPagerAdapter;
        if (pagerAdapter != null) {
            try {
                pagerAdapter.unregisterDataSetObserver(this.mDataSetObserver);
            } catch (Exception unused) {
            }
            this.mPagerAdapter = null;
        }
    }
}
