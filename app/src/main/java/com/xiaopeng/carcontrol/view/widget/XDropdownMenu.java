package com.xiaopeng.carcontrol.view.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.theme.XThemeManager;
import com.xiaopeng.xui.utils.XuiUtils;
import com.xiaopeng.xui.widget.XTextView;

/* loaded from: classes2.dex */
public class XDropdownMenu extends XTextView implements AdapterView.OnItemClickListener, PopupWindow.OnDismissListener {
    private static final String TAG = "XDropdownMenu";
    private static final long TIME_OUT = 3000;
    private MyAdapter mAdapter;
    private final Runnable mAutoCloseTask;
    private int mCurrentSelection;
    private Handler mHandler;
    private final int mHoriOffset;
    private XTextView mListTextView;
    private ListView mListView;
    private OnItemSelectedListener mOnItemClickListener;
    private PopupWindow mPopupWindow;
    private final int mVerticalOffset;

    /* loaded from: classes2.dex */
    public interface OnItemSelectedListener {
        boolean onInterceptItemSelected(int position, String title);

        void onItemSelected(int position, String title);
    }

    public XDropdownMenu(Context context) {
        this(context, null);
    }

    public XDropdownMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XDropdownMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, 0);
        this.mAutoCloseTask = new Runnable() { // from class: com.xiaopeng.carcontrol.view.widget.-$$Lambda$XDropdownMenu$maEGibl5lzXSaL4dvQkhVTsL3O0
            @Override // java.lang.Runnable
            public final void run() {
                XDropdownMenu.this.dismiss();
            }
        };
        this.mVerticalOffset = getResources().getDimensionPixelOffset(R.dimen.x_dropdown_menu_inset_vertical);
        this.mHoriOffset = getResources().getDimensionPixelOffset(R.dimen.x_dropdown_menu_inset_horizontal);
        this.mCurrentSelection = -1;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray obtainAttributes = getResources().obtainAttributes(attrs, R.styleable.XDropdownMenu);
        CharSequence[] textArray = obtainAttributes.getTextArray(R.styleable.XDropdownMenu_edmDropdownEntries);
        int integer = obtainAttributes.getInteger(R.styleable.XDropdownMenu_edmDropdownSelection, this.mCurrentSelection);
        this.mAdapter = new MyAdapter(getContext(), R.layout.x_dropdown_menu_item, R.id.x_drop_down_tv);
        createPopupWindow();
        setEntries(textArray);
        setSelection(integer);
        setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.widget.XDropdownMenu.1
            @Override // android.view.View.OnClickListener
            public void onClick(View v) {
                if (XDropdownMenu.this.mPopupWindow == null) {
                    return;
                }
                if (XDropdownMenu.this.mPopupWindow.isShowing()) {
                    XDropdownMenu.this.dismiss();
                } else {
                    XDropdownMenu.this.show();
                }
            }
        });
        obtainAttributes.recycle();
    }

    private void createPopupWindow() {
        this.mPopupWindow = new PopupWindow(getContext());
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.x_dropdown_menu_list, (ViewGroup) null);
        this.mPopupWindow.setContentView(inflate);
        ListView listView = (ListView) inflate.findViewById(R.id.list_view);
        this.mListView = listView;
        listView.setAdapter((ListAdapter) this.mAdapter);
        this.mListView.setOnItemClickListener(this);
        this.mPopupWindow.setBackgroundDrawable(new ColorDrawable(0));
        this.mPopupWindow.setOutsideTouchable(true);
        this.mPopupWindow.setClippingEnabled(false);
        this.mPopupWindow.setOnDismissListener(this);
        initListTextView();
    }

    private void initListTextView() {
        XTextView xTextView = (XTextView) this.mPopupWindow.getContentView().findViewById(R.id.list_view_anchor);
        this.mListTextView = xTextView;
        xTextView.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.widget.-$$Lambda$XDropdownMenu$nPFcIrTYWOKbe51OtHcE8JJtuf4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                XDropdownMenu.this.lambda$initListTextView$0$XDropdownMenu(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListTextView$0$XDropdownMenu(View v) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void show() {
        this.mPopupWindow.setWidth(getWidth() - (this.mHoriOffset * 2));
        int[] iArr = new int[2];
        getLocationOnScreen(iArr);
        if (iArr[1] + getHeight() + this.mPopupWindow.getHeight() + this.mVerticalOffset > getContext().getResources().getDisplayMetrics().heightPixels) {
            getHeight();
            this.mPopupWindow.getHeight();
        }
        setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, ResUtils.getDrawable(R.drawable.ic_small_upper), (Drawable) null);
        this.mPopupWindow.showAsDropDown(this, this.mHoriOffset, -getHeight());
        startAutoCloseTask(TIME_OUT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismiss() {
        PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow != null && popupWindow.isShowing()) {
            this.mPopupWindow.dismiss();
        }
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mAutoCloseTask);
        }
    }

    final void startAutoCloseTask(long delay) {
        LogUtils.d(TAG, "dismiss menu: delay=" + delay, false);
        if (this.mHandler == null) {
            this.mHandler = new Handler();
        }
        this.mHandler.removeCallbacks(this.mAutoCloseTask);
        this.mHandler.postDelayed(this.mAutoCloseTask, delay);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String titleWithIndex = getTitleWithIndex(position);
        OnItemSelectedListener onItemSelectedListener = this.mOnItemClickListener;
        if (onItemSelectedListener != null && !onItemSelectedListener.onInterceptItemSelected(position, titleWithIndex)) {
            this.mOnItemClickListener.onItemSelected(position, titleWithIndex);
            setSelection(position);
        }
        dismiss();
    }

    public void setSelection(int position) {
        this.mCurrentSelection = position;
        if (position >= 0 && position < this.mAdapter.getCount()) {
            String titleWithIndex = getTitleWithIndex(position);
            setText(titleWithIndex);
            if (this.mListTextView == null) {
                initListTextView();
            }
            this.mListTextView.setText(titleWithIndex);
            this.mListView.setSelection(position);
            this.mAdapter.setSelectedPosition(position);
            this.mAdapter.notifyDataSetChanged();
            return;
        }
        setText(R.string.drive_mode_choose);
        if (this.mListTextView == null) {
            initListTextView();
        }
        this.mListTextView.setText(R.string.drive_mode_choose);
    }

    public int getSelection() {
        return this.mCurrentSelection;
    }

    public String getTitleWithIndex(int index) {
        return (index < 0 || index >= this.mAdapter.getCount()) ? "" : this.mAdapter.getItem(index);
    }

    public String getSelectionTitle() {
        return getTitleWithIndex(getSelection());
    }

    public void setEntries(String[] array) {
        this.mAdapter.clear();
        if (array != null && array.length > 0) {
            this.mAdapter.addAll(array);
            setSelection(this.mCurrentSelection);
        }
        setDropdownHeight(this.mAdapter.getCount());
    }

    public void setEntries(CharSequence[] array) {
        this.mAdapter.clear();
        if (array != null && array.length > 0) {
            for (CharSequence charSequence : array) {
                this.mAdapter.add(charSequence.toString());
            }
            setSelection(this.mCurrentSelection);
        }
        setDropdownHeight(this.mAdapter.getCount());
    }

    private void setDropdownHeight(int itemSize) {
        int dip2px;
        if (itemSize > 5) {
            dip2px = XuiUtils.dip2px(408.0f);
        } else {
            dip2px = itemSize > 0 ? XuiUtils.dip2px(((itemSize + 1) * 80) + ((itemSize - 1) * 2)) + ResUtils.getDimensionPixelSize(R.dimen.x_dropdown_menu_inset_vertical) : 0;
        }
        PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow != null) {
            popupWindow.setHeight(dip2px);
        }
    }

    public void setOnItemClickListener(OnItemSelectedListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XTextView, android.widget.TextView, android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (XThemeManager.isThemeChanged(newConfig)) {
            setListViewTheme();
        }
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mAutoCloseTask);
        }
        setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, ResUtils.getDrawable(R.drawable.ic_small_lower), (Drawable) null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XTextView, android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setListViewTheme();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XTextView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setListViewTheme();
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mAutoCloseTask);
        }
    }

    private void setListViewTheme() {
        ListView listView = this.mListView;
        if (listView != null) {
            listView.setBackground(getContext().getDrawable(R.drawable.x_dropdown_menu_list_bg));
        }
    }

    /* loaded from: classes2.dex */
    public static class MyAdapter extends ArrayAdapter<String> {
        private int mSelectedPosition;

        public MyAdapter(Context context, int resource, int tvResource) {
            super(context, resource, tvResource);
            this.mSelectedPosition = -1;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            view.findViewById(R.id.x_drop_down_tv).setSelected(this.mSelectedPosition == position);
            return view;
        }

        public void setSelectedPosition(int position) {
            this.mSelectedPosition = position;
        }
    }
}
