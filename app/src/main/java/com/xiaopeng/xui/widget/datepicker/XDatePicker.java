package com.xiaopeng.xui.widget.datepicker;

import android.content.Context;
import android.content.res.Configuration;
import android.icu.util.Calendar;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.utils.XLogUtils;
import com.xiaopeng.xui.widget.XFrameLayout;
import java.util.Locale;

/* loaded from: classes2.dex */
public class XDatePicker extends XFrameLayout {
    private static final String LOG_TAG = "XDatePicker";
    private final XDatePickerDelegate mDelegate;

    /* loaded from: classes2.dex */
    public interface OnDateChangedListener {
        void onDateChanged(XDatePicker xDatePicker, int i, int i2, int i3);
    }

    /* loaded from: classes2.dex */
    interface XDatePickerDelegate {
        void autofill(AutofillValue autofillValue);

        boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent);

        AutofillValue getAutofillValue();

        int getDayOfMonth();

        int getFirstDayOfWeek();

        Calendar getMaxDate();

        Calendar getMinDate();

        int getMonth();

        boolean getSpinnersShown();

        int getYear();

        void init(int i, int i2, int i3, OnDateChangedListener onDateChangedListener);

        boolean isEnabled();

        void onConfigurationChanged(Configuration configuration);

        void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent);

        void onRestoreInstanceState(Parcelable parcelable);

        Parcelable onSaveInstanceState(Parcelable parcelable);

        void setAutoFillChangeListener(OnDateChangedListener onDateChangedListener);

        void setEnabled(boolean z);

        void setFirstDayOfWeek(int i);

        void setMaxDate(long j);

        void setMinDate(long j);

        void setOnDateChangedListener(OnDateChangedListener onDateChangedListener);

        void setSpinnersShown(boolean z);

        void updateDate(int i, int i2, int i3);
    }

    public XDatePicker(Context context) {
        this(context, null);
    }

    public XDatePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XDatePicker(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.XDatePicker);
    }

    public XDatePicker(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        if (getImportantForAutofill() == 0) {
            setImportantForAutofill(1);
        }
        XDatePickerDelegate createSpinnerUIDelegate = createSpinnerUIDelegate(context, attributeSet, i, i2);
        this.mDelegate = createSpinnerUIDelegate;
        createSpinnerUIDelegate.setAutoFillChangeListener(new OnDateChangedListener() { // from class: com.xiaopeng.xui.widget.datepicker.XDatePicker.1
            @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.OnDateChangedListener
            public void onDateChanged(XDatePicker xDatePicker, int i3, int i4, int i5) {
                AutofillManager autofillManager = (AutofillManager) XDatePicker.this.getContext().getSystemService(AutofillManager.class);
                if (autofillManager != null) {
                    autofillManager.notifyValueChanged(XDatePicker.this);
                }
            }
        });
    }

    private XDatePickerDelegate createSpinnerUIDelegate(Context context, AttributeSet attributeSet, int i, int i2) {
        return new XDatePickerSpinnerDelegate(this, context, attributeSet, i, i2);
    }

    public void init(int i, int i2, int i3, OnDateChangedListener onDateChangedListener) {
        this.mDelegate.init(i, i2, i3, onDateChangedListener);
    }

    public void updateDate(int i, int i2, int i3) {
        this.mDelegate.updateDate(i, i2, i3);
    }

    public void setOnDateChangedListener(OnDateChangedListener onDateChangedListener) {
        this.mDelegate.setOnDateChangedListener(onDateChangedListener);
    }

    public int getYear() {
        return this.mDelegate.getYear();
    }

    public int getMonth() {
        return this.mDelegate.getMonth();
    }

    public int getDayOfMonth() {
        return this.mDelegate.getDayOfMonth();
    }

    public long getMinDate() {
        return this.mDelegate.getMinDate().getTimeInMillis();
    }

    public void setMinDate(long j) {
        this.mDelegate.setMinDate(j);
    }

    public long getMaxDate() {
        return this.mDelegate.getMaxDate().getTimeInMillis();
    }

    public void setMaxDate(long j) {
        this.mDelegate.setMaxDate(j);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        if (this.mDelegate.isEnabled() == z) {
            return;
        }
        super.setEnabled(z);
        this.mDelegate.setEnabled(z);
    }

    @Override // android.view.View
    public boolean isEnabled() {
        return this.mDelegate.isEnabled();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return XDatePicker.class.getName();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDelegate.onConfigurationChanged(configuration);
    }

    /* loaded from: classes2.dex */
    static abstract class AbstractXDatePickerDelegate implements XDatePickerDelegate {
        protected OnDateChangedListener mAutoFillChangeListener;
        private long mAutofilledValue;
        protected Context mContext;
        protected Calendar mCurrentDate;
        protected Locale mCurrentLocale;
        protected OnDateChangedListener mOnDateChangedListener;
        protected XDatePicker mXDelegator;

        protected void onLocaleChanged(Locale locale) {
        }

        public AbstractXDatePickerDelegate(XDatePicker xDatePicker, Context context) {
            this.mXDelegator = xDatePicker;
            this.mContext = context;
            setCurrentLocale(Locale.getDefault());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void setCurrentLocale(Locale locale) {
            if (locale.equals(this.mCurrentLocale)) {
                return;
            }
            this.mCurrentLocale = locale;
            onLocaleChanged(locale);
        }

        @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
        public void setOnDateChangedListener(OnDateChangedListener onDateChangedListener) {
            this.mOnDateChangedListener = onDateChangedListener;
        }

        @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
        public void setAutoFillChangeListener(OnDateChangedListener onDateChangedListener) {
            this.mAutoFillChangeListener = onDateChangedListener;
        }

        @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
        public final void autofill(AutofillValue autofillValue) {
            if (autofillValue == null || !autofillValue.isDate()) {
                XLogUtils.w(XDatePicker.LOG_TAG, autofillValue + " could not be autofilled into " + this);
                return;
            }
            long dateValue = autofillValue.getDateValue();
            Calendar calendar = Calendar.getInstance(this.mCurrentLocale);
            calendar.setTimeInMillis(dateValue);
            updateDate(calendar.get(1), calendar.get(2), calendar.get(5));
            this.mAutofilledValue = dateValue;
        }

        @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
        public final AutofillValue getAutofillValue() {
            long j = this.mAutofilledValue;
            if (j == 0) {
                j = this.mCurrentDate.getTimeInMillis();
            }
            return AutofillValue.forDate(j);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void resetAutofilledValue() {
            this.mAutofilledValue = 0L;
        }

        @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
        public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.getText().add(getFormattedCurrentDate());
        }

        protected String getFormattedCurrentDate() {
            return DateUtils.formatDateTime(this.mContext, this.mCurrentDate.getTimeInMillis(), 22);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* loaded from: classes2.dex */
        public static class SavedState extends View.BaseSavedState {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: com.xiaopeng.xui.widget.datepicker.XDatePicker.AbstractXDatePickerDelegate.SavedState.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SavedState createFromParcel(Parcel parcel) {
                    return new SavedState(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SavedState[] newArray(int i) {
                    return new SavedState[i];
                }
            };
            private final int mCurrentView;
            private final int mListPosition;
            private final int mListPositionOffset;
            private final long mMaxDate;
            private final long mMinDate;
            private final int mSelectedDay;
            private final int mSelectedMonth;
            private final int mSelectedYear;

            public SavedState(Parcelable parcelable, int i, int i2, int i3, long j, long j2) {
                this(parcelable, i, i2, i3, j, j2, 0, 0, 0);
            }

            public SavedState(Parcelable parcelable, int i, int i2, int i3, long j, long j2, int i4, int i5, int i6) {
                super(parcelable);
                this.mSelectedYear = i;
                this.mSelectedMonth = i2;
                this.mSelectedDay = i3;
                this.mMinDate = j;
                this.mMaxDate = j2;
                this.mCurrentView = i4;
                this.mListPosition = i5;
                this.mListPositionOffset = i6;
            }

            private SavedState(Parcel parcel) {
                super(parcel);
                this.mSelectedYear = parcel.readInt();
                this.mSelectedMonth = parcel.readInt();
                this.mSelectedDay = parcel.readInt();
                this.mMinDate = parcel.readLong();
                this.mMaxDate = parcel.readLong();
                this.mCurrentView = parcel.readInt();
                this.mListPosition = parcel.readInt();
                this.mListPositionOffset = parcel.readInt();
            }

            @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                super.writeToParcel(parcel, i);
                parcel.writeInt(this.mSelectedYear);
                parcel.writeInt(this.mSelectedMonth);
                parcel.writeInt(this.mSelectedDay);
                parcel.writeLong(this.mMinDate);
                parcel.writeLong(this.mMaxDate);
                parcel.writeInt(this.mCurrentView);
                parcel.writeInt(this.mListPosition);
                parcel.writeInt(this.mListPositionOffset);
            }

            public int getSelectedDay() {
                return this.mSelectedDay;
            }

            public int getSelectedMonth() {
                return this.mSelectedMonth;
            }

            public int getSelectedYear() {
                return this.mSelectedYear;
            }

            public long getMinDate() {
                return this.mMinDate;
            }

            public long getMaxDate() {
                return this.mMaxDate;
            }

            public int getCurrentView() {
                return this.mCurrentView;
            }

            public int getListPosition() {
                return this.mListPosition;
            }

            public int getListPositionOffset() {
                return this.mListPositionOffset;
            }
        }
    }
}
