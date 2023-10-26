package com.xiaopeng.xui.widget.datepicker;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.icu.util.Calendar;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.widget.XNumberPicker;
import com.xiaopeng.xui.widget.datepicker.XDatePicker;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;

/* loaded from: classes2.dex */
public class XDatePickerSpinnerDelegate extends XDatePicker.AbstractXDatePickerDelegate {
    private static final String DATE_FORMAT = "MM/dd/yyyy";
    private static final boolean DEFAULT_ENABLED_STATE = true;
    private static final int DEFAULT_END_YEAR = 2100;
    private static final boolean DEFAULT_SPINNERS_SHOWN = true;
    private static final int DEFAULT_START_YEAR = 1900;
    private final DateFormat mDateFormat;
    private XNumberPicker mDaySpinner;
    private boolean mIsEnabled;
    private Calendar mMaxDate;
    private Calendar mMinDate;
    private XNumberPicker mMonthSpinner;
    private int mNumberOfMonths;
    private String[] mShortMonths;
    private final LinearLayout mSpinners;
    private Calendar mTempDate;
    private XNumberPicker mYearSpinner;

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public int getFirstDayOfWeek() {
        return 0;
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public void setFirstDayOfWeek(int i) {
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.AbstractXDatePickerDelegate, com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public /* bridge */ /* synthetic */ void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.AbstractXDatePickerDelegate, com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public /* bridge */ /* synthetic */ void setAutoFillChangeListener(XDatePicker.OnDateChangedListener onDateChangedListener) {
        super.setAutoFillChangeListener(onDateChangedListener);
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.AbstractXDatePickerDelegate, com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public /* bridge */ /* synthetic */ void setOnDateChangedListener(XDatePicker.OnDateChangedListener onDateChangedListener) {
        super.setOnDateChangedListener(onDateChangedListener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public XDatePickerSpinnerDelegate(XDatePicker xDatePicker, Context context, AttributeSet attributeSet, int i, int i2) {
        super(xDatePicker, context);
        this.mDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        this.mIsEnabled = true;
        this.mXDelegator = xDatePicker;
        this.mContext = context;
        setCurrentLocale(Locale.getDefault());
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.XDatePicker, i, i2);
        boolean z = obtainStyledAttributes.getBoolean(R.styleable.XDatePicker_dp_spinnersShown, true);
        int i3 = obtainStyledAttributes.getInt(R.styleable.XDatePicker_dp_startYear, DEFAULT_START_YEAR);
        int i4 = obtainStyledAttributes.getInt(R.styleable.XDatePicker_dp_endYear, DEFAULT_END_YEAR);
        String string = obtainStyledAttributes.getString(R.styleable.XDatePicker_dp_minDate);
        String string2 = obtainStyledAttributes.getString(R.styleable.XDatePicker_dp_maxDate);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.XDatePicker_dp_xDatePickerLayout, R.layout.x_date_picker_layout);
        obtainStyledAttributes.recycle();
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(resourceId, (ViewGroup) this.mXDelegator, true).setSaveFromParentEnabled(false);
        XNumberPicker.OnValueChangeListener onValueChangeListener = new XNumberPicker.OnValueChangeListener() { // from class: com.xiaopeng.xui.widget.datepicker.-$$Lambda$XDatePickerSpinnerDelegate$4vNSUARTO8kyMe3fYUDgNOLfJ7k
            @Override // com.xiaopeng.xui.widget.XNumberPicker.OnValueChangeListener
            public final void onValueChange(XNumberPicker xNumberPicker, int i5, int i6) {
                XDatePickerSpinnerDelegate.this.lambda$new$0$XDatePickerSpinnerDelegate(xNumberPicker, i5, i6);
            }
        };
        this.mSpinners = (LinearLayout) this.mXDelegator.findViewById(R.id.pickers);
        reorderSpinners(new XNumberPicker[]{(XNumberPicker) this.mXDelegator.findViewById(R.id.picker1), (XNumberPicker) this.mXDelegator.findViewById(R.id.picker2), (XNumberPicker) this.mXDelegator.findViewById(R.id.picker3)});
        this.mDaySpinner.setFormatter(XNumberPicker.getTwoDigitFormatter());
        this.mDaySpinner.setOnLongPressUpdateInterval(100L);
        this.mDaySpinner.setOnValueChangedListener(onValueChangeListener);
        this.mMonthSpinner.setMinValue(0);
        this.mMonthSpinner.setMaxValue(this.mNumberOfMonths - 1);
        this.mMonthSpinner.setDisplayedValues(this.mShortMonths);
        this.mMonthSpinner.setOnLongPressUpdateInterval(200L);
        this.mMonthSpinner.setOnValueChangedListener(onValueChangeListener);
        this.mYearSpinner.setOnLongPressUpdateInterval(100L);
        this.mYearSpinner.setOnValueChangedListener(onValueChangeListener);
        setSpinnersShown(z);
        this.mTempDate.clear();
        if (!TextUtils.isEmpty(string)) {
            if (!parseDate(string, this.mTempDate)) {
                this.mTempDate.set(i3, 0, 1);
            }
        } else {
            this.mTempDate.set(i3, 0, 1);
        }
        setMinDate(this.mTempDate.getTimeInMillis());
        this.mTempDate.clear();
        if (!TextUtils.isEmpty(string2)) {
            if (!parseDate(string2, this.mTempDate)) {
                this.mTempDate.set(i4, 11, 31);
            }
        } else {
            this.mTempDate.set(i4, 11, 31);
        }
        setMaxDate(this.mTempDate.getTimeInMillis());
        this.mCurrentDate.setTimeInMillis(System.currentTimeMillis());
        init(this.mCurrentDate.get(1), this.mCurrentDate.get(2), this.mCurrentDate.get(5), null);
        if (this.mXDelegator.getImportantForAccessibility() == 0) {
            this.mXDelegator.setImportantForAccessibility(1);
        }
    }

    public /* synthetic */ void lambda$new$0$XDatePickerSpinnerDelegate(XNumberPicker xNumberPicker, int i, int i2) {
        this.mTempDate.setTimeInMillis(this.mCurrentDate.getTimeInMillis());
        if (xNumberPicker == this.mDaySpinner) {
            int actualMaximum = this.mTempDate.getActualMaximum(5);
            if (i == actualMaximum && i2 == 1) {
                this.mTempDate.add(5, 1);
            } else if (i == 1 && i2 == actualMaximum) {
                this.mTempDate.add(5, -1);
            } else {
                this.mTempDate.add(5, i2 - i);
            }
        } else if (xNumberPicker == this.mMonthSpinner) {
            if (i == 11 && i2 == 0) {
                this.mTempDate.add(2, 1);
            } else if (i == 0 && i2 == 11) {
                this.mTempDate.add(2, -1);
            } else {
                this.mTempDate.add(2, i2 - i);
            }
        } else if (xNumberPicker == this.mYearSpinner) {
            this.mTempDate.set(1, i2);
        } else {
            throw new IllegalArgumentException();
        }
        setDate(this.mTempDate.get(1), this.mTempDate.get(2), this.mTempDate.get(5));
        updateSpinners();
        notifyDateChanged();
    }

    private void notifyDateChanged() {
        this.mXDelegator.sendAccessibilityEvent(4);
        if (this.mOnDateChangedListener != null) {
            this.mOnDateChangedListener.onDateChanged(this.mXDelegator, getYear(), getMonth(), getDayOfMonth());
        }
        if (this.mAutoFillChangeListener != null) {
            this.mAutoFillChangeListener.onDateChanged(this.mXDelegator, getYear(), getMonth(), getDayOfMonth());
        }
    }

    private void reorderSpinners(XNumberPicker[] xNumberPickerArr) {
        char[] dateFormatOrder = getDateFormatOrder(android.text.format.DateFormat.getBestDateTimePattern(Locale.getDefault(), "yyyyMMMdd"));
        for (int i = 0; i < dateFormatOrder.length; i++) {
            char c = dateFormatOrder[i];
            if (c == 'M') {
                this.mMonthSpinner = xNumberPickerArr[i];
            } else if (c == 'd') {
                this.mDaySpinner = xNumberPickerArr[i];
            } else if (c == 'y') {
                this.mYearSpinner = xNumberPickerArr[i];
            } else {
                throw new IllegalArgumentException(Arrays.toString(dateFormatOrder));
            }
        }
    }

    public static char[] getDateFormatOrder(String str) {
        char[] cArr = new char[3];
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        for (int i2 = 0; i2 < str.length(); i2++) {
            char charAt = str.charAt(i2);
            if (charAt == 'd' || charAt == 'L' || charAt == 'M' || charAt == 'y') {
                if (charAt == 'd' && !z) {
                    cArr[i] = 'd';
                    i++;
                    z = true;
                } else if ((charAt == 'L' || charAt == 'M') && !z2) {
                    cArr[i] = 'M';
                    i++;
                    z2 = true;
                } else if (charAt == 'y' && !z3) {
                    cArr[i] = 'y';
                    i++;
                    z3 = true;
                }
            }
        }
        return cArr;
    }

    private void setDate(int i, int i2, int i3) {
        this.mCurrentDate.set(i, i2, i3);
        resetAutofilledValue();
        if (this.mCurrentDate.before(this.mMinDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMinDate.getTimeInMillis());
        } else if (this.mCurrentDate.after(this.mMaxDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMaxDate.getTimeInMillis());
        }
    }

    private boolean parseDate(String str, Calendar calendar) {
        try {
            calendar.setTime(this.mDateFormat.parse(str));
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void updateSpinners() {
        if (this.mCurrentDate.equals(this.mMinDate)) {
            this.mDaySpinner.setMinValue(this.mCurrentDate.get(5));
            this.mDaySpinner.setMaxValue(this.mCurrentDate.getActualMaximum(5));
            this.mDaySpinner.setWrapSelectorWheel(false);
            this.mMonthSpinner.setDisplayedValues(null);
            this.mMonthSpinner.setMinValue(this.mCurrentDate.get(2));
            this.mMonthSpinner.setMaxValue(this.mCurrentDate.getActualMaximum(2));
            this.mMonthSpinner.setWrapSelectorWheel(false);
        } else if (this.mCurrentDate.equals(this.mMaxDate)) {
            this.mDaySpinner.setMinValue(this.mCurrentDate.getActualMinimum(5));
            this.mDaySpinner.setMaxValue(this.mCurrentDate.get(5));
            this.mDaySpinner.setWrapSelectorWheel(false);
            this.mMonthSpinner.setDisplayedValues(null);
            this.mMonthSpinner.setMinValue(this.mCurrentDate.getActualMinimum(2));
            this.mMonthSpinner.setMaxValue(this.mCurrentDate.get(2));
            this.mMonthSpinner.setWrapSelectorWheel(false);
        } else {
            this.mDaySpinner.setMinValue(1);
            this.mDaySpinner.setMaxValue(this.mCurrentDate.getActualMaximum(5));
            this.mDaySpinner.setWrapSelectorWheel(true);
            this.mMonthSpinner.setDisplayedValues(null);
            this.mMonthSpinner.setMinValue(0);
            this.mMonthSpinner.setMaxValue(11);
            this.mMonthSpinner.setWrapSelectorWheel(true);
        }
        String[] strArr = new String[this.mCurrentDate.getActualMaximum(5)];
        int i = 0;
        while (i < this.mCurrentDate.getActualMaximum(5)) {
            int i2 = i + 1;
            strArr[i] = this.mContext.getResources().getString(R.string.x_date_picker_day, Integer.valueOf(i2));
            i = i2;
        }
        this.mDaySpinner.setDisplayedValues(strArr);
        this.mMonthSpinner.setDisplayedValues((String[]) Arrays.copyOfRange(this.mShortMonths, this.mMonthSpinner.getMinValue(), this.mMonthSpinner.getMaxValue() + 1));
        this.mYearSpinner.setMinValue(this.mMinDate.get(1));
        this.mYearSpinner.setMaxValue(this.mMaxDate.get(1));
        String[] strArr2 = new String[201];
        for (int i3 = 0; i3 < 201; i3++) {
            strArr2[i3] = this.mContext.getResources().getString(R.string.x_date_picker_year, Integer.valueOf(this.mYearSpinner.getMinValue() + i3));
        }
        this.mYearSpinner.setDisplayedValues(strArr2);
        this.mYearSpinner.setWrapSelectorWheel(false);
        this.mYearSpinner.setValue(this.mCurrentDate.get(1));
        this.mMonthSpinner.setValue(this.mCurrentDate.get(2));
        this.mDaySpinner.setValue(this.mCurrentDate.get(5));
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public void init(int i, int i2, int i3, XDatePicker.OnDateChangedListener onDateChangedListener) {
        setDate(i, i2, i3);
        updateSpinners();
        this.mOnDateChangedListener = onDateChangedListener;
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public void updateDate(int i, int i2, int i3) {
        if (isNewDate(i, i2, i3)) {
            setDate(i, i2, i3);
            updateSpinners();
            notifyDateChanged();
        }
    }

    private boolean isNewDate(int i, int i2, int i3) {
        return (this.mCurrentDate.get(1) == i && this.mCurrentDate.get(2) == i2 && this.mCurrentDate.get(5) == i3) ? false : true;
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public int getYear() {
        return this.mCurrentDate.get(1);
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public int getMonth() {
        return this.mCurrentDate.get(2);
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public int getDayOfMonth() {
        return this.mCurrentDate.get(5);
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public void setMinDate(long j) {
        this.mTempDate.setTimeInMillis(j);
        if (this.mTempDate.get(1) == this.mMinDate.get(1) && this.mTempDate.get(6) == this.mMinDate.get(6)) {
            return;
        }
        this.mMinDate.setTimeInMillis(j);
        if (this.mCurrentDate.before(this.mMinDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMinDate.getTimeInMillis());
        }
        updateSpinners();
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public Calendar getMinDate() {
        Calendar calendar = this.mMinDate;
        if (calendar != null) {
            return calendar;
        }
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(DEFAULT_START_YEAR, 1, 1);
        return calendar2;
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public void setMaxDate(long j) {
        this.mTempDate.setTimeInMillis(j);
        if (this.mTempDate.get(1) == this.mMaxDate.get(1) && this.mTempDate.get(6) == this.mMaxDate.get(6)) {
            return;
        }
        this.mMaxDate.setTimeInMillis(j);
        if (this.mCurrentDate.after(this.mMaxDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMaxDate.getTimeInMillis());
        }
        updateSpinners();
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public Calendar getMaxDate() {
        if (this.mMaxDate != null) {
            return this.mMinDate;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(DEFAULT_END_YEAR, 11, 30);
        return calendar;
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public void setEnabled(boolean z) {
        this.mDaySpinner.setEnabled(z);
        this.mMonthSpinner.setEnabled(z);
        this.mYearSpinner.setEnabled(z);
        this.mIsEnabled = z;
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public void setSpinnersShown(boolean z) {
        this.mSpinners.setVisibility(z ? 0 : 8);
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public boolean getSpinnersShown() {
        return this.mSpinners.isShown();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.AbstractXDatePickerDelegate
    public void setCurrentLocale(Locale locale) {
        super.setCurrentLocale(locale);
        this.mTempDate = getCalendarForLocale(this.mTempDate, locale);
        this.mMinDate = getCalendarForLocale(this.mMinDate, locale);
        this.mMaxDate = getCalendarForLocale(this.mMaxDate, locale);
        this.mCurrentDate = getCalendarForLocale(this.mCurrentDate, locale);
        this.mNumberOfMonths = this.mTempDate.getActualMaximum(2) + 1;
        this.mShortMonths = new DateFormatSymbols().getShortMonths();
    }

    private Calendar getCalendarForLocale(Calendar calendar, Locale locale) {
        if (calendar == null) {
            return Calendar.getInstance(locale);
        }
        long timeInMillis = calendar.getTimeInMillis();
        Calendar calendar2 = Calendar.getInstance(locale);
        calendar2.setTimeInMillis(timeInMillis);
        return calendar2;
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public void onConfigurationChanged(Configuration configuration) {
        setCurrentLocale(configuration.getLocales().get(0));
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public Parcelable onSaveInstanceState(Parcelable parcelable) {
        return new XDatePicker.AbstractXDatePickerDelegate.SavedState(parcelable, getYear(), getMonth(), getDayOfMonth(), getMinDate().getTimeInMillis(), getMaxDate().getTimeInMillis());
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof XDatePicker.AbstractXDatePickerDelegate.SavedState) {
            XDatePicker.AbstractXDatePickerDelegate.SavedState savedState = (XDatePicker.AbstractXDatePickerDelegate.SavedState) parcelable;
            setDate(savedState.getSelectedYear(), savedState.getSelectedMonth(), savedState.getSelectedDay());
            updateSpinners();
        }
    }

    @Override // com.xiaopeng.xui.widget.datepicker.XDatePicker.XDatePickerDelegate
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }
}
